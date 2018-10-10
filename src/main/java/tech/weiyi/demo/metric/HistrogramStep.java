package tech.weiyi.demo.metric;

public class HistrogramStep {

    public static final int ZERO = 0;

    public static final int ONE = 1;

    public static final int STEP_ONE = 1 << 1;

    public static final int STEP_TWO = 1 << 2;

    public static final int STEP_FOUR = 1 << 4;

    public static final int STEP_EIGHT = 1 << 8;

    public static final int ARRY_LENGTH = 8 + 4 + 2 + 1 + 1 + 1;

    public static int getIndex(long value) {
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

}
