package com.example.birdstoreandroid.Model;

import java.util.List;

public class SignUpResponse {
    private String messageError;
    private int statusCode;
    private SignUpData data;

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

    public SignUpData getData() {
        return data;
    }

    public void setData(SignUpData data) {
        this.data = data;
    }

    public static class SignUpData {
        private String userName;
        private String name;
        private String userPassword;
        private String userEmail;
        private String verifyEmail;
        private String userPhone;
        private String createdAt;
        private String role_id;
        private String addressLine;
        private String role;
        private boolean isActive;
        private List<Object> carts;
        private List<Object> orderEntities;
        private String id;
        private String createdTime;
        private String lastUpdatedTime;
        private String deletedTime;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getVerifyEmail() {
            return verifyEmail;
        }

        public void setVerifyEmail(String verifyEmail) {
            this.verifyEmail = verifyEmail;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getAddressLine() {
            return addressLine;
        }

        public void setAddressLine(String addressLine) {
            this.addressLine = addressLine;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public List<Object> getCarts() {
            return carts;
        }

        public void setCarts(List<Object> carts) {
            this.carts = carts;
        }

        public List<Object> getOrderEntities() {
            return orderEntities;
        }

        public void setOrderEntities(List<Object> orderEntities) {
            this.orderEntities = orderEntities;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getLastUpdatedTime() {
            return lastUpdatedTime;
        }

        public void setLastUpdatedTime(String lastUpdatedTime) {
            this.lastUpdatedTime = lastUpdatedTime;
        }

        public String getDeletedTime() {
            return deletedTime;
        }

        public void setDeletedTime(String deletedTime) {
            this.deletedTime = deletedTime;
        }
    }
}
