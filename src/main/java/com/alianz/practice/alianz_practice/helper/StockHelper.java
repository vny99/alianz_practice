package com.alianz.practice.alianz_practice.helper;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alianz.practice.alianz_practice.Entity.Product;
import com.alianz.practice.alianz_practice.Entity.ProductCatergory;
import com.alianz.practice.alianz_practice.Entity.Stock;
import com.alianz.practice.alianz_practice.Entity.TypeStock;
import com.alianz.practice.alianz_practice.repository.StockTypeRepository;

@Service
public class StockHelper {

    private String stockName;


    @Autowired
    private StockTypeRepository repo;

    public TypeStock storeAndGet(Product product) {
        this.stockName = product.getName();
        return withRemainingStock(product);
    }

    private TypeStock withRemainingStock(Product product) {
        TypeStock typeStock = repo.findByCatergory(product.getType()).orElse(null);
        if (typeStock == null) {
            typeStock = new TypeStock(null, product.getType(), new ArrayList<>());
            Stock newStock = new Stock(stockName, 1);
            typeStock.getStock().add(newStock);
        } else {
            Stock stock = typeStock.getStock().stream().filter(s -> s.getStockName().equals(product.getName()))
                    .findFirst()
                    .orElse(null);
            if (stock == null) {
                Stock newStock = new Stock(stockName, 1);
                typeStock.getStock().add(newStock);
            } else {
                stock.setRemainingStock(stock.getRemainingStock() + 1);
            }

        }
        repo.save(typeStock);
        return typeStock;

    }

    public void deleteStock(String stockName, ProductCatergory type) {
        TypeStock typeStock = repo.findByCatergory(type).orElse(null);
        if (typeStock != null) {
            Stock stock = typeStock.getStock().stream().filter(s -> s.getStockName().equals(stockName)).findFirst()
                    .orElse(null);
            if (stock != null) {
                int remStock = stock.getRemainingStock() - 1;
                if (remStock == 0) {
                    typeStock.getStock().remove(stock);
                    if (typeStock.getStock().isEmpty()) {
                        repo.delete(typeStock);
                    } else {
                        stock.setRemainingStock(remStock);
                    }
                    repo.save(typeStock);
                }

            }

        }
    }

    public void deleteAllStocks() {
        repo.deleteAll();
    }

}
