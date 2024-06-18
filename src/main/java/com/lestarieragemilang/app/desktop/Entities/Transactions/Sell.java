package com.lestarieragemilang.app.desktop.Entities.Transactions;

import java.time.LocalDate;

public class Sell {
    private LocalDate purchaseDate;
    private String brand, productType, supplierName;
    private int invoiceNumber, stockId, supplierId, quantity, price, subTotal, total;

    public Sell(LocalDate purchaseDate, String brand, String productType, String supplierName, int invoiceNumber,
            int stockId, int supplierId, int quantity, int price, int subTotal, int total) {
        this.purchaseDate = purchaseDate;
        this.brand = brand;
        this.productType = productType;
        this.supplierName = supplierName;
        this.invoiceNumber = invoiceNumber;
        this.stockId = stockId;
        this.supplierId = supplierId;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = subTotal;
        this.total = total;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
