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
                Buy buy = new Buy(null, null, null, null, 0, 0, 0, 0, 0);
                buy.setPurchaseDate(rs.getDate("purchase_date"));
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
}
