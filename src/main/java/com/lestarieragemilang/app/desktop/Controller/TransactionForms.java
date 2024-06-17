package com.lestarieragemilang.app.desktop.Controller;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXComboBox;
import com.lestarieragemilang.app.desktop.Dao.Transactions.BuyDao;
import com.lestarieragemilang.app.desktop.Entities.Transactions.Buy;
import com.lestarieragemilang.app.desktop.Utilities.GenerateRandomID;
import com.lestarieragemilang.app.desktop.Utilities.Transactions.BuyTablePopulator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TransactionForms {

    GenerateRandomID gen = new GenerateRandomID();

    BuyTablePopulator buyTablePopulator = new BuyTablePopulator();

    // Buy Form
    @FXML
    private TableColumn<?, ?> buyBrandCol, buyDateCol, buyInvoiceCol, buyOnSupplierNameCol, buyPriceCol, buySubTotalCol,
            buyTotalCol, buyTypeCol;
    @FXML
    private TextField buyBrandField, buyInvoiceNumber, buyPriceField, buyTotalField, buyTypeField, supplierNameField;
    @FXML
    private DatePicker buyDate;
    @FXML
    private TableView<Buy> buyTable;
    @FXML
    private JFXComboBox<Object> stockIDDropdown;
    @FXML
    private JFXComboBox<Object> supplierIDDropDown;

    @FXML
    void addBuyButton(ActionEvent event) {
        
    }

    @FXML
    void editBuyButton(ActionEvent event) {

    }

    @FXML
    void removeBuyButton(ActionEvent event) {

    }

    @FXML
    void resetBuyButton(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        buyTablePopulator.populateBuyTable(buyDateCol, buyBrandCol, buyTypeCol, buyOnSupplierNameCol, buyInvoiceCol,
                buySubTotalCol, buyPriceCol, buyTotalCol, buyTable);

        BuyDao buyDao = new BuyDao();

        // Stock ID Dropdown
        ObservableList<Object> stockIds = FXCollections.observableArrayList(buyDao.getAllStockIds());
        stockIDDropdown.setItems(stockIds);
        if (!stockIds.isEmpty()) {
            stockIDDropdown.getSelectionModel().selectFirst();
            String firstStockId = stockIds.get(0).toString();
            List<String> firstStockDetails = buyDao.getBrandTypePrice(firstStockId);
            buyBrandField.setText(firstStockDetails.get(0)); // Assuming the brand is the first item in the list
            buyTypeField.setText(firstStockDetails.get(1)); // Assuming the type is the second item in the list
            buyPriceField.setText(firstStockDetails.get(2)); // Assuming the price is the third item in the list
        }

        stockIDDropdown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String stockId = newValue.toString();
                List<String> stockDetails = buyDao.getBrandTypePrice(stockId);
                buyBrandField.setText(stockDetails.get(0)); // Assuming the brand is the first item in the list
                buyTypeField.setText(stockDetails.get(1)); // Assuming the type is the second item in the list
                buyPriceField.setText(stockDetails.get(2)); // Assuming the price is the third item in the list
            }
        });

        // Supplier ID Dropdown

        ObservableList<Object> supplierIds = FXCollections.observableArrayList(buyDao.getSupplierIds());
        supplierIDDropDown.setItems(supplierIds);
        if (!supplierIds.isEmpty()) {
            supplierIDDropDown.getSelectionModel().selectFirst();
            String firstSupplierId = supplierIds.get(0).toString();
            String firstSupplierName = buyDao.getSupplierName(firstSupplierId);
            supplierNameField.setText(firstSupplierName);
        }

        supplierIDDropDown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String supplierId = newValue.toString();
                String supplierName = buyDao.getSupplierName(supplierId);
                supplierNameField.setText(supplierName);
            }
        });

    }
}
