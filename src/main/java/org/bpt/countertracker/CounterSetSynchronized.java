package org.bpt.countertracker;

import java.util.concurrent.locks.ReentrantLock;

public class CounterSetSynchronized<E extends Enum<E>> extends CounterSetDecorator<E>{
	private final CounterSetTracker<E> counters;
	private final ReentrantLock lock;
	
	public static <E extends Enum<E>> CounterSetTracker<E> newInstance(CounterSetTracker<E> counters) {
		return new CounterSetSynchronized<E>(counters);
	}

	private CounterSetSynchronized(CounterSetTracker<E> counters) {
		this.counters = counters;
		this.lock = new ReentrantLock();
	}

	@Override
	public int size() {
		try {
			lock.lock();
			return counters.size();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void update(E key, long value) {
		try {
			lock.lock();
			counters.update(key, value);
		} finally {
			lock.unlock();
		}		
	}

	@Override
	public long get(E key) {
		try {
			lock.lock();
			return counters.get(key);
		} finally {
			lock.unlock();
		}
	}
}