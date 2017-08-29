package com.market.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.market.utils.HttpUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FLY on 2017/7/23.
 */
public class T {

    public static void main(String[] args) throws Exception {
        // 比特儿、币安、币久、B8、云币、聚币、
        String url = "https://apim.bter.com/apim/v1/getArticleList";
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();

        paramList.add(new BasicNameValuePair("appKey", "1C843F4B-C351-4A9F-EB51-B722122341D5"));
        paramList.add(new BasicNameValuePair("appLang", "cn"));
        paramList.add(new BasicNameValuePair("cateId", "0"));
        paramList.add(new BasicNameValuePair("pageIndex", "1"));
        paramList.add(new BasicNameValuePair("pageSize", "20"));
        paramList.add(new BasicNameValuePair("sign", "294a4ad793c014aa578aa8e2b73f3a52"));
        paramList.add(new BasicNameValuePair("token", ""));

        JSONObject jsonObject = HttpUtils.httpPostWithResponseJSONObject(url, paramList, null);

//        "https://apim.bter.com/apim/v1/getArticle";
//        appKey	1C843F4B-C351-4A9F-EB51-B722122341D5
//        appLang	cn
//        id	16245
//        sign	ed11196cab2668646bd2d5fcaca36211
//        token

    }

}
