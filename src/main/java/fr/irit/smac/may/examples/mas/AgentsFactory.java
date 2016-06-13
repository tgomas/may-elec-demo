package fr.irit.smac.may.examples.mas;

import fr.irit.smac.may.examples.mas.behavior.Behavior;

public interface AgentsFactory<Msg> {
	public void createAgent(String name, Behavior<Msg> behavior);
}
