package com.alianz.practice.alianz_practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alianz.practice.alianz_practice.builder.RespBuilder;
import com.alianz.practice.alianz_practice.response.Resp;
import com.alianz.practice.alianz_practice.service.ProductCategoryService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alianz/product/type")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    private static final String PRODUCTS_OF_TYPE_DELETED_SUCCESS = "Products deleted successfully of type : ";
    private static final String PRODUCT_TYPES_ADDED_SUCCESS = "Product types added successfully :";
    private static final String AUTH_HEADER = "X-Authorities";
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    private static final String ACCESS_DENIED = "ONLY ADMIN CAN PERFORM THIS OPERATION";

    @GetMapping("/get")
    public Mono<ResponseEntity<Resp>> getProductsByType(@RequestParam String productCategory,
            @RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN) && !authorties.contains(USER)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RespBuilder.from(ACCESS_DENIED, HttpStatus.FORBIDDEN).build()));
        }
        return productCategoryService.getProductsByType(productCategory).collectList()
                .map(stocks -> ResponseEntity.ok().body(RespBuilder.from(stocks, HttpStatus.OK).build()));
    }

    @DeleteMapping("/delete")
    public Mono<ResponseEntity<Resp>> deleteProductByType(@RequestParam String productCategory,
            @RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RespBuilder.from(ACCESS_DENIED, HttpStatus.FORBIDDEN).build()));
        }
        return productCategoryService.deleteByType(productCategory).then(Mono.just(ResponseEntity.ok()
                .body(RespBuilder.from(PRODUCTS_OF_TYPE_DELETED_SUCCESS + productCategory, HttpStatus.OK).build())));
    }

    @GetMapping("/get/all")
    public Mono<ResponseEntity<Resp>> getAllTypes(@RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN) && !authorties.contains(USER)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RespBuilder.from(ACCESS_DENIED, HttpStatus.FORBIDDEN).build()));
        }
        return productCategoryService.getAllTypes().collectList()
                .map(types -> ResponseEntity.ok().body(RespBuilder.from(types, HttpStatus.OK).build()));
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<Resp>> addType(@RequestParam String category,
            @RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RespBuilder.from(ACCESS_DENIED, HttpStatus.FORBIDDEN).build()));
        }
        return productCategoryService.addType(category).map(
                productCategory -> ResponseEntity.ok().body(RespBuilder.from(productCategory, HttpStatus.OK).build()));
    }

    @PostMapping("/load/all")
    public Mono<ResponseEntity<Resp>> addAllTypes(@RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN) && !authorties.contains(USER)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RespBuilder.from(ACCESS_DENIED, HttpStatus.FORBIDDEN).build()));
        }
        return productCategoryService.loadAllTypes().then(Mono
                .just(ResponseEntity.ok().body(RespBuilder.from(PRODUCT_TYPES_ADDED_SUCCESS, HttpStatus.OK).build())));
    }

}
