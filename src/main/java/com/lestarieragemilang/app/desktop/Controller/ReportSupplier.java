package com.lestarieragemilang.app.desktop.Controller;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

import com.lestarieragemilang.app.desktop.Configurations.ReportConfiguration.JasperLoader;
import com.lestarieragemilang.app.desktop.Entities.Supplier;
import com.lestarieragemilang.app.desktop.Utilities.SupplierTablePopulator;

public class ReportSupplier {
  @FXML
  private TableColumn<?, ?> supplierAddressCol;

  @FXML
  private TableColumn<?, ?> supplierContactCol;

  @FXML
  private TableColumn<?, ?> supplierEmailCol;

  @FXML
  private TableColumn<?, ?> supplierIDCol;

  @FXML
  private TableColumn<?, ?> supplierNameCol;

  @FXML
  private TableView<Supplier> supplierTable;

  @FXML
  private TextField supplierSearchField;

  @FXML
  void printJasperSupplier(MouseEvent event) {
    try {
      JasperLoader loader = new JasperLoader();
      loader.showJasperReport(
          "src/main/java/com/lestarieragemilang/app/desktop/Configurations/ReportConfiguration/supplier-list.jasper",
          event);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void supplierSearch() {
    FilteredList<Supplier> filteredData = new FilteredList<>(supplierTable.getItems());
    supplierSearchField.textProperty()
        .addListener((observable, oldValue, newValue) -> filteredData.setPredicate(supplier -> {
          if (newValue == null || newValue.isEmpty()) {
            return true;
          }
          String lowerCaseFilter = newValue.toLowerCase();
          if (supplier.getSupplierName().toLowerCase().contains(lowerCaseFilter)
              || supplier.getSupplierAddress().toLowerCase().contains(lowerCaseFilter)
              || supplier.getSupplierContact().toLowerCase().contains(lowerCaseFilter)
              || supplier.getSupplierEmail().toLowerCase().contains(lowerCaseFilter)) {
            return true;
          }
          return false;
        }));
    supplierTable.setItems(filteredData);
  }

  @FXML
  void initialize() throws SQLException {
    SupplierTablePopulator supplierTablePopulator = new SupplierTablePopulator();
    supplierTablePopulator.populateSupplierTable(supplierIDCol, supplierNameCol, supplierAddressCol,
        supplierContactCol, supplierEmailCol, supplierTable);

    supplierSearch();
  }
}
