package com.lestarieragemilang.app.desktop.Utilities;

import java.util.List;

import com.lestarieragemilang.app.desktop.Dao.CustomerDao;
import com.lestarieragemilang.app.desktop.Entities.Customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomerTablePopulator {

    private CustomerDao customerDao = new CustomerDao();

    public void populateCustomerTable(TableColumn<?, ?> customerIDCol, TableColumn<?, ?> customerNameCol, TableColumn<?, ?> customerAddressCol, TableColumn<?, ?> customerContactCol, TableColumn<?, ?> customerEmailCol, TableView<Customer> customerTable) {
        ObservableList<Customer> customerData = FXCollections.observableArrayList();
        List<Customer> customers = customerDao.getAllCustomers();

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerContactCol.setCellValueFactory(new PropertyValueFactory<>("customerContact"));
        customerEmailCol.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));

        customerData.addAll(customers);
        customerTable.setItems(customerData);
    }
    
}
