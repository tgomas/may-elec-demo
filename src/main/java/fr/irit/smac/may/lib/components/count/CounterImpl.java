package fr.irit.smac.may.lib.components.count;

import java.util.concurrent.atomic.AtomicInteger;

import fr.irit.smac.may.lib.interfaces.Do;
import fr.irit.smac.may.lib.interfaces.Pull;

public class CounterImpl extends Counter {

	AtomicInteger counter = new AtomicInteger();

	@Override
	protected Do make_increment() {
		return () -> counter.incrementAndGet();
	}

	@Override
	protected Do make_decrement() {
		return () -> counter.decrementAndGet();
	}

	@Override
	protected Pull<Integer> make_getCount() {
		return () -> counter.get();
	}

	@Override
	protected Do make_reset() {
		return () -> counter.set(0);
	}

}
