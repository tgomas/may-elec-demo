package fr.irit.smac.may.lib.components.interactions.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import fr.irit.smac.may.lib.components.connectors.interfaces.Subscription;
import fr.irit.smac.may.lib.components.interactions.Notifier;
import fr.irit.smac.may.lib.interfaces.Do;

public class NotifierDoImpl extends Notifier<Do> {

	final Set<Do> subscribed = new HashSet<>();

	@Override
	protected Do make_notification() {
		return () -> subscribed.forEach(port -> port.doIt());
	}

	@Override
	protected Subscription<Do> make_subscribtion() {
		return new Subscription<Do>() {

			@Override
			public boolean subscribe(Do port) {
				Objects.requireNonNull(port);
				return subscribed.add(port);
			}

			@Override
			public boolean unsubscribe(Do port) {
				return subscribed.remove(port);
			}
		};
	}

}
