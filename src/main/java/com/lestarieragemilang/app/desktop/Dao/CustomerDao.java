package com.lestarieragemilang.app.desktop.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lestarieragemilang.app.desktop.Configurations.DatabaseConfiguration;
import com.lestarieragemilang.app.desktop.Entities.Customer;

public class CustomerDao extends DatabaseConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(CustomerDao.class);

    private void executeUpdate(String sql, Consumer<PreparedStatement> statementSetter) {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            statementSetter.accept(stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Database operation failed", e);
        }
    }

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
            logger.error("Failed to get all customers", e);
        }
        return customers;

    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customer_id, customer_name, contact, address, email) VALUES (?, ?, ?, ?, ?)";

        executeUpdate(sql, stmt -> {
            try {
                stmt.setInt(1, customer.getCustomerId());
                stmt.setString(2, customer.getCustomerName());
                stmt.setString(3, customer.getCustomerContact());
                stmt.setString(4, customer.getCustomerAddress());
                stmt.setString(5, customer.getCustomerEmail());
            } catch (SQLException e) {
                logger.error("Failed to set statement parameters", e);
            }
        });
    }

    public void updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET customer_name = ?, contact = ?, " +
                "address = ?, email = ? WHERE customer_id = ?";
        executeUpdate(sql, stmt -> {
            try {
                stmt.setString(1, customer.getCustomerName());
                stmt.setString(2, customer.getCustomerContact());
                stmt.setString(3, customer.getCustomerAddress());
                stmt.setString(4, customer.getCustomerEmail());
                stmt.setInt(5, customer.getCustomerId());
            } catch (SQLException e) {
                logger.error("Failed to set statement parameters", e);
            }
        });
    }

    public void removeCustomer(Customer customer) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        executeUpdate(sql, stmt -> {
            try {
                stmt.setInt(1, customer.getCustomerId());
            } catch (SQLException e) {
                logger.error("Failed to set statement parameters", e);
            }
        });
    }

}
