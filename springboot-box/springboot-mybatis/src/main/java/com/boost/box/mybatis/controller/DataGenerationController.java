package com.boost.box.mybatis.controller;

import com.boost.box.mybatis.service.DataGenerationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: qdj
 * @date: 2019-11-08 16:14
 **/
@RestController
@RequestMapping("/gen")
public class DataGenerationController {

    private final DataGenerationService dataGenerationService;

    public DataGenerationController(DataGenerationService dataGenerationService) {
        this.dataGenerationService = dataGenerationService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Map<String, String> generateUser(@RequestParam(name = "cnt") int cnt){
        dataGenerationService.generateUser(cnt);

        Map<String, String > result = new HashMap<>(16);
        result.put("status", "OK");
        result.put("time", new Date().toString());
        return result;
    }
}
