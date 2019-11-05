package com.boost.box.multitask.controller;

import com.boost.box.multitask.service.MultiThreadsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @description: TODO
 * @author: qdj
 * @date: 2019-11-01 14:07
 **/

@RestController
@RequestMapping("/multi")
public class MultiTaskController {

    private static final Logger logger = LoggerFactory.getLogger(MultiTaskController.class);

    private static final String TASK_NAME_PREFIX = "任务";

    @Autowired
    private MultiThreadsService multiThreadsService;

    /**
     * 后台异步处理任务，直接返回到前端
     * @param cnt 模拟的任务数量
     * @return
     */
    @RequestMapping(value = "/exec", method = RequestMethod.GET)
    public String doTask(@RequestParam(value = "cnt") int cnt){
        logger.info("请求执行的任务数为{}", cnt);
        for (int i = 0; i < cnt; i++) {
            multiThreadsService.doTask(TASK_NAME_PREFIX + i);
        }
        return "后台收到任务数目：" + cnt;
    }


    /**
     * 同步处理任务，
     * @param cnt 协议处理任务的数量
     * @return
     */
    @RequestMapping(value = "/process", method = RequestMethod.GET)
    public String process(@RequestParam(value = "cnt") int cnt){
        logger.info("请求处理协议数量{}", cnt);

        String[] protocolNumbers = new String[cnt];
        for (int i = 0; i < cnt; i++) {
            protocolNumbers[i] = String.valueOf(1000000 + new Random().nextInt(10000));
        }
        multiThreadsService.processProtocol(protocolNumbers);

        return "后台收到协议处理任务数量：" + cnt;
    }

    /**
     * 同步处理任务
     */
    @RequestMapping(value = "/p", method = RequestMethod.GET)
    public String doProcess(){
        multiThreadsService.process();
        return "OK";
    }

}
