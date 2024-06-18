package com.lestarieragemilang.app.desktop.Utilities.Transactions;

import java.util.List;

import com.lestarieragemilang.app.desktop.Dao.Transactions.SellDao;
import com.lestarieragemilang.app.desktop.Entities.Transactions.Sell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SellTablePopulator {
    private SellDao sellDao = new SellDao();

    public void populateSellTable(
        TableColumn<?, ?> sellDateCol,
        TableColumn<?, ?> sellBrandCol,
        TableColumn<?, ?> sellTypeCol,
        TableColumn<?, ?> sellOnCustomerNameCol,
        TableColumn<?, ?> sellInvoiceCol,
        TableColumn<?, ?> sellSubTotalCol,
        TableColumn<?, ?> sellPriceCol,
        TableColumn<?, ?> sellTotalCol,
        TableView<Sell> sellTable
    ) {
        ObservableList<Sell> sellData = FXCollections.observableArrayList();
        List<Sell> sells = sellDao.getAll();

        sellDateCol.setCellValueFactory(new PropertyValueFactory<>("sellDate"));
        sellBrandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        sellTypeCol.setCellValueFactory(new PropertyValueFactory<>("productType"));
        sellOnCustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        sellInvoiceCol.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        sellSubTotalCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        sellPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        sellTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        sellData.addAll(sells);
        sellTable.setItems(sellData);
    }
}