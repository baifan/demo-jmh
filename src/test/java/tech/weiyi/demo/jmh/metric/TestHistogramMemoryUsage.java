package tech.weiyi.demo.jmh.metric;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tech.weiyi.demo.metric.TimeSliceHistogramCalculator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class TestHistogramMemoryUsage {


    public static void main(String[] args) throws InterruptedException {
        final int timeDivisionMs = 60000;
        final int threadSize = 50;
        final int eachThreadLoopSize = 800;
        ExecutorService service = Executors.newFixedThreadPool(threadSize);
        TimeSliceHistogramCalculator histogram = new TimeSliceHistogramCalculator(timeDivisionMs, 2);
        AtomicBoolean ab = new AtomicBoolean(true);
        for (int i = 0; i < threadSize; i++) {
            service.submit(() -> {
                while (ab.get()) {
                    for (int j = 0; j < eachThreadLoopSize; j++) {
                        histogram.addElapse(Long.toString(j), ThreadLocalRandom.current().nextInt(65535), true);
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        Thread printMetric = new Thread(() -> {
            do {
                try {
                    Thread.sleep(timeDivisionMs);
                    System.out.println("===============================================");
                    System.out.println(JSONObject.toJSONString(histogram.getHistogramQueue().poll(), SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.PrettyFormat));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (ab.get());
        });
        printMetric.start();

        Thread.sleep(timeDivisionMs * 100);
        ab.set(false);
        service.shutdown();

        System.out.println("Shutdown............................");

    }

}
