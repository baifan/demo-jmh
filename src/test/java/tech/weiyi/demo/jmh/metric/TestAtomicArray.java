package tech.weiyi.demo.jmh.metric;

import java.util.concurrent.atomic.AtomicLongArray;

public class TestAtomicArray {
    public static void main(String[] args) {
        AtomicLongArray longArray = new AtomicLongArray(5);
        for (int i = 0; i < 1000; i++) {
            if (i < 5) {
                longArray.compareAndSet((i % 5), 0, i);
            } else {
                longArray.compareAndSet((i % 5), i - 5, i);
            }
        }
        System.out.println(longArray.toString());
    }
}
