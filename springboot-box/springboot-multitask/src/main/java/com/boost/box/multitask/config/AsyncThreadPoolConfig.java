package com.boost.box.multitask.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * SpringBoot异步线程池，@EnableAsync注解开启SpringBoot对异步任务的支持。
 * @author: qdj
 * @date: 2019-11-01 13:57
 **/
@Configuration
@EnableAsync
public class AsyncThreadPoolConfig {

    private static final Logger logger = LoggerFactory.getLogger(AsyncThreadPoolConfig.class);

    @Bean
    public Executor asyncWorkExecutor(){
        logger.info("异步线程池配置开始...");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(10000);
        executor.setThreadNamePrefix("async-threads-group-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        logger.info("异步线程池初始化完成...");
        return executor;
    }


}
