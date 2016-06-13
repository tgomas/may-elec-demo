package fr.irit.smac.may.examples.mas.behavior;

import fr.irit.smac.may.examples.mas.messaging.interfaces.Message;
import fr.irit.smac.may.lib.interfaces.Do;

public abstract class AbstractBehavior extends Behavior<Message> {

	public AbstractBehavior() {
		super();
	}

	@Override
	protected Do make_perceive() {
		return this::perceive;
	}

	/**
	 * Call processMsg on all received messages.
	 */
	protected void perceive() {
		requires().messaging().getAllMessages().forEach(m -> processMsg(m));
	}

	/**
	 * Process a message.
	 * 
	 * @param msg
	 *            the message to process.
	 */
	protected abstract void processMsg(Message msg);

	@Override
	protected Do make_decideAndAct() {
		return this::decideAndAct;
	}

	/**
	 * Decision and action implementation.
	 */
	protected abstract void decideAndAct();
}