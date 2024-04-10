package com.alianz.practice.alianz_practice.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alianz.practice.alianz_practice.service.MorseService;


@RestController
@RequestMapping("/morse")
public class MorseController {

    @Autowired
    private MorseService service;    
    @GetMapping("/decode/")
    public ResponseEntity<Set<String>> decode(@RequestParam String code) {
        return ResponseEntity.ok(service.decode(code));
    }
    
}
