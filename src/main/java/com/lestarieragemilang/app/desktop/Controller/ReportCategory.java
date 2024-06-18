package com.lestarieragemilang.app.desktop.Controller;

import java.sql.SQLException;

import com.lestarieragemilang.app.desktop.Configurations.ReportConfiguration.JasperLoader;
import com.lestarieragemilang.app.desktop.Entities.Category;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import com.lestarieragemilang.app.desktop.Utilities.CategoryTablePopulator;

public class ReportCategory {

  @FXML
  private TableColumn<?, ?> brandCategoryCol;

  @FXML
  private TableColumn<?, ?> categoryIDCol;

  @FXML
  private TableView<Category> categoryTable;

  @FXML
  private TableColumn<?, ?> sizeCategoryCol;

  @FXML
  private TableColumn<?, ?> typeCategoryCol;

  @FXML
  private TableColumn<?, ?> unitCategoryCol;

  @FXML
  private TableColumn<?, ?> weightCategoryCol;

  @FXML
  private TextField categorySearchField;

  @FXML
  void printJasperCategory(MouseEvent event) {
    try {
      JasperLoader loader = new JasperLoader();
      loader.showJasperReportCategory(
          "src/main/java/com/lestarieragemilang/app/desktop/Configurations/ReportConfiguration/category-list.jasper",
          categorySearchField.getText(), categorySearchField.getText(), categorySearchField.getText(),
          categorySearchField.getText(), categorySearchField.getText(), event);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
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
  void initialize() throws SQLException {
    CategoryTablePopulator categoryTablePopulator = new CategoryTablePopulator();
    categoryTablePopulator.populateCategoryTable(categoryIDCol, brandCategoryCol, typeCategoryCol, sizeCategoryCol,
        weightCategoryCol, unitCategoryCol, categoryTable);

    categorySearch();
  }
}
