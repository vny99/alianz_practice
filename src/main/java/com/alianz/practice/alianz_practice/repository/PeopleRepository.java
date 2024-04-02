package com.alianz.practice.alianz_practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alianz.practice.alianz_practice.Entity.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer>{
    
}
