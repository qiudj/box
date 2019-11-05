package com.boost.box.multitask.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: TODO
 * @author: qdj
 * @date: 2019-11-04 10:27
 **/
@Configuration
public class ProtocolThreadPoolConfig {

    private static final Logger logger = LoggerFactory.getLogger(ProtocolThreadPoolConfig.class);

    @Bean
    public ThreadPoolExecutor protocolThreadPool(){
        logger.info("协议处理线程池配置开始...");

        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread();
                thread.setName("protocol-process-group" + threadNumber.getAndIncrement());
                return thread;
            }
        };
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4,10,100,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),threadFactory);

        logger.info("协议处理线程池初始化完成...");
        return executor;
    }


}
