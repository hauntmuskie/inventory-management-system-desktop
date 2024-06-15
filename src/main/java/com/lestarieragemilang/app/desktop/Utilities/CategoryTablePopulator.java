package com.lestarieragemilang.app.desktop.Utilities;

import java.util.List;

import com.lestarieragemilang.app.desktop.Dao.CategoryDao;
import com.lestarieragemilang.app.desktop.Entities.Category;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoryTablePopulator {

    private CategoryDao categoryDao = new CategoryDao();

    public void populateCategoryTable(TableColumn<?, ?> categoryIDCol, TableColumn<?, ?> brandCategoryCol, TableColumn<?, ?> typeCategoryCol, TableColumn<?, ?> sizeCategoryCol, TableColumn<?, ?> weightCategoryCol, TableColumn<?, ?> unitCategoryCol, TableView<Category> categoryTable) {
        ObservableList<Category> categoryData = FXCollections.observableArrayList();
        List<Category> categories = categoryDao.getAllCategories();
    
        categoryIDCol.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        brandCategoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryBrand"));
        typeCategoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryType"));
        sizeCategoryCol.setCellValueFactory(new PropertyValueFactory<>("categorySize"));
        weightCategoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryWeight"));
        unitCategoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryUnit"));
    
        categoryData.addAll(categories);
        categoryTable.setItems(categoryData);

    }    



    
}
