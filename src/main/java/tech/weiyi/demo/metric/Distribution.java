package tech.weiyi.demo.metric;

import java.util.Map;

public interface Distribution {

    int getIndex(int value);

    Map<Integer, Long> getElapseScatter();

    void addElapse(int elapse);

}
