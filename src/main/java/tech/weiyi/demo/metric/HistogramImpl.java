package tech.weiyi.demo.metric;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class HistogramImpl implements Histogram {

    private final AtomicLong totalCall;

    private final LongAdder totalCallFailed;

    private final LongAdder totalElapseSuccess;

    private Scatter scatterSuccess;

    /**
     * 公共构造函数
     */
    public HistogramImpl() {
        this(new PowerScatter());
    }

    public HistogramImpl(Scatter scatterSuccess) {
        super();
        this.scatterSuccess = scatterSuccess;
        this.totalElapseSuccess = new LongAdder();
        this.totalCall = new AtomicLong();
        this.totalCallFailed = new LongAdder();
    }

    public long addElapse(int elapse, boolean isSuccess) {
        if (isSuccess) {
            totalElapseSuccess.add(elapse);
            scatterSuccess.addElapse(elapse);
        } else {
            totalCallFailed.increment();
        }
        return totalCall.incrementAndGet();
    }

    public long getTotalCall() {
        return totalCall.get();
    }

    public long getTotalCallFailed() {
        return totalCallFailed.sum();
    }

    public long getTotalElapseSuccess() {
        return totalElapseSuccess.sum();
    }

    public long[] getElapseScatterSuccess() {
        return this.scatterSuccess.getElapseScatter();
    }

}
