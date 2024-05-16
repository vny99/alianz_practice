package com.alianz.practice.alianz_practice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.alianz.practice.alianz_practice.Entity.Product;
import com.alianz.practice.alianz_practice.Entity.ProductCategory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
public interface ProductRespository extends R2dbcRepository<Product, String>{
    public Mono<Void> deleteByProductCategoryId(ProductCategory type);
    
    public Flux<Product> findByProductCategoryId(ProductCategory productCatergory);
    
    public Mono<Product> findById(String id);

    public Flux<Product> getByProductCategoryId(int typeId);

    public Mono<Void> deleteByProductCategoryId(int typeId);

}
