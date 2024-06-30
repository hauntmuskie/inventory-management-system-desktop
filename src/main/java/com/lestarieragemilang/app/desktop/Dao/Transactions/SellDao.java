package com.lestarieragemilang.app.desktop.Dao.Transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lestarieragemilang.app.desktop.Configurations.DatabaseConfiguration;
import com.lestarieragemilang.app.desktop.Entities.Transactions.Sell;

public class SellDao extends DatabaseConfiguration {

    public List<Sell> getAll() {
        List<Sell> sells = new ArrayList<>();
        String sql = "SELECT * FROM sales";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Sell sell = new Sell(null, null, null, null, 0, 0, 0, 0, 0.0, 0.0, 0.0);
                sell.setSellDate(rs.getDate("sale_date").toLocalDate());
                sell.setInvoiceNumber(rs.getInt("invoice_number"));
                sell.setStockId(rs.getInt("stock_id"));
                sell.setBrand(rs.getString("brand"));
                sell.setProductType(rs.getString("product_type"));
                sell.setPrice(rs.getDouble("price"));
                sell.setCustomerId(rs.getInt("customer_id"));
                sell.setCustomerName(rs.getString("customer_name"));
                sell.setQuantity(rs.getInt("quantity"));

                sells.add(sell);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sells;
    }

    public long sumTotal() {
        String sql = "SELECT SUM(quantity * price) FROM sales";
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

    public List<Sell> searchSellData(String data) {
        List<Sell> sells = new ArrayList<>();
        String queryString = String.format(
            "SELECT *              " +
            "FROM sales            " +
            "WHERE (               " +
                "sale_date         LIKE '%%%s%%' OR " +
                "invoice_number    LIKE '%%%s%%' OR " +
                "stock_id          LIKE '%%%s%%' OR " +
                "brand             LIKE '%%%s%%' OR " +
                "product_type      LIKE '%%%s%%' OR " +
                "price             LIKE '%%%s%%' OR " +
                "customer_id       LIKE '%%%s%%' OR " +
                "customer_name     LIKE '%%%s%%' OR " +
                "quantity          LIKE '%%%s%%'    " +
            ")",
            data, data, data,
            data, data, data,
            data, data, data
        );

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(queryString);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Sell sell = new Sell(null, null, null, null, 0, 0, 0, 0, 0.0, 0.0, 0.0);

                sell.setSellDate(rs.getDate("sale_date").toLocalDate());
                sell.setInvoiceNumber(rs.getInt("invoice_number"));
                sell.setStockId(rs.getInt("stock_id"));
                sell.setBrand(rs.getString("brand"));
                sell.setProductType(rs.getString("product_type"));
                sell.setPrice(rs.getDouble("price"));
                sell.setCustomerId(rs.getInt("customer_id"));
                sell.setCustomerName(rs.getString("customer_name"));
                sell.setQuantity(rs.getInt("quantity"));

                sells.add(sell);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sells;
    }

    public void addSell(Sell sell) {
        String sql = "INSERT INTO sales (sale_date, invoice_number, stock_id, brand, product_type, price, customer_id, customer_name, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(sell.getSellDate()));
            stmt.setInt(2, sell.getInvoiceNumber());
            stmt.setInt(3, sell.getStockId());
            stmt.setString(4, sell.getBrand());
            stmt.setString(5, sell.getProductType());
            stmt.setDouble(6, sell.getPrice());
            stmt.setInt(7, sell.getCustomerId());
            stmt.setString(8, sell.getCustomerName());
            stmt.setInt(9, sell.getQuantity());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSell(Sell sell) {
        String sql = "UPDATE sales SET brand = ?, product_type = ?, customer_name = ?, invoice_number = ?, stock_id = ?, quantity = ?, price = ?, sale_date = ? WHERE invoice_number = ?";
    
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, sell.getBrand());
            stmt.setString(2, sell.getProductType());
            stmt.setString(3, sell.getCustomerName());
            stmt.setInt(4, sell.getInvoiceNumber());
            stmt.setInt(5, sell.getStockId());
            stmt.setInt(6, sell.getQuantity());
            stmt.setDouble(7, sell.getPrice());
            stmt.setDate(8, java.sql.Date.valueOf(sell.getSellDate()));
            stmt.setInt(9, sell.getInvoiceNumber());
    
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public void removeSell(Sell sell) {
        String sql = "DELETE FROM sales WHERE invoice_number = ?";
    
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, sell.getInvoiceNumber());
    
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
        String sql = "SELECT c.brand, c.product_type, s.selling_price FROM stocks s INNER JOIN categories c ON s.category_id = c.category_id WHERE s.stock_id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, stockId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                brandTypePrice.add(rs.getString("brand"));
                brandTypePrice.add(rs.getString("product_type"));
                brandTypePrice.add(rs.getString("selling_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brandTypePrice;
    }

    public List<Integer> getCustomerIds() {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT customer_id FROM customers";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ids.add(rs.getInt("customer_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    // get cust name
    public String getCustomerName(String customerName) {
        String name = "";
        String sql = "SELECT customer_name FROM customers WHERE customer_id = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customerName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                name = rs.getString("customer_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
}