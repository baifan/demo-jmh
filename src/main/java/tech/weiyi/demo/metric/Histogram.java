package tech.weiyi.demo.metric;

import java.util.concurrent.atomic.LongAdder;

public class Histogram {

    private final LongAdder[] durationDistribution;

    private final LongAdder durationSum;

    private final LongAdder countSum;

    /**
     * 公共构造函数
     */
    public Histogram() {
        super();
        durationDistribution = new LongAdder[HistrogramStep.ARRY_LENGTH];
        for (int i = 0; i < HistrogramStep.ARRY_LENGTH; i++) {
            durationDistribution[i] = new LongAdder();
        }
        durationSum = new LongAdder();
        countSum = new LongAdder();
    }

    public void addDuration(long duration) {
        countSum.increment();
        durationSum.add(duration);
        durationDistribution[HistrogramStep.getIndex(duration)].increment();
    }

    public long getCountSum() {
        return countSum.sum();
    }

    public long getDurationSum() {
        return durationSum.sum();
    }

    public long[] getDurationDistribution() {
        long[] result = new long[durationDistribution.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = durationDistribution[i].sum();
        }
        return result;
    }

}
