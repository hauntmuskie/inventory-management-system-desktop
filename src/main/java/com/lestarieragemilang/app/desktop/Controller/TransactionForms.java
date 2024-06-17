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
import javafx.collections.transformation.FilteredList;
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
    private TextField buyBrandField, buyInvoiceNumber, buyPriceField, buyTotalField, buyTypeField, supplierNameField,
            transactionBuySearchField;
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
        GenerateRandomID gen = new GenerateRandomID();

        int invoiceNumber = gen.generateRandomId();

        Buy newBuy = new Buy(LocalDate.now(), buyBrandField.getText(), buyTypeField.getText(),
                supplierNameField.getText(),
                invoiceNumber, Integer.parseInt(stockIDDropdown.getValue().toString()),
                Integer.parseInt(supplierIDDropDown.getValue().toString()), 1,
                Double.parseDouble(buyPriceField.getText()),
                Double.parseDouble(buyTotalField.getText()), Double.parseDouble(buyTotalField.getText()));

        BuyDao buyDao = new BuyDao();
        buyDao.addBuy(newBuy);

        buyTable.getItems().add(newBuy);

        buyBrandField.clear();
        buyTypeField.clear();
        buyPriceField.clear();
        buyTotalField.clear();
        buyInvoiceNumber.clear();
        supplierNameField.clear();
        buyDate.setValue(null);

    }

    @FXML
    void editBuyButton() {
        Buy selectedBuy = buyTable.getSelectionModel().getSelectedItem();
        if (selectedBuy != null) {
            buyBrandField.setText(selectedBuy.getBrand());
            buyTypeField.setText(selectedBuy.getProductType());
            buyPriceField.setText(String.valueOf(selectedBuy.getPrice()));
            buyTotalField.setText(String.valueOf(selectedBuy.getTotal()));
            buyInvoiceNumber.setText(String.valueOf(selectedBuy.getInvoiceNumber()));
            supplierNameField.setText(selectedBuy.getSupplierName());
            buyDate.setValue(selectedBuy.getPurchaseDate());
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to edit");
        }
    }

    @FXML
    void removeBuyButton(ActionEvent event) {
        Buy selectedBuy = buyTable.getSelectionModel().getSelectedItem();
        if (selectedBuy != null) {
            buyTable.getItems().remove(selectedBuy);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to remove");
        }
    }

    @FXML
    void resetBuyButton(ActionEvent event) {

    }

    @FXML
    void confirmBuyButton() {
        Buy selectedBuy = buyTable.getSelectionModel().getSelectedItem();
        if (selectedBuy != null) {
            BuyDao buyDao = new BuyDao();
            buyDao.updateBuy(selectedBuy);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to confirm");
        }
    }

    void searchBuyData() {
        BuyDao buyDao = new BuyDao();
        List<Buy> buyList = buyDao.getAll();
        ObservableList<Buy> buyObservableList = FXCollections.observableArrayList(buyList);
        FilteredList<Buy> filteredData = new FilteredList<>(buyObservableList, p -> true);
        transactionBuySearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(buy -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (buy.getBrand().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (buy.getProductType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (buy.getSupplierName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(buy.getInvoiceNumber()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(buy.getStockId()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(buy.getSupplierId()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(buy.getQuantity()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(buy.getPrice()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(buy.getSubTotal()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(buy.getTotal()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

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

        searchBuyData();

    }
}
