package com.alianz.practice.alianz_practice.helper;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alianz.practice.alianz_practice.Entity.Product;
import com.alianz.practice.alianz_practice.Entity.ProductCatergory;
import com.alianz.practice.alianz_practice.repository.ProductRespository;

@Service
public class ProductHelper {
    @Autowired
    private ProductRespository repo;

    public void deleteProductByType(String type) {
        java.util.List<Product> products = repo.findByType(ProductCatergory.parse(type));
        repo.deleteAll(products);
    }
    
}
