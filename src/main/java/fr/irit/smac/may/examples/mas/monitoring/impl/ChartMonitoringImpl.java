package fr.irit.smac.may.examples.mas.monitoring.impl;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import fr.irit.smac.libs.tooling.plot.server.AgentPlotChart;
import fr.irit.smac.may.examples.mas.monitoring.Monitoring;
import fr.irit.smac.may.examples.mas.monitoring.interfaces.MonitoringControl;
import fr.irit.smac.may.examples.mas.monitoring.interfaces.SeriesPublish;
import fr.irit.smac.may.lib.interfaces.Do;

public class ChartMonitoringImpl extends Monitoring<Double> {

	Predicate<String> agentsFilter = s -> true;
	Predicate<String> valuesFilter = s -> true;

	AtomicInteger nbAgents = new AtomicInteger(0);
	AgentPlotChart nbChart = new AgentPlotChart("# Agents");

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
		AgentPlotChart chart;

		public AgentMonitorImpl(String id) {
			super();
			this.agentId = id;
			nbChart.add(nbAgents.incrementAndGet());
		}

		@Override
		protected void start() {
			super.start();
		}

		@Override
		protected SeriesPublish<Double> make_publisher() {
			return new SeriesPublish<Double>() {

				@Override
				synchronized public void publish(String name, Double value) {
					if (agentsFilter.test(agentId) && valuesFilter.test(name)) {
						if (chart == null) {
							chart = new AgentPlotChart(agentId);
						}
						chart.add(name, value);
					}
				}
			};
		}

		@Override
		protected Do make_stop() {
			return new Do() {

				@Override
				public void doIt() {
					if (chart != null) {
						chart.close();
					}
					nbChart.add(nbAgents.decrementAndGet());
				}
			};
		}
	}
}
