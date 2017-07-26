package com.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 17-7-17.
 */
@Controller
@RequestMapping(value = "/coin")
public class VirtualCoinMarketController {

    // https://coupon.jd.com/ilink/couponSendFront/send_index.action?key=f74f06c1ccde410e9092fcbe85bf8062&roleId=7479723&to=chongzhi.jd.com/&cpdad=1DLSUE

    @RequestMapping(value = "/info")
    public String index() {
        return "coin_info";
    }

}
