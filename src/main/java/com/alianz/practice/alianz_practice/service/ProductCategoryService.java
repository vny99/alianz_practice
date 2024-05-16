package com.alianz.practice.alianz_practice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alianz.practice.alianz_practice.Entity.Product;
import com.alianz.practice.alianz_practice.Entity.ProductCategory;
import com.alianz.practice.alianz_practice.Entity.Stock;
import com.alianz.practice.alianz_practice.dao.ProductCategoryDAO;
import com.alianz.practice.alianz_practice.dao.ProductDAO;
import com.alianz.practice.alianz_practice.dao.StockDAO;
import com.alianz.practice.alianz_practice.exceptions.ProductCouldNotBeAdderException;
import com.alianz.practice.alianz_practice.model.ProductData;
import com.alianz.practice.alianz_practice.response.Resp;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryDAO productCategoryDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private StockDAO stockDAO;

    private static final String PRODUCT_COULD_NOT_BE_ADDED = "Product could not be added ";

    private static Logger logger = LoggerFactory.getLogger(ProductCategoryService.class);

    public Mono<Void> deleteByType(String type) {
        return productCategoryDAO.parse(type).map(ProductCategory::getId).flatMap(productDAO::deleteByType).then();
    }

    public Flux<ProductCategory> getAllTypes() {
        return productCategoryDAO.getAllCetegories();
    }

    public Mono<Stock> updateStock(String name, int stock) {
        return stockDAO.updateStock(name, stock);
    }

    public Mono<Stock> getStockByName(String name) {
        return stockDAO.getStockByName(name);
    }

    public Flux<Product> getProductsByType(String productCategory) {
        return productCategoryDAO.parse(productCategory).map(ProductCategory::getId)
                .flatMapMany(productDAO::getProductsByType);
    }

    public Mono<ProductCategory> addType(String category) {
        return Mono.justOrEmpty(category).flatMap(productCategoryDAO::create);
    }

    public Mono<Void> loadAllTypes() {
        return Flux.fromArray(ProductData.PRODUCT_TYPES)
                .concatMap((type) -> {
                    logger.info("Product type {}", type);
                    return productCategoryDAO.create(type);
                })
                .onErrorResume(
                        ex -> Mono.error(new ProductCouldNotBeAdderException(PRODUCT_COULD_NOT_BE_ADDED +
                                ex.getMessage())))
                .then();
    }

    public Flux<Stock> getAllStock() {
        return stockDAO.getAllStock();
    }

    public Flux<Stock> getStockByNames(List<String> productNames) {
        return Flux.fromIterable(productNames)
                .flatMap(stockDAO::getStockByName);
    }

    public Mono<Void> clearStock() {
        return stockDAO.clearStock();
    }

}
