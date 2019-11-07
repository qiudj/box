package com.boost.box.multitask.service.impl;

import com.boost.box.multitask.common.CountDownRunnableTask;
import com.boost.box.multitask.service.MultiThreadsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 实现类impl
 * @author: qdj
 * @date: 2019-11-01 14:11
 **/
@Service
public class MultiAsyncServiceImpl implements MultiThreadsService {

    private static final Logger logger = LoggerFactory.getLogger(MultiAsyncServiceImpl.class);

    /** 构造器方式注入同步线程池 */
    private final ThreadPoolExecutor syncWorkExecutor;

    public MultiAsyncServiceImpl(@Qualifier("syncWorkExecutor") ThreadPoolExecutor executor) {
        this.syncWorkExecutor = executor;
    }

    /**
     * 01 异步执行
     * asyncWorkExecutor指明执行当前方法的线程池，@Async注解将Service层的服务异步化，对方法的调用会被提交到线程池异步的执行。
     * @param taskName 任务名称
     */
    @Override
    @Async("asyncWorkExecutor")
    public void processByAsyncWay(String taskName) {
        try {
            // 模拟100~200ms的处理时间
            Thread.sleep(100 + new Random().nextInt(100));
        } catch (Exception e) {
            logger.info("异步处理异常：{}", e.getMessage());
        }
        logger.info("{}任务已经处理完成，异步处理线程：{}", taskName, Thread.currentThread().getName());
    }

    /**
     * 02 同步执行CountDownRunnableTask
     * @param taskNumber 任务数量
     */
    @Override
    public void processBySyncWay(int taskNumber) {
        CountDownLatch countDownLatch = new CountDownLatch(taskNumber);
        for (int i = 0; i < taskNumber; i++) {
            syncWorkExecutor.execute(new CountDownRunnableTask(countDownLatch, String.valueOf(i)));
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }
        logger.info("同步处理任务完成，共{}个。", taskNumber);
    }

}