package com.lestarieragemilang.app.desktop.Controller;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXComboBox;
import com.lestarieragemilang.app.desktop.Dao.CategoryDao;
import com.lestarieragemilang.app.desktop.Entities.Category;
import com.lestarieragemilang.app.desktop.Utilities.CategoryTablePopulator;
import com.lestarieragemilang.app.desktop.Utilities.GenerateRandomID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
    TextField categoryIDIncrement, categorySearchField;

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

        categoryTable.setItems(FXCollections.observableArrayList());
        categoryTable.getItems().add(category);

        // refresh
        categoryTablePopulator.populateCategoryTable(categoryIDCol, brandCategoryCol, typeCategoryCol, sizeCategoryCol,
                weightCategoryCol, unitCategoryCol, categoryTable);
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

    private void setCategoryDropDownItems(List<Category> categories, Function<Category, String> categoryMapper,
            ComboBox<String> dropDown) {
        ObservableList<String> categoryItems = categories.stream()
                .map(categoryMapper)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        dropDown.setItems(categoryItems);
    }

    void categorySearch() {
        FilteredList<Category> filteredData = new FilteredList<>(categoryTable.getItems());
        categorySearchField.textProperty()
                .addListener((observable, oldValue, newValue) -> filteredData.setPredicate(category -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (category.getCategoryBrand().toLowerCase().contains(lowerCaseFilter)
                            || category.getCategoryType().toLowerCase().contains(lowerCaseFilter)
                            || category.getCategorySize().toLowerCase().contains(lowerCaseFilter)
                            || category.getCategoryWeight().toLowerCase().contains(lowerCaseFilter)
                            || category.getCategoryUnit().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                }));
        categoryTable.setItems(filteredData);
    }

    @FXML
    public void initialize() {
        categoryTablePopulator.populateCategoryTable(categoryIDCol, brandCategoryCol, typeCategoryCol, sizeCategoryCol,
                weightCategoryCol, unitCategoryCol, categoryTable);

        CategoryDao categoryDao = new CategoryDao();

        setCategoryDropDownItems(categoryDao.getAllCategoryBrands(), Category::getCategoryBrand, categoryBrandDropDown);
        setCategoryDropDownItems(categoryDao.getAllCategoryTypes(), Category::getCategoryType, categoryTypeDropDown);
        setCategoryDropDownItems(categoryDao.getAllCategorySizes(), Category::getCategorySize, categorySizeDropDown);
        setCategoryDropDownItems(categoryDao.getAllCategoryWeights(), Category::getCategoryWeight,
                categoryWeightDropDown);
        setCategoryDropDownItems(categoryDao.getAllCategoryUnits(), Category::getCategoryUnit, categoryUnitDropDown);
        categorySearch();

    }

}
