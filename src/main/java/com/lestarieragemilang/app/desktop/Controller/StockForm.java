package com.lestarieragemilang.app.desktop.Controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXComboBox;
import com.lestarieragemilang.app.desktop.Dao.CategoryDao;
import com.lestarieragemilang.app.desktop.Dao.StockDao;
import com.lestarieragemilang.app.desktop.Entities.Category;
import com.lestarieragemilang.app.desktop.Entities.Stock;
import com.lestarieragemilang.app.desktop.Utilities.GenerateRandomID;
import com.lestarieragemilang.app.desktop.Utilities.StockTablePopulator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class StockForm {
    GenerateRandomID gen = new GenerateRandomID();

    // Stock Table
    @FXML
    TableColumn<Stock, String> stockIDCol, stockOnCategoryIDCol, stockBrandCol, stockTypeCol, stockSizeCol,
            stockWeightCol,
            stockUnitCol, stockQuantityCol, stockBuyPriceCol, stockSellPriceCol;

    @FXML
    private TableView<Stock> stockTable;

    @FXML
    TextField stockIDIncrement, stockSizeField, stockBuyPriceField, stockSellPriceField, stockQuantityField,
            stockSearchField;

    private StockTablePopulator stockTablePopulator = new StockTablePopulator();

    @FXML
    JFXComboBox<Integer> categoryIDDropDown;

    private StockDao stockDao = new StockDao();
    private CategoryDao categoryDao = new CategoryDao();

    @FXML
    void addStockButton(ActionEvent event) {
        if (JOptionPane.showConfirmDialog(null, "Do you want to add this stock?", "Add Stock",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            List<Category> categories = categoryDao.getAllCategories();

<<<<<<< HEAD
            Stock stock = new Stock(gen.generateRandomId(), categoryIDDropDown.getValue(), categories.get(0).getCategoryBrand(),
                    categories.get(0).getCategoryType(), stockQuantityField.getText(), categories.get(0).getCategorySize(),
                    categories.get(0).getCategoryUnit(), stockBuyPriceField.getText(), categories.get(0).getCategoryWeight(),
=======
            Stock stock = new Stock(gen.generateRandomId(), categoryIDDropDown.getValue(),
                    categories.get(0).getCategoryBrand(),
                    categories.get(0).getCategoryType(), stockSizeField.getText(),
                    categories.get(0).getCategoryWeight(),
                    categories.get(0).getCategoryUnit(), stockQuantityField.getText(), stockBuyPriceField.getText(),
>>>>>>> a20b4e0ee86616262a478ba26f8c30b101b8ec68
                    stockSellPriceField.getText());
            stockDao.addStock(stock);

            stockTable.getItems().add(stock);
        }
    }

    @FXML
    void editStockButton(ActionEvent event) {
        if (JOptionPane.showConfirmDialog(null, "Do you want to edit this stock?", "Edit Stock",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Stock selectedStock = stockTable.getSelectionModel().getSelectedItem();

            if (selectedStock != null) {
                stockIDIncrement.setText(String.valueOf(selectedStock.getStockID()));
                categoryIDDropDown.setValue(selectedStock.getStockOnCategoryID());
                stockQuantityField.setText(selectedStock.getQuantity());
                stockBuyPriceField.setText(selectedStock.getPurchasePrice());
                stockSellPriceField.setText(selectedStock.getPurchaseSell());
                stockQuantityField.setText(selectedStock.getQuantity());
            }
        }
    }

    @FXML
    void resetStockButton(ActionEvent event) {
        if (JOptionPane.showConfirmDialog(null, "Do you want to reset the stock form?", "Reset Stock Form",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            stockIDIncrement.clear();
            categoryIDDropDown.setValue(null);
            stockQuantityField.clear();
            stockBuyPriceField.clear();
            stockSellPriceField.clear();
            stockQuantityField.clear();

            // populate stock table
            try {
                stockTablePopulator.populateStockTable(stockIDCol, stockOnCategoryIDCol, stockBrandCol, stockTypeCol,
                        stockSizeCol,
                        stockWeightCol, stockUnitCol, stockQuantityCol, stockBuyPriceCol, stockSellPriceCol,
                        stockTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void removeStockButton(ActionEvent event) {
        Stock stock = stockTable.getSelectionModel().getSelectedItem(); // Declare and initialize the stock variable
        if (stock != null) { // Check if stock is not null
            Alert alert = new Alert(AlertType.CONFIRMATION,
                    "Are you sure you want to delete the following stock: \n" + stock.toString() + "?", ButtonType.YES,
                    ButtonType.NO);
            alert.setTitle("Delete Stock");
            alert.setHeaderText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                stockDao.removeStock(stock.getStockID());
                stockTable.getItems().remove(stock);

                Alert infoAlert = new Alert(AlertType.INFORMATION,
                        "Stock with ID: " + stock.getStockID() + " deleted.");
                infoAlert.setTitle("Stock Deleted");
                infoAlert.setHeaderText(null);
                infoAlert.showAndWait();
            } else {
                Alert infoAlert = new Alert(AlertType.INFORMATION, "Deletion cancelled.");
                infoAlert.setTitle("Deletion Cancelled");
                infoAlert.setHeaderText(null);
                infoAlert.showAndWait();
            }
        }
    }

    void stockSearch() {
        FilteredList<Stock> filteredData = new FilteredList<>(stockTable.getItems());
        stockSearchField.textProperty()
                .addListener((observable, oldValue, newValue) -> filteredData.setPredicate(stock -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (stock.getCategoryBrand().toLowerCase().contains(lowerCaseFilter)
                            || stock.getCategoryType().toLowerCase().contains(lowerCaseFilter)
                            || stock.getCategorySize().toLowerCase().contains(lowerCaseFilter)
                            || stock.getCategoryWeight().toLowerCase().contains(lowerCaseFilter)
                            || stock.getCategoryUnit().toLowerCase().contains(lowerCaseFilter)
                            || stock.getQuantity().toString().toLowerCase().contains(lowerCaseFilter)
                            || stock.getPurchasePrice().toString().toLowerCase().contains(lowerCaseFilter)
                            || stock.getPurchaseSell().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                }));
        stockTable.setItems(filteredData);
    }

    @FXML
    public void initialize() {
        try {
            stockTablePopulator.populateStockTable(stockIDCol, stockOnCategoryIDCol, stockBrandCol, stockTypeCol,
                    stockSizeCol,
                    stockWeightCol, stockUnitCol, stockQuantityCol, stockBuyPriceCol, stockSellPriceCol, stockTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<Integer> stockList = FXCollections.observableArrayList(stockDao.getStockCategoryIds());
        categoryIDDropDown.setItems(stockList);
        stockSearch();
    }
}