package com.alianz.practice.alianz_practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alianz.practice.alianz_practice.Entity.Product;
import com.alianz.practice.alianz_practice.builder.RespBuilder;
import com.alianz.practice.alianz_practice.requests.CreateProductRequest;
import com.alianz.practice.alianz_practice.response.Resp;
import com.alianz.practice.alianz_practice.service.ProductService;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alianz/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final String PRODUCT_DELETED_SUCCESSFULLY = "Product deleted successfully id : ";
    private static final String PRODUCT_UPDATED_SUCCESSFULLY = "Product updated successfully id : ";
    private static final String PRODUCT_ADDED_SUCCESSFULLY = "Product added successfully id : ";
    private static final String PRODUCTS_LOADED_SUCCESSFULLY = "Products loaded sucessfully :";
    private static final String PRODUCTS_DELETED_SUCCESSFULLY = "Products deleted sucessfully :";
    private static final String AUTH_HEADER = "X-Authorities";
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    private static final String ACCESS_DENIED = "ONLY ADMIN CAN PERFORM THIS OPERATION";

    @GetMapping("/get/")
    public Mono<ResponseEntity<Resp>> getProductById(@RequestParam String id,
            @RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN) && !authorties.contains(USER)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RespBuilder.from(ACCESS_DENIED, HttpStatus.FORBIDDEN).build()));
        }
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok().body(RespBuilder.from(product, HttpStatus.OK).build()));
    }

    @GetMapping("/getByIds")
    public Flux<Product> getProductsByIds(@RequestParam List<String> ids) {
        return productService.getProductsByIds(ids);
    }

    @DeleteMapping("/delete")
    public Mono<ResponseEntity<Resp>> deleteProductById(@RequestParam String id,
            @RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RespBuilder.from(ACCESS_DENIED, HttpStatus.FORBIDDEN).build()));
        }
        return productService.deleteProductById(id).then(Mono.just(
                ResponseEntity.ok().body(RespBuilder.from(PRODUCTS_DELETED_SUCCESSFULLY + id, HttpStatus.OK).build())));
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<Resp>> updateProduct(@RequestParam String id,
            @Valid @RequestBody CreateProductRequest request, @RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RespBuilder.from(ACCESS_DENIED, HttpStatus.FORBIDDEN).build()));
        }
        return productService.updateProduct(request, id).then(Mono.just(
                ResponseEntity.ok().body(RespBuilder.from(PRODUCT_UPDATED_SUCCESSFULLY + id, HttpStatus.OK).build()))
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest()
                        .body(RespBuilder.from(ex.getMessage(), HttpStatus.BAD_REQUEST).build()))));
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<Resp>> addProduct(@Valid @RequestBody CreateProductRequest request,
            @RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RespBuilder.from(ACCESS_DENIED, HttpStatus.FORBIDDEN).build()));
        }
        return productService.addProduct(request)
                .map(productId -> ResponseEntity.ok().body(RespBuilder.from(productId, HttpStatus.OK).build()))
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest()
                        .body(RespBuilder.from(ex.getMessage(), HttpStatus.BAD_REQUEST).build())));
    }

    @PostMapping("/load")
    public Mono<ResponseEntity<Resp>> loadProducts(@RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RespBuilder.from(ACCESS_DENIED, HttpStatus.FORBIDDEN).build()));
        }
        return productService.loadProducts().then(Mono.just(ResponseEntity.badRequest()
                .body(RespBuilder.from(PRODUCTS_LOADED_SUCCESSFULLY, HttpStatus.OK).build())));
    }

    @PostMapping("/unload")
    public Mono<ResponseEntity<Resp>> unloadProducts(@RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RespBuilder.from(ACCESS_DENIED, HttpStatus.FORBIDDEN).build()));
        }
        return productService.deleteAllProducts().then(Mono.just(ResponseEntity.badRequest()
                .body(RespBuilder.from(PRODUCTS_DELETED_SUCCESSFULLY, HttpStatus.OK).build())));
    }

    @GetMapping("/getAllIds")
    public Mono<ResponseEntity<Resp>> getAllProductIds(@RequestHeader(AUTH_HEADER) String authorties) {
        if (!authorties.contains(ADMIN) && !authorties.contains(USER)) {
            return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(RespBuilder.from(ACCESS_DENIED, HttpStatus.FORBIDDEN).build()));
        }
        return productService.getAllProductIds().collectList()
                .map(ids -> ResponseEntity.ok().body(RespBuilder.from(ids, HttpStatus.OK).build()));
    }

}
