package com.tranthanhqueanh.myapplication.models;

public class Product {
    private int productID;
    private String productName;
    private double unitPrice;
    private String imageLink;

    public Product(int productID, String productName, double unitPrice, String imageLink) {
        this.productID = productID;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.imageLink = imageLink;
    }

    public int getProductID() { return productID; }
    public String getProductName() { return productName; }
    public double getUnitPrice() { return unitPrice; }
    public String getImageLink() { return imageLink; }
}
