package fr.irit.smac.may.examples.electrical.messages;

import fr.irit.smac.may.examples.mas.messaging.impl.BasicMessageImpl;

/**
 * A message to send a value
 *
 * @param <T>
 *            type of the value
 */
public class ValueMessage<T> extends BasicMessageImpl {

	T value;

	public ValueMessage(String sender, T value) {
		super(sender);
		this.value = value;
	}

	public T getValue() {
		return value;
	}
}
