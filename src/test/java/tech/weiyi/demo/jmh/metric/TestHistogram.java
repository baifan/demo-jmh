package tech.weiyi.demo.jmh.metric;

import com.alibaba.fastjson.JSONObject;
import tech.weiyi.demo.metric.CommonHistogram;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class TestHistogram {

    public static final int THREAD_SIZE = 100;

    public static final int MAX_DURATION = 66666;

    public static void main(String[] args) throws InterruptedException {
        CommonHistogram histogram = new CommonHistogram();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_SIZE);
        CountDownLatch latch = new CountDownLatch(THREAD_SIZE);
        for (int i = 0; i < THREAD_SIZE; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 100000; j++) {
                    histogram.addElapse(ThreadLocalRandom.current().nextInt(MAX_DURATION));
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println(histogram.getTotalCall());
        System.out.println(histogram.getTotalElapse());
        System.out.println(JSONObject.toJSONString(histogram.getElapseScatter()));
    }
}
