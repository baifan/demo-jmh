package tech.weiyi.demo.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
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
import tech.weiyi.demo.metric.TimeSliceHistogram;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@State(Scope.Group)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(5)
@Threads(50)
public class Jmh03TimeSliceHistogram {

    private TimeSliceHistogram histogram = new TimeSliceHistogram(16, 1000);

    @Benchmark
    @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
    @Warmup(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
    @Measurement(iterations = 16, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
    @Group("jmh")
    public void measure() {
        histogram.addElapse(ThreadLocalRandom.current().nextInt(60));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Jmh03TimeSliceHistogram.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }

}
