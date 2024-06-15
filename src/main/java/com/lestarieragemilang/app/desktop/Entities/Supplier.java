package com.lestarieragemilang.app.desktop.Entities;

public class Supplier {
    private int supplierId;
    private String supplierName, supplierAddress, supplierContact, supplierEmail;

    public Supplier(int supplierId, String supplierName, String supplierAddress, String supplierContact,
            String supplierEmail) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierContact = supplierContact;
        this.supplierEmail = supplierEmail;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }
}
