package com.boost.box.multitask.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @description: TODO
 * @author: qdj
 * @date: 2019-11-01 15:32
 **/
public class ProtocolProcessTak implements Callable {

    private CountDownLatch countDownLatch;
    private String protocolNumber;
    private static final Logger logger = LoggerFactory.getLogger(ProtocolProcessTak.class);

    public ProtocolProcessTak(CountDownLatch countDownLatch, String protocolNumber){
        this.countDownLatch = countDownLatch;
        this.protocolNumber = protocolNumber;
    }

    @Override
    public Object call() throws Exception {
        // 模拟一下任务处理
        Thread.sleep(100  + new Random().nextInt(1000));

        logger.info("协议{} 由线程{} 处理完成。",protocolNumber, Thread.currentThread().getName());
        countDownLatch.countDown();

        // 这里暂时不做返回
        return null;
    }
}
