package com.lestarieragemilang.app.desktop.Dao;

import java.sql.*;

public class SteelShopDao {
    private Connection connection;

    public SteelShopDao(Connection connection) {
        this.connection = connection;
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("Steel Shop Database Report\n");
        report.append("==========================\n\n");

        report.append(getCategoriesReport());
        report.append(getCustomersReport());
        report.append(getPurchasingReport());
        report.append(getReturnsReport());
        report.append(getSalesReport());
        report.append(getStocksReport());
        report.append(getSuppliersReport());
        report.append(getUsersReport());

        return report.toString();
    }

    private String getCategoriesReport() {
        StringBuilder report = new StringBuilder();
        report.append("Categories Report:\n");
        report.append("------------------\n");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM categories");
            while (rs.next()) {
                report.append(String.format("ID: %d, Brand: %s, Type: %s, Size: %s, Weight: %.2f %s\n",
                        rs.getInt("category_id"), rs.getString("brand"), rs.getString("product_type"),
                        rs.getString("size"), rs.getDouble("weight"), rs.getString("weight_unit")));
            }
        } catch (SQLException e) {
            report.append("Error fetching categories: " + e.getMessage() + "\n");
        }
        report.append("\n");
        return report.toString();
    }

    private String getCustomersReport() {
        StringBuilder report = new StringBuilder();
        report.append("Customers Report:\n");
        report.append("------------------\n");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customers");
            while (rs.next()) {
                report.append(String.format("ID: %d, Name: %s, Contact: %s, Email: %s\n",
                        rs.getInt("customer_id"), rs.getString("customer_name"),
                        rs.getString("contact"), rs.getString("email")));
            }
        } catch (SQLException e) {
            report.append("Error fetching customers: " + e.getMessage() + "\n");
        }
        report.append("\n");
        return report.toString();
    }

    private String getPurchasingReport() {
        StringBuilder report = new StringBuilder();
        report.append("Purchasing Report:\n");
        report.append("------------------\n");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM purchasing");
            while (rs.next()) {
                report.append(String.format("Date: %s, Invoice: %d, Stock ID: %d, Brand: %s, Type: %s, " +
                        "Price: %.2f, Quantity: %d, Supplier: %s\n",
                        rs.getDate("purchase_date"), rs.getInt("invoice_number"), rs.getInt("stock_id"),
                        rs.getString("brand"), rs.getString("product_type"), rs.getDouble("price"),
                        rs.getInt("quantity"), rs.getString("supplier_name")));
            }
        } catch (SQLException e) {
            report.append("Error fetching purchasing data: " + e.getMessage() + "\n");
        }
        report.append("\n");
        return report.toString();
    }

    private String getReturnsReport() {
        StringBuilder report = new StringBuilder();
        report.append("Returns Report:\n");
        report.append("---------------\n");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM returns");
            while (rs.next()) {
                report.append(String.format("Date: %s, ID: %d, Type: %s, Invoice: %d, Reason: %s\n",
                        rs.getDate("return_date"), rs.getInt("return_id"), rs.getString("return_type"),
                        rs.getInt("invoice_number"), rs.getString("reason")));
            }
        } catch (SQLException e) {
            report.append("Error fetching returns data: " + e.getMessage() + "\n");
        }
        report.append("\n");
        return report.toString();
    }

    private String getSalesReport() {
        StringBuilder report = new StringBuilder();
        report.append("Sales Report:\n");
        report.append("-------------\n");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sales");
            while (rs.next()) {
                report.append(String.format("Date: %s, Invoice: %d, Stock ID: %d, Brand: %s, Type: %s, " +
                        "Price: %.2f, Quantity: %d, Customer: %s\n",
                        rs.getDate("sale_date"), rs.getInt("invoice_number"), rs.getInt("stock_id"),
                        rs.getString("brand"), rs.getString("product_type"), rs.getDouble("price"),
                        rs.getInt("quantity"), rs.getString("customer_name")));
            }
        } catch (SQLException e) {
            report.append("Error fetching sales data: " + e.getMessage() + "\n");
        }
        report.append("\n");
        return report.toString();
    }

    private String getStocksReport() {
        StringBuilder report = new StringBuilder();
        report.append("Stocks Report:\n");
        report.append("--------------\n");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM stocks");
            while (rs.next()) {
                report.append(String.format(
                        "ID: %d, Category ID: %d, Quantity: %d, Purchase Price: %.2f, Selling Price: %.2f\n",
                        rs.getInt("stock_id"), rs.getInt("category_id"), rs.getInt("quantity"),
                        rs.getDouble("purchase_price"), rs.getDouble("selling_price")));
            }
        } catch (SQLException e) {
            report.append("Error fetching stocks data: " + e.getMessage() + "\n");
        }
        report.append("\n");
        return report.toString();
    }

    private String getSuppliersReport() {
        StringBuilder report = new StringBuilder();
        report.append("Suppliers Report:\n");
        report.append("-----------------\n");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM suppliers");
            while (rs.next()) {
                report.append(String.format("ID: %d, Name: %s, Contact: %s, Email: %s\n",
                        rs.getInt("supplier_id"), rs.getString("supplier_name"),
                        rs.getString("contact"), rs.getString("email")));
            }
        } catch (SQLException e) {
            report.append("Error fetching suppliers data: " + e.getMessage() + "\n");
        }
        report.append("\n");
        return report.toString();
    }

    private String getUsersReport() {
        StringBuilder report = new StringBuilder();
        report.append("Users Report:\n");
        report.append("-------------\n");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                report.append(String.format("ID: %d, Username: %s, Email: %s, Name: %s, Created: %s, Updated: %s\n",
                        rs.getInt("id"), rs.getString("username"), rs.getString("email"),
                        rs.getString("name"), rs.getTimestamp("created_at"), rs.getTimestamp("updated_at")));
            }
        } catch (SQLException e) {
            report.append("Error fetching users data: " + e.getMessage() + "\n");
        }
        report.append("\n");
        return report.toString();
    }
}