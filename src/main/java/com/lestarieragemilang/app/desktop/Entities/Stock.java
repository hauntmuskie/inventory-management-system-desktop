package com.lestarieragemilang.app.desktop.Entities;

public class Stock {
    private int stockID, stockOnCategoryID;
    private String quantity, purchasePrice, purchaseSell, categoryBrand, categoryType, categorySize, categoryWeight,
            categoryUnit;

    public Stock(int stockID, int stockOnCategoryID, String quantity, String purchasePrice, String purchaseSell,
            String categoryBrand, String categoryType, String categorySize, String categoryWeight,
            String categoryUnit) {
        this.stockID = stockID;
        this.stockOnCategoryID = stockOnCategoryID;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.purchaseSell = purchaseSell;
        this.categoryBrand = categoryBrand;
        this.categoryType = categoryType;
        this.categorySize = categorySize;
        this.categoryWeight = categoryWeight;
        this.categoryUnit = categoryUnit;
    }

    public Stock(int randomId, Integer value, int categoryBrand2, int categoryType2, String text, int categoryWeight2,
            int categoryUnit2, String text2, String text3, String text4) {

    this.stockID = randomId;
    this.stockOnCategoryID = value;
    this.quantity = text;
    this.purchasePrice = text2;
    this.purchaseSell = text3;
    this.categoryBrand = String.valueOf(categoryBrand2);
    this.categoryType = String.valueOf(categoryType2);
    this.categorySize = text4;
    this.categoryWeight = String.valueOf(categoryWeight2);
    this.categoryUnit = String.valueOf(categoryUnit2);
    }

    @Override
    public String toString() {
        return "Stock [\n" +
                "    categoryBrand=" + categoryBrand + ",\n" +
                "    categorySize=" + categorySize + ",\n" +
                "    categoryType=" + categoryType + ",\n" +
                "    categoryUnit=" + categoryUnit + ",\n" +
                "    categoryWeight=" + categoryWeight + ",\n" +
                "    purchasePrice=" + purchasePrice + ",\n" +
                "    purchaseSell=" + purchaseSell + ",\n" +
                "    quantity=" + quantity + ",\n" +
                "    stockID=" + stockID + ",\n" +
                "    stockOnCategoryID=" + stockOnCategoryID + "\n" +
                "]";
    }

    public int getStockID() {
        return stockID;
    }

    public void setStockID(int stockID) {
        this.stockID = stockID;
    }

    public int getStockOnCategoryID() {
        return stockOnCategoryID;
    }

    public void setStockOnCategoryID(int stockOnCategoryID) {
        this.stockOnCategoryID = stockOnCategoryID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getPurchaseSell() {
        return purchaseSell;
    }

    public void setPurchaseSell(String purchaseSell) {
        this.purchaseSell = purchaseSell;
    }

    public String getCategoryBrand() {
        return categoryBrand;
    }

    public void setCategoryBrand(String categoryBrand) {
        this.categoryBrand = categoryBrand;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategorySize() {
        return categorySize;
    }

    public void setCategorySize(String categorySize) {
        this.categorySize = categorySize;
    }

    public String getCategoryWeight() {
        return categoryWeight;
    }

    public void setCategoryWeight(String categoryWeight) {
        this.categoryWeight = categoryWeight;
    }

    public String getCategoryUnit() {
        return categoryUnit;
    }

    public void setCategoryUnit(String categoryUnit) {
        this.categoryUnit = categoryUnit;
    }

}