package com.alianz.practice.alianz_practice.requests;

import java.util.List;

import jakarta.annotation.Nonnull;

public class PurchaseRequest {
    @Nonnull
   private List<String> productIds;

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }
   
}
