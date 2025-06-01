package com.oneonetrade.fx.dto.currency;

public class NzdUsd {

    private String date;
    private String nzdusd;

    public NzdUsd(String date, String nzdusd) {
        this.date = date;
        this.nzdusd = nzdusd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNzdusd() {
        return nzdusd;
    }

    public void setNzdusd(String nzdusd) {
        this.nzdusd = nzdusd;
    }

    @Override
    public String toString() {
        return "NzdUsd{" +
                "date='" + date + '\'' +
                ", nzdusd='" + nzdusd + '\'' +
                '}';
    }
}
