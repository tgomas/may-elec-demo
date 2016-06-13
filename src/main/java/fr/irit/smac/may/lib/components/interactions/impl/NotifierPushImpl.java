package fr.irit.smac.may.lib.components.interactions.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import fr.irit.smac.may.lib.components.connectors.interfaces.Subscription;
import fr.irit.smac.may.lib.components.interactions.Notifier;
import fr.irit.smac.may.lib.interfaces.Push;

public class NotifierPushImpl<T> extends Notifier<Push<T>> {

	final Set<Push<T>> subscribed = new HashSet<>();

	@Override
	protected Push<T> make_notification() {
		return thing -> subscribed.forEach(port -> port.push(thing));
	}

	@Override
	protected Subscription<Push<T>> make_subscribtion() {
		return new Subscription<Push<T>>() {

			@Override
			public boolean subscribe(Push<T> port) {
				Objects.requireNonNull(port);
				return subscribed.add(port);
			}

			@Override
			public boolean unsubscribe(Push<T> port) {
				return subscribed.remove(port);
			}
		};
	}

}
