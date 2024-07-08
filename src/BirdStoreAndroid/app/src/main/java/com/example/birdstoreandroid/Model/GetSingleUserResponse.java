package com.example.birdstoreandroid.Model;

import java.util.Date;

public class GetSingleUserResponse {
    private String messageError;
    private int statusCode;
    private UserDetail data;

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

    public UserDetail getData() {
        return data;
    }

    public void setData(UserDetail data) {
        this.data = data;
    }

    public static class UserDetail {
        private String userName;
        private String userEmail;
        private String userPhone;
        private String addressLine;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getAddressLine() {
            return addressLine;
        }

        public void setAddressLine(String addressLine) {
            this.addressLine = addressLine;
        }
    }
}
