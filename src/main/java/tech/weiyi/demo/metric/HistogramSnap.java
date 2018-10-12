package tech.weiyi.demo.metric;

import java.util.Map;

public class HistogramSnap {

    private long begStamp;

    private long endStamp;

    private long totalElapse;

    private long totalCall;

    private Map<Integer, Long> scatter;

    /**
     * 公共构造函数
     */
    public HistogramSnap() {
        super();
    }

    public HistogramSnap(long begStamp, long endStamp, long totalElapse, long totalCall, Map<Integer, Long> scatter) {
        this.begStamp = begStamp;
        this.endStamp = endStamp;
        this.totalElapse = totalElapse;
        this.totalCall = totalCall;
        this.scatter = scatter;
    }

    public long getBegStamp() {
        return begStamp;
    }

    public void setBegStamp(long begStamp) {
        this.begStamp = begStamp;
    }

    public long getEndStamp() {
        return endStamp;
    }

    public void setEndStamp(long endStamp) {
        this.endStamp = endStamp;
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

    public Map<Integer, Long> getScatter() {
        return scatter;
    }

    public void setScatter(Map<Integer, Long> scatter) {
        this.scatter = scatter;
    }
}
