package com.alianz.practice.alianz_practice.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.alianz.practice.alianz_practice.Entity.Stock;

import reactor.core.publisher.Mono;

public interface StockRepository extends R2dbcRepository<Stock, String>{

    Mono<Stock> findByProductName(String name);

    Mono<Boolean> existsByProductName(String name);
    
}
