package com.boost.box.multitask.controller;

import com.boost.box.multitask.service.MultiThreadsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 多线程任务处理
 * @author: qdj
 * @date: 2019-11-01 14:07
 **/

@RestController
@RequestMapping("/multi")
public class MultiTaskController {

    private static final Logger logger = LoggerFactory.getLogger(MultiTaskController.class);

    private static final String TASK_NAME_PREFIX = "任务";

    private final MultiThreadsService multiThreadsService;

    public MultiTaskController(MultiThreadsService multiThreadsService) {
        this.multiThreadsService = multiThreadsService;
    }

    /**
     * 异步处理任务
     * @param n 任务数量
     */
    @RequestMapping(value = "/async", method = RequestMethod.GET)
    public String async(@RequestParam(value = "n") int n){
        logger.info("异步处理请求的任务数{}", n);
        for (int i = 0; i < n; i++) {
            multiThreadsService.processByAsyncWay(TASK_NAME_PREFIX + i);
        }
        return "异步，收到任务数量：" + n;
    }

    /**
     * 同步处理任务
     */
    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    public Map<String, String> sync(@RequestParam(value = "n") int n) throws InterruptedException {
        logger.info("同步处理请求...");
        multiThreadsService.processBySyncWay(n);

        Map<String, String> result = new HashMap<>(16);

        result.put("code","OK");
        result.put("time", new Date().toString());
        return result;
    }

}