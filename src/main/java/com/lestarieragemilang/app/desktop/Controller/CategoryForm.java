package com.lestarieragemilang.app.desktop.Controller;

import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXComboBox;
import com.lestarieragemilang.app.desktop.Dao.CategoryDao;
import com.lestarieragemilang.app.desktop.Entities.Category;
import com.lestarieragemilang.app.desktop.Utilities.CategoryTablePopulator;
import com.lestarieragemilang.app.desktop.Utilities.GenerateRandomID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CategoryForm {
    GenerateRandomID gen = new GenerateRandomID();

    // Category Table
    @FXML
    private TableColumn<Category, String> categoryIDCol, brandCategoryCol, typeCategoryCol, sizeCategoryCol,
            weightCategoryCol, unitCategoryCol;

    @FXML
    private TableView<Category> categoryTable;

    private CategoryTablePopulator categoryTablePopulator = new CategoryTablePopulator();

    @FXML
    TextField categoryIDIncrement;

    @FXML
    JFXComboBox<String> categoryBrandDropDown, categoryTypeDropDown, categorySizeDropDown, categoryWeightDropDown,
            categoryUnitDropDown;

    // Category Table

    @FXML
    void addCategoryButton(ActionEvent event) {
        Category category = new Category(gen.generateRandomId(), categoryBrandDropDown.getValue(),
                categoryTypeDropDown.getValue(), categorySizeDropDown.getValue(), categoryWeightDropDown.getValue(),
                categoryUnitDropDown.getValue());
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.addCategory(category);

        categoryTable.getItems().add(category);
    }

    @FXML
    void editCategoryButton(ActionEvent event) {
        Category selectedCategory = categoryTable.getSelectionModel().getSelectedItem();

        if (selectedCategory != null) {
            categoryIDIncrement.setText(String.valueOf(selectedCategory.getCategoryId()));
            categoryBrandDropDown.setValue(selectedCategory.getCategoryBrand());
            categoryTypeDropDown.setValue(selectedCategory.getCategoryType());
            categorySizeDropDown.setValue(selectedCategory.getCategorySize());
            categoryWeightDropDown.setValue(selectedCategory.getCategoryWeight());
            categoryUnitDropDown.setValue(selectedCategory.getCategoryUnit());
        }
    }

    @FXML
    void resetCategoryButton(ActionEvent event) {
        categoryIDIncrement.clear();
        categoryBrandDropDown.setValue(null);
        categoryTypeDropDown.setValue(null);
        categorySizeDropDown.setValue(null);
        categoryWeightDropDown.setValue(null);
        categoryUnitDropDown.setValue(null);
    }

    @FXML
    void removeCategoryButton(ActionEvent event) {
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this category?", "Delete Category",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Category category = categoryTable.getSelectionModel().getSelectedItem();
            CategoryDao categoryDao = new CategoryDao();
            categoryDao.removeCategory(category);
            categoryTable.getItems().remove(category);
            JOptionPane.showMessageDialog(null, "Category deleted.");
        } else {
            JOptionPane.showMessageDialog(null, "Deletion cancelled.");
        }
    }

    @FXML
    public void initialize() {
        // Category Table
        categoryTablePopulator.populateCategoryTable(categoryIDCol, brandCategoryCol, typeCategoryCol, sizeCategoryCol,
                weightCategoryCol, unitCategoryCol, categoryTable);

        // Fetch category brands from the database
        CategoryDao categoryDao = new CategoryDao();
        ObservableList<Category> categories = FXCollections.observableArrayList(categoryDao.getAllCategoryBrands());
        ObservableList<String> categoryBrands = categories.stream()
                .map(Category::getCategoryBrand)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        categoryBrandDropDown.setItems(categoryBrands);
    }
}
