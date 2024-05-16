package com.alianz.practice.alianz_practice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alianz.practice.alianz_practice.Entity.Product;
import com.alianz.practice.alianz_practice.dao.ProductDAO;
import com.alianz.practice.alianz_practice.requests.CreateProductRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private static final String PRODUCT_NOT_FOUND = "Product not found id : ";
    private static final String PRODUCT_ADD_ERROR = "Product could not be added";

    @Autowired
    private ProductDAO productDAO;

    public Mono<Product> getProductById(String id) {
        return productDAO.getProductById(id);
    }

    public Mono<Void> deleteProductById(String id) {
        return productDAO.deleteProductById(id);
    }

    public Mono<Void> updateProduct(CreateProductRequest request, String id) {
        return productDAO.updateProduct(request, id);
    }

    public Mono<String> addProduct(CreateProductRequest request) {
        return productDAO.addProduct(request);
    }

    public Flux<String> getAllProductIds() {
        return productDAO.getAllProductIds();
    }

    // @PostConstruct
    public Mono<Void> loadProducts() {
        return productDAO.loadProducts();
    }

    public Mono<Void> deleteAllProducts() {
        return productDAO.deleteAllProducts();
    }

    public Flux<Product> getProductsByIds(List<String> ids) {
        return productDAO.getProductsByIds(ids);
    }

}
