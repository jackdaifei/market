package com.market.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.market.utils.HttpUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 17-7-17.
 */
@Controller
@RequestMapping(value = "/coin")
public class VirtualCoinMarketController {

    private static final JSONObject coinSupport = new JSONObject();
    static {
        coinSupport.put("BTC", true);
        coinSupport.put("ETH", true);
        coinSupport.put("SNT", true);
        coinSupport.put("EOS", true);
        coinSupport.put("QTUM", true);
        coinSupport.put("1ST", true);
        coinSupport.put("BTS", true);
        coinSupport.put("ETC", true);
        coinSupport.put("ANS", true);
        coinSupport.put("GXS", true);
        coinSupport.put("GNT", true);
    }

    // https://coupon.jd.com/ilink/couponSendFront/send_index.action?key=f74f06c1ccde410e9092fcbe85bf8062&roleId=7479723&to=chongzhi.jd.com/&cpdad=1DLSUE

    @RequestMapping(value = "/info")
    public String info() {
        return "coin_info";
    }

    @RequestMapping(value = "/price")
    @ResponseBody
    public JSONObject price() {
//        bterJSON();


        return null;
    }

    private JSONObject yunBiJSON() throws Exception {
        JSONObject coinTypeJSON = HttpUtils.httpGetWithResponseJSON("https://yunbi.com//api/v2/markets.json", getCommonHeaderArray());
        Set<String> ketSet = coinTypeJSON.keySet();
        for (String key : ketSet) {

        }


        return null;
    }

    private JSONObject bterJSON() throws Exception {
        JSONObject bterJSON = new JSONObject();

        bterJSON.put("BTC", getBterJSON("BTC", ""));
        bterJSON.put("SNT", getBterJSON("SNT", "bcb22a2897aeea31acd520fd28cad587"));
        bterJSON.put("EOS", getBterJSON("EOS", "d9e1d0a0b724e0607b687b557c63e562"));
        bterJSON.put("ETH", getBterJSON("ETH", "b9d073c4b4a2ad7dd4c4d20bc485c4af"));
        bterJSON.put("QTUM", getBterJSON("QTUM", "3ad1cd073496457f41f02383ea7145a4"));

        return bterJSON;
    }

    private JSONObject getBterJSON(String currencyType, String sign) {
        try {
            String bterUrl = "https://apim.bter.com/apim/v1/marketDepth";
            JSONObject jsonObject = HttpUtils.httpPostWithResponseJSON(bterUrl, getBterParamList(currencyType, sign), getCommonHeaderArray());

            JSONObject json = new JSONObject();
            json.put("price", jsonObject.getJSONObject("datas").getFloat("currentPrice"));
            JSONObject sellFirstJSON = new JSONObject();
            sellFirstJSON.put("price", jsonObject.getJSONObject("datas").getJSONArray("asks").getJSONArray(0).getFloatValue(0));
            sellFirstJSON.put("count", jsonObject.getJSONObject("datas").getJSONArray("asks").getJSONArray(0).getFloatValue(1));
            json.put("sellFirst", sellFirstJSON);
            JSONObject buyFirstJSON = new JSONObject();
            buyFirstJSON.put("price", jsonObject.getJSONObject("datas").getJSONArray("bids").getJSONArray(0).getFloatValue(0));
            buyFirstJSON.put("count", jsonObject.getJSONObject("datas").getJSONArray("bids").getJSONArray(0).getFloatValue(1));
            json.put("buyFirst", buyFirstJSON);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    private List<NameValuePair> getBterParamList(String currencyType, String sign) {
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("appKey", "1C843F4B-C351-4A9F-EB51-B722122341D5"));
        paramList.add(new BasicNameValuePair("appLang", "cn"));
        paramList.add(new BasicNameValuePair("currencyType", currencyType));
        paramList.add(new BasicNameValuePair("depth", "0.1"));
        paramList.add(new BasicNameValuePair("exchangeType", "CNY"));
        paramList.add(new BasicNameValuePair("length", "20"));
        paramList.add(new BasicNameValuePair("sign", sign));
        paramList.add(new BasicNameValuePair("token", ""));
        return paramList;
    }

    private List<Header> getCommonHeaderList() {
        List<Header> headerList = new ArrayList<Header>();
        headerList.add(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
        headerList.add(new BasicHeader("Accept", "application/json"));
        return headerList;
    }

    private Header[] getCommonHeaderArray() {
        List<Header> headerList = getCommonHeaderList();
        Header[] headers = new Header[headerList.size()];
        return headerList.toArray(headers);
    }
}
