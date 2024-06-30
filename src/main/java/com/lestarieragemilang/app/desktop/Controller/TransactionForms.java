package com.lestarieragemilang.app.desktop.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.lestarieragemilang.app.desktop.Dao.Transactions.BuyDao;
import com.lestarieragemilang.app.desktop.Dao.Transactions.SellDao;
import com.lestarieragemilang.app.desktop.Entities.Transactions.Buy;
import com.lestarieragemilang.app.desktop.Entities.Transactions.Sell;
import com.lestarieragemilang.app.desktop.Utilities.GenerateRandomID;
import com.lestarieragemilang.app.desktop.Utilities.Transactions.BuyTablePopulator;
import com.lestarieragemilang.app.desktop.Utilities.Transactions.SellTablePopulator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;



public class TransactionForms {
    GenerateRandomID gen = new GenerateRandomID();

    BuyTablePopulator buyTablePopulator = new BuyTablePopulator();

    @FXML
    JFXButton editBuyButtonText, editSellButtonText;

    // Buy Form
    @FXML
    private TableColumn<?, ?> buyBrandCol, buyDateCol, buyInvoiceCol, buyOnSupplierNameCol, buyPriceCol, buySubTotalCol,
            buyTotalCol, buyTypeCol;

    @FXML
    private TableColumn<?, ?> sellBrandCol, sellDateCol, sellInvoiceCol, sellOnCustomerNameCol, sellPriceCol,
            sellSubTotalCol,
            sellTotalCol, sellTypeCol;
    @FXML
    private TextField buyBrandField, buyInvoiceNumber, buyPriceField, buyTotalField, buyTypeField, supplierNameField,
            transactionBuySearchField, buyTotalPrice;

    @FXML
    private TextField sellBrandField, sellInvoiceNumber, sellPriceField, sellTotalField, sellTypeField,
            customerNameField,
            transactionSellSearchField, sellTotalPrice;
    @FXML
    private DatePicker buyDate;

    @FXML
    private DatePicker sellDate;

    @FXML
    private TableView<Buy> buyTable;

    @FXML
    private TableView<Sell> sellTable;

    @FXML
    private JFXComboBox<Object> buyStockIDDropdown, sellStockIDDropdown;

    @FXML
    private JFXComboBox<Object> supplierIDDropDown, customerIDDropDown;

    // Sell Form
    @FXML
    void addSellButton(ActionEvent event) {
        GenerateRandomID gen = new GenerateRandomID();

        int invoiceNumber = gen.generateRandomId();

        Sell newSell = new Sell(
            LocalDate.now(), sellBrandField.getText(), sellTypeField.getText(),
            customerNameField.getText(),
            invoiceNumber, Integer.parseInt(buyStockIDDropdown.getValue().toString()),
            Integer.parseInt(supplierIDDropDown.getValue().toString()), 
            Integer.parseInt(sellTotalField.getText()),
            Double.parseDouble(sellPriceField.getText()),
            Double.parseDouble(sellTotalField.getText()),
            Double.parseDouble(sellTotalField.getText())
        );

        SellDao sellDao = new SellDao();
        sellDao.addSell(newSell);

        sellTable.getItems().add(newSell);

        sellBrandField.clear();
        sellTypeField.clear();
        sellPriceField.clear();
        sellTotalField.clear();
        sellInvoiceNumber.clear();
        customerNameField.clear();
        sellDate.setValue(null);
    }

    
    @FXML
    void editSellButton(ActionEvent event) {
        Sell selectedSell = sellTable.getSelectionModel().getSelectedItem();

        if (selectedSell != null) {
            if (editSellButtonText.getText().equals("KONFIRMASI")) {
                // Confirmation alert
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to update the sell?");
                
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    updateSelectedSell(selectedSell);
                    
                    SellDao sellDao = new SellDao();
                    sellDao.updateSell(selectedSell);

                    sellTable.refresh();
                    editSellButtonText.setText("EDIT");

                    // Success alert
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Sell has been successfully updated.");
                }
            } else {
                populateSellFields(selectedSell);
                editSellButtonText.setText("KONFIRMASI");
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Information", "Please select a sell to edit.");
        }
    }

    private void updateSelectedSell(Sell selectedSell) {
        selectedSell.setBrand(sellBrandField.getText());
        selectedSell.setProductType(sellTypeField.getText());
        selectedSell.setCustomerName(customerNameField.getText());
        selectedSell.setInvoiceNumber(Integer.parseInt(sellInvoiceNumber.getText()));
        selectedSell.setStockId(Integer.parseInt(buyStockIDDropdown.getValue().toString()));
        selectedSell.setQuantity(Integer.parseInt(sellTotalField.getText()));
        selectedSell.setPrice(Double.parseDouble(sellPriceField.getText()));
        selectedSell.setSellDate(sellDate.getValue());
    }

    private void populateSellFields(Sell selectedSell) {
        sellBrandField.setText(selectedSell.getBrand());
        sellTypeField.setText(selectedSell.getProductType());
        sellPriceField.setText(String.valueOf(selectedSell.getPrice()));
        sellTotalField.setText(String.valueOf(selectedSell.getQuantity()));
        sellInvoiceNumber.setText(String.valueOf(selectedSell.getInvoiceNumber()));
        customerNameField.setText(selectedSell.getCustomerName());
        sellDate.setValue(selectedSell.getSellDate());
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    void removeSellButton(ActionEvent event) {
        Sell selectedSell = sellTable.getSelectionModel().getSelectedItem();
        if (selectedSell != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete the following sell: \n" + selectedSell.toString() + "?",
                    ButtonType.YES,
                    ButtonType.NO);
            alert.setTitle("Delete Sell");
            alert.setHeaderText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                SellDao sellDao = new SellDao();
                sellDao.removeSell(selectedSell);

                List<Sell> sells = sellTable.getItems();
                sells.remove(selectedSell);
                sellTable.setItems(FXCollections.observableArrayList(sells));

                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION,
                        "Sell with Invoice Number: " + selectedSell.getInvoiceNumber() + " deleted.");
                infoAlert.setTitle("Sell Deleted");
                infoAlert.setHeaderText(null);
                infoAlert.showAndWait();
            } else {
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Deletion cancelled.");
                infoAlert.setTitle("Deletion Cancelled");
                infoAlert.setHeaderText(null);
                infoAlert.showAndWait();
            }
        }
    }

    @FXML
    void resetSellButton(ActionEvent event) {
        sellBrandField.clear();
        sellTypeField.clear();
        sellPriceField.clear();
        sellTotalField.clear();
        sellInvoiceNumber.clear();
        customerNameField.clear();
        sellDate.setValue(null);
    }

    @FXML
    void confirmSellButton() {
        Sell selectedSell = sellTable.getSelectionModel().getSelectedItem();
        if (selectedSell != null) {
            SellDao sellDao = new SellDao();
            sellDao.updateSell(selectedSell);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to confirm");
        }
    }

    void searchSellData() {
        SellDao sellDao = new SellDao();
        List<Sell> sellList = sellDao.getAll();
        ObservableList<Sell> sellObservableList = FXCollections.observableArrayList(sellList);
        FilteredList<Sell> filteredData = new FilteredList<>(sellObservableList, p -> true);
        transactionSellSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(sell -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (sell.getBrand().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (sell.getProductType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (sell.getCustomerName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(sell.getInvoiceNumber()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(sell.getStockId()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(sell.getQuantity()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(sell.getPrice()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(sell.getSubTotal()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(sell.getPriceTotal()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }





    /**
     * 
     *     |-------------------------------------------|
     *     |--------------- | Buy Form | --------------|
     *     |-------------------------------------------|
     * 
     */

    @FXML
    void addBuyButton(ActionEvent event) {
        GenerateRandomID gen = new GenerateRandomID();

        int invoiceNumber = gen.generateRandomId();

        Buy newBuy = new Buy(LocalDate.now(), buyBrandField.getText(), buyTypeField.getText(),
            supplierNameField.getText(),
            invoiceNumber, Integer.parseInt(buyStockIDDropdown.getValue().toString()),
            Integer.parseInt(supplierIDDropDown.getValue().toString()), 
            Integer.parseInt(buyTotalField.getText()),
            Double.parseDouble(buyPriceField.getText()),
            Double.parseDouble(buyTotalField.getText()),
            Double.parseDouble(buyTotalField.getText())
        );

        BuyDao buyDao = new BuyDao();
        buyDao.addBuy(newBuy);

        buyTable.getItems().add(newBuy);

        buyTotalPrice.setText(Long.toString(buyDao.sumTotal()));

        buyBrandField.clear();
        buyTypeField.clear();
        buyPriceField.clear();
        buyTotalField.clear();
        buyInvoiceNumber.clear();
        supplierNameField.clear();
        buyDate.setValue(null);
    }

    @FXML
    void editBuyButton(ActionEvent event) {
        Buy selectedBuy = buyTable.getSelectionModel().getSelectedItem();

        if (selectedBuy != null) {
            if (editBuyButtonText.getText().equals("KONFIRMASI")) {
                // Confirmation alert
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to update the buy?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        updateSelectedBuy(selectedBuy);

                        BuyDao buyDao = new BuyDao();
                        buyDao.updateBuy(selectedBuy);

                        buyTable.refresh();
                        editBuyButtonText.setText("EDIT");

                        // Success alert
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Buy has been successfully updated.");
                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "Error", "Failed to update the buy.");
                    }
                }
            } else {
                populateBuyFields(selectedBuy);
                editBuyButtonText.setText("KONFIRMASI");
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Information", "Please select a buy to edit.");
        }
    }

    private void updateSelectedBuy(Buy selectedBuy) {
        selectedBuy.setBrand(buyBrandField.getText());
        selectedBuy.setProductType(buyTypeField.getText());
        selectedBuy.setSupplierName(supplierNameField.getText());
        selectedBuy.setInvoiceNumber(Integer.parseInt(buyInvoiceNumber.getText()));
        selectedBuy.setStockId(Integer.parseInt(buyStockIDDropdown.getValue().toString()));
        selectedBuy.setQuantity(Integer.parseInt(buyTotalField.getText()));
        selectedBuy.setPrice(Double.parseDouble(buyPriceField.getText()));
        selectedBuy.setPurchaseDate(buyDate.getValue());
    }

    private void populateBuyFields(Buy selectedBuy) {
        buyBrandField.setText(selectedBuy.getBrand());
        buyTypeField.setText(selectedBuy.getProductType());
        buyPriceField.setText(String.valueOf(selectedBuy.getPrice()));
        buyTotalField.setText(String.valueOf(selectedBuy.getQuantity()));
        buyInvoiceNumber.setText(String.valueOf(selectedBuy.getInvoiceNumber()));
        supplierNameField.setText(selectedBuy.getSupplierName());
        buyDate.setValue(selectedBuy.getPurchaseDate());
    }

    @FXML
    void removeBuyButton(ActionEvent event) {
        Buy selectedBuy = buyTable.getSelectionModel().getSelectedItem();
        if (selectedBuy != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete the following buy: \n" + selectedBuy.toString() + "?",
                    ButtonType.YES,
                    ButtonType.NO);
            alert.setTitle("Delete Buy");
            alert.setHeaderText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                BuyDao buyDao = new BuyDao();
                buyDao.removeBuy(selectedBuy);

                List<Buy> buys = buyTable.getItems();
                buys.remove(selectedBuy);
                buyTable.setItems(FXCollections.observableArrayList(buys));

                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION,
                        "Buy with Invoice Number: " + selectedBuy.getInvoiceNumber() + " deleted.");
                infoAlert.setTitle("Buy Deleted");
                infoAlert.setHeaderText(null);
                infoAlert.showAndWait();
            } else {
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Deletion cancelled.");
                infoAlert.setTitle("Deletion Cancelled");
                infoAlert.setHeaderText(null);
                infoAlert.showAndWait();
            }
        }
    }

    @FXML
    void resetBuyButton(ActionEvent event) {
        buyBrandField.clear();
        buyTypeField.clear();
        buyPriceField.clear();
        buyTotalField.clear();
        buyInvoiceNumber.clear();
        supplierNameField.clear();
        buyDate.setValue(null);
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
                } else if (String.valueOf(buy.getPriceTotal()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

    }

    void buyInit() {
        BuyTablePopulator buyTablePopulator = new BuyTablePopulator();

        // Buy Table
        buyTablePopulator.populateBuyTable(
            buyDateCol, buyBrandCol, buyTypeCol, buyOnSupplierNameCol,
            buyInvoiceCol, buySubTotalCol, buyPriceCol, buyTotalCol, buyTable
        );

        BuyDao buyDao = new BuyDao();

        // Stock ID Dropdown
        ObservableList<Object> stockIds = FXCollections.observableArrayList(buyDao.getAllStockIds());
        buyStockIDDropdown.setItems(stockIds);
        if (!stockIds.isEmpty()) {
            buyStockIDDropdown.getSelectionModel().selectFirst();
            String firstStockId = stockIds.get(0).toString();
            List<String> firstStockDetails = buyDao.getBrandTypePrice(firstStockId);
            buyBrandField.setText(firstStockDetails.get(0)); // Assuming the brand is the first item in the list
            buyTypeField.setText(firstStockDetails.get(1)); // Assuming the type is the second item in the list
            buyPriceField.setText(firstStockDetails.get(2)); // Assuming the price is the third item in the list
        }

        buyStockIDDropdown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
    }

    void sellInit() {
        // Sell Table
        SellTablePopulator sellTablePopulator = new SellTablePopulator();
        sellTablePopulator.populateSellTable(sellDateCol, sellBrandCol, sellTypeCol, sellOnCustomerNameCol,
                sellInvoiceCol,
                sellSubTotalCol, sellPriceCol, sellTotalCol, sellTable);

        SellDao sellDao = new SellDao();

        // Stock ID Dropdown
        ObservableList<Object> stockIds = FXCollections.observableArrayList(sellDao.getAllStockIds());
        sellStockIDDropdown.setItems(stockIds);
        if (!stockIds.isEmpty()) {
            sellStockIDDropdown.getSelectionModel().selectFirst();
            String firstStockId = stockIds.get(0).toString();
            List<String> firstStockDetails = sellDao.getBrandTypePrice(firstStockId);
            sellBrandField.setText(firstStockDetails.get(0)); // Assuming the brand is the first item in the list
            sellTypeField.setText(firstStockDetails.get(1)); // Assuming the type is the second item in the list
            sellPriceField.setText(firstStockDetails.get(2)); // Assuming the price is the third item in the list
        }

        sellStockIDDropdown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String stockId = newValue.toString();
                List<String> stockDetails = sellDao.getBrandTypePrice(stockId);
                sellBrandField.setText(stockDetails.get(0)); // Assuming the brand is the first item in the list
                sellTypeField.setText(stockDetails.get(1)); // Assuming the type is the second item in the list
                sellPriceField.setText(stockDetails.get(2)); // Assuming the price is the third item in the list
            }
        });

        // Customer ID Dropdown
        ObservableList<Object> customerIds = FXCollections.observableArrayList(sellDao.getCustomerIds());
        customerIDDropDown.setItems(customerIds);
        if (!customerIds.isEmpty()) {
            customerIDDropDown.getSelectionModel().selectFirst();
            String firstCustomerId = customerIds.get(0).toString();
            String firstCustomerName = sellDao.getCustomerName(firstCustomerId);
            customerNameField.setText(firstCustomerName);
        }

        customerIDDropDown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String customerId = newValue.toString();
                String customerName = sellDao.getCustomerName(customerId);
                customerNameField.setText(customerName);
            }
        });

        searchSellData();
    }

    @FXML
    void calculateBuyTotalPrice(ActionEvent event) {
        double price = Double.parseDouble(buyPriceField.getText());
        double total = price * 1;
        buyTotalPrice.setText(String.valueOf(total));
    }

    @FXML
    void calculateSellTotalPrice(ActionEvent event) {
        double price = Double.parseDouble(sellPriceField.getText());
        double total = price * 1;
        sellTotalPrice.setText(String.valueOf(total));
    }

    @FXML
    void searchDataBuyAction(ActionEvent event) {
        BuyDao buyDao = new BuyDao();
        ObservableList<Buy> data = FXCollections.observableArrayList();

        data = FXCollections.observableArrayList();

        List<Buy> searchDatas = buyDao
            .searchBuyData(transactionBuySearchField.getText());

        for (Buy rowData : searchDatas) {
            data.add(rowData);
            buyTable.setItems(data);
        }
        
        buyTable.refresh();
    }

    @FXML
    void searchDataSellAction(ActionEvent event) {
        SellDao sellDao = new SellDao();
        ObservableList<Sell> data = FXCollections.observableArrayList();

        data = FXCollections.observableArrayList();

        List<Sell> searchDatas = sellDao
            .searchSellData(transactionSellSearchField.getText());

        for (Sell rowData : searchDatas) {
            data.add(rowData);
            sellTable.setItems(data);
        }
        
        sellTable.refresh();
    }

    @FXML
    public void initialize() {
        try {
            BuyDao buyDao = new BuyDao();
            SellDao sellDao = new SellDao();

            buyTotalPrice.setText(Long.toString(buyDao.sumTotal()));
            sellTotalPrice.setText(Long.toString(sellDao.sumTotal()));
            
            buyInit();
            sellInit();
            searchBuyData();
        } catch (Exception e) {
            Logger.getLogger(TransactionForms.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
