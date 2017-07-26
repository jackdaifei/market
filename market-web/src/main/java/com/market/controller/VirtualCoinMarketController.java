package com.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 17-7-17.
 */
@Controller
@RequestMapping(value = "/coin")
public class VirtualCoinMarketController {

    @RequestMapping(value = "/info")
    public String index() {
        return "coin_info";
    }

}
