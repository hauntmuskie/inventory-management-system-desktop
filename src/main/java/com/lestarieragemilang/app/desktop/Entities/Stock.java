package com.lestarieragemilang.app.desktop.Entities;

public class Stock {
    private String stockId;
    private int categoryId, stock;
    private double buyPrice, sellPrice;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Stock(String stockId, int categoryId, int stock, double buyPrice, double sellPrice) {
        this.stockId = stockId;
        this.categoryId = categoryId;
        this.stock = stock;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

}