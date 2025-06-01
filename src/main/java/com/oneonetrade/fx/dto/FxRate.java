package com.oneonetrade.fx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FxRate {
    @JsonProperty("Date")
    private String date;

    @JsonProperty("USD/NTD")
    private String usdNtd;

    @JsonProperty("RMB/NTD")
    private String rmbNtd;

    @JsonProperty("EUR/USD")
    private String eurUsd;

    @JsonProperty("USD/JPY")
    private String usdJpy;

    @JsonProperty("GBP/USD")
    private String gbpUsd;

    @JsonProperty("AUD/USD")
    private String audUsd;

    @JsonProperty("USD/HKD")
    private String usdHkd;

    @JsonProperty("USD/RMB")
    private String usdRmb;

    @JsonProperty("USD/ZAR")
    private String usdZar;

    @JsonProperty("NZD/USD")
    private String nzdUsd;

    // Default constructor (recommended for JSON deserialization)
    public FxRate() {
    }

    // Constructor with all fields (optional, but convenient for creating objects)
    public FxRate(String date, String usdNtd, String rmbNtd, String eurUsd, String usdJpy,
                  String gbpUsd, String audUsd, String usdHkd, String usdRmb,
                  String usdZar, String nzdUsd) {
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

    public String getUsdNtd() {
        return usdNtd;
    }

    public void setUsdNtd(String usdNtd) {
        this.usdNtd = usdNtd;
    }

    public String getRmbNtd() {
        return rmbNtd;
    }

    public void setRmbNtd(String rmbNtd) {
        this.rmbNtd = rmbNtd;
    }

    public String getEurUsd() {
        return eurUsd;
    }

    public void setEurUsd(String eurUsd) {
        this.eurUsd = eurUsd;
    }

    public String getUsdJpy() {
        return usdJpy;
    }

    public void setUsdJpy(String usdJpy) {
        this.usdJpy = usdJpy;
    }

    public String getGbpUsd() {
        return gbpUsd;
    }

    public void setGbpUsd(String gbpUsd) {
        this.gbpUsd = gbpUsd;
    }

    public String getAudUsd() {
        return audUsd;
    }

    public void setAudUsd(String audUsd) {
        this.audUsd = audUsd;
    }

    public String getUsdHkd() {
        return usdHkd;
    }

    public void setUsdHkd(String usdHkd) {
        this.usdHkd = usdHkd;
    }

    public String getUsdRmb() {
        return usdRmb;
    }

    public void setUsdRmb(String usdRmb) {
        this.usdRmb = usdRmb;
    }

    public String getUsdZar() {
        return usdZar;
    }

    public void setUsdZar(String usdZar) {
        this.usdZar = usdZar;
    }

    public String getNzdUsd() {
        return nzdUsd;
    }

    public void setNzdUsd(String nzdUsd) {
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
