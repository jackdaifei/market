package com.market.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.market.utils.HttpUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by FLY on 2017/7/15.
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {


    @RequestMapping(value = "/")
    @ResponseBody
    public JSONObject test(String key, String address) {
        try {
            File file = new File("D:\\Java Project\\market\\market-web\\etchain.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            String url = "https://ico.etchain.org/etchain/walletDetail?walletAddress=" + address;
            JSONObject json = HttpUtils.httpGetWithResponseJSON(url, null);
            System.out.println(json);
            if ("0000".equals(json.getString("retCode"))) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write("key:" + key + ", address:" + address);
                writer.newLine();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/a")
    @ResponseBody
    public JSONObject a() {
        return new JSONObject();
    }

}
