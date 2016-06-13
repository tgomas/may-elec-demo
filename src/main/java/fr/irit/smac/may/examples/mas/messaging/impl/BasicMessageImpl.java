package fr.irit.smac.may.examples.mas.messaging.impl;

import fr.irit.smac.may.examples.mas.messaging.interfaces.Message;

public class BasicMessageImpl implements Message {

	protected String sender;

	public BasicMessageImpl(String sender) {
		super();
		this.sender = sender;
	}

	/**
	 * @return the id of the message sender
	 */
	public String getSender() {
		return sender;
	}

}