package com.oneonetrade.fx.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsdNtd {
    private String date;
    private String usdntd;

    public UsdNtd(String date, String usdntd) {
        this.date = date;
        this.usdntd = usdntd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsdntd() {
        return usdntd;
    }

    public void setUsdntd(String usdntd) {
        this.usdntd = usdntd;
    }

    @Override
    public String toString() {
        return "UsdNtd{" +
                "date='" + date + '\'' +
                ", usdntd='" + usdntd + '\'' +
                '}';
    }
}
