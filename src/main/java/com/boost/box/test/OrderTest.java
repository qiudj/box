package com.boost.box.test;

import com.boost.box.service.impl.OrderServiceImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 50; i++){
            service.submit(() -> new OrderServiceImpl().doOrder());
        }
        Thread.sleep(1000 * 300);
        service.shutdown();
    }
}
