package com.alianz.practice.alianz_practice.exceptions;

public class ProductsCanNotBeLoadedException extends RuntimeException{
    public ProductsCanNotBeLoadedException(String msg){
        super(msg);
    }
}
