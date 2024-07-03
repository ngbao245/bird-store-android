package com.example.birdstoreandroid.Model;

public class GetProductDetailResponse {
    private String messageError;
    private int statusCode;
    private ProductDetail data;

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

    public ProductDetail getData() {
        return data;
    }

    public void setData(ProductDetail data) {
        this.data = data;
    }

    public static class ProductDetail {
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
