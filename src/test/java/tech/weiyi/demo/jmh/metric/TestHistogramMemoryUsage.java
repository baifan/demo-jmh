package tech.weiyi.demo.jmh.metric;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import tech.weiyi.demo.metric.MethodCall;
import tech.weiyi.demo.metric.TimeSliceHistogramMap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class TestHistogramMemoryUsage {


    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(50);
        TimeSliceHistogramMap histogram = new TimeSliceHistogramMap(16, 60000);
        AtomicBoolean ab = new AtomicBoolean(true);
        for (int i = 0; i < 50; i++) {
            service.submit(() -> {
                while (ab.get()) {
                    for (int j = 0; j < 700; j++) {
                        histogram.addElapse(new MethodCall(j, Long.toString(j)), ThreadLocalRandom.current().nextInt(65535));
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
                    Thread.sleep(60000);
                    System.out.println("===============================================");
                    System.out.println(JSONObject.toJSONString(histogram.getMethodHistogram(System.currentTimeMillis() - 60000), SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.PrettyFormat));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (ab.get());
        });
        printMetric.start();

        Thread.sleep(100000000);
        ab.set(false);
        service.shutdown();

        System.out.println("Shutdown............................");

    }

}
