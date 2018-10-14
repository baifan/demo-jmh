package tech.weiyi.demo.metric;

public interface Histogram {

    long addElapse(int elapse, boolean isSuccess);

    long getTotalCall();

    long getTotalCallFailed();

    long getTotalElapseSuccess();

    long[] getElapseScatterSuccess();
}
