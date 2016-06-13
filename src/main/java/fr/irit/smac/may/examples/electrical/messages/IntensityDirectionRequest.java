package fr.irit.smac.may.examples.electrical.messages;

import fr.irit.smac.util.avt.Feedback;

public class IntensityDirectionRequest extends DirectionRequest<Double> {

	public IntensityDirectionRequest(String sender, Feedback direction,
			Double criticality, Double knownValue) {
		super(sender, direction, criticality, knownValue);
	}

}
