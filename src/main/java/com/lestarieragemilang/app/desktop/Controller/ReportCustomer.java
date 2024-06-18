package com.lestarieragemilang.app.desktop.Controller;

import java.sql.SQLException;

import com.lestarieragemilang.app.desktop.Configurations.ReportConfiguration.JasperLoader;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import com.lestarieragemilang.app.desktop.Entities.Customer;
import com.lestarieragemilang.app.desktop.Utilities.CustomerTablePopulator;

public class ReportCustomer {

  @FXML
  private TableColumn<?, ?> customerAddressCol;

  @FXML
  private TableColumn<?, ?> customerContactCol;

  @FXML
  private TableColumn<?, ?> customerEmailCol;

  @FXML
  private TableColumn<?, ?> customerIDCol;

  @FXML
  private TableColumn<?, ?> customerNameCol;

  @FXML
  private TableView<Customer> customerTable;

  @FXML
  private TextField customerSearchField;

  @FXML
  void printJasperCustomer(MouseEvent event) {
    try {
      JasperLoader loader = new JasperLoader();
      loader.showJasperReport(
          "src/main/java/com/lestarieragemilang/app/desktop/Configurations/ReportConfiguration/customer-list.jasper",
          event);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void customerSearch() {
    FilteredList<Customer> filteredData = new FilteredList<>(customerTable.getItems());
    customerSearchField.textProperty()
        .addListener((observable, oldValue, newValue) -> filteredData.setPredicate(customer -> {
          if (newValue == null || newValue.isEmpty()) {
            return true;
          }
          String lowerCaseFilter = newValue.toLowerCase();
          if (customer.getCustomerName().toLowerCase().contains(lowerCaseFilter)
              || customer.getCustomerAddress().toLowerCase().contains(lowerCaseFilter)
              || customer.getCustomerContact().toLowerCase().contains(lowerCaseFilter)
              || customer.getCustomerEmail().toLowerCase().contains(lowerCaseFilter)) {
            return true;
          }
          return false;
        }));
    customerTable.setItems(filteredData);
  }
  

  @FXML
  void initialize() throws SQLException {
    CustomerTablePopulator customerTablePopulator = new CustomerTablePopulator();
    customerTablePopulator.populateCustomerTable(customerIDCol, customerNameCol, customerAddressCol,
        customerContactCol, customerEmailCol, customerTable);

        customerSearch();
  }
}
