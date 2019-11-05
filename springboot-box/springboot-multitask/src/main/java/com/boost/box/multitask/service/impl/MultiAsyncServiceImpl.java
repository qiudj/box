package com.boost.box.multitask.service.impl;

import com.boost.box.multitask.common.ProtocolProcessTak;
import com.boost.box.multitask.service.MultiThreadsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: TODO
 * @author: qdj
 * @date: 2019-11-01 14:11
 **/
@Service
public class MultiAsyncServiceImpl implements MultiThreadsService {

    private static final Logger logger = LoggerFactory.getLogger(MultiAsyncServiceImpl.class);

    @Autowired
    private ThreadPoolExecutor executor;

    /**
     * asyncWorkExecutor表明执行当前方法的线程池，@Async注解将Service层的服务异步化，对方法的调用会被提交到线程池异步执行。
     * @param taskName 任务名称
     */
    @Override
    @Async("asyncWorkExecutor")
    public void doTask(String taskName) {
        try {
            // 模拟100~200ms的处理时间
            Thread.sleep(100 + new Random().nextInt(100));
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        logger.info("{}处理完成，处理线程：{}", taskName, Thread.currentThread().getName());
    }

    @Override
    public void processProtocol(String... protocolNumbers) {
        CountDownLatch countDownLatch = new CountDownLatch(protocolNumbers.length);
        List<FutureTask<ProtocolProcessTak>> taskList = new ArrayList<>();
        for (String protocolNumber : protocolNumbers) {
            ProtocolProcessTak task = new ProtocolProcessTak(countDownLatch, protocolNumber);
            FutureTask<ProtocolProcessTak> futureTask = new FutureTask<ProtocolProcessTak>(task);

            // 保存任务列表，让后面获取任务执行结果
            taskList.add(futureTask);
            executor.submit(futureTask);
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }

        // 这里所有任务已经处理完，可以接收任务的处理情况
        logger.info("协议处理完成，共{}个", protocolNumbers.length);
    }

    @Override
    public void process() {
        executor.submit(() -> System.out.println("当前线程:" + Thread.currentThread().getName()));
    }

}

