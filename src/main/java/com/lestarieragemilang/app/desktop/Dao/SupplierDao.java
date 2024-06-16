package com.lestarieragemilang.app.desktop.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lestarieragemilang.app.desktop.Configurations.DatabaseConfiguration;
import com.lestarieragemilang.app.desktop.Entities.Supplier;

public class SupplierDao extends DatabaseConfiguration {

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Supplier supplier = new Supplier(0, null, null, null, null);
                supplier.setSupplierId(rs.getInt("supplier_id"));
                supplier.setSupplierName(rs.getString("supplier_name"));
                supplier.setSupplierAddress(rs.getString("address"));
                supplier.setSupplierContact(rs.getString("contact"));
                supplier.setSupplierEmail(rs.getString("email"));

                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;

    }

    public void addSupplier(Supplier supplier) {
        String sql = "INSERT INTO suppliers (supplier_id, supplier_name, address, contact, email) VALUES (?, ?, ?, ?, ?)";
    
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, supplier.getSupplierId());
            stmt.setString(2, supplier.getSupplierName());
            stmt.setString(3, supplier.getSupplierAddress());
            stmt.setString(4, supplier.getSupplierContact());
            stmt.setString(5, supplier.getSupplierEmail());
    
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSupplier(Supplier supplier) {
        String sql = "UPDATE suppliers SET supplier_name = ?, address = ?, contact = ?, email = ? WHERE supplier_id = ?";
    
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getSupplierAddress());
            stmt.setString(3, supplier.getSupplierContact());
            stmt.setString(4, supplier.getSupplierEmail());
            stmt.setInt(5, supplier.getSupplierId());
    
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeSupplier(Supplier supplier) {
        String sql = "DELETE FROM suppliers WHERE supplier_id = ?";
    
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, supplier.getSupplierId());
    
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
