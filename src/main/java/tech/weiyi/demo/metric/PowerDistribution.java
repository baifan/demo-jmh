package tech.weiyi.demo.metric;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

public class PowerDistribution implements Distribution {

    public static final int STEP_ONE = 1 << 1;

    public static final int STEP_TWO = 1 << 2;

    public static final int STEP_FOUR = 1 << 4;

    public static final int STEP_EIGHT = 1 << 8;

    public static final int MAX_INDEX = 8 + 4 + 2 + 1 + 1;

    private final LongAdder[] elapseScatter;

    /**
     * 公共构造函数
     */
    public PowerDistribution() {
        super();
        elapseScatter = new LongAdder[MAX_INDEX + 1];
        for (int i = 0; i <= MAX_INDEX; i++) {
            elapseScatter[i] = new LongAdder();
        }
    }

    public int getIndex(int value) {
        int index = 0;
        if (value >= STEP_EIGHT) {
            value = value >> 8;
            index += 8;
        }
        if (value >= STEP_FOUR) {
            value = value >> 4;
            index += 4;
        }
        if (value >= STEP_TWO) {
            value = value >> 2;
            index += 2;
        }

        if (value >= STEP_ONE) {
            value = value >> 1;
            index += 1;
        }
        if (value >= 1) {
            index += 1;
        }
        return index;
    }

    @Override
    public Map<Integer, Long> getElapseScatter() {
        Map<Integer, Long> scatter = new HashMap<>();
        long value = elapseScatter[0].sum();
        if (value != 0) {
            scatter.put(0, value);
        }
        for (int i = 1; i <= MAX_INDEX; i++) {
            scatter.put(1 << (i - 1), elapseScatter[i].sum());
        }
        return scatter;
    }

    @Override
    public void addElapse(int duration) {
        elapseScatter[this.getIndex(duration)].increment();
    }

    @Override
    public void reset() {
        for (LongAdder londAdder : elapseScatter) {
            londAdder.reset();
        }
    }

}
