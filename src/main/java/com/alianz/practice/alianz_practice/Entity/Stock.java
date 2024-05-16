package com.alianz.practice.alianz_practice.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("stock")
public class Stock implements Persistable<String>{
    @Id
    private String id;
    @Column("productName")
    private String productName;
    @Column("productStock")
    private int productStock;

    @Transient
    private boolean isNew = true;

    public Stock(String stockId, String productName, int productStock) {
        this.id = stockId;
        this.productName = productName;
        this.productStock = productStock;
    }

    public Stock() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    @Override
    public String toString() {
        return "Stock [productName=" + productName + ", productStock=" + productStock + "]";
    }

    

    @Override
    @Transient
    public boolean isNew() {
        return isNew;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

}
