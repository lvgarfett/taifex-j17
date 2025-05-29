package com.oneonetrade.fx.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyData {
    @JsonProperty("Date")
    private String date; // Storing as String for the "YYYYMMdd" format

    @JsonProperty("USD/NTD")
    private double usdNtd;

    @JsonProperty("RMB/NTD")
    private double rmbNtd;

    @JsonProperty("EUR/USD")
    private double eurUsd;

    @JsonProperty("USD/JPY")
    private double usdJpy;

    @JsonProperty("GBP/USD")
    private double gbpUsd;

    @JsonProperty("AUD/USD")
    private double audUsd;

    @JsonProperty("USD/HKD")
    private double usdHkd;

    @JsonProperty("USD/RMB")
    private double usdRmb;

    @JsonProperty("USD/ZAR")
    private double usdZar;

    @JsonProperty("NZD/USD")
    private double nzdUsd;

    // Default constructor (recommended for JSON deserialization)
    public CurrencyData() {
    }

    // Constructor with all fields (optional, but convenient for creating objects)
    public CurrencyData(String date, double usdNtd, double rmbNtd, double eurUsd, double usdJpy,
                        double gbpUsd, double audUsd, double usdHkd, double usdRmb,
                        double usdZar, double nzdUsd) {
        this.date = date;
        this.usdNtd = usdNtd;
        this.rmbNtd = rmbNtd;
        this.eurUsd = eurUsd;
        this.usdJpy = usdJpy;
        this.gbpUsd = gbpUsd;
        this.audUsd = audUsd;
        this.usdHkd = usdHkd;
        this.usdRmb = usdRmb;
        this.usdZar = usdZar;
        this.nzdUsd = nzdUsd;
    }

    // --- Getters and Setters ---

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getUsdNtd() {
        return usdNtd;
    }

    public void setUsdNtd(double usdNtd) {
        this.usdNtd = usdNtd;
    }

    public double getRmbNtd() {
        return rmbNtd;
    }

    public void setRmbNtd(double rmbNtd) {
        this.rmbNtd = rmbNtd;
    }

    public double getEurUsd() {
        return eurUsd;
    }

    public void setEurUsd(double eurUsd) {
        this.eurUsd = eurUsd;
    }

    public double getUsdJpy() {
        return usdJpy;
    }

    public void setUsdJpy(double usdJpy) {
        this.usdJpy = usdJpy;
    }

    public double getGbpUsd() {
        return gbpUsd;
    }

    public void setGbpUsd(double gbpUsd) {
        this.gbpUsd = gbpUsd;
    }

    public double getAudUsd() {
        return audUsd;
    }

    public void setAudUsd(double audUsd) {
        this.audUsd = audUsd;
    }

    public double getUsdHkd() {
        return usdHkd;
    }

    public void setUsdHkd(double usdHkd) {
        this.usdHkd = usdHkd;
    }

    public double getUsdRmb() {
        return usdRmb;
    }

    public void setUsdRmb(double usdRmb) {
        this.usdRmb = usdRmb;
    }

    public double getUsdZar() {
        return usdZar;
    }

    public void setUsdZar(double usdZar) {
        this.usdZar = usdZar;
    }

    public double getNzdUsd() {
        return nzdUsd;
    }

    public void setNzdUsd(double nzdUsd) {
        this.nzdUsd = nzdUsd;
    }

    @Override
    public String toString() {
        return "CurrencyData{" +
                "date='" + date + '\'' +
                ", usdNtd=" + usdNtd +
                ", rmbNtd=" + rmbNtd +
                ", eurUsd=" + eurUsd +
                ", usdJpy=" + usdJpy +
                ", gbpUsd=" + gbpUsd +
                ", audUsd=" + audUsd +
                ", usdHkd=" + usdHkd +
                ", usdRmb=" + usdRmb +
                ", usdZar=" + usdZar +
                ", nzdUsd=" + nzdUsd +
                '}';
    }
}
