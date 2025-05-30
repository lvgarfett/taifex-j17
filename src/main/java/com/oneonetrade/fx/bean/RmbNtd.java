package com.oneonetrade.fx.bean;

public class RmbNtd {

    private String date;
    private String rmbntd;

    public RmbNtd(String date, String rmbntd) {
        this.date = date;
        this.rmbntd = rmbntd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRmbntd() {
        return rmbntd;
    }

    public void setRmbntd(String rmbntd) {
        this.rmbntd = rmbntd;
    }

    @Override
    public String toString() {
        return "RmbNtd{" +
                "date='" + date + '\'' +
                ", rmbntd='" + rmbntd + '\'' +
                '}';
    }
}
