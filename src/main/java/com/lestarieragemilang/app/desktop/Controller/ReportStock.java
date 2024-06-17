package com.lestarieragemilang.app.desktop.Controller;

import java.sql.SQLException;

import com.lestarieragemilang.app.desktop.Configurations.ReportConfiguration.JasperLoader;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import com.lestarieragemilang.app.desktop.Entities.Stock;
import com.lestarieragemilang.app.desktop.Utilities.StockTablePopulator;

public class ReportStock {

  @FXML
  private TableColumn<?, ?> stockBrandCol;

  @FXML
  private TableColumn<?, ?> stockBuyPriceCol;

  @FXML
  private TableColumn<?, ?> stockIDCol;

  @FXML
  private TableColumn<?, ?> stockOnCategoryIDCol;

  @FXML
  private TableColumn<?, ?> stockQuantityCol;

  @FXML
  private TableColumn<?, ?> stockSellPriceCol;

  @FXML
  private TableColumn<?, ?> stockSizeCol;

  @FXML
  private TableView<Stock> stockTable;

  @FXML
  private TableColumn<?, ?> stockTypeCol;

  @FXML
  private TableColumn<?, ?> stockUnitCol;

  @FXML
  private TableColumn<?, ?> stockWeightCol;

  @FXML
  void printJasperStock(MouseEvent event) {
    try {
      JasperLoader loader = new JasperLoader();
      loader.showJasperReport("src/main/java/com/lestarieragemilang/app/desktop/Configurations/ReportConfiguration/stock-list.jasper", event);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @FXML
  void initialize() throws SQLException {
    StockTablePopulator stockTablePopulator = new StockTablePopulator();
    stockTablePopulator.populateStockTable(stockIDCol, stockOnCategoryIDCol, stockBrandCol, stockTypeCol,
        stockSizeCol,
        stockWeightCol, stockUnitCol, stockQuantityCol, stockBuyPriceCol, stockSellPriceCol, stockTable);
  }
}
