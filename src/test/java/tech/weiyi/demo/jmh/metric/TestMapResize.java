package tech.weiyi.demo.jmh.metric;

import java.util.HashMap;
import java.util.Map;

public class TestMapResize {
    public static void main(String[] args) {
        Map<Integer, Integer> map64 = new HashMap<>(56);
        for (int i = 0; i < 64; i++) {
            map64.put(i, i);
        }

    }
}
