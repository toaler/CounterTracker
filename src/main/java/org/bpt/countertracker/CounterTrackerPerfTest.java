package org.bpt.countertracker;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;


/**
 * Performance test suite for {@link CounterSet} API's.
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
	
	@State(Scope.Benchmark)
	public static class Counter {
		private CounterSet<Foo> counter;
		
		public Counter() {
			counter = CounterSet.<Foo>newInstance(Foo.class);
			
		}

		public CounterSet<Foo> getCounter() {
			return counter;
		}
	}

	@GenerateMicroBenchmark
	public void testUpdate(Counter counter) {
		counter.counter.update(Foo.BAR, 1L);
	}
}