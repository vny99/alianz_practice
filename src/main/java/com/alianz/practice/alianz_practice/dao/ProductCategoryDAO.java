package com.alianz.practice.alianz_practice.dao;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alianz.practice.alianz_practice.Entity.ProductCategory;
import com.alianz.practice.alianz_practice.exceptions.ProductCouldNotBeAdderException;
import com.alianz.practice.alianz_practice.repository.ProductCategoryRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductCategoryDAO {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    private static final String PRODUCT_CATEGORY_ALREADY_EXISTS = "Product Category already exists: ";
    private static final String PRODUCT_CATEGORY_NOT_FOUND = "Product Category not found: ";

    private static final String PRODUCT_CATEGORY_COULD_NOT_BE_ADDED = "Proudct Category could not be added ;";

    public Mono<ProductCategory> create(String type) {
        return Mono.justOrEmpty(type).flatMap(this::isExistingType).flatMap(exists -> {
            if (exists) {
                return Mono.error(new ProductCouldNotBeAdderException(PRODUCT_CATEGORY_ALREADY_EXISTS + type));
            } else {
                return Mono.just(type);
            }
        }).flatMap(typeValue -> {
            return setTheProductId().flatMap(id -> {
                ProductCategory productCategory = new ProductCategory();
                productCategory.setType(type);
                productCategory.setId(id);
                return productCategoryRepository.save(productCategory);
            });
        });
    }

    private Mono<Integer> setTheProductId() {
        return productCategoryRepository.count().flatMap(count -> {
            int nextId = count.intValue() + 1;
            return Mono.just(nextId);
        });
    }

    public Mono<ProductCategory> parse(int id) {
        return productCategoryRepository.findById(id);
    }

    public Mono<ProductCategory> parse(String type) {
        return productCategoryRepository.findByType(type);
    }

    public Mono<Void> delete(String type) {
        return productCategoryRepository.deleteByType(type)
                .onErrorMap(ex -> new NoSuchElementException(PRODUCT_CATEGORY_NOT_FOUND + type));
    }

    public Mono<Boolean> isExistingType(String type) {
        return productCategoryRepository.existsByType(type);
    }

    public Mono<Boolean> isExistingType(int id) {
        return productCategoryRepository.existsById(id);
    }

    public Flux<ProductCategory> getAllCetegories() {
        return productCategoryRepository.findAll();
    }

}
