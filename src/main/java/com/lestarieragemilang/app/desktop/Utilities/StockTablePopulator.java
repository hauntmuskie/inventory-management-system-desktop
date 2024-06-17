package com.lestarieragemilang.app.desktop.Utilities;

import java.sql.SQLException;
import java.util.List;

import com.lestarieragemilang.app.desktop.Dao.CategoryDao;
import com.lestarieragemilang.app.desktop.Dao.StockDao;
import com.lestarieragemilang.app.desktop.Entities.Category;
import com.lestarieragemilang.app.desktop.Entities.Stock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StockTablePopulator {
    StockDao stockDao = new StockDao();
    CategoryDao categoryDao = new CategoryDao();

    public void populateStockTable(TableColumn<Stock, String> stockIDCol, TableColumn<Stock, String> stockOnCategoryIDCol,
            TableColumn<Stock, String> categoryBrandCol, TableColumn<Stock, String> categoryTypeCol, TableColumn<Stock, String> categorySizeCol,
            TableColumn<Stock, String> categoryWeightCol, TableColumn<Stock, String> categoryUnitCol, TableColumn<Stock, String> quantityCol,
            TableColumn<Stock, String> purchasePriceCol, TableColumn<Stock, String> purchaseSellCol, TableView<Stock> stockTable)
            throws SQLException {
        ObservableList<Stock> stockData = FXCollections.observableArrayList();
        List<Stock> stocks = stockDao.getAllStocks();

        stockIDCol.setCellValueFactory(new PropertyValueFactory<>("stockID"));
        stockOnCategoryIDCol.setCellValueFactory(new PropertyValueFactory<>("stockOnCategoryID"));
        categoryBrandCol.setCellValueFactory(new PropertyValueFactory<>("categoryBrand"));
        categoryTypeCol.setCellValueFactory(new PropertyValueFactory<>("categoryType"));
        categorySizeCol.setCellValueFactory(new PropertyValueFactory<>("categorySize"));
        categoryWeightCol.setCellValueFactory(new PropertyValueFactory<>("categoryWeight"));
        categoryUnitCol.setCellValueFactory(new PropertyValueFactory<>("categoryUnit"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchasePriceCol.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        purchaseSellCol.setCellValueFactory(new PropertyValueFactory<>("purchaseSell"));

        for (Stock stock : stocks) {
            Category category = categoryDao.getCategoryById(stock.getStockOnCategoryID());
            stock.setCategoryBrand(category.getCategoryBrand());
            stock.setCategoryType(category.getCategoryType());
            stock.setCategorySize(category.getCategorySize());
            stock.setCategoryWeight(category.getCategoryWeight());
            stock.setCategoryUnit(category.getCategoryUnit());
        }
        stockData.addAll(stocks);
        stockTable.setItems(stockData);
    }
}