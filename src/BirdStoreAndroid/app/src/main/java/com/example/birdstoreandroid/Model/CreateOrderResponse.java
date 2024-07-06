package com.example.birdstoreandroid.Model;

import java.util.List;

public class CreateOrderResponse {
    private String messageError;
    private int statusCode;
    private CreateOrderData data;

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

    public CreateOrderData getData() {
        return data;
    }

    public void setData(CreateOrderData data) {
        this.data = data;
    }

    public static class CreateOrderData {
        private String order_date;
        private String paymentMenthod_id;
        private int orderTotal;
        private int orderStatus;
        private String address;
        private Object paymentMenthod;
        private String user_id;
        private Object user;
        private List<Cart> carts;
        private String id;
        private String createdTime;
        private String lastUpdatedTime;
        private Object deletedTime;

        public String getOrder_date() {
            return order_date;
        }

        public void setOrder_date(String order_date) {
            this.order_date = order_date;
        }

        public String getPaymentMenthod_id() {
            return paymentMenthod_id;
        }

        public void setPaymentMenthod_id(String paymentMenthod_id) {
            this.paymentMenthod_id = paymentMenthod_id;
        }

        public int getOrderTotal() {
            return orderTotal;
        }

        public void setOrderTotal(int orderTotal) {
            this.orderTotal = orderTotal;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getPaymentMenthod() {
            return paymentMenthod;
        }

        public void setPaymentMenthod(Object paymentMenthod) {
            this.paymentMenthod = paymentMenthod;
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

        public List<Cart> getCarts() {
            return carts;
        }

        public void setCarts(List<Cart> carts) {
            this.carts = carts;
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

    public static class Cart {
        private String product_id;
        private Product product;
        private String order_id;
        private Object order;
        private int price;
        private int quantity;
        private String user_id;
        private Object user;
        private String id;
        private String createdTime;
        private String lastUpdatedTime;
        private Object deletedTime;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
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

    public static class Product {
        private String category_id;
        private String image;
        private int price;
        private String name;
        private boolean sex;
        private String description;
        private int discount;
        private int statusProduct;
        private String day_of_birth;
        private Object birdCategory;
        private List<Object> carts;
        private Object userID;
        private String id;
        private String createdTime;
        private String lastUpdatedTime;
        private Object deletedTime;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSex() {
            return sex;
        }

        public void setSex(boolean sex) {
            this.sex = sex;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public int getStatusProduct() {
            return statusProduct;
        }

        public void setStatusProduct(int statusProduct) {
            this.statusProduct = statusProduct;
        }

        public String getDay_of_birth() {
            return day_of_birth;
        }

        public void setDay_of_birth(String day_of_birth) {
            this.day_of_birth = day_of_birth;
        }

        public Object getBirdCategory() {
            return birdCategory;
        }

        public void setBirdCategory(Object birdCategory) {
            this.birdCategory = birdCategory;
        }

        public List<Object> getCarts() {
            return carts;
        }

        public void setCarts(List<Object> carts) {
            this.carts = carts;
        }

        public Object getUserID() {
            return userID;
        }

        public void setUserID(Object userID) {
            this.userID = userID;
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
