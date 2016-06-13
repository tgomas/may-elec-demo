package fr.irit.smac.may.examples.mas.monitoring.interfaces;

import java.util.List;

public interface SeriesObserve<T> {

	public List<T> observe(String id);
}
