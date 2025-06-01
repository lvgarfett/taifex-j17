package com.oneonetrade.fx.dto.currency;

public class AudUsd {

    private String date;
    private String audusd;

    public AudUsd(String date, String audusd) {
        this.date = date;
        this.audusd = audusd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAudusd() {
        return audusd;
    }

    public void setAudusd(String audusd) {
        this.audusd = audusd;
    }

    @Override
    public String toString() {
        return "AudUsd{" +
                "date='" + date + '\'' +
                ", audusd='" + audusd + '\'' +
                '}';
    }
}
