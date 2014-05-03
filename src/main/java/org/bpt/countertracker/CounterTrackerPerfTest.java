package org.bpt.countertracker;

import java.util.concurrent.TimeUnit;

import org.bpt.countertracker.CounterTracker.CounterTrackerBuilder;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;


/**
 * Performance test suite for {@link CounterTracker} API's.
 * 
 * @author toaler
 *
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class CounterTrackerPerfTest {
	public enum Foo {
		BAR,
		BAZ;
	}
	
	public enum Boo {
		FAR,
		FAZ;
	}
	
	@State(Scope.Benchmark)
	public static class Counter {
		private CounterTracker counter;
		
		public Counter() {
			CounterTrackerBuilder builder = CounterTrackerBuilder.newInstance();
			builder.addType(Foo.class).addType(Boo.class);
			counter = builder.build();
			
		}

		public CounterTracker getCounter() {
			return counter;
		}
	}

	@GenerateMicroBenchmark
	public void testUpdate(Counter counter) {
		counter.counter.update(Foo.BAR, 1L);
	}
}