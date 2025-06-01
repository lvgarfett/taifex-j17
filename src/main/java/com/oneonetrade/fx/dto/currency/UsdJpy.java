package com.oneonetrade.fx.dto.currency;

public class UsdJpy {

    private String date;
    private String usdjpy;

    public UsdJpy(String date, String usdjpy) {
        this.date = date;
        this.usdjpy = usdjpy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsdjpy() {
        return usdjpy;
    }

    public void setUsdjpy(String usdjpy) {
        this.usdjpy = usdjpy;
    }

    @Override
    public String toString() {
        return "UsdJpy{" +
                "date='" + date + '\'' +
                ", usdjpy='" + usdjpy + '\'' +
                '}';
    }
}
