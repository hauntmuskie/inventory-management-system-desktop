package com.lestarieragemilang.app.desktop.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lestarieragemilang.app.desktop.Configurations.DatabaseConfiguration;
import com.lestarieragemilang.app.desktop.Entities.Category;

public class CategoryDao extends DatabaseConfiguration {
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category(0, null, null, null, null, null);
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryBrand(rs.getString("brand"));
                category.setCategoryType(rs.getString("product_type"));
                category.setCategorySize(rs.getString("size"));
                category.setCategoryWeight(rs.getString("weight"));
                category.setCategoryUnit(rs.getString("weight_unit"));

                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<Category> getAllCategoryBrands() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT brand FROM categories";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category(0, null, null, null, null, null);
                category.setCategoryBrand(rs.getString("brand"));

                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public void addCategory(Category category) {
        String sql = "INSERT INTO categories (category_id, brand, product_type, size, weight, weight_unit) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, category.getCategoryId());
            stmt.setString(2, category.getCategoryBrand());
            stmt.setString(3, category.getCategoryType());
            stmt.setString(4, category.getCategorySize());
            stmt.setString(5, category.getCategoryWeight());
            stmt.setString(6, category.getCategoryUnit());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(Category category) {
        String sql = "UPDATE categories SET brand = ?, product_type = ?, size = ?, weight = ?, weight_unit = ? WHERE category_id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getCategoryBrand());
            stmt.setString(2, category.getCategoryType());
            stmt.setString(3, category.getCategorySize());
            stmt.setString(4, category.getCategoryWeight());
            stmt.setString(5, category.getCategoryUnit());
            stmt.setInt(6, category.getCategoryId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCategory(Category category) {
        String sql = "DELETE FROM categories WHERE category_id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, category.getCategoryId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // get id only

    public Category getCategoryById(int categoryId) {
        Category category = new Category(0, null, null, null, null, null);
        String sql = "SELECT * FROM categories WHERE category_id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    category.setCategoryId(rs.getInt("category_id"));
                    category.setCategoryBrand(rs.getString("brand"));
                    category.setCategoryType(rs.getString("product_type"));
                    category.setCategorySize(rs.getString("size"));
                    category.setCategoryWeight(rs.getString("weight"));
                    category.setCategoryUnit(rs.getString("weight_unit"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }


}
