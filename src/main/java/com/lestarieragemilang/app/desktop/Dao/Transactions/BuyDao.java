package com.lestarieragemilang.app.desktop.Dao.Transactions;

import java.sql.Connection;
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
                Buy buy = new Buy(null, null, null, null, 0, 0, 0, 0, 0.0, 0.0, 0.0);

                buy.setPurchaseDate(rs.getDate("purchase_date").toLocalDate());
                buy.setInvoiceNumber(rs.getInt("invoice_number"));
                buy.setStockId(rs.getInt("stock_id"));
                buy.setBrand(rs.getString("brand"));
                buy.setProductType(rs.getString("product_type"));
                buy.setPrice(rs.getDouble("price"));
                buy.setSupplierId(rs.getInt("supplier_id"));
                buy.setSupplierName(rs.getString("supplier_name"));
                buy.setQuantity(rs.getInt("quantity"));
                buys.add(buy);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return buys;
    }

    public long sumTotal() {
        String sql = "SELECT SUM(quantity * price) FROM purchasing";
        long totalCost = 0;

        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                totalCost = rs.getLong(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalCost;
    }

    public void addBuy(Buy buy) {
        String sql = "INSERT INTO purchasing (purchase_date, invoice_number, stock_id, brand, product_type, price, supplier_id, supplier_name, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(buy.getPurchaseDate()));
            stmt.setInt(2, buy.getInvoiceNumber());
            stmt.setInt(3, buy.getStockId());
            stmt.setString(4, buy.getBrand());
            stmt.setString(5, buy.getProductType());
            stmt.setDouble(6, buy.getPrice());
            stmt.setInt(7, buy.getSupplierId());
            stmt.setString(8, buy.getSupplierName());
            stmt.setInt(9, buy.getQuantity());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBuy(Buy buy) {
        String sql = "UPDATE purchasing SET brand = ?, product_type = ?, price = ?, sub_total = ?, price_total = ?, supplier_id = ?, supplier_name = ?, quantity = ? WHERE purchase_date = ? AND invoice_number = ? AND stock_id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, buy.getBrand());
            stmt.setString(2, buy.getProductType());
            stmt.setDouble(3, buy.getPrice());
            stmt.setDouble(4, buy.getSubTotal());
            stmt.setDouble(5, buy.getPriceTotal());
            stmt.setInt(6, buy.getSupplierId());
            stmt.setString(7, buy.getSupplierName());
            stmt.setInt(8, buy.getQuantity());
            stmt.setDate(9, java.sql.Date.valueOf(buy.getPurchaseDate()));
            stmt.setInt(10, buy.getInvoiceNumber());
            stmt.setInt(11, buy.getStockId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBuy(Buy buy) {
        String sql = "DELETE FROM purchasing WHERE invoice_number = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, buy.getInvoiceNumber());

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

    // get sup id with param
    public int getSupplierId(String supplierId) {
        int id = 0;
        String sql = "SELECT supplier_id FROM suppliers WHERE supplier_id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, supplierId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("supplier_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    // get sup name with param
    public String getSupplierName(String supplierId) {
        String name = "";
        String sql = "SELECT supplier_name FROM suppliers WHERE supplier_id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, supplierId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                name = rs.getString("supplier_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    // get supplier id with param
    public List<Integer> getSupplierIds() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT supplier_id FROM suppliers";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ids.add(rs.getInt("supplier_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    // get stock id with param
    public int getStockId(String stockId) {
        int id = 0;
        String sql = "SELECT stock_id FROM stocks WHERE stock_id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, stockId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("stock_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    // get brand, type, purchase price with param
    public List<Integer> getAllStockIds() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT stock_id FROM stocks";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ids.add(rs.getInt("stock_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public List<String> getBrandTypePrice(String stockId) {
        List<String> brandTypePrice = new ArrayList<>();
        String sql = "SELECT c.brand, c.product_type, s.purchase_price FROM stocks s INNER JOIN categories c ON s.category_id = c.category_id WHERE s.stock_id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, stockId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                brandTypePrice.add(rs.getString("brand"));
                brandTypePrice.add(rs.getString("product_type"));
                brandTypePrice.add(rs.getString("purchase_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandTypePrice;
    }
}
