package fr.irit.smac.may.examples.mas.monitoring.interfaces;

public interface SeriesPublish<T> {

	/**
	 * Publishes the value under provided name
	 * @param name
	 * @param value
	 */
	public void publish(String name, T value);
}
