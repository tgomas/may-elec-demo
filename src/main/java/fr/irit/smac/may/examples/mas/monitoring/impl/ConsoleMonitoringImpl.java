package fr.irit.smac.may.examples.mas.monitoring.impl;

import java.util.function.Predicate;

import fr.irit.smac.may.examples.mas.monitoring.Monitoring;
import fr.irit.smac.may.examples.mas.monitoring.interfaces.MonitoringControl;
import fr.irit.smac.may.examples.mas.monitoring.interfaces.SeriesPublish;
import fr.irit.smac.may.lib.interfaces.Do;

public class ConsoleMonitoringImpl extends Monitoring<Double> {

	Predicate<String> agentsFilter = s -> true;
	Predicate<String> valuesFilter = s -> true;

	@Override
	protected AgentMonitor<Double> make_AgentMonitor(String id) {
		return new AgentMonitorImpl(id);
	}

	@Override
	protected MonitoringControl make_control() {
		return new MonitoringControl() {

			@Override
			public void filterAgents(Predicate<String> filter) {
				agentsFilter = filter;
			}

			@Override
			public void filterValues(Predicate<String> filter) {
				valuesFilter = filter;
			}

		};
	}

	private final class AgentMonitorImpl extends AgentMonitor<Double> {
		String agentId;

		AgentMonitorImpl(String id) {
			this.agentId = id;
		}

		@Override
		protected SeriesPublish<Double> make_publisher() {
			return new SeriesPublish<Double>() {

				@Override
				public void publish(String name, Double value) {
					if (agentsFilter.test(agentId) && valuesFilter.test(name)) {
						System.out.println("[" + agentId + "] " + name + " : " + value);
					}
				}
			};
		}

		@Override
		protected Do make_stop() {
			return new Do() {

				@Override
				public void doIt() {
					agentId = null;
				}
			};
		}
	}
}