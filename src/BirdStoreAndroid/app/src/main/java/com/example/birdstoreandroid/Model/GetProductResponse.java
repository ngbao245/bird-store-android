package com.example.birdstoreandroid.Model;

import java.util.List;

public class GetProductResponse {
    private String messageError;
    private int statusCode;
    private List<GetProductRequest> data;

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<GetProductRequest> getData() {
        return data;
    }

    public void setData(List<GetProductRequest> data) {
        this.data = data;
    }
}
