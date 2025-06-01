package com.oneonetrade.fx.dto.currency;

public class GbpUsd {

    private String date;
    private String gbpusd;

    public GbpUsd(String date, String gbpusd) {
        this.date = date;
        this.gbpusd = gbpusd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGbpusd() {
        return gbpusd;
    }

    public void setGbpusd(String gbpusd) {
        this.gbpusd = gbpusd;
    }

    @Override
    public String toString() {
        return "GbpUsd{" +
                "date='" + date + '\'' +
                ", gbpusd='" + gbpusd + '\'' +
                '}';
    }
}
