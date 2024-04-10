package com.alianz.practice.alianz_practice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alianz.practice.alianz_practice.Entity.Product;
import com.alianz.practice.alianz_practice.Entity.ProductCatergory;
@Repository
public interface ProductRespository extends JpaRepository<Product, String>{
    public void deleteByType(ProductCatergory type);

    public List<Product> findByType(ProductCatergory productCatergory);
}
