package com.market.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.List;

/**
 * Created by Administrator on 17-7-17.
 */
public class HttpUtils {

    /**
     * post请求，返回字符串
     * @param url
     * @param paramList
     * @param headers
     * @return
     * @throws Exception
     */
    public static String httpPostWithResponseString(String url, List<NameValuePair> paramList, Header[] headers) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        if (CollectionUtils.isNotEmpty(paramList)) {
            httpPost.setEntity(new UrlEncodedFormEntity(paramList));
        }
        if (ArrayUtils.isNotEmpty(headers)) {
            httpPost.setHeaders(headers);
        }
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setStaleConnectionCheckEnabled(true)
                .build();

        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        CloseableHttpResponse response = client.execute(httpPost);

        String responseStr = EntityUtils.toString(response.getEntity(), "utf-8");
        response.close();
        return responseStr;
    }

    /**
     * post请求，返回JSON
     * @param url
     * @param paramList
     * @param headers
     * @return
     * @throws Exception
     */
    public static JSONObject httpPostWithResponseJSON(String url, List<NameValuePair> paramList, Header[] headers) throws Exception {
        String responseStr = httpPostWithResponseString(url, paramList, headers);
        return JSONObject.parseObject(responseStr);
    }

    /**
     * post请求，返回JSONArray
     * @param url
     * @param paramList
     * @param headers
     * @return
     * @throws Exception
     */
    public static JSONArray httpPostWithResponseJSONArray(String url, List<NameValuePair> paramList, Header[] headers) throws Exception {
        String responseStr = httpPostWithResponseString(url, paramList, headers);
        return JSONArray.parseArray(responseStr);
    }

    /**
     * get请求，返回字符串
     * @param url
     * @param headers
     * @return
     * @throws Exception
     */
    public static String httpGetWithResponseString(String url, Header[] headers) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        if (ArrayUtils.isNotEmpty(headers)) {
            httpGet.setHeaders(headers);
        }
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setStaleConnectionCheckEnabled(true)
                .build();
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();

        CloseableHttpResponse response = client.execute(httpGet);
        String responseStr = EntityUtils.toString(response.getEntity());
        response.close();
        return responseStr;
    }

    /**
     * get请求，返回JSON
     * @param url
     * @param headers
     * @return
     * @throws Exception
     */
    public static JSONObject httpGetWithResponseJSON(String url, Header[] headers) throws Exception {
        String responseStr = httpGetWithResponseString(url, headers);
        return JSONObject.parseObject(responseStr);
    }

}
