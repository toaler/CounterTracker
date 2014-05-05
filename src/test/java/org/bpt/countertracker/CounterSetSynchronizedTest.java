package org.bpt.countertracker;

import junit.framework.TestCase;

public class CounterSetSynchronizedTest extends TestCase {
	public enum Foo {
		BAR,
		BAZ;
	}

	public void testCounterUpdateAndGet() {
		CounterSetTracker<Foo> counters;
		counters = CounterSet.newInstance(Foo.class);
		counters = CounterSetSynchronized.newInstance(counters);
		
		assertEquals(0L, counters.get(Foo.BAR));
		counters.update(Foo.BAR, 1L);
		assertEquals(1L, counters.get(Foo.BAR));
		counters.update(Foo.BAR, 1L);
		assertEquals(2L, counters.get(Foo.BAR));
	}

	public void testCounterSize() {
		CounterSetTracker<Foo> counters = CounterSet.newInstance(Foo.class);
		assertEquals(2L, counters.size());
	}
}
