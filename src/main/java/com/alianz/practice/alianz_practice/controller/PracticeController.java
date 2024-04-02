package com.alianz.practice.alianz_practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alianz.practice.alianz_practice.Entity.Person;
import com.alianz.practice.alianz_practice.service.PracticeService;

@RestController
@RequestMapping("/alianz/practice")
public class PracticeController {

    @Autowired
    private PracticeService service;

    @GetMapping("/fizzbuzz/{limit}")
    public ResponseEntity<List<String>> getFizzBuzz(@PathVariable Integer limit) {
        return ResponseEntity.ok(service.getFizzBuzz(limit));
    }

    @GetMapping("/generateSeries/{limit}")
    public ResponseEntity<String> generateSeries(@PathVariable Integer limit) {
        return ResponseEntity.ok(service.generateSeries(limit));
    }

    @PostMapping("/generatePeople/")
    public ResponseEntity<List<Person>> postMethodName(@RequestParam("count") Integer count) {
        return ResponseEntity.ok(service.generatePeople(count));
    }

    @GetMapping("/female-minors/count")
    public ResponseEntity<Integer> getNumOfFemaleMinors() {
        int count = service.getNumOfFemaleMinors();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/adults/percentage")
    public ResponseEntity<Double> getLegalPercentage() {
        Double percentage = service.getLegalPercentage();
        return new ResponseEntity<>(percentage, HttpStatus.OK);
    }

    @GetMapping("/females/percentage")
    public ResponseEntity<Object> getFemalePercentage() {
        Object percentage = service.getFemalePercentage();
        return new ResponseEntity<>(percentage, HttpStatus.OK);
    }

    @GetMapping("/adults/count")
    public ResponseEntity<Integer> getNumOfAdults() {
        int count = service.getNumOfAdults();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/minors/count")
    public ResponseEntity<Integer> getNumOfMinors() {
        int count = service.getNumOfMinors();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/male-adults/count")
    public ResponseEntity<Integer> getNumOfMaleAdults() {
        int count = service.getNumOfMaleAdults();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/wage")
    public ResponseEntity<Double> getWage(@RequestParam("hours") int hours, @RequestParam("rate") int rate) {
        double wage = service.getWage(hours, rate);
        return new ResponseEntity<>(wage, HttpStatus.OK);
    }

}
