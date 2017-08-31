package com.market.vo.price;

/**
 * Created by FLY on 2017/8/7.
 */
public class CoinPriceVO {

    private float coinPrice;

    private float buyFirstPrice;

    private float buyFirstCount;

    private float sellFirstPrice;

    private float sellFirstCount;

    private float highPrice24Hour;

    private float lowPrice24Hour;

    private float volume24Hour;

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

    public float getHighPrice24Hour() {
        return highPrice24Hour;
    }

    public void setHighPrice24Hour(float highPrice24Hour) {
        this.highPrice24Hour = highPrice24Hour;
    }

    public float getLowPrice24Hour() {
        return lowPrice24Hour;
    }

    public void setLowPrice24Hour(float lowPrice24Hour) {
        this.lowPrice24Hour = lowPrice24Hour;
    }

    public float getVolume24Hour() {
        return volume24Hour;
    }

    public void setVolume24Hour(float volume24Hour) {
        this.volume24Hour = volume24Hour;
    }
}
