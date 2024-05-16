package com.alianz.practice.alianz_practice.advicer;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alianz.practice.alianz_practice.builder.RespBuilder;
import com.alianz.practice.alianz_practice.exceptions.ProductCouldNotBeAdderException;
import com.alianz.practice.alianz_practice.response.Resp;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ProductCouldNotBeAdderException.class)
    public Mono<ResponseEntity<Resp>> handleProductCouldNotBeAdded(ProductCouldNotBeAdderException exception) {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RespBuilder.from(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Mono<ResponseEntity<Resp>> handleProductNotFound(NoSuchElementException exception) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(RespBuilder.from(exception.getMessage(), HttpStatus.NOT_FOUND).build()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<Resp>> handleIllegalArgumentException(IllegalArgumentException exception) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RespBuilder.from(exception.getMessage(), HttpStatus.BAD_REQUEST).build()));
    }

}
