package com.lestarieragemilang.app.desktop.Dao.Transactions;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lestarieragemilang.app.desktop.Configurations.DatabaseConfiguration;
import com.lestarieragemilang.app.desktop.Entities.Transactions.Buy;

public class BuyDao extends DatabaseConfiguration {

    public List<Buy> getAll() {
        List<Buy> buys = new ArrayList<>();
        String sql = "SELECT * FROM purchasing";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Buy buy = new Buy(null, null, null, null, 0, 0, 0, 0, 0);
                buy.setPurchaseDate(rs.getDate("purchase_date").toLocalDate());
                buy.setBrand(rs.getString("brand"));
                buy.setProductType(rs.getString("product_type"));
                buy.setSupplierName(rs.getString("supplier_name"));
                buy.setInvoiceNumber(rs.getInt("invoice_number"));
                buy.setStockId(rs.getInt("stock_id"));
                buy.setSupplierId(rs.getInt("supplier_id"));
                buy.setQuantity(rs.getInt("quantity"));
                buy.setPrice(rs.getDouble("price"));

                buys.add(buy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return buys;
    }

    public void addBuy(Buy buy) {
        String sql = "INSERT INTO purchasing (purchase_date, brand, product_type, supplier_name, invoice_number, stock_id, supplier_id, quantity, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(buy.getPurchaseDate()));
            stmt.setString(2, buy.getBrand());
            stmt.setString(3, buy.getProductType());
            stmt.setString(4, buy.getSupplierName());
            stmt.setInt(5, buy.getInvoiceNumber());
            stmt.setInt(6, buy.getStockId());
            stmt.setInt(7, buy.getSupplierId());
            stmt.setInt(8, buy.getQuantity());
            stmt.setDouble(9, buy.getPrice());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBuy(Buy buy) {
        String sql = "UPDATE purchasing SET brand = ?, product_type = ?, supplier_name = ?, invoice_number = ?, stock_id = ?, supplier_id = ?, quantity = ?, price = ? WHERE purchase_date = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, buy.getBrand());
            stmt.setString(2, buy.getProductType());
            stmt.setString(3, buy.getSupplierName());
            stmt.setInt(4, buy.getInvoiceNumber());
            stmt.setInt(5, buy.getStockId());
            stmt.setInt(6, buy.getSupplierId());
            stmt.setInt(7, buy.getQuantity());
            stmt.setDouble(8, buy.getPrice());
            stmt.setDate(9, java.sql.Date.valueOf(buy.getPurchaseDate()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBuy(Date purchaseDate) {
        String sql = "DELETE FROM purchasing WHERE purchase_date = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, purchaseDate);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object> getStockCategoryIdsFromCategory() {
        List<Object> stockCategoryIds = new ArrayList<>();
        String sql = "SELECT stocks.category_id, categories.brand, categories.product_type, stocks.purchase_price FROM stocks INNER JOIN categories ON stocks.category_id = categories.category_id";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                stockCategoryIds.add(rs.getInt("category_id"));
                stockCategoryIds.add(rs.getString("brand"));
                stockCategoryIds.add(rs.getString("product_type"));
                stockCategoryIds.add(rs.getString("purchase_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockCategoryIds;
        
    }

    public List<Object> getSupplierIdsFromSupplier() {
        List<Object> supplierIds = new ArrayList<>();
        String sql = "SELECT supplier_id, supplier_name FROM suppliers";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                supplierIds.add(rs.getString("supplier_id"));
                supplierIds.add(rs.getString("supplier_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplierIds;
    }

    // get all stock
    public List<Integer> getAllStocks() {
        List<Integer> stocks = new ArrayList<>();
        String sql = "SELECT stock_id, quantity, purchase_price FROM stocks";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                stocks.add(rs.getInt("stock_id"));
                stocks.add(rs.getInt("quantity"));
                stocks.add(rs.getInt("purchase_price"));

                

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }

    // get all supplier
    public List<Integer> getAllSuppliers() {
        List<Integer> suppliers = new ArrayList<>();
        String sql = "SELECT supplier_id FROM suppliers";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                suppliers.add(rs.getInt("supplier_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;

    }

    // get brand, type, and price from stocks
    public List<String> getBrandTypePriceFromStocks() {
        List<String> brandTypePrice = new ArrayList<>();
        String sql = "SELECT brand, type, purchase_price FROM stocks";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                brandTypePrice.add(rs.getString("brand"));
                brandTypePrice.add(rs.getString("type"));
                brandTypePrice.add(rs.getString("purchase_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandTypePrice;
    }

    public List<Integer> getBrandBySupplierId(int selectedSupplierId) {
        List<Integer> brand = new ArrayList<>();
        String sql = "SELECT brand FROM suppliers WHERE supplier_id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, String.valueOf(selectedSupplierId));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    brand.add(rs.getInt("brand"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brand;
    }

    public List<Object> getSupplierNameFromSupplierId(int selectedSupplierId) {
        List<Object> supplierName = new ArrayList<>();
        String sql = "SELECT supplier_id FROM suppliers";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                supplierName.add(rs.getString("supplier_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplierName;
    }
    
}
