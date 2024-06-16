package com.lestarieragemilang.app.desktop.Controller;

import java.sql.SQLException;
import java.util.List;

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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    TextField stockIDIncrement, stockSizeField, stockBuyPriceField, stockSellPriceField, stockQuantityField;

    private StockTablePopulator stockTablePopulator = new StockTablePopulator();

    @FXML
    JFXComboBox<Integer> categoryIDDropDown;

    @FXML
    void addStockButton(ActionEvent event) {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.getAllCategories();

        
        Stock stock = new Stock(gen.generateRandomId(), categoryIDDropDown.getValue(), stockQuantityField.getText(),
                stockBuyPriceField.getText(), stockSellPriceField.getText(), categories.get(categoryIDDropDown.getValue()).getCategoryBrand(),
                categories.get(categoryIDDropDown.getValue()).getCategoryType(), stockQuantityField.getText(), categories.get(categoryIDDropDown.getValue()).getCategoryWeight(),
                categories.get(categoryIDDropDown.getValue()).getCategoryUnit());

        StockDao stockDao = new StockDao();
        stockDao.addStock(stock);

        stockTable.getItems().add(stock);
    }

    @FXML
    void editStockButton(ActionEvent event) {
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

    @FXML
    void resetStockButton(ActionEvent event) {
        stockIDIncrement.clear();
        categoryIDDropDown.setValue(null);
        stockQuantityField.clear();
        stockBuyPriceField.clear();
        stockSellPriceField.clear();
        stockQuantityField.clear();
    }

    @FXML
    void removeStockButton(ActionEvent event) {
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this stock?", "Delete Stock",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Stock stock = stockTable.getSelectionModel().getSelectedItem();
            StockDao stockDao = new StockDao();
            stockDao.removeStock(stock.getStockID());
            stockTable.getItems().remove(stock);
            JOptionPane.showMessageDialog(null, "Stock deleted.");
        } else {
            JOptionPane.showMessageDialog(null, "Deletion cancelled.");
        }
    }

    // initialize
    @FXML
    public void initialize() {
        try {
            stockTablePopulator.populateStockTable(stockIDCol, stockOnCategoryIDCol, stockBrandCol, stockTypeCol,
                    stockSizeCol,
                    stockWeightCol, stockUnitCol, stockQuantityCol, stockBuyPriceCol, stockSellPriceCol, stockTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Fetch id_category list in stocks from the database
        StockDao stock = new StockDao();
        ObservableList<Integer> stockList = FXCollections.observableArrayList(stock.getStockCategoryIds());
        categoryIDDropDown.setItems(stockList);
    }
}
