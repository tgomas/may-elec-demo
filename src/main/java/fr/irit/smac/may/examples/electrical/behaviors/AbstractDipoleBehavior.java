package fr.irit.smac.may.examples.electrical.behaviors;

import fr.irit.smac.may.examples.electrical.knowledges.DipoleKnowledge;
import fr.irit.smac.may.examples.electrical.knowledges.DipoleKnowledge.Terminal;
import fr.irit.smac.may.examples.electrical.messages.PotentialMsg;
import fr.irit.smac.may.examples.mas.behavior.AbstractBehavior;
import fr.irit.smac.may.examples.mas.messaging.interfaces.Message;

public abstract class AbstractDipoleBehavior extends AbstractBehavior {

	protected DipoleKnowledge knowledge;

	public AbstractDipoleBehavior() {
		super();
	}

	public AbstractDipoleBehavior(DipoleKnowledge knowledge) {
		this.knowledge = knowledge;
	}

	@Override
	protected void processMsg(Message m) {
		if (m instanceof PotentialMsg) {
			PotentialMsg pm = (PotentialMsg) m;
			Terminal terminal = knowledge.getTerminal(pm.getSender());
			if (terminal != null) {
				knowledge.setTerminalV(terminal, pm.getValue());
			}
		}
	}
}