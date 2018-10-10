package tech.weiyi.demo.metric;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;

public class MapDistribution implements Distribution {

    private final ConcurrentMap<Integer, LongAdder> elapseScatter;

    /**
     * 公共构造函数
     */
    public MapDistribution() {
        super();
        elapseScatter = new ConcurrentHashMap<>();
    }

    public int getIndex(int value) {
        return value;
    }

    @Override
    public Map<Integer, Long> getElapseScatter() {
        Map<Integer, Long> result = new HashMap<>(elapseScatter.size());
        for (Map.Entry<Integer, LongAdder> entry : elapseScatter.entrySet()) {
            result.put(entry.getKey(), entry.getValue().sum());
        }
        return result;
    }

    @Override
    public void addElapse(int duration) {
        LongAdder longAdder = elapseScatter.get(duration);
        if (longAdder == null) {
            longAdder = new LongAdder();
            LongAdder existAdder = elapseScatter.putIfAbsent(duration, longAdder);
            if (existAdder != null) {
                longAdder = existAdder;
            }
        }
        longAdder.increment();

    }

    @Override
    public void reset() {
        elapseScatter.clear();
    }

}
