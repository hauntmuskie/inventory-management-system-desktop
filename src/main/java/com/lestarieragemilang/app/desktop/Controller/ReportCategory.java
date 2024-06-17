package com.lestarieragemilang.app.desktop.Controller;

import java.sql.SQLException;

import com.lestarieragemilang.app.desktop.Configurations.ReportConfiguration.JasperLoader;
import com.lestarieragemilang.app.desktop.Entities.Category;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
  void printJasperCategory(MouseEvent event) {
    try {
      JasperLoader loader = new JasperLoader();
      loader.showJasperReport(
          "src/main/java/com/lestarieragemilang/app/desktop/Configurations/ReportConfiguration/category-list.jasper",
          event);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void initialize() throws SQLException {
    CategoryTablePopulator categoryTablePopulator = new CategoryTablePopulator();
    categoryTablePopulator.populateCategoryTable(categoryIDCol, brandCategoryCol, typeCategoryCol, sizeCategoryCol,
        weightCategoryCol, unitCategoryCol, categoryTable);
  }
}
