package com.lestarieragemilang.app.desktop.Utilities.Transactions;

import java.util.List;

import com.lestarieragemilang.app.desktop.Dao.Transactions.BuyDao;
import com.lestarieragemilang.app.desktop.Entities.Transactions.Buy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BuyTablePopulator {
    private BuyDao buyDao = new BuyDao();

    public void populateBuyTable(
        TableColumn<?, ?> buyDateCol,
        TableColumn<?, ?> buyBrandCol,
        TableColumn<?, ?> buyTypeCol,
        TableColumn<?, ?> buyOnSupplierNameCol,
        TableColumn<?, ?> buyInvoiceCol,
        TableColumn<?, ?> buySubTotalCol,
        TableColumn<?, ?> buyPriceCol,
        TableColumn<?, ?> buyTotalCol,
        TableView<Buy> buyTable
    ) {
        ObservableList<Buy> buyData = FXCollections.observableArrayList();
        List<Buy> buys = buyDao.getAll();

        buyDateCol.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        buyBrandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        buyTypeCol.setCellValueFactory(new PropertyValueFactory<>("productType"));
        buyOnSupplierNameCol.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        buyInvoiceCol.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        buyPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        buyTotalCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        buySubTotalCol.setCellValueFactory(new PropertyValueFactory<>("subTotal"));

        buyData.addAll(buys);
        buyTable.setItems(buyData);
    }
    
    
}
