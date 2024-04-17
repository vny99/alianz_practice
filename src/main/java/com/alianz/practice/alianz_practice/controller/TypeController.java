package com.alianz.practice.alianz_practice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alianz.practice.alianz_practice.response.Response;
import com.alianz.practice.alianz_practice.service.TypeService;

@RestController
@RequestMapping("/alianz/product/type")
public class TypeController {

    @Autowired
    private TypeService service;

    @Autowired
    private Response response;

    private static final String PRODUCT_TYPE_DELETED_SUCCESS = "Product deleted successfully type : ";

    @GetMapping("/get")
    @PostAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Map<String, Object>> getStockByType(@RequestParam String type) {
        return ResponseEntity.ok().body(response.buildResponse(service.getStockByType(type), HttpStatus.OK));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> deleteProductByType(@RequestParam String type) {
        service.deleteByType(type);
        return ResponseEntity.ok()
                .body(response.buildResponse(PRODUCT_TYPE_DELETED_SUCCESS + type, HttpStatus.OK));
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<Map<String, Object>> getAllTypes() {
        return ResponseEntity.ok().body(response.buildResponse(service.getAllTypes(), HttpStatus.OK));
    }

}
