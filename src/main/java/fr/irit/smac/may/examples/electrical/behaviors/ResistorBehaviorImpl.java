package fr.irit.smac.may.examples.electrical.behaviors;

import fr.irit.smac.may.examples.electrical.knowledges.DipoleKnowledge.Terminal;
import fr.irit.smac.may.examples.electrical.knowledges.ResistorKnowledgeImpl;
import fr.irit.smac.may.examples.electrical.messages.DirectionRequest;
import fr.irit.smac.may.examples.electrical.messages.IntensityDirectionRequest;
import fr.irit.smac.may.examples.electrical.messages.IntensityMsg;
import fr.irit.smac.may.examples.electrical.messages.PotentialDirectionRequest;
import fr.irit.smac.may.examples.mas.messaging.impl.BasicMessageImpl;
import fr.irit.smac.may.examples.mas.messaging.interfaces.Message;
import fr.irit.smac.util.avt.Feedback;

public class ResistorBehaviorImpl extends AbstractDipoleBehavior {

	Double worstIntensityCriticality = 0d;
	Feedback intensityDirection;

	public ResistorBehaviorImpl(ResistorKnowledgeImpl knowledge) {
		super(knowledge);
	}

	@Override
	protected void processMsg(Message m) {
		super.processMsg(m);
		if (m instanceof IntensityDirectionRequest) {
			IntensityDirectionRequest idr = (IntensityDirectionRequest) m;
			Feedback direction = idr.direction;
			Double knownValue = idr.knownValue;
			if (idr.getSender().equals(knowledge.getId(Terminal.SECOND))) {
				direction = DirectionRequest.opposite(direction);
				knownValue = -knownValue;
			}

			if (knowledge.getI().equals(knownValue)) {
				if (worstIntensityCriticality < idr.criticality) {
					worstIntensityCriticality = idr.criticality;
					intensityDirection = direction;
					requires().logger().debug("received I" + idr.direction + " from " + idr.getSender());
				}
			}
		}
	}

	@Override
	protected void decideAndAct() {
		if (knowledge != null) {
			// monitor values
			if (knowledge.getR() != null) {
				requires().monitor().publish("resistor", knowledge.getR());
			}
			if (knowledge.getI() != null) {
				requires().monitor().publish("intensity", knowledge.getI());
			}
			if (knowledge.getU() != null) {
				requires().monitor().publish("tension", knowledge.getU());
			}
			requires().logger().debug(knowledge.toString());

			// send message to terminals (in order to be added in their
			// neighborhood)
			if (knowledge.getV(Terminal.FIRST) == null) {
				BasicMessageImpl msg = new BasicMessageImpl(knowledge.getId());
				requires().messaging().send(msg, knowledge.getId(Terminal.FIRST));
			}
			if (knowledge.getV(Terminal.SECOND) == null) {
				BasicMessageImpl msg = new BasicMessageImpl(knowledge.getId());
				requires().messaging().send(msg, knowledge.getId(Terminal.SECOND));
			}
			// communicate the intensity
			final Double intensity = knowledge.getI();
			if (intensity != null) {
				IntensityMsg firstMsg = new IntensityMsg(knowledge.getId(), intensity);
				requires().messaging().send(firstMsg, knowledge.getId(Terminal.FIRST));
				IntensityMsg secondMsg = new IntensityMsg(knowledge.getId(), -intensity);
				requires().messaging().send(secondMsg, knowledge.getId(Terminal.SECOND));
			}

			// request potential updates
			if (intensityDirection != null) {
				PotentialDirectionRequest firstMsg = new PotentialDirectionRequest(knowledge.getId(),
						DirectionRequest.opposite(intensityDirection), worstIntensityCriticality,
						knowledge.getV(Terminal.FIRST));
				requires().messaging().send(firstMsg, knowledge.getId(Terminal.FIRST));
				requires().logger().debug("send V" + DirectionRequest.opposite(intensityDirection) + " to "
						+ knowledge.getId(Terminal.FIRST));
				PotentialDirectionRequest secondMsg = new PotentialDirectionRequest(knowledge.getId(),
						intensityDirection, worstIntensityCriticality, knowledge.getV(Terminal.SECOND));
				requires().messaging().send(secondMsg, knowledge.getId(Terminal.SECOND));
				requires().logger().debug("send V" + intensityDirection + " to " + knowledge.getId(Terminal.SECOND));
				// clear request
				worstIntensityCriticality = 0d;
				intensityDirection = null;
			}
		}
	}
}
