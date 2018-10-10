package tech.weiyi.demo.metric;

import java.util.Map;

public class HistogramSnap {

    private long timestamp;

    private long totalElapse;

    private long totalCall;

    private Map<Integer, Long> elapseScatter;

    /**
     * 公共构造函数
     */
    public HistogramSnap() {
        super();
    }

    public HistogramSnap(long timestamp, long totalElapse, long totalCall, Map<Integer, Long> elapseScatter) {
        this.timestamp = timestamp;
        this.totalElapse = totalElapse;
        this.totalCall = totalCall;
        this.elapseScatter = elapseScatter;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTotalElapse() {
        return totalElapse;
    }

    public void setTotalElapse(long totalElapse) {
        this.totalElapse = totalElapse;
    }

    public long getTotalCall() {
        return totalCall;
    }

    public void setTotalCall(long totalCall) {
        this.totalCall = totalCall;
    }

    public Map<Integer, Long> getElapseScatter() {
        return elapseScatter;
    }

    public void setElapseScatter(Map<Integer, Long> elapseScatter) {
        this.elapseScatter = elapseScatter;
    }
}
