package com.market.controller;

import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by Administrator on 17-7-29.
 */
@Controller
public class EtchainController {

    @RequestMapping("/etchain/checkAddress")
    @ResponseBody
    public JSONObject checkAddress(String pk, String address, HttpServletRequest request) throws Exception {
        String url = "https://ico.etchain.org/etchain/walletDetail?walletAddress=" + address;
        HttpGet get = new HttpGet(url);
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        CloseableHttpResponse response = client.execute(get);
        String responseStr = EntityUtils.toString(response.getEntity());
//        System.out.println(responseStr);
        response.close();
        JSONObject jsonObject = JSONObject.parseObject(responseStr);
        if ("0000".equals(jsonObject.getString("retCode"))) {
            String sysPath = request.getSession().getServletContext().getRealPath("/");
            File file = new File(sysPath + "/etchain.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write("key:" + pk + "\taddress:" + address);
            writer.newLine();
            writer.close();
        }
        return null;
    }

}
