package fr.irit.smac.may.examples.mas;

import fr.irit.smac.may.examples.mas.behavior.Behavior;
import fr.irit.smac.may.examples.mas.logging.Logging;
import fr.irit.smac.may.examples.mas.logging.impl.LoggingImpl;
import fr.irit.smac.may.examples.mas.messaging.Messaging;
import fr.irit.smac.may.examples.mas.messaging.impl.MessagingImpl;
import fr.irit.smac.may.examples.mas.monitoring.Monitoring;
import fr.irit.smac.may.examples.mas.monitoring.impl.ChartMonitoringImpl;
import fr.irit.smac.may.examples.mas.scheduling.MasTwoPhasesScheduling;
import fr.irit.smac.may.examples.mas.scheduling.impl.MasTwoPhasesSchedulingImpl;
import fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUI;
import fr.irit.smac.may.lib.components.scheduling.SchedulingControllerGUIImpl;
import fr.irit.smac.may.lib.interfaces.Do;

public class MasImpl<Msg> extends Mas<Msg> {
	@Override
	protected void start() {
		super.start();
		// push cycle nb to the logging component at each cycle notification
		Do subscribedPort = new Do() {
			@Override
			public void doIt() {
				Integer nb = parts().scheduler().observe().getStepNb();
				parts().log().cycleNotification().push(nb);
			}
		};
		parts().scheduler().observe().subscribe(subscribedPort);
	};

	@Override
	protected MasTwoPhasesScheduling make_scheduler() {
		return new MasTwoPhasesSchedulingImpl();
	}

	@Override
	protected Messaging<Msg> make_messaging() {
		return new MessagingImpl<Msg>();
	}

	@Override
	protected Mas.Agent<Msg> make_Agent(String id, Behavior<Msg> behavior) {
		return new AgentImpl<Msg>(id, behavior);
	}

	@Override
	protected AgentsFactory<Msg> make_factory() {
		return new AgentsFactory<Msg>() {

			@Override
			public void createAgent(String id, Behavior<Msg> behavior) {
				// TODO don't create if it already exists
				newAgent(id, behavior);
			}
		};
	}

	@Override
	protected SchedulingControllerGUI make_gui() {
		return new SchedulingControllerGUIImpl(500);
	}

	@Override
	protected Monitoring<Double> make_monitor() {
		return new ChartMonitoringImpl();
	}

	@Override
	protected Logging make_log() {
		return new LoggingImpl();
	}

}
