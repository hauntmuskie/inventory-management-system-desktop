package com.lestarieragemilang.app.desktop.Controller;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXComboBox;
import com.lestarieragemilang.app.desktop.Dao.Transactions.BuyDao;
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
        int invoiceNumber = buyInvoiceNumber.getText().isEmpty() ? 0 : Integer.parseInt(buyInvoiceNumber.getText());
        int stockId = stockIDDropdown.getValue().toString().isEmpty() ? 0
                : Integer.parseInt(stockIDDropdown.getValue().toString());
        int supplierId = supplierIDDropDown.getValue().toString().isEmpty() ? 0
                : Integer.parseInt(supplierIDDropDown.getValue().toString());
        int quantity = buyTotalField.getText().isEmpty() ? 0 : Integer.parseInt(buyTotalField.getText());
        double price = buyPriceField.getText().isEmpty() ? 0.0 : Double.parseDouble(buyPriceField.getText());

        Buy buy = new Buy(buyDate.getValue(), buyBrandField.getText(), buyTypeField.getText(),
                supplierNameField.getText(),
                invoiceNumber, stockId,
                supplierId, quantity,
                price);

        BuyDao buyDao = new BuyDao();
        buyDao.addBuy(buy);

        buyTable.getItems().add(buy);
    }

    @FXML
    void editBuyButton(ActionEvent event) {
        Buy selectedBuy = buyTable.getSelectionModel().getSelectedItem();
        BuyDao buyDao = new BuyDao();

        if (selectedBuy != null) {
            selectedBuy.setPurchaseDate(buyDate.getValue());
            selectedBuy.setBrand(buyBrandField.getText());
            selectedBuy.setProductType(buyTypeField.getText());
            selectedBuy.setSupplierName(supplierNameField.getText());

            if (!buyInvoiceNumber.getText().isEmpty()) {
                selectedBuy.setInvoiceNumber(Integer.parseInt(buyInvoiceNumber.getText()));
            }
            if (!stockIDDropdown.getValue().toString().isEmpty()) {
                selectedBuy.setStockId(Integer.parseInt(stockIDDropdown.getValue().toString()));
            }
            if (!supplierIDDropDown.getValue().toString().isEmpty()) {
                selectedBuy.setSupplierId(Integer.parseInt(supplierIDDropDown.getValue().toString()));
            }
            if (!buyTotalField.getText().isEmpty()) {
                selectedBuy.setQuantity(Integer.parseInt(buyTotalField.getText()));
            }
            if (!buyPriceField.getText().isEmpty()) {
                selectedBuy.setPrice(Double.parseDouble(buyPriceField.getText()));
            }

            if (JOptionPane.showConfirmDialog(null, "Are you sure you want to update this buy?", "Update Buy",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                buyDao.updateBuy(selectedBuy);
            }

            buyTable.refresh();
        }
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
