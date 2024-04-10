package com.alianz.practice.alianz_practice.requests;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateProductRequest {

    @NotBlank(message = "Product Name is required") 
    private String productName;
    @NotBlank(message = "Product Description is required")
    private String productDescription;
    @NotBlank(message = "Product Type is required")
    private String productType;
    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0.0")
    @DecimalMax(value = "100000.0", message = "Price must be less than or equal to 100000.0")
    private Double productPrice;

    public CreateProductRequest() {
    }
    
    public CreateProductRequest(String productName, String productDescription, String productType,
            Double productPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productType = productType;
        this.productPrice = productPrice;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public Double getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }
    @Override
    public String toString() {
        return "CreateProductRequest [productName=" + productName + ", productDescription="
                + productDescription + ", productType=" + productType + ", productPrice=" + productPrice + "]";
    }
    
}
