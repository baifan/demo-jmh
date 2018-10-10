package tech.weiyi.demo.metric;

import java.util.concurrent.atomic.AtomicStampedReference;

public class TimeSlideHistogram {

    private AtomicStampedReference<Histogram>[] histograms;

    private final long TIME_20181010 = 1539100800000L;

    private final int timeDivision;

    private final int slide;

    public TimeSlideHistogram(int slide, int timeDivision) {
        super();
        if ((slide & slide - 1) != 0) {
            throw new IllegalArgumentException("Slide should be 2's power : " + slide);
        }
        this.slide = slide;
        this.timeDivision = timeDivision;
        histograms = new AtomicStampedReference[slide];
        for (int i = 0; i < slide; i++) {
            histograms[i] = new AtomicStampedReference<>(new CommonHistogram(), getStamp(System.currentTimeMillis()));
        }
    }

    public void addElapse(int duration) {
        int newStamp = (int) ((System.currentTimeMillis() - TIME_20181010) / timeDivision);
        int index = newStamp % slide;
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
        int index = stamp % slide;
        AtomicStampedReference<Histogram> reference = histograms[index];

        if (reference.getStamp() != stamp) {
            return null;
        }
        Histogram histogram = reference.getReference();
        return new HistogramSnap(getDivisionStamp(timestamp), histogram.getTotalElapse(), histogram.getTotalCall(), histogram.getElapseScatter());
    }

    private long getDivisionStamp(long timestamp) {
        return (timestamp / timeDivision) * timeDivision;
    }

    private int getStamp(long timestamp) {
        return (int) ((timestamp - TIME_20181010) / timeDivision);
    }

}
