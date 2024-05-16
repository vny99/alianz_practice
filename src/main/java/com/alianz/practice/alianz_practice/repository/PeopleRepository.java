package com.alianz.practice.alianz_practice.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.alianz.practice.alianz_practice.Entity.Person;

@Repository
public interface PeopleRepository extends R2dbcRepository<Person, Integer>{
    
}
