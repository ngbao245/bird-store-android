package com.example.birdstoreandroid.Model;

import java.util.List;

public class GetCategoryResponse {
    private String messageError;
    private List<GetCategoryRequest> data;
    private int statusCode;

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public List<GetCategoryRequest> getData() {
        return data;
    }

    public void setData(List<GetCategoryRequest> data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
