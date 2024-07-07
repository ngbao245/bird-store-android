package com.example.birdstoreandroid.Model;

import java.util.List;

public class CreateOrderRequest {
    private List<String> listIDCarts;
    private String user_id;
    private String paymentMenthod_id;
    private String address;

    public List<String> getListIDCarts() {
        return listIDCarts;
    }

    public void setListIDCarts(List<String> listIDCarts) {
        this.listIDCarts = listIDCarts;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPaymentMenthod_id() {
        return paymentMenthod_id;
    }

    public void setPaymentMenthod_id(String paymentMenthod_id) {
        this.paymentMenthod_id = paymentMenthod_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
