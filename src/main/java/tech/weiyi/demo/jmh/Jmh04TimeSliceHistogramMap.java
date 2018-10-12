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
import tech.weiyi.demo.metric.MethodCall;
import tech.weiyi.demo.metric.TimeSliceHistogramMap;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(5)
@Threads(300)
public class Jmh04TimeSliceHistogramMap {

    private TimeSliceHistogramMap histogram = new TimeSliceHistogramMap(16, 1000);

    private AtomicLong id = new AtomicLong();

    @Benchmark
    @BenchmarkMode({Mode.AverageTime})
    @Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 16, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    public void measure() {
        int index = (int) (id.incrementAndGet() % 500);
        MethodCall methodCall = new MethodCall(index, String.valueOf(index));
        histogram.addElapse(methodCall, ThreadLocalRandom.current().nextInt(500));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Jmh04TimeSliceHistogramMap.class.getSimpleName())
//                .shouldDoGC(true)
                .build();
        new Runner(opt).run();
    }

}
