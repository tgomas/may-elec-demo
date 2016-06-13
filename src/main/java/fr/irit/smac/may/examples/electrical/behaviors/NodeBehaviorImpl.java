package fr.irit.smac.may.examples.electrical.behaviors;

import fr.irit.smac.may.examples.electrical.knowledges.NodeKnowledgeImpl;
import fr.irit.smac.may.examples.electrical.messages.IntensityMsg;
import fr.irit.smac.may.examples.electrical.messages.PotentialDirectionRequest;
import fr.irit.smac.may.examples.electrical.messages.PotentialMsg;
import fr.irit.smac.may.examples.mas.behavior.Behavior;
import fr.irit.smac.may.examples.mas.messaging.interfaces.Message;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.util.avt.Feedback;

public class NodeBehaviorImpl extends Behavior<Message> {

	NodeKnowledgeImpl knowledge;
	private Double previousISumChange = 0d;
	private boolean receivedPdr;

	public NodeBehaviorImpl(NodeKnowledgeImpl knowledge) {
		super();
		this.knowledge = knowledge;
	}

	@Override
	protected Do make_perceive() {
		return new Do() {

			@Override
			public void doIt() {
				requires().messaging().getAllMessages().forEach(m -> processMsg(m));
				requires().monitor().publish("potential", knowledge.potential.getValue());
				requires().logger().debug("potential: " + knowledge.potential.getValue());
				if (knowledge.intensities.get("R1") != null) {
					requires().monitor().publish("R1", knowledge.intensities.get("R1"));
				}
				if (knowledge.intensities.get("R2") != null) {
					requires().monitor().publish("R2", knowledge.intensities.get("R2"));
				}
				if (knowledge.intensities.get("R3") != null) {
					requires().monitor().publish("R3", knowledge.intensities.get("R3"));
				}
			}
		};
	}

	private void processMsg(Message m) {
		knowledge.neighbors.add(m.getSender());
		if (m instanceof PotentialDirectionRequest) {
			PotentialDirectionRequest pdr = (PotentialDirectionRequest) m;
			receivedPdr = true;
			if (pdr.knownValue.equals(knowledge.potential.getValue())
					&& knowledge.worstPotentialCriticality < pdr.criticality) {
				requires().logger().debug("received " + pdr);
				knowledge.worstPotentialCriticality = pdr.criticality;
				knowledge.potentialDirection = pdr.direction;
			}
		} else if (m instanceof IntensityMsg) {
			IntensityMsg im = (IntensityMsg) m;
			knowledge.intensities.put(im.getSender(), im.getValue());
		}
	}

	@Override
	protected Do make_decideAndAct() {
		return this::decideAndAct;
	}

	private void decideAndAct() {
		// kirchhoff law
		Double intensitiesSum = 0d;
		for (Double intensity : knowledge.intensities.values()) {
			intensitiesSum += intensity;
		}
		if (!receivedPdr && intensitiesSum != 0) {
			if (knowledge.potentialDirection == null && !previousISumChange.equals(intensitiesSum)) {
				requires().logger().debug("Isum: " + intensitiesSum + " previousChange: " + previousISumChange);
				previousISumChange = intensitiesSum;
				if (intensitiesSum > 0) {
					knowledge.potential.adjustValue(Feedback.GREATER);
				} else if (intensitiesSum < 0) {
					knowledge.potential.adjustValue(Feedback.LOWER);
				}
			}
		}

		// potential tuning
		if (knowledge.potentialDirection != null) {
			requires().logger().debug(
					"potential=" + knowledge.potential.getValue() + "  adjust it " + knowledge.potentialDirection);
			knowledge.potential.adjustValue(knowledge.potentialDirection);
		}
		sendPotential();

		// knowledge cleaning
		knowledge.potentialDirection = null;
		knowledge.worstPotentialCriticality = 0d;
		receivedPdr = false;
	}

	private void sendPotential() {
		requires().logger().debug("send msg: potential=" + knowledge.potential.getValue());
		PotentialMsg message = new PotentialMsg(knowledge.getId(), knowledge.potential.getValue());
		for (String receiver : knowledge.neighbors) {
			requires().messaging().send(message, receiver);
		}
	}
}
