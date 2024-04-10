package com.alianz.practice.alianz_practice.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alianz.practice.alianz_practice.requests.CreateProductRequest;
import com.alianz.practice.alianz_practice.requests.Response;
import com.alianz.practice.alianz_practice.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/alianz/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private Response response;

    private static final String PRODUCT_DELETED_SUCCESSFULLY = "Product deleted successfully id : ";
    private static final String PRODUCT_UPDATED_SUCCESSFULLY = "Product updated successfully id : ";
    private static final String PRODUCT_ADDED_SUCCESSFULLY = "Product added successfully id : ";
    private static final String PRODUCTS_LOADED_SUCCESSFULLY = "Products loaded sucessfully";
    private static final String PRODUCTS_DELETED_SUCCESSFULLY = "Products deleted sucessfully";

    @GetMapping("/get/")
    public ResponseEntity<Map<String, Object>> getProductById(@RequestParam String id) {
        return ResponseEntity.ok().body(response.buildResponse(service.getProductById(id), HttpStatus.OK));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteProductById(@RequestParam String id) {
        service.deleteProductById(id);
        return ResponseEntity.ok()
                .body(response.buildResponse(PRODUCT_DELETED_SUCCESSFULLY+id, HttpStatus.OK));
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateProduct(@RequestParam String id,
            @Valid @RequestBody CreateProductRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(response.buildResponse(getValidationErrors(result.getAllErrors()), HttpStatus.BAD_REQUEST));
        }
        service.updateProduct(request, id);
        return ResponseEntity.ok()
                .body(response.buildResponse(PRODUCT_UPDATED_SUCCESSFULLY, HttpStatus.OK));
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addProduct(@Valid @RequestBody CreateProductRequest request,
            BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(response.buildResponse(getValidationErrors(result.getAllErrors()), HttpStatus.BAD_REQUEST));
        }
        String id = service.addProduct(request);
        return ResponseEntity.ok()
                .body(response.buildResponse(PRODUCT_ADDED_SUCCESSFULLY+id, HttpStatus.OK));
    }

    @PostMapping("/load")
    public ResponseEntity<Map<String, Object>> loadProducts() {
        service.loadProducts();
        return ResponseEntity.ok().body(response.buildResponse(PRODUCTS_LOADED_SUCCESSFULLY, HttpStatus.OK));
    }

    @PostMapping("/unload")
    public ResponseEntity<Map<String, Object>> unloadProducts() {
        service.deleteAllProducts();
        return ResponseEntity.ok().body(response.buildResponse(PRODUCTS_DELETED_SUCCESSFULLY, HttpStatus.OK));
    }

    @GetMapping("/getAllIds")
    public ResponseEntity<Map<String, Object>> getAllProductIds() {
        return ResponseEntity.ok().body(response.buildResponse(service.getAllProductIds(), HttpStatus.OK));
    }


    private List<String> getValidationErrors(List<ObjectError> allErrors) {
        return allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
    }

}
