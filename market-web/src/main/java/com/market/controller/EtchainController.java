package com.market.controller;

import com.alibaba.fastjson.JSONObject;
import com.market.utils.HttpUtils;
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
    public JSONObject checkAddress(String key, String address, HttpServletRequest request) throws Exception {
        guessAddress(key, address, "https://ico.etchain.org/etchain/walletDetail?walletAddress=" + address, "etchain.txt", true, request);
        guessAddress(key, address, "https://neo.org/ICO/query/" + address, "ant.txt", false, request);


        return null;
    }

    private void guessAddress(String key, String address, String url, String fileName, boolean isJsonResult, HttpServletRequest request) {
        boolean bingo = false;
        String r = "";
        try {
            if (isJsonResult) {
                JSONObject jsonObject = HttpUtils.httpGetWithResponseJSONObject(url, null);
                if ("0000".equals(jsonObject.getString("retCode"))) {
                    r = jsonObject.toJSONString();
                    bingo = true;
                }
            } else {
                String result = HttpUtils.httpGetWithResponseString(url, null);
                if (!"address not found".equals(result)) {
                    r = result;
                    bingo = true;
                }
            }
            if (bingo) {
                String sysPath = request.getSession().getServletContext().getRealPath("/");
                File file = new File(sysPath + "/" + fileName);
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write("key:" + key + "\taddress:" + address + "\t" + r);
                writer.newLine();
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                String sysPath = request.getSession().getServletContext().getRealPath("/");
                File file = new File(sysPath + "/error.txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write("key:" + key + "\taddress:" + address + "\t" + r);
                writer.newLine();
                writer.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
