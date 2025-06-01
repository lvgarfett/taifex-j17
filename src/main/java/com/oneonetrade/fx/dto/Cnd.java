package com.oneonetrade.fx.dto;

public class Cnd {

    private String startDate;
    private String endDate;
    private String currency;

    public Cnd(String startDate, String endDate, String currency) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.currency = currency;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "ApiRequest{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
