package tech.weiyi.demo.jmh.metric;

import com.alibaba.fastjson.JSONObject;
import tech.weiyi.demo.metric.TimeSlideHistogram;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestTimeSlideHistogram {

    public static void main(String[] args) throws InterruptedException {
        int threadSize = 100;
        int taskSize = 1;
        int taskLoop = 1;
        int timeDivision = 1000;
        TimeSlideHistogram timeSlideHistogram = new TimeSlideHistogram(16, timeDivision);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(taskSize);
        long timestamp = System.currentTimeMillis();
        for (int z = 0; z < taskSize; z++) {
            executorService.submit(() -> {

                try {
                    for (int j = 0; j < taskLoop; j++) {
                        for (int i = 0; i < 65535; i++) {
                            timeSlideHistogram.addElapse(i);
                        }
                        Thread.sleep(timeDivision);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }
        latch.await();

        System.out.println(JSONObject.toJSONString(timeSlideHistogram.getHistogram(timestamp), true));
    }
}
