package com.example.birdstoreandroid.Model;

public class AddToCartResponse {
    private String messageError;
    private int statusCode;
    private CartItem data;

    // Getters and setters


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

    public CartItem getData() {
        return data;
    }

    public void setData(CartItem data) {
        this.data = data;
    }

    public static class CartItem {
        private String product_id;
        private Object product;
        private Object order_id;
        private Object order;
        private int price;
        private int quantity;
        private String user_id;
        private Object user;
        private String id;
        private String createdTime;
        private String lastUpdatedTime;
        private Object deletedTime;

        // Getters and setters


        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public Object getProduct() {
            return product;
        }

        public void setProduct(Object product) {
            this.product = product;
        }

        public Object getOrder_id() {
            return order_id;
        }

        public void setOrder_id(Object order_id) {
            this.order_id = order_id;
        }

        public Object getOrder() {
            return order;
        }

        public void setOrder(Object order) {
            this.order = order;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public Object getUser() {
            return user;
        }

        public void setUser(Object user) {
            this.user = user;
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

        public Object getDeletedTime() {
            return deletedTime;
        }

        public void setDeletedTime(Object deletedTime) {
            this.deletedTime = deletedTime;
        }
    }
}
