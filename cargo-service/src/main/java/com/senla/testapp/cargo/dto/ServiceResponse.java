package com.senla.testapp.cargo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ServiceResponse<T> {

    private final int responseCode;
    private final String responseMessage;
    private final T data;

    public ServiceResponse(int responseCode, String responseMessage, T data) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public T getData() {
        return data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return responseCode == 0;
    }

    public static class Builder<T> {

        private int responseCode;
        private String responseMessage;
        private T data;

        public Builder<T> setResponseCode(int responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public Builder<T> setResponseMessage(String responseMessage) {
            this.responseMessage = responseMessage;
            return this;
        }

        public Builder<T> setData(T data) {
            this.data = data;
            return this;
        }

        public ServiceResponse<T> build() {
            return new ServiceResponse<T>(responseCode, responseMessage, data);
        }

    }

}
