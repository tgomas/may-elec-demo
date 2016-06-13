package fr.irit.smac.may.examples.mas.messaging.impl;

import java.util.Collection;

import fr.irit.smac.may.examples.mas.messaging.Messaging;
import fr.irit.smac.may.examples.mas.messaging.interfaces.AsyncCommunicator;
import fr.irit.smac.may.lib.components.collections.ConcurrentQueueImpl;
import fr.irit.smac.may.lib.components.collections.Queue;
import fr.irit.smac.may.lib.components.interactions.Broadcast;
import fr.irit.smac.may.lib.components.interactions.BroadcastImpl;
import fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiver;
import fr.irit.smac.may.lib.components.interactions.MapRefAsyncReceiverImpl;

public class MessagingImpl<Msg> extends Messaging<Msg> {

	@Override
	protected MapRefAsyncReceiver<Msg, String> make_receiver() {
		return new MapRefAsyncReceiverImpl<Msg, String>();
	}

	@Override
	protected Broadcast<Msg, String> make_broadcast() {
		return new BroadcastImpl<Msg, String>();
	}

	@Override
	protected Messaging.AgentMessaging<Msg> make_AgentMessaging(String id) {
		return new AgentMessaging<Msg>() {

			@Override
			protected AsyncCommunicator<Msg> make_messaging() {
				return new AsyncCommunicator<Msg>() {

					@Override
					public void send(Msg msg, String receiverId) {
						parts().sender().send().send(msg, receiverId);
					}

					@Override
					public Msg getMessage() {
						return parts().mailBox().get().pull();
					}

					@Override
					public Collection<Msg> getAllMessages() {
						return parts().mailBox().getAll().pull();
					}

					@Override
					public void broadcast(Msg message) {
						parts().broadcaster().broadcast().push(message);
					}

				};
			}

			@Override
			protected Queue<Msg> make_mailBox() {
				return new ConcurrentQueueImpl<Msg>();
			}
		};
	}
}
