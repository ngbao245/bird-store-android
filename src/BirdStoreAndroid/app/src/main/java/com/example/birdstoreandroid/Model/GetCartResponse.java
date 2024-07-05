package com.example.birdstoreandroid.Model;

import java.util.Collection;
import java.util.List;

public class GetCartResponse {
    private String messageError;
    private int statusCode;
    private Collection<? extends com.example.birdstoreandroid.Feature.Cart.CartItem> data;

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

    public Collection<? extends com.example.birdstoreandroid.Feature.Cart.CartItem> getData() {
        return data;
    }

    public void setData(Collection<? extends com.example.birdstoreandroid.Feature.Cart.CartItem> data) {
        this.data = data;
    }

    public class CartItem {
        private String id;
        private String product_id;
        private String order_id;
        private int price;
        private int quantity;
        private String user_id;
        private Product product;
        private String createdTime;
        private String lastUpdatedTime;
        private String deletedTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
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

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
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

    public class Product {
        private String id;
        private String category_id;
        private String image;
        private int price;
        private String name;
        private boolean sex;
        private String description;
        private int statusProduct;
        private int discount;
        private String day_of_birth;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public int getStatusProduct() {
            return statusProduct;
        }

        public void setStatusProduct(int statusProduct) {
            this.statusProduct = statusProduct;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public String getDay_of_birth() {
            return day_of_birth;
        }

        public void setDay_of_birth(String day_of_birth) {
            this.day_of_birth = day_of_birth;
        }
    }
}
