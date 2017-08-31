package com.market.vo.price;

/**
 * Created by FLY on 2017/8/7.
 */
public class CoinVO {

    private String coinName;

    private String platform;

    /**
     * 价格升降标记， -1跌；0平；1涨
     */
    private int upDownFlag;

    private CoinPriceVO coinPriceVO;

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getUpDownFlag() {
        return upDownFlag;
    }

    public void setUpDownFlag(int upDownFlag) {
        this.upDownFlag = upDownFlag;
    }

    public CoinPriceVO getCoinPriceVO() {
        return coinPriceVO;
    }

    public void setCoinPriceVO(CoinPriceVO coinPriceVO) {
        this.coinPriceVO = coinPriceVO;
    }
}
