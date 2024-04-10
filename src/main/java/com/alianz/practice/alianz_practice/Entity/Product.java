package com.alianz.practice.alianz_practice.Entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "productId", updatable = false, nullable = false)
    private String productId;
    private String name;
    private String description;
    private ProductCatergory type;
    private Double price;
    
    public Product() {
    }   

    public Product(String productId, String name, String description, ProductCatergory type, Double price) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public ProductCatergory getType() {
        return type;
    }

    public void setType(ProductCatergory type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", name=" + name + ", description=" + description + ", type=" + type
                + ", price=" + price + "]";
    }

    

}
