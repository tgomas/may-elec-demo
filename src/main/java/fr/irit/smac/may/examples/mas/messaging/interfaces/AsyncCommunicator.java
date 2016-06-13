package fr.irit.smac.may.examples.mas.messaging.interfaces;

import fr.irit.smac.may.lib.interfaces.Broadcast;
import fr.irit.smac.may.lib.interfaces.Send;

public interface AsyncCommunicator<Msg> extends Send<Msg, String>,
		Broadcast<Msg>, AsyncReceive<Msg> {

}
