package com.oneonetrade.fx.dto.currency;

public class UsdHkd {

    private String date;
    private String usdhkd;

    public UsdHkd(String date, String usdhkd) {
        this.date = date;
        this.usdhkd = usdhkd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsdhkd() {
        return usdhkd;
    }

    public void setUsdhkd(String usdhkd) {
        this.usdhkd = usdhkd;
    }

    @Override
    public String toString() {
        return "UsdHkd{" +
                "date='" + date + '\'' +
                ", usdhkd='" + usdhkd + '\'' +
                '}';
    }
}
