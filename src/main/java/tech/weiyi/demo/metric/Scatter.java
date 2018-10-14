package tech.weiyi.demo.metric;

public interface Scatter {

    int getIndex(int value);

    long[] getElapseScatter();

    void addElapse(int elapse);

}
