package tech.weiyi.demo.metric;

import java.util.concurrent.atomic.LongAdder;

public class PowerScatter implements Scatter {

    public static final int STEP_ONE = 1 << 1;

    public static final int STEP_TWO = 1 << 2;

    public static final int STEP_FOUR = 1 << 4;

    public static final int STEP_EIGHT = 1 << 8;

    public static final int MAX_INDEX = 8 + 4 + 2 + 1 + 1;

    private final LongAdder[] elapseScatter;

    /**
     * 公共构造函数
     */
    public PowerScatter() {
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
    public long[] getElapseScatter() {
        long[] result = new long[MAX_INDEX + 1];
        for (int i = 0; i <= MAX_INDEX; i++) {
            result[i] = elapseScatter[i].sum();
        }
        return result;
    }

    @Override
    public void addElapse(int elapse) {
        elapseScatter[this.getIndex(elapse)].increment();
    }

}
