package com.alianz.practice.alianz_practice.Entity;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TypeStock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "productId", updatable = false, nullable = false)
    private String typeId;
    private ProductCatergory catergory;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Stock> stock;
    public TypeStock() {
    }
    public TypeStock(String typeId, ProductCatergory catergory, List<Stock> stock) {
        this.typeId = typeId;
        this.catergory = catergory;
        this.stock = stock;
    }
    public String getTypeId() {
        return typeId;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    public ProductCatergory getCatergory() {
        return catergory;
    }
    public void setCatergory(ProductCatergory catergory) {
        this.catergory = catergory;
    }
    public List<Stock> getStock() {
        return stock;
    }
    public void setStock(List<Stock> stock) {
        this.stock = stock;
    }
    @Override
    public String toString() {
        return "TypeStock [typeId=" + typeId + ", catergory=" + catergory + ", stock=" + stock + "]";
    }
    
}
