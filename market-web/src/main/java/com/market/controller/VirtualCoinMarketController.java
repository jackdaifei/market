package com.market.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.market.utils.HttpUtils;
import com.market.vo.CoinPriceVO;
import com.market.vo.CoinVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 17-7-17.
 */
@Controller
@RequestMapping(value = "/coin")
public class VirtualCoinMarketController {

    private Cache<String, List<CoinVO>> coinCache = CacheBuilder.newBuilder().maximumSize(10).expireAfterWrite(5, TimeUnit.SECONDS).build();


    private static final JSONObject coinSupport = new JSONObject();
    static {
        coinSupport.put("BTC", new ArrayList<CoinVO>());
        coinSupport.put("ETH", new ArrayList<CoinVO>());
        coinSupport.put("SNT", new ArrayList<CoinVO>());
        coinSupport.put("EOS", new ArrayList<CoinVO>());
        coinSupport.put("QTUM", new ArrayList<CoinVO>());
        coinSupport.put("1ST", new ArrayList<CoinVO>());
        coinSupport.put("BTS", new ArrayList<CoinVO>());
        coinSupport.put("ETC", new ArrayList<CoinVO>());
        coinSupport.put("LTC", new ArrayList<CoinVO>());
        coinSupport.put("ANS", new ArrayList<CoinVO>());
        coinSupport.put("GXS", new ArrayList<CoinVO>());
        coinSupport.put("GNT", new ArrayList<CoinVO>());
        coinSupport.put("OMG", new ArrayList<CoinVO>());
        coinSupport.put("PAY", new ArrayList<CoinVO>());
        coinSupport.put("CDT", new ArrayList<CoinVO>());
    }

    // https://coupon.jd.com/ilink/couponSendFront/send_index.action?key=f74f06c1ccde410e9092fcbe85bf8062&roleId=7479723&to=chongzhi.jd.com/&cpdad=1DLSUE

    @RequestMapping(value = "/info")
    public String info() {
        return "coin_info";
    }

    @RequestMapping(value = "/price")
    @ResponseBody
    public JSONObject price() {
        JSONObject coinJsonInfo = new JSONObject();
        try {
            List<CoinVO> coinInfoList = new ArrayList<CoinVO>();
            coinInfoList.addAll(getYunBiCoinInfoList());
            coinInfoList.addAll(getBterCoinInfoList());

            coinJsonInfo = formatPrice(coinInfoList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(coinJsonInfo);
        return coinJsonInfo;
    }

    @SuppressWarnings(value = "unchecked")
    private JSONObject formatPrice(List<CoinVO> coinInfoList) {
        for (CoinVO coinInfo : coinInfoList) {
            List<CoinVO> subList = (List<CoinVO>)coinSupport.get(coinInfo.getCoinName());
            if (CollectionUtils.isEmpty(subList)) {
                subList = new ArrayList<CoinVO>();
            }
            subList.add(coinInfo);
            if (StringUtils.isNotBlank(coinInfo.getCoinName())) {
                coinSupport.put(coinInfo.getCoinName(), subList);
            }
        }
        return coinSupport;
    }

    private List<CoinVO> getYunBiCoinInfoList() throws Exception {
        List<CoinVO> coinInfoList = coinCache.getIfPresent("yunbiCoinList");
        if (CollectionUtils.isNotEmpty(coinInfoList)) {
            return coinInfoList;
        }
        coinInfoList = new ArrayList<CoinVO>();
        try {
            JSONObject yunBiJson = HttpUtils.httpGetWithResponseJSONObject("https://yunbi.com/api/v2/tickers.json", getCommonHeaderArray());
            JSONArray coinTypeJSONArray = HttpUtils.httpGetWithResponseJSONArray("https://yunbi.com/api/v2/markets.json", getCommonHeaderArray());

            for (int i = 0; i < coinTypeJSONArray.size(); i++) {
                JSONObject coinType = coinTypeJSONArray.getJSONObject(i);
                String coinKey = coinType.getString("id");
                String coinKeyToUpperCase = coinKey.replace("cny", "").toUpperCase();
                if (coinSupport.containsKey(coinKeyToUpperCase)) {
                    String depthUrl = "https://yunbi.com/api/v2/depth.json?market=" + coinKey + "&limit=1";
                    try {
                        CoinVO coinVO = getYunBiCoinInfo(yunBiJson.getJSONObject(coinKey), HttpUtils.httpGetWithResponseJSONObject(depthUrl, getCommonHeaderArray()));
                        coinVO.setCoinName(coinKeyToUpperCase);
                        coinInfoList.add(coinVO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        coinCache.put("yunbiCoinList", coinInfoList);
        return coinInfoList;
    }

    private CoinVO getYunBiCoinInfo(JSONObject coinJSON, JSONObject depthJSON) throws Exception {
        CoinVO coinVO = new CoinVO();
        coinVO.setPlatFrom("云币");
        CoinPriceVO coinPriceVO = new CoinPriceVO();
        coinPriceVO.setCoinPrice(coinJSON.getJSONObject("ticker").getFloat("last"));

        coinPriceVO.setBuyFirstPrice(depthJSON.getJSONArray("bids").getJSONArray(0).getFloatValue(0));
        coinPriceVO.setBuyFirstCount(depthJSON.getJSONArray("bids").getJSONArray(0).getFloatValue(1));

        coinPriceVO.setSellFirstPrice(depthJSON.getJSONArray("asks").getJSONArray(0).getFloatValue(0));
        coinPriceVO.setSellFirstCount(depthJSON.getJSONArray("asks").getJSONArray(0).getFloatValue(1));

        coinVO.setCoinPriceVO(coinPriceVO);
        return coinVO;
    }

    private List<CoinVO> getBterCoinInfoList() throws Exception {
        List<CoinVO> coinInfoList = coinCache.getIfPresent("bterCoinList");
        if (CollectionUtils.isNotEmpty(coinInfoList)) {
            return coinInfoList;
        }
        coinInfoList = new ArrayList<CoinVO>();
        coinInfoList.add(getBterCoinInfo("BTC", "8884d0604cf15d0fa4d4704bd613e5f3"));
        coinInfoList.add(getBterCoinInfo("SNT", "bcb22a2897aeea31acd520fd28cad587"));
        coinInfoList.add(getBterCoinInfo("EOS", "d9e1d0a0b724e0607b687b557c63e562"));
        coinInfoList.add(getBterCoinInfo("ETH", "b9d073c4b4a2ad7dd4c4d20bc485c4af"));
        coinInfoList.add(getBterCoinInfo("QTUM", "3ad1cd073496457f41f02383ea7145a4"));
        coinInfoList.add(getBterCoinInfo("LTC", "56d7dd971e7aba15846b6156205ba58f"));
        coinInfoList.add(getBterCoinInfo("BTS", "a6d044aae5d1a3cb8ed2d5e338eb2a48"));
        coinInfoList.add(getBterCoinInfo("ETC", "b11c6ece83a1b2194529fdcc71c18e7d"));
        coinCache.put("bterCoinList", coinInfoList);
        return coinInfoList;
    }

    private CoinVO getBterCoinInfo(String currencyType, String sign) {
        CoinVO coinVO = new CoinVO();
        try {
            String bterUrl = "https://apim.bter.com/apim/v1/marketDepth";
            JSONObject jsonObject = HttpUtils.httpPostWithResponseJSONObject(bterUrl, getBterParamList(currencyType, sign), getCommonHeaderArray());
            coinVO.setPlatFrom("比特儿");
            coinVO.setCoinName(currencyType);
            CoinPriceVO coinPriceVO = new CoinPriceVO();
            coinPriceVO.setCoinPrice(jsonObject.getJSONObject("datas").getFloat("currentPrice"));

            coinPriceVO.setBuyFirstPrice(jsonObject.getJSONObject("datas").getJSONArray("bids").getJSONArray(0).getFloatValue(0));
            coinPriceVO.setBuyFirstCount(jsonObject.getJSONObject("datas").getJSONArray("bids").getJSONArray(0).getFloatValue(1));

            coinPriceVO.setSellFirstPrice(jsonObject.getJSONObject("datas").getJSONArray("asks").getJSONArray(0).getFloatValue(0));
            coinPriceVO.setSellFirstCount(jsonObject.getJSONObject("datas").getJSONArray("asks").getJSONArray(0).getFloatValue(1));
            coinVO.setCoinPriceVO(coinPriceVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coinVO;
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
