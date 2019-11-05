package com.boost.box.multitask.service;

/**
 * @description: 一些关于线程池的示例
 * @author: qdj
 * @date: 2019-11-01 14:11
 **/
public interface MultiThreadsService {

    /**
     * 异步执行任务
     * @param taskName 任务名称
     */
    void doTask(String taskName);

    /**
     * 处理协议的方法
     * @param protocolNumbers 协议号
     */
    void processProtocol(String... protocolNumbers);

    /**
     * 处理
     */
    void process();
}
