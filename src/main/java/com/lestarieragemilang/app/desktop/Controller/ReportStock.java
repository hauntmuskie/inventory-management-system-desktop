package com.lestarieragemilang.app.desktop.Controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import com.lestarieragemilang.app.desktop.Configurations.ReportConfiguration.JasperLoader;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
  private TextField stockSearchField;

  @FXML
  void printJasperStock(MouseEvent event) throws IOException, URISyntaxException {
    String path = "/com/lestarieragemilang/app/desktop/jasper/stock-list.jasper";
    URL url = ReportStock.class.getResource(path).toURI().toURL();
    try {
      JasperLoader loader = new JasperLoader();
      loader.showJasperReportStock(
          url,
          stockSearchField.getText(), stockSearchField.getText(), stockSearchField.getText(),
          stockSearchField.getText(), stockSearchField.getText(), stockSearchField.getText(),
          stockSearchField.getText(), stockSearchField.getText(), event);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
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
  void initialize() throws SQLException {
    StockTablePopulator stockTablePopulator = new StockTablePopulator();
    stockTablePopulator.populateStockTable(stockIDCol, stockOnCategoryIDCol, stockBrandCol, stockTypeCol,
        stockSizeCol,
        stockWeightCol, stockUnitCol, stockQuantityCol, stockBuyPriceCol, stockSellPriceCol, stockTable);

    stockSearch();
  }
}
