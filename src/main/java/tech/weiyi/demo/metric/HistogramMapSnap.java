package tech.weiyi.demo.metric;

import java.util.Map;

public class HistogramMapSnap {

    private long stamp;

    private long timeDivisionMs;

    private long createTime;

    private Map<String, Histogram> histogramMap;

    /**
     * 公共构造函数
     */
    public HistogramMapSnap() {
        super();
    }

    public HistogramMapSnap(long createTime, long stamp, long timeDivisionMs, Map<String, Histogram> histogramMap) {
        this.createTime = createTime;
        this.stamp = stamp;
        this.timeDivisionMs = timeDivisionMs;
        this.histogramMap = histogramMap;

    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getStamp() {
        return stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

    public long getTimeDivisionMs() {
        return timeDivisionMs;
    }

    public void setTimeDivisionMs(long timeDivisionMs) {
        this.timeDivisionMs = timeDivisionMs;
    }

    public Map<String, Histogram> getHistogramMap() {
        return histogramMap;
    }

    public void setHistogramMap(Map<String, Histogram> histogramMap) {
        this.histogramMap = histogramMap;
    }
}
