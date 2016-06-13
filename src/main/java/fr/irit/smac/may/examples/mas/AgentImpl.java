package fr.irit.smac.may.examples.mas;

import fr.irit.smac.may.examples.mas.Mas.Agent;
import fr.irit.smac.may.examples.mas.behavior.Behavior;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;

public class AgentImpl<Msg> extends Agent<Msg> {

	String id;
	Behavior<Msg> behavior;

	public AgentImpl(String id, Behavior<Msg> behavior) {
		super();
		this.id = id;
		this.behavior = behavior;
	}

	@Override
	protected Pull<String> make_id() {
		return new Pull<String>() {

			@Override
			public String pull() {
				return id;
			}
		};
	}

	@Override
	protected Behavior<Msg> make_behavior() {
		return behavior;
	}

	@Override
	protected Do make_stop() {
		return new Do() {

			@Override
			public void doIt() {
				parts().scheduler().stop().doIt();
				parts().messaging().stop().doIt();
				parts().monitor().stop().doIt();
				parts().logger().stop().doIt();
			}
		};
	}

}
