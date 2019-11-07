package com.boost.box.multitask.config;

import com.boost.box.multitask.common.SyncThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;ß
import java.util.concurrent.TimeUnit;

/**
 * 同步线程池配置
 * @author: qdj
 * @date: 2019-11-04 10:27
 **/
@Configuration
public class SyncThreadPoolConfig {

    private static final Logger logger = LoggerFactory.getLogger(SyncThreadPoolConfig.class);

    @Bean(value = "syncWorkExecutor")
    public ThreadPoolExecutor syncWorkExecutor(){
        logger.info("同步线程池配置开始...");

        // 拒绝策略，调用线程处理
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4,10,100,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000), new SyncThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        logger.info("同步线程池初始化完成，线程池初始大小{}，最大大小{}", executor.getPoolSize(), executor.getMaximumPoolSize());
        return executor;
    }

}