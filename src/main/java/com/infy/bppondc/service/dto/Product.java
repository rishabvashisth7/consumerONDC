package com.infy.bppondc.service.dto;

public class Product {

    private String productName;
    private String productId;

    @Override
    public String toString() {
        return "Product{" + "productName='" + productName + '\'' + ", productId='" + productId + '\'' + '}';
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Product() {}

    public Product(String productName, String productId) {
        this.productName = productName;
        this.productId = productId;
    }
}
