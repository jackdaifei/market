package com.market.vo;

/**
 * Created by FLY on 2017/8/7.
 */
public class CoinPriceVO {

    private float coinPrice;

    private float buyFirstPrice;

    private float buyFirstCount;

    private float sellFirstPrice;

    private float sellFirstCount;

    public float getCoinPrice() {
        return coinPrice;
    }

    public void setCoinPrice(float coinPrice) {
        this.coinPrice = coinPrice;
    }

    public float getBuyFirstPrice() {
        return buyFirstPrice;
    }

    public void setBuyFirstPrice(float buyFirstPrice) {
        this.buyFirstPrice = buyFirstPrice;
    }

    public float getBuyFirstCount() {
        return buyFirstCount;
    }

    public void setBuyFirstCount(float buyFirstCount) {
        this.buyFirstCount = buyFirstCount;
    }

    public float getSellFirstPrice() {
        return sellFirstPrice;
    }

    public void setSellFirstPrice(float sellFirstPrice) {
        this.sellFirstPrice = sellFirstPrice;
    }

    public float getSellFirstCount() {
        return sellFirstCount;
    }

    public void setSellFirstCount(float sellFirstCount) {
        this.sellFirstCount = sellFirstCount;
    }
}
