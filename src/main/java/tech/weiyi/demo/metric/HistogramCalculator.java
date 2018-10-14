package tech.weiyi.demo.metric;

import java.util.Queue;

public interface HistogramCalculator {

    long addElapse(String operationIdr, int elapse, boolean isSuccess);

    Queue<HistogramMapSnap> getHistogramQueue();
}
