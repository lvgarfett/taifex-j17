package com.oneonetrade.fx.bean;

public class UsdZar {

    private String date;
    private String usdzar;

    public UsdZar(String date, String usdzar) {
        this.date = date;
        this.usdzar = usdzar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsdzar() {
        return usdzar;
    }

    public void setUsdzar(String usdzar) {
        this.usdzar = usdzar;
    }

    @Override
    public String toString() {
        return "UsdZar{" +
                "date='" + date + '\'' +
                ", usdzar='" + usdzar + '\'' +
                '}';
    }
}
