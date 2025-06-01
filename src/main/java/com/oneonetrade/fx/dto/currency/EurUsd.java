package com.oneonetrade.fx.dto.currency;

public class EurUsd {

    private String date;
    private String eurusd;

    public EurUsd(String date, String eurusd) {
        this.date = date;
        this.eurusd = eurusd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEurusd() {
        return eurusd;
    }

    public void setEurusd(String eurusd) {
        this.eurusd = eurusd;
    }

    @Override
    public String toString() {
        return "EurUsd{" +
                "date='" + date + '\'' +
                ", eurusd='" + eurusd + '\'' +
                '}';
    }
}
