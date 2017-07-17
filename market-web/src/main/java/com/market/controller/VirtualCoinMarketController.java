package com.market.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 17-7-17.
 */
@Controller
@RequestMapping(value = "/virtual")
public class VirtualCoinMarketController {

    @RequestMapping(value = "/index")
    @ResponseBody
    public JSONObject index() {

        return null;
    }

}
