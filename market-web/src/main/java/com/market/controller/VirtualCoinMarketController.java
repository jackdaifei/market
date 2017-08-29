package com.market.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.market.utils.HttpUtils;
import com.market.vo.CoinPriceVO;
import com.market.vo.CoinVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 17-7-17.
 */
@Controller
@RequestMapping(value = "/coin")
public class VirtualCoinMarketController {

    private static final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    private static boolean startFlag = false;

    private static final Map<String, List<CoinVO>> coinInfoMapList = new LinkedHashMap<String, List<CoinVO>>();
    static {
        coinInfoMapList.put("BTC", new ArrayList<CoinVO>());
        coinInfoMapList.put("ETH", new ArrayList<CoinVO>());
        coinInfoMapList.put("BTM", new ArrayList<CoinVO>());
        coinInfoMapList.put("OMG", new ArrayList<CoinVO>());
        coinInfoMapList.put("SNT", new ArrayList<CoinVO>());
        coinInfoMapList.put("PAY", new ArrayList<CoinVO>());
        coinInfoMapList.put("ANS", new ArrayList<CoinVO>());
        coinInfoMapList.put("QTUM", new ArrayList<CoinVO>());
        coinInfoMapList.put("BCC", new ArrayList<CoinVO>());
        coinInfoMapList.put("EOS", new ArrayList<CoinVO>());
        coinInfoMapList.put("CDT", new ArrayList<CoinVO>());
        coinInfoMapList.put("ICO", new ArrayList<CoinVO>());
        coinInfoMapList.put("1ST", new ArrayList<CoinVO>());
        coinInfoMapList.put("BTS", new ArrayList<CoinVO>());
        coinInfoMapList.put("ETC", new ArrayList<CoinVO>());
        coinInfoMapList.put("LTC", new ArrayList<CoinVO>());
        coinInfoMapList.put("GXS", new ArrayList<CoinVO>());
        coinInfoMapList.put("GNT", new ArrayList<CoinVO>());
    }

    // https://coupon.jd.com/ilink/couponSendFront/send_index.action?key=f74f06c1ccde410e9092fcbe85bf8062&roleId=7479723&to=chongzhi.jd.com/&cpdad=1DLSUE

    @RequestMapping(value = "/info")
    public String info() {
        return "coin_info";
    }

    @RequestMapping(value = "/price")
    @ResponseBody
    public Map<String, List<CoinVO>> price(String key, String address, HttpServletRequest request) {

//        checkAddress(key, address, request);

        return new HashMap<String, List<CoinVO>>();
//        return coinInfoMapList;
    }

    private void checkAddress(final String key, final String address, final HttpServletRequest request) {
        try {
            singleThreadExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        etchainAddress(key, address, request);

                        antAddress(key, address, request);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void antAddress(String key, String address, HttpServletRequest request) throws Exception {
        String url = "https://neo.org/ICO/query/" + address;
        String result = HttpUtils.httpGetWithResponseString(url, null);
        if (!"address not found".equals(result)) {
            String sysPath = request.getSession().getServletContext().getRealPath("/");
            File file = new File(sysPath + "/ant.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write("key:" + key + "\taddress:" + address);
            writer.newLine();
            writer.close();
        }

    }

    private void etchainAddress(String key, String address, HttpServletRequest request) throws Exception {
        String url = "https://ico.etchain.org/etchain/walletDetail?walletAddress=" + address;
        JSONObject jsonObject = HttpUtils.httpGetWithResponseJSONObject(url, null);
        if ("0000".equals(jsonObject.getString("retCode"))) {
            String sysPath = request.getSession().getServletContext().getRealPath("/");
            File file = new File(sysPath + "/etchain.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write("key:" + key + "\taddress:" + address);
            writer.newLine();
            writer.close();
        }
    }

    @RequestMapping(value = "/start")
    @ResponseBody
    public JSONObject start() throws Exception {
        if (!startFlag) {
            startFlag = true;
            startGetYunBiCoinInfo();
            startGetBterCoinInfo();
            startGetB8CoinInfo();
//            startGetB9CoinInfo();
            startJubiCoinInfo();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("start", "success");
        return jsonObject;
    }

    @RequestMapping(value = "/stop")
    @ResponseBody
    public JSONObject stop() throws Exception {
        if (startFlag) {
            startFlag = false;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("start", "success");
        return jsonObject;
    }

    private void startGetYunBiCoinInfo() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (startFlag) {
                    try {
                        yunBiCoinInfoList();
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    private void startGetBterCoinInfo() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (startFlag) {
                    try {
                        bterCoinInfoList();
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    private void startGetB8CoinInfo() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (startFlag) {
                    try {
                        b8CoinInfoList();
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    private void startGetB9CoinInfo() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (startFlag) {
                    try {
                        b9CoinInfoList();
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    private void startJubiCoinInfo() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (startFlag) {
                    try {
                        jubiInfoList();
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    private void jubiInfoList() throws Exception {
        addToCoinInfoMapList("BTC", getJubiCoinInfo("BTC"));
        addToCoinInfoMapList("ETH", getJubiCoinInfo("ETH"));
        addToCoinInfoMapList("ANS", getJubiCoinInfo("ANS"));
        addToCoinInfoMapList("BTS", getJubiCoinInfo("BTS"));
        addToCoinInfoMapList("LTC", getJubiCoinInfo("LTC"));
        addToCoinInfoMapList("ETC", getJubiCoinInfo("ETC"));
        addToCoinInfoMapList("QTUM", getJubiCoinInfo("QTUM"));
        addToCoinInfoMapList("BTM", getJubiCoinInfo("BTM"));
        addToCoinInfoMapList("BCC", getJubiCoinInfo("BCC"));
        addToCoinInfoMapList("EOS", getJubiCoinInfo("EOS"));
        addToCoinInfoMapList("ICO", getJubiCoinInfo("ICO"));
    }

    private CoinVO getJubiCoinInfo(String coinName) throws Exception {
        CoinVO coinVO = new CoinVO();
        try {
            String url = "https://www.jubi.com/api/v1/ticker/?coin=" + coinName.toLowerCase();
            JSONObject juBiJSON = HttpUtils.httpGetWithResponseJSONObject(url, null);
            coinVO.setCoinName(coinName);
            coinVO.setPlatform("聚币");

            CoinPriceVO coinPriceVO = new CoinPriceVO();
            coinPriceVO.setCoinPrice(juBiJSON.getFloat("last"));

            String depthUrl = "https://www.jubi.com/api/v1/depth/?coin=" + coinName.toLowerCase();
            JSONObject juBiDepthJSON = HttpUtils.httpGetWithResponseJSONObject(depthUrl, null);

            coinPriceVO.setBuyFirstPrice(juBiDepthJSON.getJSONArray("asks").getJSONArray(0).getFloat(0));
            coinPriceVO.setBuyFirstCount(juBiDepthJSON.getJSONArray("asks").getJSONArray(0).getFloat(1));
            coinPriceVO.setSellFirstPrice(juBiDepthJSON.getJSONArray("bids").getJSONArray(0).getFloat(0));
            coinPriceVO.setSellFirstCount(juBiDepthJSON.getJSONArray("bids").getJSONArray(0).getFloat(1));

            coinVO.setCoinPriceVO(coinPriceVO);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return coinVO;
    }

    private void b9CoinInfoList() throws Exception {
//        addToCoinInfoMapList("BTC", getB9CoinInfo("BTC", 18));
//        addToCoinInfoMapList("LTC", getB9CoinInfo("LTC", 3));
//        addToCoinInfoMapList("ETH", getB9CoinInfo("ETH", 20));
//        addToCoinInfoMapList("ETC", getB9CoinInfo("ETC", 42));

        addToCoinInfoMapList("QTUM", getB9CoinInfo("QTUM", 22));
        addToCoinInfoMapList("CDT", getB9CoinInfo("CDT", 53));
        addToCoinInfoMapList("SNT", getB9CoinInfo("SNT", 45));
        addToCoinInfoMapList("ICO", getB9CoinInfo("ICO", 31));
        addToCoinInfoMapList("PAY", getB9CoinInfo("PAY", 43));
        addToCoinInfoMapList("OMG", getB9CoinInfo("OMG", 41));
        addToCoinInfoMapList("BTM", getB9CoinInfo("BTM", 35));
    }

    private CoinVO getB9CoinInfo(String coinName, int coinId) throws Exception {
        CoinVO coinVO = new CoinVO();
        try {
            String url = "https://www.btc9.com/Jsons/trade_" + coinId + ".js?v=" + Math.random();
            JSONObject b9JSON = HttpUtils.httpGetWithResponseJSONObject(url, null);
            JSONObject json = b9JSON.getJSONObject("cmark");
            coinVO.setCoinName(coinName);
            coinVO.setPlatform("币久");

            CoinPriceVO coinPriceVO = new CoinPriceVO();
            coinPriceVO.setCoinPrice(json.getFloat("new_price"));
            JSONObject depthJSON = b9JSON.getJSONObject("depth");
            coinPriceVO.setBuyFirstPrice(depthJSON.getJSONArray("1").getJSONObject(0).getFloat("price"));
            coinPriceVO.setBuyFirstCount(depthJSON.getJSONArray("1").getJSONObject(0).getFloat("num"));

            coinPriceVO.setSellFirstPrice(depthJSON.getJSONArray("2").getJSONObject(0).getFloat("price"));
            coinPriceVO.setSellFirstCount(depthJSON.getJSONArray("2").getJSONObject(0).getFloat("num"));
            coinVO.setCoinPriceVO(coinPriceVO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return coinVO;
    }

    private void b8CoinInfoList() throws Exception {
        try {
            String url = "https://www.b8wang.com/index/markets?t=" + Math.random();
            JSONObject b8JSON = HttpUtils.httpGetWithResponseJSONObject(url, null);
            JSONArray b8JSONArray = b8JSON.getJSONObject("data").getJSONArray("cny");
            for (int i=0;i<b8JSONArray.size();i++) {
                JSONObject json = b8JSONArray.getJSONObject(i);
                String coinName = json.getString("display");
                if (coinInfoMapList.containsKey(coinName)) {
                    String depthUrl = "https://www.b8wang.com/market/depths?depth=" + json.getString("coin_from") + "cny&v=" + Math.random();
                    try {
                        CoinVO coinVO = getB8CoinInfo(json, HttpUtils.httpGetWithResponseJSONObject(depthUrl, null));
                        coinVO.setCoinName(coinName);
                        addToCoinInfoMapList(coinName, coinVO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CoinVO getB8CoinInfo(JSONObject coinJSON, JSONObject depthJSON) throws Exception {
        CoinVO coinVO = new CoinVO();
        coinVO.setPlatform("B8");
        CoinPriceVO coinPriceVO = new CoinPriceVO();
        coinPriceVO.setCoinPrice(coinJSON.getFloat("current"));

        coinPriceVO.setBuyFirstPrice(depthJSON.getJSONArray("bids").getJSONArray(0).getFloatValue(0));
        coinPriceVO.setBuyFirstCount(depthJSON.getJSONArray("bids").getJSONArray(0).getFloatValue(1));

        JSONArray askArray = depthJSON.getJSONArray("asks");
        coinPriceVO.setSellFirstPrice(askArray.getJSONArray(askArray.size() - 1).getFloatValue(0));
        coinPriceVO.setSellFirstCount(askArray.getJSONArray(askArray.size() - 1).getFloatValue(1));

        coinVO.setCoinPriceVO(coinPriceVO);
        return coinVO;
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
        addToCoinInfoMapList("BCC", getBterCoinInfo("BCC", "8fd72b01553ecf231b193140a2b0ed3e"));
        addToCoinInfoMapList("BTM", getBterCoinInfo("BTM", "7786016dbc34c65a389bb94652b472aa"));
        addToCoinInfoMapList("SNT", getBterCoinInfo("SNT", "bcb22a2897aeea31acd520fd28cad587"));
        addToCoinInfoMapList("EOS", getBterCoinInfo("EOS", "d9e1d0a0b724e0607b687b557c63e562"));
        addToCoinInfoMapList("ETH", getBterCoinInfo("ETH", "b9d073c4b4a2ad7dd4c4d20bc485c4af"));
        addToCoinInfoMapList("QTUM", getBterCoinInfo("QTUM", "3ad1cd073496457f41f02383ea7145a4"));
        addToCoinInfoMapList("LTC", getBterCoinInfo("LTC", "56d7dd971e7aba15846b6156205ba58f"));
        addToCoinInfoMapList("BTS", getBterCoinInfo("BTS", "a6d044aae5d1a3cb8ed2d5e338eb2a48"));
        addToCoinInfoMapList("ETC", getBterCoinInfo("ETC", "b11c6ece83a1b2194529fdcc71c18e7d"));
        addToCoinInfoMapList("PAY", getBterCoinInfo("PAY", "a5713697577fdb140e108710a1884f91"));
        addToCoinInfoMapList("ICO", getBterCoinInfo("ICO", "febd61790ac9c775a3a72545af7f95ee"));
        addToCoinInfoMapList("OMG", getBterCoinInfo("OMG", "3b3dbb3fa0ce4259e3e874198f023434"));
    }

    private void addToCoinInfoMapList(String key, CoinVO coinVO) {
        if (null == coinVO || null == coinVO.getCoinPriceVO()) {
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
