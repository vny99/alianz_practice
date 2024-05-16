package com.alianz.practice.alianz_practice.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.alianz.practice.alianz_practice.Entity.ProductCategory;

import reactor.core.publisher.Mono;

@Repository
public interface ProductCategoryRepository extends R2dbcRepository<ProductCategory, Integer> {

    public Mono<ProductCategory> findByType(String type);

    public Mono<Void> deleteByType(String type);

    public Mono<Boolean> existsByType(String type);

}
