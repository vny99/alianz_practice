package com.alianz.practice.alianz_practice.dao;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alianz.practice.alianz_practice.Entity.Stock;
import com.alianz.practice.alianz_practice.repository.StockRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StockDAO {
    @Autowired private StockRepository stockRepository;

    private static final String STOCK_NOT_FOUND = "Stock not found for product name : ";

    public Mono<Stock> updateStock(String name, int stock) {
       return getStockByName(name).flatMap(existingStock ->{
        existingStock.setProductStock(stock);
        existingStock.setNew(Boolean.FALSE);
        return stockRepository.save(existingStock);
       });
    }

    public Mono<Stock> getStockByName(String name) {
        return stockRepository.existsByProductName(name.toUpperCase()).flatMap(exists -> {
            if(exists){
                return stockRepository.findByProductName(name.toUpperCase());
            } else {
                return Mono.error(new NoSuchElementException(STOCK_NOT_FOUND + name));
            }
           });
    }

    public Mono<Stock> updateStock(String newProductName, String oldProductName){
        return this.decrementStock(oldProductName).flatMap(stock -> incrementStock(newProductName));
        
    }

    public Mono<Stock> incrementStock(String name){
        return this.parseStock(name.toUpperCase()).flatMap(stock -> {
            stock.setProductStock(stock.getProductStock() + 1);
            stock.setNew(Boolean.FALSE);
            return stockRepository.save(stock);
        });
    }

    public Mono<Stock> decrementStock(String name){
        return this.parseStock(name.toUpperCase()).flatMap(stock -> {
            stock.setProductStock(stock.getProductStock() - 1);
            stock.setNew(Boolean.FALSE);
            return stockRepository.save(stock);
        });
    }

    public Mono<Stock> parseStock (String name){
        return stockRepository.existsByProductName(name).flatMap(exists -> {
            if(exists){
                return stockRepository.findByProductName(name);
            } else {
                return stockRepository.save(new Stock(UUID.randomUUID().toString(), name, 0));
            }
           });
    }

    public Flux<Stock> getAllStock() {
        return stockRepository.findAll();
    }

    public Mono<Void> clearStock() {
        return stockRepository.deleteAll();
    }
    
}
