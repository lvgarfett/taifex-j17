package com.oneonetrade.fx.api;

public class ApiRequest {

    private String date;
    private String ccy;
    private String rate;

    public ApiRequest(String date, String ccy, String rate) {
        this.date = date;
        this.ccy = ccy;
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "date='" + date + '\'' +
                ", ccy='" + ccy + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}
