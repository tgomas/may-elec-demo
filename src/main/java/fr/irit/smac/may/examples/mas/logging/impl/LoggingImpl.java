package fr.irit.smac.may.examples.mas.logging.impl;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import fr.irit.smac.libs.tooling.logging.AgentLog;
import fr.irit.smac.may.examples.mas.logging.Logging;
import fr.irit.smac.may.examples.mas.logging.interfaces.LoggingControl;
import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Push;

public class LoggingImpl extends Logging {

	Predicate<String> agentsFilter = s -> true;
	Predicate<String> valuesFilter = s -> true;

	AtomicInteger nbAgents = new AtomicInteger(0);

	@Override
	protected void start() {
		AgentLog.initializer().clearLogFolderOnStartup(true).logEnabled(true)
				.logLevel(Level.ALL).initialize();
	};

	@Override
	protected AgentLogger make_AgentLogger(String id) {
		return new AgentLoggerImpl(id);
	}

	@Override
	protected LoggingControl make_control() {
		return new LoggingControl() {

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

	private final class AgentLoggerImpl extends AgentLogger {
		Logger logger;

		AgentLoggerImpl(String id) {
			logger = AgentLog.getLogger(id);
			nbAgents.incrementAndGet();
		}

		@Override
		protected Logger make_logger() {
			return logger;
		}

		@Override
		protected Do make_stop() {
			return new Do() {

				@Override
				public void doIt() {
					logger = null;
					nbAgents.decrementAndGet();
				}
			};
		}
	}

	@Override
	protected Push<Integer> make_cycleNotification() {
		return (nb) -> System.out.println("logging cycle : " + nb
				+ "; # agents : " + nbAgents);
	}
}