package tech.weiyi.demo.jmh.metric;

import com.alibaba.fastjson.JSONObject;
import tech.weiyi.demo.metric.HistogramImpl;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestHistogram {

    public static final int THREAD_SIZE = 100;

    public static final int MAX_ELAPSE = 66666;

    public static void main(String[] args) throws InterruptedException {
        HistogramImpl histogram = new HistogramImpl();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_SIZE);
        CountDownLatch latch = new CountDownLatch(THREAD_SIZE);
        for (int i = 0; i < THREAD_SIZE; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 65536; j++) {
                    histogram.addElapse(j, true);
                }
                for (int j = 0; j < 1; j++) {
                    histogram.addElapse(1, false);
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println(histogram.getTotalCall());
        System.out.println(histogram.getTotalElapseSuccess());
        System.out.println(JSONObject.toJSONString(histogram.getElapseScatterSuccess()));
        System.out.println(JSONObject.toJSONString(histogram.getTotalCallFailed()));
    }
}
