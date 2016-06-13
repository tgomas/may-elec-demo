package fr.irit.smac.may.examples.electrical.knowledges;

import fr.irit.smac.may.examples.mas.knowledge.Knowledge;

public interface DipoleKnowledge extends Knowledge {

	public enum Terminal {
		FIRST, SECOND
	}

	/**
	 * @param terminal
	 * @return the terminal id
	 */
	public String getId(Terminal terminal);

	/**
	 * @param id
	 * @return the terminal, null if no terminal is identified by the id.
	 */
	public Terminal getTerminal(String id);

	/**
	 * @return the tension
	 */
	public Double getU();

	/**
	 * @return the resistor
	 */
	public Double getR();

	/**
	 * @return the intensity
	 */
	public Double getI();

	/**
	 * Get the potential of a terminal
	 * 
	 * @param terminal
	 * @param potential
	 */
	public Double getV(Terminal terminal);

	/**
	 * Set the potential of a terminal
	 * 
	 * @param terminal
	 * @param potential
	 */
	public void setTerminalV(Terminal terminal, Double potential);
}