package com.market.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.market.utils.HttpUtils;
import com.market.vo.CoinPriceVO;
import com.market.vo.CoinVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 17-7-17.
 */
@Controller
@RequestMapping(value = "/coin")
public class VirtualCoinMarketController {

    private static final Map<String, List<CoinVO>> coinInfoMapList = new LinkedHashMap<String, List<CoinVO>>();
    static {
        coinInfoMapList.put("BTC", new ArrayList<CoinVO>());
        coinInfoMapList.put("ETH", new ArrayList<CoinVO>());
        coinInfoMapList.put("SNT", new ArrayList<CoinVO>());
        coinInfoMapList.put("EOS", new ArrayList<CoinVO>());
        coinInfoMapList.put("QTUM", new ArrayList<CoinVO>());
        coinInfoMapList.put("1ST", new ArrayList<CoinVO>());
        coinInfoMapList.put("BTS", new ArrayList<CoinVO>());
        coinInfoMapList.put("ETC", new ArrayList<CoinVO>());
        coinInfoMapList.put("LTC", new ArrayList<CoinVO>());
        coinInfoMapList.put("ANS", new ArrayList<CoinVO>());
        coinInfoMapList.put("GXS", new ArrayList<CoinVO>());
        coinInfoMapList.put("GNT", new ArrayList<CoinVO>());
        coinInfoMapList.put("OMG", new ArrayList<CoinVO>());
        coinInfoMapList.put("PAY", new ArrayList<CoinVO>());
        coinInfoMapList.put("CDT", new ArrayList<CoinVO>());
    }

    // https://coupon.jd.com/ilink/couponSendFront/send_index.action?key=f74f06c1ccde410e9092fcbe85bf8062&roleId=7479723&to=chongzhi.jd.com/&cpdad=1DLSUE

    @RequestMapping(value = "/info")
    public String info() {
        return "coin_info";
    }

    @RequestMapping(value = "/price")
    @ResponseBody
    public Map<String, List<CoinVO>> price() {
        return coinInfoMapList;
    }

    @RequestMapping(value = "/start")
    @ResponseBody
    public JSONObject start() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        yunBiCoinInfoList();
                        bterCoinInfoList();
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("start", "success");
        return jsonObject;
    }

    private void yunBiCoinInfoList() throws Exception {
        try {
            JSONObject yunBiJson = HttpUtils.httpGetWithResponseJSONObject("https://yunbi.com/api/v2/tickers.json", getCommonHeaderArray());
            JSONArray coinTypeJSONArray = HttpUtils.httpGetWithResponseJSONArray("https://yunbi.com/api/v2/markets.json", getCommonHeaderArray());

            for (int i = 0; i < coinTypeJSONArray.size(); i++) {
                JSONObject coinType = coinTypeJSONArray.getJSONObject(i);
                String coinKey = coinType.getString("id");
                String coinKeyToUpperCase = coinKey.replace("cny", "").toUpperCase();
                if (coinInfoMapList.containsKey(coinKeyToUpperCase)) {
                    String depthUrl = "https://yunbi.com/api/v2/depth.json?market=" + coinKey + "&limit=1";
                    try {
                        CoinVO coinVO = getYunBiCoinInfo(yunBiJson.getJSONObject(coinKey), HttpUtils.httpGetWithResponseJSONObject(depthUrl, getCommonHeaderArray()));
                        coinVO.setCoinName(coinKeyToUpperCase);
                        addToCoinInfoMapList(coinKeyToUpperCase, coinVO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CoinVO getYunBiCoinInfo(JSONObject coinJSON, JSONObject depthJSON) throws Exception {
        CoinVO coinVO = new CoinVO();
        coinVO.setPlatform("云币");
        CoinPriceVO coinPriceVO = new CoinPriceVO();
        coinPriceVO.setCoinPrice(coinJSON.getJSONObject("ticker").getFloat("last"));

        coinPriceVO.setBuyFirstPrice(depthJSON.getJSONArray("bids").getJSONArray(0).getFloatValue(0));
        coinPriceVO.setBuyFirstCount(depthJSON.getJSONArray("bids").getJSONArray(0).getFloatValue(1));

        coinPriceVO.setSellFirstPrice(depthJSON.getJSONArray("asks").getJSONArray(0).getFloatValue(0));
        coinPriceVO.setSellFirstCount(depthJSON.getJSONArray("asks").getJSONArray(0).getFloatValue(1));

        coinVO.setCoinPriceVO(coinPriceVO);
        return coinVO;
    }

    private void bterCoinInfoList() throws Exception {
        addToCoinInfoMapList("BTC", getBterCoinInfo("BTC", "8884d0604cf15d0fa4d4704bd613e5f3"));
        addToCoinInfoMapList("SNT", getBterCoinInfo("SNT", "bcb22a2897aeea31acd520fd28cad587"));
        addToCoinInfoMapList("EOS", getBterCoinInfo("EOS", "d9e1d0a0b724e0607b687b557c63e562"));
        addToCoinInfoMapList("ETH", getBterCoinInfo("ETH", "b9d073c4b4a2ad7dd4c4d20bc485c4af"));
        addToCoinInfoMapList("QTUM", getBterCoinInfo("QTUM", "3ad1cd073496457f41f02383ea7145a4"));
        addToCoinInfoMapList("LTC", getBterCoinInfo("LTC", "56d7dd971e7aba15846b6156205ba58f"));
        addToCoinInfoMapList("BTS", getBterCoinInfo("BTS", "a6d044aae5d1a3cb8ed2d5e338eb2a48"));
        addToCoinInfoMapList("ETC", getBterCoinInfo("ETC", "b11c6ece83a1b2194529fdcc71c18e7d"));
        addToCoinInfoMapList("PAY", getBterCoinInfo("ETC", "a5713697577fdb140e108710a1884f91"));
    }

    private void addToCoinInfoMapList(String key, CoinVO coinVO) {
        if (null == coinVO) {
            return;
        }
        if (coinInfoMapList.containsKey(key)) {
            List<CoinVO> coinVOList = coinInfoMapList.get(key);
            if (CollectionUtils.isNotEmpty(coinVOList)) {
                boolean hasPlatform = false;
                for (CoinVO coin : coinVOList) {
                    if (coin.getPlatform().equals(coinVO.getPlatform())) {
                        hasPlatform = true;
                        if (coin.getCoinPriceVO().getCoinPrice() > coinVO.getCoinPriceVO().getCoinPrice()) { // 历史价格大于最新价格，跌
                            coin.setUpDownFlag(-1);
                        } else if (coin.getCoinPriceVO().getCoinPrice() < coinVO.getCoinPriceVO().getCoinPrice()) { // 历史价格小于最新价格，涨
                            coin.setUpDownFlag(1);
                        } else {
                            coin.setUpDownFlag(0);
                        }
                        coin.getCoinPriceVO().setCoinPrice(coinVO.getCoinPriceVO().getCoinPrice());
                        coin.getCoinPriceVO().setSellFirstPrice(coinVO.getCoinPriceVO().getSellFirstPrice());
                        coin.getCoinPriceVO().setSellFirstCount(coinVO.getCoinPriceVO().getSellFirstCount());
                        coin.getCoinPriceVO().setBuyFirstPrice(coinVO.getCoinPriceVO().getBuyFirstPrice());
                        coin.getCoinPriceVO().setBuyFirstCount(coinVO.getCoinPriceVO().getBuyFirstCount());
                    }
                }
                if (!hasPlatform) {
                    coinVOList.add(coinVO);
                }
            } else {
                coinVOList.add(coinVO);
            }
        }
    }

    private CoinVO getBterCoinInfo(String currencyType, String sign) {
        CoinVO coinVO = new CoinVO();
        try {
            String bterUrl = "https://apim.bter.com/apim/v1/marketDepth";
            JSONObject jsonObject = HttpUtils.httpPostWithResponseJSONObject(bterUrl, getBterParamList(currencyType, sign), getCommonHeaderArray());
            coinVO.setPlatform("比特儿");
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
            return null;
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
