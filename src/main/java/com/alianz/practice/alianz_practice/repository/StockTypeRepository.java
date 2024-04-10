package com.alianz.practice.alianz_practice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alianz.practice.alianz_practice.Entity.ProductCatergory;
import com.alianz.practice.alianz_practice.Entity.TypeStock;
@Repository
public interface StockTypeRepository extends JpaRepository<TypeStock, String>{
    public Optional<TypeStock> findByCatergory ( ProductCatergory stockType);
    public void deleteByCatergory(ProductCatergory stockType);
}
