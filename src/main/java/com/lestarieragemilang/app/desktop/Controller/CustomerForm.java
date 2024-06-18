package com.lestarieragemilang.app.desktop.Controller;

import javax.swing.JOptionPane;

import com.lestarieragemilang.app.desktop.Dao.CustomerDao;
import com.lestarieragemilang.app.desktop.Entities.Customer;
import com.lestarieragemilang.app.desktop.Utilities.CustomerTablePopulator;
import com.lestarieragemilang.app.desktop.Utilities.GenerateRandomID;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CustomerForm {
    GenerateRandomID gen = new GenerateRandomID();

    // Customer Table
    @FXML
    private TableColumn<Customer, String> customerIDCol, customerNameCol, customerAddressCol, customerContactCol,
            customerEmailCol;

    @FXML
    TableView<Customer> customerTable;

    @FXML
    TextField customerIDIncrement, customerNameField, customerAddressField, customerContactField, customerEmailField,
            customerSearchField;

    private final CustomerTablePopulator customerTablePopulator = new CustomerTablePopulator();

    // Customer Table

    // add

    @FXML
    void addCustomerButton(ActionEvent event) {
        Customer customer = new Customer(gen.generateRandomId(), customerNameField.getText(),
                customerAddressField.getText(),
                customerContactField.getText(), customerEmailField.getText());

        CustomerDao customerDao = new CustomerDao();
        customerDao.addCustomer(customer);

        customerTable.getItems().add(customer);

        resetField();

    }

    @FXML
    void editCustomerButton(ActionEvent event) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            customerIDIncrement.setText(String.valueOf(selectedCustomer.getCustomerId()));
            customerNameField.setText(selectedCustomer.getCustomerName());
            customerAddressField.setText(selectedCustomer.getCustomerAddress());
            customerContactField.setText(selectedCustomer.getCustomerContact());
            customerEmailField.setText(selectedCustomer.getCustomerEmail());
        }

    }

    void resetField() {
        customerIDIncrement.clear();
        customerNameField.clear();
        customerAddressField.clear();
        customerContactField.clear();
        customerEmailField.clear();

    }

    @FXML
    void resetCustomerButton(ActionEvent event) {
        resetField();
    }

    @FXML
    void removeCustomerButton(ActionEvent event) {
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this customer?", "Delete Customer",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            CustomerDao customerDao = new CustomerDao();
            customerDao.removeCustomer(customer);
            customerTable.getItems().remove(customer);
            JOptionPane.showMessageDialog(null, "Customer deleted.");
        } else {
            JOptionPane.showMessageDialog(null, "Deletion cancelled.");
        }
    }

    @FXML
    void searchCustomerButton(ActionEvent event) {
        CustomerDao customerDao = new CustomerDao();
        customerTable.getItems().clear();
        customerTable.getItems().addAll(customerDao.getAllCustomers());
    }

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
    public void initialize() {
        // Customer Table
        customerTablePopulator.populateCustomerTable(customerIDCol, customerNameCol, customerAddressCol,
                customerContactCol, customerEmailCol, customerTable);
                
                customerSearch();
    }
}
