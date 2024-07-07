package com.example.birdstoreandroid.Feature.Order;

public class OrderItem {
    private String id;
    private String product_id;
    private float price;
    private OrderItem.Product product;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public OrderItem.Product getProduct() {
        return product;
    }

    public void setProduct(OrderItem.Product product) {
        this.product = product;
    }

    public class Product{
        private String id;
        private String name;
        private float price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }
    }
}
