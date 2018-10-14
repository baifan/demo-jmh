package tech.weiyi.demo.metric;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestHistogramPrecision {


    public static void main(String[] args) throws InterruptedException {
        final int timeDivisionMs = 10000;
        final int loop = 300;
        final int threadSize = 50;
        long currentTime = System.currentTimeMillis();
        Thread.sleep((currentTime / timeDivisionMs + 1) * timeDivisionMs - currentTime + 1);
        ExecutorService service = Executors.newFixedThreadPool(threadSize);
        TimeSliceHistogramCalculator histogram = new TimeSliceHistogramCalculator(timeDivisionMs, 2);

        CountDownLatch cdl = new CountDownLatch(threadSize);
        for (int i = 0; i < threadSize; i++) {
            service.submit(() -> {
                try {

                    for (int y = 0; y < loop; y++) {
                        for (int j = 0; j < 10 + y; j++) {
                            for (int x = 0; x < 65535; x++) {
                                histogram.addElapse(Long.toString(j), x, true);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cdl.countDown();
            });
        }
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            System.out.println(JSONObject.toJSONString(histogram.getHistogramQueue().poll(), true));
        }, timeDivisionMs, timeDivisionMs, TimeUnit.MILLISECONDS);

        cdl.await();
//        service.shutdown();
        System.out.println("Stop...................");
    }

}
