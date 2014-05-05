package org.bpt.countertracker;

public interface CounterSetTracker<E extends Enum<E>> {

	int size();

	void update(E key, long value);

	long get(E key);

}
