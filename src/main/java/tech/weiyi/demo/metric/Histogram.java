package tech.weiyi.demo.metric;

import java.util.Map;

public interface Histogram {

    void addElapse(int elapse);

    long getTotalCall();

    long getTotalElapse();

    Map<Integer, Long> getElapseScatter();

    void setDistributor(Distribution distribution);

    void reset();
}
