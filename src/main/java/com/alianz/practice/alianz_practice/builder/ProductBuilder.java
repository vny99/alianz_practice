package com.alianz.practice.alianz_practice.builder;

import com.alianz.practice.alianz_practice.Entity.Product;
import com.alianz.practice.alianz_practice.Entity.ProductCatergory;
import com.alianz.practice.alianz_practice.requests.CreateProductRequest;

public class ProductBuilder {
    private String name;
    private String description;
    private ProductCatergory type;
    private Double price;

    public static ProductBuilder from(CreateProductRequest request) {
        return new ProductBuilder().withName(request.getProductName()).withDescription(request.getProductDescription())
                .withType(request.getProductType()).withPrice(request.getProductPrice());
    }

    private ProductBuilder withName(String productName) {
        this.name = productName;
        return this;
    }

    private ProductBuilder withDescription(String descrition) {
        this.description = descrition;
        return this;
    }

    private ProductBuilder withType(String type) {
        this.type = ProductCatergory.parse(type);
        return this;
    }

    private ProductBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }

    public Product build() {
        return new Product(null, name, description, type, price);
    }

}
