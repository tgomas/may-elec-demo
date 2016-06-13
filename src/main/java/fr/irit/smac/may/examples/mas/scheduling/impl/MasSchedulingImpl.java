package fr.irit.smac.may.examples.mas.scheduling.impl;

import java.util.concurrent.Executors;

import fr.irit.smac.may.examples.mas.scheduling.MasScheduling;
import fr.irit.smac.may.lib.components.connectors.Sequence2;
import fr.irit.smac.may.lib.components.connectors.impl.Sequence2DoImpl;
import fr.irit.smac.may.lib.components.scheduling.Clock;
import fr.irit.smac.may.lib.components.scheduling.ClockImpl;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapperImpl;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;
import fr.irit.smac.may.lib.components.scheduling.SchedulerImpl;
import fr.irit.smac.may.lib.interfaces.Do;

public class MasSchedulingImpl extends MasScheduling {

	@Override
	protected ExecutorServiceWrapper make_executorService() {
		return new ExecutorServiceWrapperImpl(Executors.newFixedThreadPool(5));
	}

	@Override
	protected Clock make_clock() {
		return new ClockImpl();
	}

	@Override
	protected Scheduler make_perceptionScheduler() {
		return new SchedulerImpl();
	}

	@Override
	protected Scheduler make_decisionAndActionScheduler() {
		return new SchedulerImpl();
	}

	@Override
	protected Sequence2<Do> make_bothTick() {
		return new Sequence2DoImpl();
	}

	@Override
	protected AgentScheduler make_AgentScheduler() {
		return new AgentScheduler() {

			@Override
			protected Sequence2<Do> make_bothStop() {
				return new Sequence2DoImpl();
			}
		};
	}

}
