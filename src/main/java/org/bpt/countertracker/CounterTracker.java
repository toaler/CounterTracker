package org.bpt.countertracker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * {@code CounterTracker} provides services to manage a set of counters. One to
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
public class CounterTracker {
	private final long[] counter;
	private final Map<Class<? extends Enum<?>>, Integer> offset;

	private CounterTracker(Map<Class<? extends Enum<?>>, Integer> offset,
			int size) {
		this.counter = new long[size];
		this.offset = offset;
	}

	/**
	 * @return The aggregate sum of all the counters registered.
	 */
	public int size() {
		return counter.length;
	}

	/**
	 * For the given counter, represented by {@code type} add {@code value} to the corresponding counter.
	 * 
	 * @param type - {@link Enum} constant to identify corresponding counter
	 * @param value - Value to aggregate to existing counter
	 */
	public <T extends Enum<T>> void update(T type, long value) {
		Integer i = offset.get(type.getClass());

		if (i == null) {
			throw new IllegalArgumentException();
		}

		counter[i] = counter[i] + value;
	}

	/**
	 * Returns the value for the given {@code Enum} constant.
	 * 
	 * @param type - {@link Enum} constant to identify corresponding counter
	 * @return - current value for counter corresponding to {@code type}
	 */
	public <T extends Enum<T>> long get(T type) {
		return counter[offset.get(type.getClass())];
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();

		for (Class<? extends Enum<?>> e : offset.keySet()) {
			if (s.length() != 0) {
				s.append(", ");
			}

			s.append(e);
		}

		return s.toString();
	}

	
	/**
	 * {@code CounterTracker} builder, which provides the ability to register {@link Enum}'s.
	 * 
	 * @author toaler
	 *
	 */
	public static class CounterTrackerBuilder {
		private final Set<Class<? extends Enum<?>>> enums;

		public CounterTrackerBuilder addType(Class<? extends Enum<?>> type) {
			enums.add(type);
			return this;
		}

		private CounterTrackerBuilder() {
			enums = new HashSet<Class<? extends Enum<?>>>();
		}

		public static CounterTrackerBuilder newInstance() {
			return new CounterTrackerBuilder();
		}

		public CounterTracker build() {
			if (enums.size() == 0)
				throw new IllegalStateException();

			Map<Class<? extends Enum<?>>, Integer> enumToOffset = new HashMap<Class<? extends Enum<?>>, Integer>();

			int i = 0, size = 0;
			for (Class<? extends Enum<?>> e : enums) {
				enumToOffset.put(e, i++);
				size += e.getEnumConstants().length;
			}

			return new CounterTracker(enumToOffset, size);
		}
	}
}