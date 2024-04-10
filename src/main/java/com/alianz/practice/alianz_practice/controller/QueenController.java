package com.alianz.practice.alianz_practice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alianz.practice.alianz_practice.service.QueensService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/queen")
public class QueenController {

    @Autowired
    private QueensService queenService;
    @GetMapping("/combinations/")
    public ResponseEntity<List<String>> getQueensArrangement(@RequestParam String num) {
        return ResponseEntity.ok(queenService.arrangeQueens(Integer.parseInt(num)));
    }
    
}
