package com.alianz.practice.alianz_practice.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alianz.practice.alianz_practice.Entity.ProductCatergory;
import com.alianz.practice.alianz_practice.Entity.Stock;
import com.alianz.practice.alianz_practice.Entity.TypeStock;
import com.alianz.practice.alianz_practice.helper.ProductHelper;
import com.alianz.practice.alianz_practice.repository.StockTypeRepository;

@Service
public class TypeService {

    private static final String TYPE_NOT_FOUND = "Type not found : ";
    @Autowired
    private StockTypeRepository repo;

    @Autowired
    private ProductHelper productHelper;

    public void deleteByType(String type) {
        try {
            TypeStock typeStock = repo.findByCatergory(ProductCatergory.parse(type)).orElseThrow(() -> new NoSuchElementException(TYPE_NOT_FOUND + type));
            repo.delete(typeStock);
            productHelper.deleteProductByType(type);
        } catch (Exception e) {
            throw new NoSuchElementException(TYPE_NOT_FOUND + type);
        }
    }

    public List<ProductCatergory> getAllTypes() {
        return repo.findAll().stream().map(TypeStock::getCatergory).collect(Collectors.toList());
    }

    public List<Stock> getStockByType(String type) {
        TypeStock typeStock = repo.findByCatergory(ProductCatergory.parse(type)).orElseThrow(() -> new NoSuchElementException(TYPE_NOT_FOUND + type));
        return typeStock.getStock();
    }
    
}
