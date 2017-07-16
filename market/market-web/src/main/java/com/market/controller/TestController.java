package com.market.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by FLY on 2017/7/15.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {


    @RequestMapping(value = "/")
    @ResponseBody
    public JSONObject test() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("1", "1");
        jsonObject.put("2", "2");
        jsonObject.put("3", "3");
        return jsonObject;
    }

}
