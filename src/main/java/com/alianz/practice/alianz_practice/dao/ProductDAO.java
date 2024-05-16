package com.alianz.practice.alianz_practice.dao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alianz.practice.alianz_practice.Entity.Product;
import com.alianz.practice.alianz_practice.Entity.ProductCategory;
import com.alianz.practice.alianz_practice.Entity.Stock;
import com.alianz.practice.alianz_practice.builder.ProductBuilder;
import com.alianz.practice.alianz_practice.exceptions.ProductCouldNotBeAdderException;
import com.alianz.practice.alianz_practice.model.ProductData;
import com.alianz.practice.alianz_practice.repository.ProductRespository;
import com.alianz.practice.alianz_practice.requests.CreateProductRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductDAO {

    @Autowired
    private ProductRespository productRepository;

    @Autowired
    private ProductCategoryDAO productCategoryDAO;

    @Autowired
    private StockDAO stockDAO;

    private static final String PRODUCT_NOT_FOUND = "Product not found id : ";
    private static final String PRODUCT_ADD_ERROR = "Product could not be added ;";
    private static final int DUMMY_DATE_COUNT = 50;
    private static final int INTEGER_ZERO = 0;

    private static final String TYPE_NOT_FOUND = "Type Could not be found : ";

    public Mono<Product> getProductById(String id) {
        return productRepository.findById(id).onErrorMap(
                exception -> new NoSuchElementException(PRODUCT_NOT_FOUND + id + " : " + exception.getMessage()))
                .switchIfEmpty(Mono.error(new NoSuchElementException(PRODUCT_NOT_FOUND + id)));
    }

    public Mono<Void> deleteProductById(String id) {
        return productRepository.findById(id).flatMap(product -> stockDAO.decrementStock(product.getName()))
                .then(productRepository.deleteById(id)).onErrorMap(exception -> new NoSuchElementException(
                        PRODUCT_NOT_FOUND + id + " : " + exception.getMessage()));
    }

    public Mono<Void> updateProduct(CreateProductRequest request, String id) {
        return productRepository.existsById(id).flatMap(exists -> {
            if (exists) {
                return productRepository.findById(id).flatMap(oldProduct -> {
                   return  productCategoryDAO.parse(request.getProductType())
                   .flatMap(type -> stockDAO.updateStock(request.getProductName(), oldProduct.getName())
                           .map(stock -> ProductBuilder.from(request, type.getId(), stock.getId())
                                   .build()))
                   .map(newProduct -> {
                       newProduct.setNew(Boolean.FALSE);
                       return newProduct;
                   })
                   .flatMap(product -> {
                       product.setId(id);
                       return productRepository.save(product);
                   }).then();
                }) ;
            } else {
                throw new NoSuchElementException(PRODUCT_NOT_FOUND + id);
            }
        });
    }

    public Mono<String> addProduct(CreateProductRequest request) {
        return Mono.justOrEmpty(request)
                .flatMap(creationRequest -> productCategoryDAO.isExistingType(request.getProductType()))
                .flatMap(exists -> exists ? Mono.just(request)
                        : Mono.error(new ProductCouldNotBeAdderException(TYPE_NOT_FOUND + request.getProductType())))
                .flatMap(productRequest -> productCategoryDAO.parse(request.getProductType()))
                .flatMap(type -> stockDAO.incrementStock(request.getProductName())
                        .map(stock -> ProductBuilder.from(request, type.getId(), stock.getId()).build()))
                .flatMap(productRepository::save)
                .map(Product::getId)
                .onErrorMap(exception -> {
                    exception.printStackTrace();
                    return new ProductCouldNotBeAdderException(PRODUCT_ADD_ERROR + exception.getMessage());
                });
    }

    public Mono<Void> loadProducts() {
        return Flux.range(INTEGER_ZERO, DUMMY_DATE_COUNT).flatMap(index -> {
            Random random = new Random();
            double price = 50.0 + (150.0 - 50.0) * random.nextDouble();
            String randType = ProductData.PRODUCT_TYPES[random.nextInt(ProductData.PRODUCT_TYPES.length)];
            String randName = ProductData.PRODUCT_NAMES[random.nextInt(ProductData.PRODUCT_NAMES.length)];
            String description = ProductData.PRODUCT_DESCRIPTIONS[random
                    .nextInt(ProductData.PRODUCT_DESCRIPTIONS.length)];
            return productCategoryDAO.isExistingType(randType).flatMap(exists -> {
                if (exists) {
                    return productCategoryDAO.parse(randType);
                } else {
                    return productCategoryDAO.create(randType);
                }
            }).map(type -> new Product(String.valueOf(index), randName, description, type.getId(), randName,
                    price))
                    .flatMap(productRepository::save);
        }).then();
    }

    public Mono<Void> deleteAllProducts() {
        return productRepository.deleteAll();
    }

    public Flux<String> getAllProductIds() {
        return productRepository.findAll().map(Product::getId);
    }

    public Mono<Void> deleteByType(int typeId) {
        return productRepository.deleteByProductCategoryId(typeId);
    }

    public Flux<Product> getProductsByType(int typeId) {
        return productRepository.getByProductCategoryId(typeId);
    }

    public Flux<Product> getProductsByIds(List<String> ids) {      
       return productRepository.findAllById(ids);
    }

}
