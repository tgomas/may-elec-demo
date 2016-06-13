package fr.irit.smac.may.examples.mas.logging.interfaces;

import java.util.function.Predicate;

public interface LoggingControl {

	/**
	 * Sets a filter on the id of the agents to be monitored. By default all
	 * publishing agents are monitored.
	 * 
	 * @param names
	 */
	void filterAgents(Predicate<String> filter);

	/**
	 * Sets a filter on the name of the values to be monitored. By default all
	 * published values are monitored.
	 * 
	 * @param names
	 */
	void filterValues(Predicate<String> filter);

}
