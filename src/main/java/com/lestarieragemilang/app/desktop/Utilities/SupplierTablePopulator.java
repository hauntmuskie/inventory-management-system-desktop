package com.lestarieragemilang.app.desktop.Utilities;

import com.lestarieragemilang.app.desktop.Dao.SupplierDao;
import com.lestarieragemilang.app.desktop.Entities.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SupplierTablePopulator {

    private SupplierDao supplierDao = new SupplierDao();

    public void populateSupplierTable(TableColumn<?, ?> supplierIDCol, TableColumn<?, ?> supplierNameCol, TableColumn<?, ?> supplierAddressCol, TableColumn<?, ?> supplierContactCol, TableColumn<?, ?> supplierEmailCol, TableView<Supplier> supplierTable) {
        ObservableList<Supplier> supplierData = FXCollections.observableArrayList();
        List<Supplier> suppliers = supplierDao.getAllSuppliers();

        supplierIDCol.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        supplierNameCol.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supplierAddressCol.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        supplierContactCol.setCellValueFactory(new PropertyValueFactory<>("supplierContact"));
        supplierEmailCol.setCellValueFactory(new PropertyValueFactory<>("supplierEmail"));

        supplierData.addAll(suppliers);
        supplierTable.setItems(supplierData);
    }
}