package org.bpt.countertracker;

import org.bpt.countertracker.CounterTracker.CounterTrackerBuilder;

import junit.framework.TestCase;

/**
 * Test suite for {@link CounterTracker}
 * 
 * @author toal
 *
 */
public class CounterTrackerTest extends TestCase {
	
	public enum Foo {
		BAR,
		BAZ;
	}
	
	public enum Boo {
		FAR,
		FAZ;
	}
	
	public enum Zoo {
		MAR;
	}
	
	public void testCounterWithoutEnums() {
		try {
			CounterTrackerBuilder builder = CounterTrackerBuilder.newInstance();
			builder.build();
			fail();
		} catch (IllegalStateException x) {
			// Expected exception thrown so swallow
		}
	}
	public void testUpdateCounter() {
		CounterTrackerBuilder builder = CounterTrackerBuilder.newInstance();
		builder.addType(Foo.class).addType(Boo.class);
		
		CounterTracker counters = builder.build();
		
		assertEquals(Foo.values().length + Boo.values().length, counters.size());
		assertEquals(0L, counters.get(Foo.BAR));
		
		counters.update(Foo.BAR, 1L);
		assertEquals(1L, counters.get(Foo.BAR));
		
		counters.update(Foo.BAR, 1L);
		assertEquals(2L, counters.get(Foo.BAR));
		
		try {
			counters.update(Zoo.MAR, 1L);
			fail();
		} catch (IllegalArgumentException x) {
			// Expected exception thrown so swallow
		}
		
		counters.update(Boo.FAZ, 3L);
		assertEquals(3L, counters.get(Boo.FAZ));
		
		counters.update(Boo.FAZ, 1L);
		assertEquals(4L, counters.get(Boo.FAZ));
	}
}
