package fr.irit.smac.may.examples.mas.messaging.interfaces;

import java.util.Collection;

public interface AsyncReceive<Msg> {

	/**
	 * Get a message from the mailbox, and remove it from the mailbox.
	 * 
	 * @return a message, null if the mailbox is empty
	 */
	public Msg getMessage();

	/**
	 * Get all messages from the mailbox, and clear the mailbox.
	 * 
	 * @return a collection of messages
	 */
	public Collection<Msg> getAllMessages();
}
