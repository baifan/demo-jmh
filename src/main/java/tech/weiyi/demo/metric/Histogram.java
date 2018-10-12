package tech.weiyi.demo.metric;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class Histogram {


    private final LongAdder totalElapse;

    private final AtomicLong totalCall;

    private Distribution distribution;

    /**
     * 公共构造函数
     */
    public Histogram() {
        this(new PowerDistribution());
    }

    public Histogram(Distribution distribution) {
        super();
        this.distribution = distribution;
        this.totalElapse = new LongAdder();
        this.totalCall = new AtomicLong();
    }

    public long addElapse(int elapse) {
        totalElapse.add(elapse);
        distribution.addElapse(elapse);
        return totalCall.incrementAndGet();
    }

    public long getTotalCall() {
        return totalCall.get();
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
}
