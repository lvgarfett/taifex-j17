package com.oneonetrade.fx.api;

public class ApiResponseSuccess<T> {

    private final ApiError error;
    private final T currency;

    public ApiResponseSuccess(ApiError error, T currency) {
        this.error = error;
        this.currency = currency;
    }

    public ApiError getError() {
        return error;
    }

    public T getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "ApiResponseSuccess{" +
                "error=" + error +
                ", currency=" + currency +
                '}';
    }
}
