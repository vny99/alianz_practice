package com.alianz.practice.alianz_practice.builder;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alianz.practice.alianz_practice.Entity.Product;
import com.alianz.practice.alianz_practice.Entity.ProductCategory;
import com.alianz.practice.alianz_practice.dao.ProductCategoryDAO;
import com.alianz.practice.alianz_practice.requests.CreateProductRequest;

import reactor.core.publisher.Mono;

@Service
public class ProductBuilder {

    private String name;
    private String description;
    private int typeId;
    private String stockId;
    private Double price;

    public static ProductBuilder from(CreateProductRequest request, int typeId, String stockId) {
        return new ProductBuilder().withName(request.getProductName())
                .withDescription(request.getProductDescription())
                .withType(typeId).withStock(stockId).withPrice(request.getProductPrice());
    }

    private ProductBuilder withStock(String stockId) {
        this.stockId = stockId;
        return this;
    }

    private ProductBuilder withName(String productName) {
        this.name = productName;
        return this;
    }

    private ProductBuilder withDescription(String descrition) {
        this.description = descrition;
        return this;
    }

    private ProductBuilder withType(int type) {
        this.typeId = type;
        return this;
    }

    private ProductBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }

    public Product build() {
        return new Product(UUID.randomUUID().toString(), name.toUpperCase(), description, typeId, stockId, price);
    }

}
