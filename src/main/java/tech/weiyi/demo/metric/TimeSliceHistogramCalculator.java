package tech.weiyi.demo.metric;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicStampedReference;

public class TimeSliceHistogramCalculator {

    private static final int STAMP_INDEX = 0;

    private static final int TIME_DIVISION_MS = 1000;

    private static final int DEFAULT_SLICE = 2;

    private static final int MAP_CAPACITY = 1024;

    private final long TIME_20181010 = 1539100800000L;

    private final AtomicStampedReference<ConcurrentMap<String, Histogram>>[] histogramAsrs;

    private final long timeDivisionMs;

    private final int slice;

    private final int cacheQueueSize = 4;

    private final Queue<HistogramMapSnap> histogramQueue;

    /**
     * 公共构造函数
     */
    public TimeSliceHistogramCalculator() {
        this(TIME_DIVISION_MS, DEFAULT_SLICE);
    }

    public TimeSliceHistogramCalculator(long timeDivisionMs, int slice) {
        super();
        this.timeDivisionMs = timeDivisionMs;
        this.histogramAsrs = new AtomicStampedReference[slice];
        this.slice = slice;
        long timestamp = System.currentTimeMillis();
        int asrStamp = getStamp(timestamp);
        int asrSlice = getSlice(timestamp);
        histogramAsrs[asrSlice] = new AtomicStampedReference<>(this.createHistogramMap(), asrStamp);
        for (int i = 1; i < slice; i++) {
            int j = (i + asrSlice) % slice;
            histogramAsrs[j] = new AtomicStampedReference<>(this.createHistogramMap(), 0);
        }

        histogramQueue = new LinkedBlockingDeque<>(cacheQueueSize);
    }

    private int getStamp(long timestamp) {
        return (int) ((timestamp - TIME_20181010) / timeDivisionMs);
    }

    private int getSlice(long timestamp) {
        return (int) (((timestamp - TIME_20181010) / timeDivisionMs) % slice);
    }

    public long addElapse(String operationIdr, int elapse, boolean isSuccess) {
        Histogram histogram = this.getTsHistogram(operationIdr);
        return histogram.addElapse(elapse, isSuccess);
    }

    private Histogram getTsHistogram(String operationIdr) {
        long currentTimestamp = System.currentTimeMillis();
        int asrStamp = this.getStamp(currentTimestamp);
        int asrSlice = this.getSlice(currentTimestamp);
        int[] asrCurStamp = new int[1];
        ConcurrentMap<String, Histogram> currentHistogramMap = histogramAsrs[asrSlice].get(asrCurStamp);
        if (asrStamp != asrCurStamp[STAMP_INDEX]) {
            ConcurrentMap<String, Histogram> newHistogramMap = this.createHistogramMap();
            boolean casSuccess = histogramAsrs[asrSlice].compareAndSet(currentHistogramMap, newHistogramMap, asrCurStamp[0], asrStamp);
            if (casSuccess) {
                int offerSlice = (asrSlice + slice - 1) % slice;
                int[] asrPreStamp = new int[1];
                ConcurrentMap<String, Histogram> preHistogramMap = histogramAsrs[offerSlice].get(asrPreStamp);
                if (asrPreStamp[STAMP_INDEX] != 0 && asrPreStamp[STAMP_INDEX] < asrStamp) {
                    this.histogramQueue.offer(new HistogramMapSnap(currentTimestamp, asrPreStamp[STAMP_INDEX] * timeDivisionMs + TIME_20181010, timeDivisionMs, preHistogramMap));
                }
                currentHistogramMap = newHistogramMap;
            } else {
                currentHistogramMap = histogramAsrs[asrSlice].getReference();
            }
        }
        Histogram currentHistogram = currentHistogramMap.get(operationIdr);
        if (currentHistogram == null) {
            currentHistogram = new HistogramImpl();
            Histogram existHistogram = currentHistogramMap.putIfAbsent(operationIdr, currentHistogram);
            if (existHistogram != null) {
                currentHistogram = existHistogram;
            }
        }
        return currentHistogram;
    }

    private ConcurrentMap<String, Histogram> createHistogramMap() {
        return new ConcurrentHashMap<>(MAP_CAPACITY);
    }

    public Queue<HistogramMapSnap> getHistogramQueue() {
        return this.histogramQueue;
    }

    protected Map<String, Histogram> getCurrentHistogram() {
        long currentTimestamp = System.currentTimeMillis();
        int asrSlice = this.getSlice(currentTimestamp);
        return histogramAsrs[asrSlice].getReference();
    }
}
