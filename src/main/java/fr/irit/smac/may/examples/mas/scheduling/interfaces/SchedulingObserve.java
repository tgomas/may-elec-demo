package fr.irit.smac.may.examples.mas.scheduling.interfaces;

import fr.irit.smac.may.lib.components.connectors.interfaces.Subscription;
import fr.irit.smac.may.lib.interfaces.Do;

public interface SchedulingObserve extends Subscription<Do> {

	/**
	 * @return the number of the current step
	 */
	public Integer getStepNb();

	/**
	 * Subscribes a port to new cycle notification. Until not unsubscribed, the
	 * port will be notified when a new cycle.
	 * 
	 * @param listeningPort
	 *            the port to subscribe
	 * @return true if the port was not already subscribed
	 */
	@Override
	public boolean subscribe(Do listeningPort);

	/**
	 * Unsubscribes a port from new cycle notification.
	 * 
	 * @param listeningPort
	 * @return true if the port was actually subscribed
	 */
	@Override
	public boolean unsubscribe(Do listeningPort);
}
