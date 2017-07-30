package com.market.controller;

import com.alibaba.fastjson.JSON;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.market.utils.HttpUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

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
        // 得到浏览器对象，直接New一个就能得到，现在就好比说你得到了一个浏览器了
        WebClient webclient = new WebClient(BrowserVersion.CHROME);

        // 这里是配置一下不加载css和javaScript,配置起来很简单，是不是
//        webclient.getOptions().setCssEnabled(false);
//        webclient.getOptions().setJavaScriptEnabled(false);
        webclient.getOptions().setJavaScriptEnabled(true);
        webclient.getOptions().setActiveXNative(false);
        webclient.getOptions().setCssEnabled(false);
        webclient.getOptions().setThrowExceptionOnScriptError(false);
//        webclient.waitForBackgroundJavaScript(600*1000);
        webclient.setAjaxController(new NicelyResynchronizingAjaxController());

        // 做的第一件事，去拿到这个网页，只需要调用getPage这个方法即可
        HtmlPage htmlpage = webclient.getPage("http://127.0.0.1");
        webclient.waitForBackgroundJavaScript(1000 * 3);
        webclient.setJavaScriptTimeout(0);

        ScriptResult s = htmlpage.executeJavaScript("createAccount()");
//        System.out.println(JSON.toJSON(s.getJavaScriptResult()));

        HtmlPage nextPage = (HtmlPage) s.getNewPage();
        DomElement lblPK = nextPage.getElementById("lbl_pk");
        DomElement address = nextPage.getElementById("lbl_address_a");

        System.out.println(lblPK.asText());
        System.out.println(address.asText());


        System.out.println(nextPage.asText());


        // 我把结果转成String
//        String result = htmlpage.asXml();


//        System.out.println(htmlpage.asXml());
    }

}
