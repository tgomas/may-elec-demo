package fr.irit.smac.may.examples.electrical.messages;

import fr.irit.smac.may.examples.mas.messaging.impl.BasicMessageImpl;
import fr.irit.smac.util.avt.Feedback;

/**
 * Message to communicate a wish about the change direction. It is useful to
 * tune a parameter and could be extended in case of multiple parameters.
 * 
 * 
 */
public class DirectionRequest<T> extends BasicMessageImpl {

	/**
	 * 
	 */
	public Feedback direction;
	public Double criticality;
	public T knownValue;

	public DirectionRequest(String sender, Feedback direction,
			Double criticality, T knownValue) {
		super(sender);
		this.direction = direction;
		this.criticality = criticality;
		this.knownValue = knownValue;
	}

	public static Feedback opposite(Feedback direction) {
		switch (direction) {
		case GREATER:
			return Feedback.LOWER;
		case LOWER:
			return Feedback.GREATER;

		default:
			return Feedback.GOOD;
		}
	}

	@Override
	public String toString() {
		return "sender: " + sender + " direction: " + direction
				+ "  criticality: " + criticality + "  knownvalue: "
				+ knownValue;
	}
}
