package tech.weiyi.demo.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import tech.weiyi.demo.metric.TimeSliceHistogramCalculator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(5)
@Threads(50)
public class Jmh04TimeSliceHistogramMap {

    private TimeSliceHistogramCalculator histogram = new TimeSliceHistogramCalculator(2000, 2);

    private AtomicLong id = new AtomicLong();

    @Benchmark
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    @Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 16, time = 500, timeUnit = TimeUnit.MILLISECONDS)
    public void measure() {
        int index = (int) (id.incrementAndGet() % 500);
        histogram.addElapse(Integer.toString(index), ThreadLocalRandom.current().nextInt(500), false);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Jmh04TimeSliceHistogramMap.class.getSimpleName())
//                .shouldDoGC(true)
                .build();
        new Runner(opt).run();
    }

}
