package fr.irit.smac.may.examples.electrical.messages;

import fr.irit.smac.util.avt.Feedback;

public class PotentialDirectionRequest extends DirectionRequest<Double> {

	public PotentialDirectionRequest(String sender, Feedback direction,
			Double criticality, Double knownValue) {
		super(sender, direction, criticality, knownValue);
	}

}
