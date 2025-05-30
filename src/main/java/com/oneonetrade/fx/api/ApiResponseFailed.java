package com.oneonetrade.fx.api;

public class ApiResponseFailed {

    private final ApiError error;

    public ApiResponseFailed(ApiError error) {
        this.error = error;
    }

    public ApiError getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ApiResponseFailed{" +
                "error=" + error +
                '}';
    }
}
