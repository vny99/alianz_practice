package com.alianz.practice.alianz_practice.Entity;

import jakarta.persistence.AttributeConverter;

public class CategoryConverter implements AttributeConverter<ProductCatergory, Integer>{

    @Override
    public Integer convertToDatabaseColumn(ProductCatergory attribute) {
        if(attribute == null){
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public ProductCatergory convertToEntityAttribute(Integer dbData) {
        if(dbData == null){
            return null;
        }
        return ProductCatergory.parse(dbData);
    }
    
}
