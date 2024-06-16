package com.lestarieragemilang.app.desktop.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.lestarieragemilang.app.desktop.Entities.Transactions.Buy;
import com.lestarieragemilang.app.desktop.Utilities.GenerateRandomID;
import com.lestarieragemilang.app.desktop.Utilities.Transactions.BuyTablePopulator;

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
    private TableColumn<?, ?> buyBrandCol;

    @FXML
    private TextField buyBrandField;

    @FXML
    private DatePicker buyDate;

    @FXML
    private TableColumn<?, ?> buyDateCol;

    @FXML
    private TableColumn<?, ?> buyInvoiceCol;

    @FXML
    private TextField buyInvoiceNumber;

    @FXML
    private TableColumn<?, ?> buyOnSupplierNameCol;

    @FXML
    private TableColumn<?, ?> buyPriceCol;

    @FXML
    private TextField buyPriceField;

    @FXML
    private TableColumn<?, ?> buySubTotalCol;

    @FXML
    private TableView<Buy> buyTable;

    @FXML
    private TableColumn<?, ?> buyTotalCol;

    @FXML
    private TextField buyTotalField;

    @FXML
    private TableColumn<?, ?> buyTypeCol;

    @FXML
    private TextField buyTypeField;

    @FXML
    private JFXComboBox<?> stockIDDropdown;

    @FXML
    private JFXComboBox<?> supplierIDDropDown;

    @FXML
    private TextField supplierNameField;

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
    }
}
