package com.lestarieragemilang.app.desktop.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lestarieragemilang.app.desktop.Configurations.DatabaseConfiguration;
import com.lestarieragemilang.app.desktop.Entities.Customer;

public class CustomerDao extends DatabaseConfiguration {

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer(rs.getInt("customer_id"), rs.getString("customer_name"),
                        rs.getString("contact"), rs.getString("address"), rs.getString("email"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;

    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customer_id, customer_name, contact, address, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customer.getCustomerId());
            stmt.setString(2, customer.getCustomerName());
            stmt.setString(3, customer.getCustomerContact());
            stmt.setString(4, customer.getCustomerAddress());
            stmt.setString(5, customer.getCustomerEmail());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET customer_name = ?, contact = ?, address = ?, email = ? WHERE customer_id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getCustomerName());
            stmt.setString(2, customer.getCustomerContact());
            stmt.setString(3, customer.getCustomerAddress());
            stmt.setString(4, customer.getCustomerEmail());
            stmt.setInt(5, customer.getCustomerId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCustomer(Customer customer) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customer.getCustomerId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
