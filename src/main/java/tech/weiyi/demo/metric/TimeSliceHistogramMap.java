package tech.weiyi.demo.metric;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;

public class TimeSliceHistogramMap {

    private static final int SLICE = 1 << 4;

    private static final int TIME_DIVISION_MS = 1000;

    public ConcurrentMap<MethodCall, TimeSliceHistogram> tsHistogramMap;

    private final int slice;

    private final int timeDivisionMs;

    private final LongAdder methodSize;

    /**
     * 公共构造函数
     */
    public TimeSliceHistogramMap() {
        this(SLICE, TIME_DIVISION_MS);

    }

    public TimeSliceHistogramMap(int slice, int timeDivisionMs) {
        super();
        this.slice = slice;
        this.timeDivisionMs = timeDivisionMs;
        this.methodSize = new LongAdder();
        this.tsHistogramMap = new ConcurrentHashMap<>(1024);
    }


    public long addElapse(MethodCall methodCall, int elapse) {
        TimeSliceHistogram histogram = tsHistogramMap.get(methodCall);
        if (histogram == null) {
            histogram = new TimeSliceHistogram(slice, timeDivisionMs);
            TimeSliceHistogram newHistogram = tsHistogramMap.putIfAbsent(methodCall, histogram);
            if (newHistogram != null) {
                histogram = newHistogram;
            } else {
                methodSize.increment();
            }
        }
        return histogram.addElapse(elapse);
    }

    public Map<String, HistogramSnap> getMethodHistogram(long timestamp) {
        Map<String, HistogramSnap> histogramSnapMap = new HashMap<>(methodSize.intValue());
        for (Map.Entry<MethodCall, TimeSliceHistogram> entry : tsHistogramMap.entrySet()) {
            TimeSliceHistogram timeSliceHistogram = entry.getValue();
            HistogramSnap histogramSnap = timeSliceHistogram.getHistogram(timestamp);
            if (histogramSnap == null) {
                continue;
            }
            histogramSnapMap.put(entry.getKey().toIdr(), histogramSnap);
        }
        return histogramSnapMap;
    }

}
