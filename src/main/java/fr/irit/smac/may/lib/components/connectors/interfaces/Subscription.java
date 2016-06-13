package fr.irit.smac.may.lib.components.connectors.interfaces;


public interface Subscription<I> {

	/**
	 * Subscribes a port to a notification. Until not unsubscribed, the port
	 * will be systematically notified.
	 * 
	 * @param listeningPort
	 *            the port to subscribe
	 * @return true if the port was not already subscribed
	 */
	public boolean subscribe(I listeningPort);

	/**
	 * Unsubscribes a port from notification.
	 * 
	 * @param listeningPort
	 * @return true if the port was actually subscribed
	 */
	public boolean unsubscribe(I listeningPort);
}
