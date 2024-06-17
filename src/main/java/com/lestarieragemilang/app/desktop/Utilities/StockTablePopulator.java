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

    public void populateStockTable(TableColumn<?, ?> stockIDCol, TableColumn<?, ?> stockOnCategoryIDCol,
            TableColumn<?, ?> stockBrandCol, TableColumn<?, ?> stockTypeCol, TableColumn<?, ?> stockSizeCol,
            TableColumn<?, ?> stockWeightCol, TableColumn<?, ?> stockUnitCol, TableColumn<?, ?> stockQuantityCol,
            TableColumn<?, ?> stockBuyPriceCol, TableColumn<?, ?> stockSellPriceCol, TableView<Stock> stockTable)
            throws SQLException {
        ObservableList<Stock> stockData = FXCollections.observableArrayList();
        List<Stock> stocks = stockDao.getAllStocks();

        stockIDCol.setCellValueFactory(new PropertyValueFactory<>("stockID"));
        stockOnCategoryIDCol.setCellValueFactory(new PropertyValueFactory<>("stockOnCategoryID"));
        stockBrandCol.setCellValueFactory(new PropertyValueFactory<>("categoryBrand"));
        stockTypeCol.setCellValueFactory(new PropertyValueFactory<>("categoryType"));
        stockSizeCol.setCellValueFactory(new PropertyValueFactory<>("categorySize"));
        stockWeightCol.setCellValueFactory(new PropertyValueFactory<>("categoryWeight"));
        stockUnitCol.setCellValueFactory(new PropertyValueFactory<>("categoryUnit"));
        stockQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        stockBuyPriceCol.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        stockSellPriceCol.setCellValueFactory(new PropertyValueFactory<>("purchaseSell"));

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