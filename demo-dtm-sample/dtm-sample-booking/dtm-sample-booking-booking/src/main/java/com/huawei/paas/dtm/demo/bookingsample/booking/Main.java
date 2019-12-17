package com.huawei.paas.dtm.demo.bookingsample.booking;

import org.apache.servicecomb.foundation.common.NamedThreadFactory;
import org.apache.servicecomb.foundation.common.utils.BeanUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        BeanUtils.init();

        int threadCount = Integer.parseInt(args[0]);
        int interval = Integer.parseInt(args[1]);

        BookingConsumer bookingConsumer = BeanUtils.getContext().getBean(BookingConsumer.class);

        Executor executor = Executors.newFixedThreadPool(threadCount, new NamedThreadFactory("booking-consumer"));
        for (int i = 0; i < threadCount; i++) {
            executor.execute(() -> {
                while (true) {
                    try {
                        bookingConsumer.book();
//                        TimeUnit.NANOSECONDS.sleep(interval);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
