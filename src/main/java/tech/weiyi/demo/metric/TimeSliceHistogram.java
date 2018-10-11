package tech.weiyi.demo.metric;

import java.util.concurrent.atomic.AtomicStampedReference;

public class TimeSliceHistogram {

    private AtomicStampedReference<Histogram>[] histograms;

    private final long TIME_20181010 = 1539100800000L;

    private final int timeDivisionMs;

    private final int slice;

    public TimeSliceHistogram(int slice, int timeDivisionMs) {
        super();
        if ((slice & slice - 1) != 0) {
            throw new IllegalArgumentException("Slice should be 2's power : " + slice);
        }
        this.slice = slice;
        this.timeDivisionMs = timeDivisionMs;
        histograms = new AtomicStampedReference[slice];
        for (int i = 0; i < slice; i++) {
            histograms[i] = new AtomicStampedReference<>(new CommonHistogram(), getStamp(System.currentTimeMillis()));
        }
    }

    public void addElapse(int duration) {
        int newStamp = (int) ((System.currentTimeMillis() - TIME_20181010) / timeDivisionMs);
        int index = newStamp % slice;
        AtomicStampedReference<Histogram> reference = histograms[index];
        Histogram histogram = reference.getReference();
        while (reference.getStamp() != newStamp) {
            histogram.reset();
            reference.attemptStamp(histogram, newStamp);
        }
        histogram.addElapse(duration);
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
        return new HistogramSnap(getDivisionStamp(timestamp), histogram.getTotalElapse(), histogram.getTotalCall(), histogram.getElapseScatter());
    }

    private long getDivisionStamp(long timestamp) {
        return (timestamp / timeDivisionMs) * timeDivisionMs;
    }

    private int getStamp(long timestamp) {
        return (int) ((timestamp - TIME_20181010) / timeDivisionMs);
    }

}
