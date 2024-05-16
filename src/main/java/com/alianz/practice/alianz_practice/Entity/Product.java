package com.alianz.practice.alianz_practice.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("product")
public class Product implements Persistable<String>{
  
    @Id
    private String id;
    private String name;
    private String description;
    @Column("productCategoryId")
    private int productCategoryId;
    @Column("stockId")
    private String stockId;
    private Double price;

    @Transient
    private boolean isNew = true;

    public Product() {
    }

    public Product(String productId, String name, String description, int productCategoryId, String stockId, Double price) {
        this.id = productId;
        this.name = name;
        this.description = description;
        this.productCategoryId = productCategoryId;
        this.stockId = stockId;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String productId) {
        this.id = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product [productId=" + id + ", name=" + name + ", description=" + description
                + ", productCategoryId=" + productCategoryId + ", stockId=" + stockId + ", price=" + price + "]";
    }

    @Override
    @Transient
    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    
}
