package org.bpt.countertracker;

import java.util.EnumMap;
import java.util.Map;

/**
 * {@link CounterSet} provides services to manage a set of counters. One to
 * many counters can be registered where each counter is uniquely identified by
 * a corresponding {@link Enum} constant. {@link Enum}'s are used to define the
 * set of counters, specifically each {@link Enum} constant will have a
 * corresponding counters as well as being used to uniquely identify the
 * corresponding counter. Define a {@link Enum} to logically represents a set of
 * counters, using the {@link Enum} constant to represent the specific counter.
 * Separate {@link Enum}'s by concerns, and register multiple {@link Enum} at
 * creation time.
 * 
 * @author toaler
 * 
 */
public class CounterSet<E extends Enum<E>> implements CounterSetTracker<E> {
	private final Map<E, Long> counters;
	
	public static <E extends Enum<E>> CounterSet<E> newInstance(Class<E> enumeration) {
		return new CounterSet<E>(enumeration);
	}

	private CounterSet(Class<E> enumeration) {
		counters = new EnumMap<E, Long>(enumeration);
		
		for (E e : enumeration.getEnumConstants()) {
			counters.put(e, 0L);
		}
	}

	/**
	 * @return The aggregate sum of all the counters registered.
	 */
	@Override
	public int size() {
		return counters.size();
	}

	/**
	 * For the given counter, represented by {@code type} add {@code value} to the corresponding counter.
	 * 
	 * @param type - {@link Enum} constant to identify corresponding counter
	 * @param value - Value to aggregate to existing counter
	 */
	@Override
	public void update(E key, long value) {
	    Long cur = counters.get(key);
		
		if (cur == null) {
			throw new IllegalArgumentException();
		}

		counters.put(key, cur + value);
	}

	/**
	 * Returns the value for the given {@code Enum} constant.
	 * 
	 * @param type - {@link Enum} constant to identify corresponding counter
	 * @return - current value for counter corresponding to {@code type}
	 */
	@Override
	public long get(E key) {
		return counters.get(key);
	}
}