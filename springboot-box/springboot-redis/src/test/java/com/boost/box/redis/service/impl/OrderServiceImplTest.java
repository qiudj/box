package com.boost.box.redis.service.impl;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class OrderServiceImplTest {

    @Test
    void doOrder() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 50; i++){
            service.submit(() -> new OrderServiceImpl().doOrder());
        }
        Thread.sleep(1000 * 300);
        service.shutdown();
    }
}