package fr.irit.smac.may.examples.mas.scheduling.impl;

import java.util.concurrent.Executors;

import fr.irit.smac.may.examples.mas.scheduling.MasTwoPhasesScheduling;
import fr.irit.smac.may.examples.mas.scheduling.PhaseScheduling;
import fr.irit.smac.may.examples.mas.scheduling.interfaces.SchedulingObserve;
import fr.irit.smac.may.lib.components.connectors.Sequence2;
import fr.irit.smac.may.lib.components.connectors.Sequence4;
import fr.irit.smac.may.lib.components.connectors.impl.Sequence2DoImpl;
import fr.irit.smac.may.lib.components.connectors.impl.Sequence4DoImpl;
import fr.irit.smac.may.lib.components.count.Counter;
import fr.irit.smac.may.lib.components.count.CounterImpl;
import fr.irit.smac.may.lib.components.interactions.Notifier;
import fr.irit.smac.may.lib.components.interactions.impl.NotifierDoImpl;
import fr.irit.smac.may.lib.components.scheduling.Clock;
import fr.irit.smac.may.lib.components.scheduling.ClockImpl;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapper;
import fr.irit.smac.may.lib.components.scheduling.ExecutorServiceWrapperImpl;
import fr.irit.smac.may.lib.components.scheduling.Scheduler;
import fr.irit.smac.may.lib.components.scheduling.SchedulerImpl;
import fr.irit.smac.may.lib.interfaces.Do;

public class MasTwoPhasesSchedulingImpl extends MasTwoPhasesScheduling {

	@Override
	protected ExecutorServiceWrapper make_executorService() {
		return new ExecutorServiceWrapperImpl(Executors.newFixedThreadPool(5));
	}

	@Override
	protected Clock make_clock() {
		return new ClockImpl();
	}

	@Override
	protected PhaseScheduling make_firstScheduling() {
		return new PhaseSchedulingImpl();
	}

	@Override
	protected PhaseScheduling make_secondScheduling() {
		return new PhaseSchedulingImpl();
	}

	@Override
	protected AgentScheduler make_AgentScheduler() {
		return new AgentSchedulerImpl();
	}

	/**
	 * A local implementation of SchedulingObserve using cycle Notifier and
	 * Counter parts.
	 *
	 */
	private final class SchedulingObserveImpl implements SchedulingObserve {
		@Override
		public boolean unsubscribe(Do listeningPort) {
			return parts().cycleNotification().subscribtion().unsubscribe(listeningPort);
		}

		@Override
		public boolean subscribe(Do listeningPort) {
			return parts().cycleNotification().subscribtion().subscribe(listeningPort);
		}

		@Override
		public Integer getStepNb() {
			return parts().cycleCounter().getCount().pull();
		}
	}

	private class PhaseSchedulingImpl extends PhaseScheduling {

		@Override
		protected Scheduler make_scheduler() {
			return new SchedulerImpl();
		}

	}

	private class AgentSchedulerImpl extends AgentScheduler {

		@Override
		protected Sequence2<Do> make_stopper() {
			return new Sequence2DoImpl();
		}

	}

	@Override
	protected Notifier<Do> make_cycleNotification() {
		return new NotifierDoImpl();
	}

	@Override
	protected SchedulingObserve make_observe() {
		return new SchedulingObserveImpl();
	}

	@Override
	protected Counter make_cycleCounter() {
		return new CounterImpl();
	}

	@Override
	protected Sequence4<Do> make_ticker() {
		return new Sequence4DoImpl();
	}

}
