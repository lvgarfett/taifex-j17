package com.oneonetrade.fx.dto.currency;

public class UsdRmb {

    private String date;
    private String usdrmb;

    public UsdRmb(String date, String usdrmb) {
        this.date = date;
        this.usdrmb = usdrmb;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsdrmb() {
        return usdrmb;
    }

    public void setUsdrmb(String usdrmb) {
        this.usdrmb = usdrmb;
    }

    @Override
    public String toString() {
        return "UsdRmb{" +
                "date='" + date + '\'' +
                ", usdrmb='" + usdrmb + '\'' +
                '}';
    }
}
