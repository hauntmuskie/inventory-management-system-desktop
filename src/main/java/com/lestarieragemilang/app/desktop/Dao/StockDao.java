package com.lestarieragemilang.app.desktop.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lestarieragemilang.app.desktop.Configurations.DatabaseConfiguration;
import com.lestarieragemilang.app.desktop.Entities.Stock;

public class StockDao extends DatabaseConfiguration {
    public List<Stock> getAllStocks() throws SQLException {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM stock";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Stock stock = new Stock(sql, 0, 0, 0, 0);
                stock.setStockId(rs.getString("id"));
                stock.setCategoryId(rs.getInt("id_kategori"));
                stock.setStock(rs.getInt("stok"));
                stock.setBuyPrice(rs.getDouble("harga_beli"));
                stock.setSellPrice(rs.getDouble("harga_jual"));


                stocks.add(stock);
            }
        }
        return stocks;
    }

    public void addStock(Stock stock) throws SQLException {
        String sql = "INSERT INTO stock (merek, jenis, ukuran, satuan, id_kategori, stok, harga_beli, harga_jual) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, stock.getStockId());
            stmt.setInt(2, stock.getCategoryId());
            stmt.setInt(3, stock.getStock());
            stmt.setDouble(4, stock.getBuyPrice());
            stmt.setDouble(5, stock.getSellPrice());
            
            stmt.executeUpdate();
        }
    }
}
