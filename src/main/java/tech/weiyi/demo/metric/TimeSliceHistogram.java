package tech.weiyi.demo.metric;

import java.util.concurrent.atomic.AtomicStampedReference;

public class TimeSliceHistogram {

    private AtomicStampedReference<Histogram>[] histograms;

    private final long TIME_20181010 = 1539100800000L;

    private final int timeDivisionMs;

    private final int slice;

    public TimeSliceHistogram(int slice, int timeDivisionMs) {
        super();
        this.slice = slice;
        this.timeDivisionMs = timeDivisionMs;
        histograms = new AtomicStampedReference[slice];
        for (int i = 0; i < slice; i++) {
            histograms[i] = new AtomicStampedReference<>(getHistogram(), getStamp(System.currentTimeMillis()));
        }
    }

    protected Histogram getHistogram() {
        return new Histogram();
    }

    public long addElapse(int elapse) {
        int newStamp = (int) ((System.currentTimeMillis() - TIME_20181010) / timeDivisionMs);
        int index = newStamp % slice;
        AtomicStampedReference<Histogram> reference = histograms[index];
        Histogram histogram = reference.getReference();
        int oldStamp = reference.getStamp();
        if (oldStamp != newStamp) {
            Histogram newHistogram = getHistogram();
            if (reference.compareAndSet(histogram, newHistogram, oldStamp, newStamp)) {
                histogram = newHistogram;
            } else {
                histogram = reference.getReference();
            }
        }
        return histogram.addElapse(elapse);
    }

    /**
     * @param timestamp 时间戳
     * @return 如果没有该数据，则返回null
     */
    public HistogramSnap getHistogram(long timestamp) {
        int stamp = getStamp(timestamp);
        int index = stamp % slice;
        AtomicStampedReference<Histogram> reference = histograms[index];

        if (reference.getStamp() != stamp) {
            return null;
        }
        Histogram histogram = reference.getReference();
        long beginTimestamp = getDivisionStamp(timestamp);
        long endTimestamp = beginTimestamp + timeDivisionMs - 1;
        return new HistogramSnap(beginTimestamp, endTimestamp, histogram.getTotalElapse(), histogram.getTotalCall(), histogram.getElapseScatter());
    }

    private long getDivisionStamp(long timestamp) {
        return (timestamp / timeDivisionMs) * timeDivisionMs;
    }

    private int getStamp(long timestamp) {
        return (int) ((timestamp - TIME_20181010) / timeDivisionMs);
    }

}
