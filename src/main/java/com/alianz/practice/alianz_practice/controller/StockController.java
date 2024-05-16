package com.alianz.practice.alianz_practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alianz.practice.alianz_practice.Entity.Stock;
import com.alianz.practice.alianz_practice.builder.RespBuilder;
import com.alianz.practice.alianz_practice.response.Resp;
import com.alianz.practice.alianz_practice.service.ProductCategoryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alianz/product/stock")
public class StockController {

    @Autowired private ProductCategoryService productCategoryService;

    private static final String NEGETIVE_STOCK = "Stock cannot be negetive";
    private static final String AUTH_HEADER = "X-Authorities";
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    private static final String ACCESS_DENIED = "ONLY ADMIN CAN PERFORM THIS OPERATION";

    @GetMapping("/get")
    public Mono<ResponseEntity<Resp>> getStockByName(@RequestParam String productName, @RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN) && !authorties.contains(USER)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RespBuilder.from(ACCESS_DENIED, HttpStatus.FORBIDDEN).build()));
        }
        return productCategoryService.getStockByName(productName)
                .map(stock -> ResponseEntity.ok().body(RespBuilder.from(stock, HttpStatus.OK).build()));
    }

    @GetMapping("/getByNames")
    public Flux<Stock> getStockByNames(@RequestParam List<String> productNames) {
        return productCategoryService.getStockByNames(productNames);
    }

    @DeleteMapping("/clear")
    public Mono<Void> clearStock(@RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN)) {
            return Mono.error(new IllegalAccessException(ACCESS_DENIED));
        }
        return productCategoryService.clearStock();
    }

    @PutMapping("/update")
    public Mono<Void> updateStock(@RequestParam String productName, @RequestParam int stock, @RequestHeader(AUTH_HEADER) String authorties){
        if(!authorties.contains(ADMIN)){
            return Mono.error(new IllegalAccessException(ACCESS_DENIED));
        }
        if (stock < 0)
            throw new IllegalArgumentException(NEGETIVE_STOCK);
        return productCategoryService.updateStock(productName, stock).then();
    }

    @GetMapping("/get/all")
    public Mono<ResponseEntity<Resp>> getAllStock(@RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN) && !authorties.contains(USER)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RespBuilder.from(ACCESS_DENIED, HttpStatus.FORBIDDEN).build()));
        }
        return productCategoryService.getAllStock().collectList()
                .map(stocks -> ResponseEntity.ok().body(RespBuilder.from(stocks, HttpStatus.OK).build()));
    }
}
