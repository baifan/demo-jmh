package tech.weiyi.demo.metric;

import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

public class CommonHistogram implements Histogram {

    private final LongAdder totalElapse;

    private final LongAdder totalCall;

    private Distribution distribution;

    /**
     * 公共构造函数
     */
    public CommonHistogram() {
        super();
        this.distribution = new MapDistribution();
        this.totalElapse = new LongAdder();
        this.totalCall = new LongAdder();
    }

    public CommonHistogram(Distribution distribution) {
        super();
        this.distribution = distribution;
        this.totalElapse = new LongAdder();
        this.totalCall = new LongAdder();
    }

    public void addElapse(int elapse) {
        totalCall.increment();
        totalElapse.add(elapse);
        distribution.addElapse(elapse);
    }

    public long getTotalCall() {
        return totalCall.sum();
    }

    public long getTotalElapse() {
        return totalElapse.sum();
    }

    public Map<Integer, Long> getElapseScatter() {
        return this.distribution.getElapseScatter();
    }

    public void setDistributor(Distribution distribution) {
        this.distribution = distribution;
    }

    @Override
    public void reset() {
        this.totalElapse.reset();
        this.totalCall.reset();
        this.distribution.reset();
    }
}
