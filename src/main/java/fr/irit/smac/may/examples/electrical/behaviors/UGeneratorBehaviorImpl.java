package fr.irit.smac.may.examples.electrical.behaviors;

import fr.irit.smac.may.examples.electrical.knowledges.DipoleKnowledgeImpl;
import fr.irit.smac.may.examples.electrical.knowledges.DipoleKnowledge.Terminal;
import fr.irit.smac.may.examples.electrical.messages.PotentialDirectionRequest;
import fr.irit.smac.may.examples.mas.messaging.impl.BasicMessageImpl;
import fr.irit.smac.util.avt.Feedback;

public class UGeneratorBehaviorImpl extends AbstractDipoleBehavior {
	public UGeneratorBehaviorImpl(DipoleKnowledgeImpl knowledge) {
		super(knowledge);
	}

	@Override
	protected void decideAndAct() {
		// monitor agent
		requires().monitor().publish("tension", knowledge.getU());
		if (knowledge.getV(Terminal.FIRST) != null) {
			requires().monitor().publish("firstPotential",
					knowledge.getV(Terminal.FIRST));
		}
		if (knowledge.getV(Terminal.SECOND) != null) {
			requires().monitor().publish("secondPotential",
					knowledge.getV(Terminal.SECOND));
		}
		requires().logger().debug(knowledge.toString());

		if (knowledge.getV(Terminal.FIRST) == null
				|| knowledge.getV(Terminal.SECOND) == null) {
			// send message to terminals (in order to be added in their
			// neighborhood)
			BasicMessageImpl msg = new BasicMessageImpl(knowledge.getId());
			requires().messaging().send(msg, knowledge.getId(Terminal.FIRST));
			requires().messaging().send(msg, knowledge.getId(Terminal.SECOND));
		} else {
			// compare actual voltage with generator tension
			Double actualVoltage = knowledge.getV(Terminal.SECOND)
					- knowledge.getV(Terminal.FIRST);
			Double error = knowledge.getU() - actualVoltage;
			if (error > 0) {
				// ask to increase the actualVoltage
				requestUChange(Terminal.FIRST, Terminal.SECOND);
			} else if (error < 0) {
				// ask to decrease the actualVoltage
				requestUChange(Terminal.SECOND, Terminal.FIRST);
			}
		}
	}

	private void requestUChange(Terminal lowerReceiver, Terminal greaterReceiver) {
		BasicMessageImpl greaterMsg = new PotentialDirectionRequest(knowledge.getId(),
				Feedback.GREATER, 100d, knowledge.getV(greaterReceiver));
		BasicMessageImpl lowerMsg = new PotentialDirectionRequest(knowledge.getId(),
				Feedback.LOWER, 100d, knowledge.getV(lowerReceiver));
		requires().messaging().send(greaterMsg,
				knowledge.getId(greaterReceiver));
		requires().messaging().send(lowerMsg, knowledge.getId(lowerReceiver));
		requires().logger().debug(
				"Sent msg: UP: " + knowledge.getId(greaterReceiver)
						+ "  DOWN: " + knowledge.getId(lowerReceiver));
	}
}
