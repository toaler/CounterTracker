package org.bpt.countertracker;

public abstract class CounterSetDecorator<E extends Enum<E>> implements CounterSetTracker<E> {
	@Override
	public abstract int size();

	@Override
	public abstract void update(E key, long value);

	@Override
	public abstract long get(E key);
}
