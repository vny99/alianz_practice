package com.alianz.practice.alianz_practice.Entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Stock {

    private String stockName;
    private int remainingStock;
    @Override
    public String toString() {
        return "Stock [stockId=" + stockName + ", remainingStock=" + remainingStock + "]";
    }
    public Stock(String stockId, int remainingStock) {
        this.stockName = stockId;
        this.remainingStock = remainingStock;
    }
    public Stock() {
    }
    public String getStockName() {
        return stockName;
    }
    public void setStockName(String stockId) {
        this.stockName = stockId;
    }
    public int getRemainingStock() {
        return remainingStock;
    }
    public void setRemainingStock(int remainingStock) {
        this.remainingStock = remainingStock;
    }
}
