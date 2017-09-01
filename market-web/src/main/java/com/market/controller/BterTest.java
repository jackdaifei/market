package com.market.controller;

import com.market.utils.HttpUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FLY on 2017/9/1.
 */
public class BterTest {

    public static void main(String[] args) throws Exception {

        api();

    }

    private static void api() throws Exception {
        String url = "https://apim.bter.com/apim/v1/getArticleList";

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("appKey", "1C843F4B-C351-4A9F-EB51-B722122341D5"));
        paramList.add(new BasicNameValuePair("appLang", "cn"));
        paramList.add(new BasicNameValuePair("cateId", "0"));
        paramList.add(new BasicNameValuePair("pageIndex", "1"));
        paramList.add(new BasicNameValuePair("pageSize", "20"));
        paramList.add(new BasicNameValuePair("sign", "294a4ad793c014aa578aa8e2b73f3a52"));
        paramList.add(new BasicNameValuePair("token", ""));

        HttpUtils.httpPostWithResponseJSONObject(url, paramList, null);
    }

    private static void web() throws Exception {
        String url = "https://bter.com/articlelist/ann";

        Header[] headers = new Header[]{
                new BasicHeader("Host", "bter.com"),
                new BasicHeader("Connection", "keep-alive"),
                new BasicHeader("Pragma", "no-cache"),
                new BasicHeader("Cache-Control", "no-cache"),
                new BasicHeader("Upgrade-Insecure-Requests", "1"),
                new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36"),
                new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"),
                new BasicHeader("Referer", "https://bter.com/"),
                new BasicHeader("Accept-Encoding", "gzip, deflate, br"),
                new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8"),
//                new BasicHeader("Cookie", "__cfduid=d19938d55df15ad5f158b20893459fc9e1499352303; captcha_reg=e5e5f9a24f3fd042bb07c8b79e6fd11e; notice=%E6%AF%94%E7%89%B9%E5%84%BF%E4%B8%8A%E7%BA%BFStorj%E4%BA%A4%E6%98%93%E5%85%AC%E5%91%8A; captcha=7c184e482cd79add734113e232a3ab1d; NB_SRVID=srv253295; nav_index=0")
        };
        HttpUtils.httpGetWithResponseString(url, headers);
    }
}
