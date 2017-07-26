package com.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 17-7-17.
 */
@Controller
@RequestMapping(value = "/virtual")
public class VirtualCoinMarketController {

    @RequestMapping(value = "/index")
    public String index() {
        return "first_vm";
    }

}
