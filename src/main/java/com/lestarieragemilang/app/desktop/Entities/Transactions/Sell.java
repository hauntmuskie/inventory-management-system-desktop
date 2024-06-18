package com.lestarieragemilang.app.desktop.Entities.Transactions;

import java.time.LocalDate;

public class Sell {
    private LocalDate sellDate;
    private String brand, productType, customerName;
    private int invoiceNumber, stockId, customerId, quantity;
    private Double price, subTotal, priceTotal;
    public Sell(LocalDate sellDate, String brand, String productType, String customerName, int invoiceNumber,
            int stockId, int customerId, int quantity, Double price, Double subTotal, Double priceTotal) {
        this.sellDate = sellDate;
        this.brand = brand;
        this.productType = productType;
        this.customerName = customerName;
        this.invoiceNumber = invoiceNumber;
        this.stockId = stockId;
        this.customerId = customerId;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = subTotal;
        this.priceTotal = priceTotal;
    }
    public LocalDate getSellDate() {
        return sellDate;
    }
    public void setSellDate(LocalDate sellDate) {
        this.sellDate = sellDate;
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
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getSubTotal() {
        return subTotal;
    }
    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
    public Double getPriceTotal() {
        return priceTotal;
    }
    public void setPriceTotal(Double priceTotal) {
        this.priceTotal = priceTotal;
    }
        
}
