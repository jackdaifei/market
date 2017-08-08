package com.market.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.market.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by daifei on 2017/6/30.
 */
public class Coin {

    public static void main(String[] args) {
        startFrame();
    }


    private static void startFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("挣钱小窗口！！！");
        jFrame.setSize(400, 350);
        jFrame.setLocationRelativeTo(null);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        jFrame.setContentPane(mainPanel);

        JPanel sntPanel = new SelfPanel("SNT", 120, 60);
        SelfLabel sntBterLabel = new SelfLabel();
        SelfLabel sntYunBiLabel = new SelfLabel();
        sntPanel.add(sntBterLabel);
        sntPanel.add(sntYunBiLabel);
        mainPanel.add(sntPanel);

        JPanel eosPanel = new SelfPanel("EOS", 120, 60);
        SelfLabel eosBterLabel = new SelfLabel();
        eosPanel.add(eosBterLabel);
        mainPanel.add(eosPanel);

        JPanel icoPanel = new SelfPanel("ICO", 120, 60);
        SelfLabel icoBterLabel = new SelfLabel();
        icoPanel.add(icoBterLabel);
        mainPanel.add(icoPanel);

        JPanel ethPanel = new SelfPanel("ETH");
        SelfLabel ethBterLabel = new SelfLabel();
        ethPanel.add(ethBterLabel);
        mainPanel.add(ethPanel);

        JPanel qtumPanel = new SelfPanel("QTUM 量子链");
        SelfLabel qtumBterLabel = new SelfLabel();
        qtumPanel.add(qtumBterLabel);
        mainPanel.add(qtumPanel);

        JPanel yunbiOther = new SelfPanel("云币Other");
        SelfLabel otherLabel = new SelfLabel();
        yunbiOther.add(otherLabel);
        mainPanel.add(yunbiOther);

        JPanel omgPanel = new SelfPanel("OMG", 120, 60);
        SelfLabel omgLabel = new SelfLabel();
        omgPanel.add(omgLabel);
        mainPanel.add(omgPanel);

        jFrame.setVisible(true);

        SelfThread selfThread = new SelfThread(sntBterLabel, eosBterLabel, icoBterLabel, ethBterLabel, qtumBterLabel, otherLabel, omgLabel);
        Thread thread = new Thread(selfThread);
        thread.start();
    }

}

class SelfPanel extends JPanel {
    SelfPanel(String title) {
        this.setPreferredSize(new Dimension(120, 130));
        this.setBorder(BorderFactory.createTitledBorder(title));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    SelfPanel(String title, int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.setBorder(BorderFactory.createTitledBorder(title));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}

class SelfLabel extends JLabel {
    SelfLabel() {
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        this.repaint();
    }

    public void setText(String bterUrl, String yunbiStr, List<NameValuePair> paramList, Header[] headers) {
        try {
            if (null != bterUrl) {
                float sntBterPrice = HttpUtils.httpPostWithResponseJSONObject(bterUrl, paramList, headers).getJSONObject("datas").getFloat("currentPrice");
                this.setText("<html><span>" + "Bter: " + sntBterPrice + "</span><br>" + yunbiStr + "</html>");
            } else {
                this.setText(yunbiStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* public SelfLabel(final String url, final List<NameValuePair> paramList) {
        Thread thread = new Thread() {
            @Override
            public void run(){
                while (true) {
                    try {
                        Header[] headers = new Header[] {
                                new BasicHeader("Content-Type", "application/x-www-form-urlencoded")
                        };
                        JSONObject result = HttpClientUtils.postResponse(url, paramList);
                        float currentPrice = result.getJSONObject("datas").getFloat("currentPrice");
                        SelfLabel.this.setText("<html>" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "<br>Bter: " + currentPrice + "</html>");
                        SelfLabel.this.repaint();
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }*/
}

class SelfThread implements Runnable {
    private final static String bterUrl = "https://apim.bter.com/apim/v1/marketDepth";
    private final static NameValuePair[] sntBterParamArray = new NameValuePair[]{
            new BasicNameValuePair("appKey", "1C843F4B-C351-4A9F-EB51-B722122341D5"),
            new BasicNameValuePair("appLang", "cn"),
            new BasicNameValuePair("currencyType", "SNT"),
            new BasicNameValuePair("depth", "0.1"),
            new BasicNameValuePair("exchangeType", "CNY"),
            new BasicNameValuePair("length", "20"),
            new BasicNameValuePair("sign", "bcb22a2897aeea31acd520fd28cad587"),
            new BasicNameValuePair("token", "")
    };

    private final static NameValuePair[] eosBterParamArray = new NameValuePair[]{
            new BasicNameValuePair("appKey", "1C843F4B-C351-4A9F-EB51-B722122341D5"),
            new BasicNameValuePair("appLang", "cn"),
            new BasicNameValuePair("currencyType", "EOS"),
            new BasicNameValuePair("depth", "0.1"),
            new BasicNameValuePair("exchangeType", "CNY"),
            new BasicNameValuePair("length", "20"),
            new BasicNameValuePair("sign", "d9e1d0a0b724e0607b687b557c63e562"),
            new BasicNameValuePair("token", "")
    };

    private final static NameValuePair[] icoBterParamArray = new NameValuePair[]{
            new BasicNameValuePair("appKey", "1C843F4B-C351-4A9F-EB51-B722122341D5"),
            new BasicNameValuePair("appLang", "cn"),
            new BasicNameValuePair("currencyType", "ICO"),
            new BasicNameValuePair("depth", "0.1"),
            new BasicNameValuePair("exchangeType", "CNY"),
            new BasicNameValuePair("length", "20"),
            new BasicNameValuePair("sign", "febd61790ac9c775a3a72545af7f95ee"),
            new BasicNameValuePair("token", "")
    };

    private final static NameValuePair[] ethBterParamArray = new NameValuePair[]{
            new BasicNameValuePair("appKey", "1C843F4B-C351-4A9F-EB51-B722122341D5"),
            new BasicNameValuePair("appLang", "cn"),
            new BasicNameValuePair("currencyType", "ETH"),
            new BasicNameValuePair("depth", "0.1"),
            new BasicNameValuePair("exchangeType", "CNY"),
            new BasicNameValuePair("length", "20"),
            new BasicNameValuePair("sign", "b9d073c4b4a2ad7dd4c4d20bc485c4af"),
            new BasicNameValuePair("token", "")
    };

    private final static NameValuePair[] qtumBterParamArray = new NameValuePair[]{
            new BasicNameValuePair("appKey", "1C843F4B-C351-4A9F-EB51-B722122341D5"),
            new BasicNameValuePair("appLang", "cn"),
            new BasicNameValuePair("currencyType", "QTUM"),
            new BasicNameValuePair("depth", "0.1"),
            new BasicNameValuePair("exchangeType", "CNY"),
            new BasicNameValuePair("length", "20"),
            new BasicNameValuePair("sign", "3ad1cd073496457f41f02383ea7145a4"),
            new BasicNameValuePair("token", "")
    };

    private final static String yunBiUrl = "https://yunbi.com//api/v2/tickers.json";
    // private final static String yunBiUrl = "https://yunbi.com//api/v2/markets.json"; // 种类
    // https://apim.bter.com/apim/v1/marketDepth
    // https://bter.com/api2
    // https://yunbi.zendesk.com/hc/zh-cn/articles/115005892327-API-%E5%BC%80%E5%8F%91%E8%80%85%E6%8E%A5%E5%8F%A3
    // https://yunbi.com//api/v2/tickers/eoscny.json



    private final static String b8Url = "https://www.b8wang.com/index/markets";

    private final static String b9Url = "https://www.btc9.com/Index/CurrencyList.html";
    private final static NameValuePair[] b9ParamArray = new NameValuePair[]{
            new BasicNameValuePair("k", "1")
    };

    private SelfLabel sntLabel;
    private SelfLabel eosLabel;
    private SelfLabel icoLabel;
    private SelfLabel ethLabel;
    private SelfLabel qtumLabel;
    private SelfLabel otherLabel;
    private SelfLabel omgLabel;

    SelfThread(SelfLabel sntLabel, SelfLabel eosLabel, SelfLabel icoLabel, SelfLabel ethLabel, SelfLabel qtumLabel, SelfLabel otherLabel, SelfLabel omgLabel) {
        this.sntLabel = sntLabel;
        this.eosLabel = eosLabel;
        this.icoLabel = icoLabel;
        this.ethLabel = ethLabel;
        this.qtumLabel = qtumLabel;
        this.otherLabel = otherLabel;
        this.omgLabel = omgLabel;
    }

    public void run(){
        while (true) {
            try {
                Header[] headers = new Header[] {
                        new BasicHeader("Content-Type", "application/x-www-form-urlencoded")
                };

//                float sntBterPrice = HttpClientUtils.postResponse(bterUrl, Arrays.asList(sntBterParamArray), headers).getJSONObject("datas").getFloat("currentPrice");
//                float eosBterPrice = HttpClientUtils.postResponse(bterUrl, Arrays.asList(eosBterParamArray), headers).getJSONObject("datas").getFloat("currentPrice");
//                float icoBterPrice = HttpClientUtils.postResponse(bterUrl, Arrays.asList(icoBterParamArray), headers).getJSONObject("datas").getFloat("currentPrice");
//                float ethBterPrice = HttpClientUtils.postResponse(bterUrl, Arrays.asList(ethBterParamArray), headers).getJSONObject("datas").getFloat("currentPrice");
//                float qtumBterPrice = HttpClientUtils.postResponse(bterUrl, Arrays.asList(qtumBterParamArray), headers).getJSONObject("datas").getFloat("currentPrice");
                String yunBiOther = "";
                String otherEos = "";
                String otherEth = "";
                StringBuilder otherQtum = new StringBuilder();
                try {
                    JSONObject yunBiJson = HttpUtils.httpGetWithResponseJSONObject(yunBiUrl, headers);
                    float first = 0f;
                    if (null != yunBiJson) {
                        otherEos += "<span>" + "云币: " + yunBiJson.getJSONObject("eoscny").getJSONObject("ticker").getFloat("last") + "</span><br>";
                        otherEth += "<span>" + "云币: " + yunBiJson.getJSONObject("ethcny").getJSONObject("ticker").getFloat("last") + "</span><br>";
                        otherQtum.append("<span>" + "云币: ").append(yunBiJson.getJSONObject("qtumcny").getJSONObject("ticker").getFloat("last")).append("</span><br>");
                        yunBiOther += "<span>" + "BTC: " + yunBiJson.getJSONObject("btccny").getJSONObject("ticker").getFloat("last") + "</span><br>";
                        first = yunBiJson.getJSONObject("1stcny").getJSONObject("ticker").getFloat("last");
                        yunBiOther += "<span>" + "1ST: " + first + "</span><br>";
                        yunBiOther += "<span>" + "ANS: " + yunBiJson.getJSONObject("anscny").getJSONObject("ticker").getFloat("last") + "</span><br>";
                        yunBiOther += "<span>" + "BTS: " + yunBiJson.getJSONObject("btscny").getJSONObject("ticker").getFloat("last") + "</span><br>";
                        yunBiOther += "<span>" + "GXS: " + yunBiJson.getJSONObject("gxscny").getJSONObject("ticker").getFloat("last") + "</span><br>";
                        yunBiOther += "<span>" + "GNT: " + yunBiJson.getJSONObject("gntcny").getJSONObject("ticker").getFloat("last") + "</span><br>";
                    }
                    if (first >= 8.0f) {
                        JOptionPane.showMessageDialog(null, "一血8元", "警告",JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String otherSnt = "";
                StringBuilder omgInfo = new StringBuilder();
                try {
                    JSONObject b8Json = HttpUtils.httpGetWithResponseJSONObject(b8Url + "?t=" + Math.random(), headers);
                    if (null != b8Json) {
                        otherSnt += "<span>" + "B8: " + b8Json.getJSONObject("data").getJSONArray("cny").getJSONObject(4).getFloat("current") + "</span><br>";
                        otherEth += "<span>" + "B8: " + b8Json.getJSONObject("data").getJSONArray("cny").getJSONObject(0).getFloat("current") + "</span><br>";
                        omgInfo.append("<span>" + "B8: ").append(b8Json.getJSONObject("data").getJSONArray("cny").getJSONObject(5).getFloat("current")).append("</span><br>");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



                StringBuilder icoInfo = new StringBuilder();
                try {
                    JSONArray b9JsonArray = HttpUtils.httpPostWithResponseJSONArray(b9Url, Arrays.asList(b9ParamArray), headers);
                    for (int i = 0; i < b9JsonArray.size(); i++) {
                        JSONObject b9Json = b9JsonArray.getJSONObject(i);
                        if (null != b9Json) {
                            String flag = b9Json.getString("currency_mark");
                            if (StringUtils.isNotBlank(flag)) {
                                if (b9Json.getString("currency_mark").equals("Qtum")) {
                                    otherQtum.append("<span>" + "币久: ").append(b9Json.getFloat("new_price")).append("</span><br>");
                                } else if (b9Json.getString("currency_mark").equals("ICO")) {
                                    icoInfo.append("<span>" + "币久: ").append(b9Json.getFloat("new_price")).append("</span><br>");
                                } else if (b9Json.getString("currency_mark").equals("OMG")) {
                                    omgInfo.append("<span>" + "币久: ").append(b9Json.getFloat("new_price")).append("</span><br>");
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                sntLabel.setText(bterUrl, otherSnt, Arrays.asList(sntBterParamArray), headers);
                eosLabel.setText(bterUrl, otherEos, Arrays.asList(eosBterParamArray), headers);
                icoLabel.setText(bterUrl, icoInfo.toString(), Arrays.asList(icoBterParamArray), headers);
                ethLabel.setText(bterUrl, otherEth, Arrays.asList(ethBterParamArray), headers);
                qtumLabel.setText(bterUrl, otherQtum.toString(), Arrays.asList(qtumBterParamArray), headers);
                otherLabel.setText(null, "<html>" + yunBiOther + "</html>", null, null);
                omgLabel.setText(null, "<html>" + omgInfo + "</html>", null, null);

                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}