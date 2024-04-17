package com.alianz.practice.alianz_practice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alianz.practice.alianz_practice.Entity.Account;

public interface AccountRespository extends JpaRepository<Account, String>{

    Optional<Account> findByEmail(String username);
    
}
