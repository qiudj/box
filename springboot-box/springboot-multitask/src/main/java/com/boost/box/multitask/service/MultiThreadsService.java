package com.boost.box.multitask.service;

/**
 * 同步和异步线程池
 * @author: qdj
 * @date: 2019-11-01 14:11
 **/
public interface MultiThreadsService {

    /**
     * 异步执行任务
     * @param taskName 任务名称
     */
    void processByAsyncWay(String taskName);

    /**
     * 处理协议的方法
     * @param taskNumber 线程数量
     */
    void processBySyncWay(int taskNumber);

}
