package com.lestarieragemilang.app.desktop.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
  void printJasperSupplier(MouseEvent event) {
    try {
      JasperLoader loader = new JasperLoader();
      loader.showJasperReport("src/main/java/com/lestarieragemilang/app/desktop/Configurations/ReportConfiguration/supplier-list.jasper", event);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void initialize() throws SQLException {
    SupplierTablePopulator supplierTablePopulator = new SupplierTablePopulator();
    supplierTablePopulator.populateSupplierTable(supplierIDCol, supplierNameCol, supplierAddressCol,
        supplierContactCol, supplierEmailCol, supplierTable);
  }
}
