package fr.irit.smac.may.examples.electrical.knowledges;

import fr.irit.smac.may.examples.mas.knowledge.BasicKnowledgeImpl;

public class DipoleKnowledgeImpl extends BasicKnowledgeImpl implements DipoleKnowledge {

	/**
	 * id of the first terminal
	 */
	protected String firstTerminal;

	/**
	 * id of the second terminal
	 */
	protected String secondTerminal;

	public Double firstPotential;

	public Double secondPotential;

	public Double tension;

	public Double resistor;

	public Double intensity;

	public DipoleKnowledgeImpl(String id, String first, String second) {
		super(id);
		firstTerminal = first;
		secondTerminal = second;
	}

	@Override
	public String getId(Terminal terminal) {
		switch (terminal) {
		case FIRST:
			return firstTerminal;
		case SECOND:
			return secondTerminal;
		}
		return null;
	}

	@Override
	public Double getU() {
		return tension;
	}

	@Override
	public Double getR() {
		return resistor;
	}

	@Override
	public Double getI() {
		return intensity;
	}

	@Override
	public Double getV(Terminal terminal) {
		switch (terminal) {
		case FIRST:
			return firstPotential;
		case SECOND:
			return secondPotential;
		}
		return null;
	}

	@Override
	public void setTerminalV(Terminal terminal, Double potential) {
		switch (terminal) {
		case FIRST:
			firstPotential = potential;
			break;
		case SECOND:
			secondPotential = potential;
			break;
		}
		computeUfromV();
	}

	/**
	 * 
	 */
	protected void computeUfromV() {
		if (firstPotential != null && secondPotential != null) {
			Double old = tension;
			tension = secondPotential - firstPotential;
			if (!tension.equals(old)) {
				computeIfromU();
			}
		}
	}

	protected void computeIfromU() {
		if (tension != null && resistor != null && !resistor.equals(0d)) {
			intensity = tension / resistor;
		}
	}

	@Override
	public String toString() {
		return "Dipole " + id + " " + firstTerminal + ": " + firstPotential
				+ "V " + secondTerminal + ": " + secondPotential + "V U="
				+ tension + "V R=" + resistor + "Ohm I=" + intensity + "A.";
	}

	@Override
	public Terminal getTerminal(String id) {
		if (id == null) {
			return null;
		}
		if (id.equals(firstTerminal)) {
			return Terminal.FIRST;
		}
		if (id.equals(secondTerminal)) {
			return Terminal.SECOND;
		}
		return null;
	}
}