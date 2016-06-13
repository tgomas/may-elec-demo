package fr.irit.smac.may.examples.electrical.messages;

public class IntensityMsg extends ValueMessage<Double> {

	public IntensityMsg(String sender, Double intensity) {
		super(sender, intensity);
	}

}
