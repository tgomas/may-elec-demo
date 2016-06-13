package fr.irit.smac.may.examples.electrical.messages;

public class PotentialMsg extends ValueMessage<Double> {

	public PotentialMsg(String sender, Double potential) {
		super(sender, potential);
	}

}
