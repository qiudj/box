package com.boost.box.multitask.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义同步线程池的线程工程类
 * @author: qdj
 * @date: 2019-11-07 16:55
 **/
public class SyncThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("sync-thread-group-" + threadNumber.getAndIncrement());
        return thread;
    }
}
