package tech.weiyi.demo.metric;

public class HistogramSnap implements Histogram {

    private long totalCall;

    private long totalCallFailed;

    private long totalElapseSuccess;

    private long[] elapseScatterSuccess;

    /**
     * 公共构造函数
     */
    public HistogramSnap() {
        super();
    }

    @Override
    public long addElapse(int elapse, boolean isSuccess) {
        return 0;
    }

    @Override
    public long getTotalCall() {
        return totalCall;
    }

    public void setTotalCall(long totalCall) {
        this.totalCall = totalCall;
    }

    @Override
    public long getTotalCallFailed() {
        return totalCallFailed;
    }

    public void setTotalCallFailed(long totalCallFailed) {
        this.totalCallFailed = totalCallFailed;
    }

    @Override
    public long getTotalElapseSuccess() {
        return totalElapseSuccess;
    }

    public void setTotalElapseSuccess(long totalElapseSuccess) {
        this.totalElapseSuccess = totalElapseSuccess;
    }

    @Override
    public long[] getElapseScatterSuccess() {
        return elapseScatterSuccess;
    }

    public void setElapseScatterSuccess(long[] elapseScatterSuccess) {
        this.elapseScatterSuccess = elapseScatterSuccess;
    }
}
