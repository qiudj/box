package com.boost.box.multitask.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 封装一个倒计数的实现Runnable接口的任务
 * @author: qdj
 * @date: 2019-11-01 15:32
 **/
public class CountDownRunnableTask implements Runnable {

    private CountDownLatch countDownLatch;
    private String taskName;
    private static final Logger logger = LoggerFactory.getLogger(CountDownRunnableTask.class);

    public CountDownRunnableTask(CountDownLatch countDownLatch, String protocolNumber){
        this.countDownLatch = countDownLatch;
        this.taskName = protocolNumber;
    }

    @Override
    public void run(){
        // 模拟一下任务处理 10~200毫秒
        try {
            Thread.sleep(10  + new Random().nextInt(190));
        } catch (InterruptedException e) {
            logger.info("CountDownRunnableTask执行异常：{}", e.getMessage());
        }

        logger.info("任务{}， 由线程{}处理完成。", taskName, Thread.currentThread().getName());
        countDownLatch.countDown();
    }
}
