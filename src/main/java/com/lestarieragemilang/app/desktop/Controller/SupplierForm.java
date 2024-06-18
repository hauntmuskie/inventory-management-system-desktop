package com.lestarieragemilang.app.desktop.Controller;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.lestarieragemilang.app.desktop.Dao.SupplierDao;
import com.lestarieragemilang.app.desktop.Entities.Supplier;
import com.lestarieragemilang.app.desktop.Utilities.GenerateRandomID;
import com.lestarieragemilang.app.desktop.Utilities.SupplierTablePopulator;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SupplierForm {
    GenerateRandomID gen = new GenerateRandomID();

    @FXML
    private JFXButton editSupplierButtonText;

    // Supplier Table
    @FXML
    TextField supplierIDIncrement, supplierNameField, supplierAddressField, supplierContactField, supplierEmailField,
            supplierSearchField;

    @FXML
    private TableColumn<Supplier, String> supplierIDCol, supplierNameCol, supplierAddressCol, supplierContactCol,
            supplierEmailCol;

    @FXML
    private TableView<Supplier> supplierTable;

    private SupplierTablePopulator supplierTablePopulator = new SupplierTablePopulator();

    // Supplier Table

    @FXML
    void addSupplierButton(ActionEvent event) {
        Supplier supplier = new Supplier(gen.generateRandomId(), supplierNameField.getText(),
                supplierAddressField.getText(),
                supplierContactField.getText(), supplierEmailField.getText());

        SupplierDao supplierDao = new SupplierDao();
        supplierDao.addSupplier(supplier);

        supplierTable.getItems().add(supplier);
    }

    @FXML
    void editSupplierButton(ActionEvent event) {
        Supplier selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
        SupplierDao supplierDao = new SupplierDao();

        if (selectedSupplier != null) {
            if (editSupplierButtonText.getText().equals("Konfirmasi")) {
                // Update the supplier details
                selectedSupplier.setSupplierName(supplierNameField.getText());
                selectedSupplier.setSupplierAddress(supplierAddressField.getText());
                selectedSupplier.setSupplierContact(supplierContactField.getText());
                selectedSupplier.setSupplierEmail(supplierEmailField.getText());

                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to update this supplier?",
                        "Update Supplier",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    supplierDao.updateSupplier(selectedSupplier);
                }

                supplierTable.refresh();

                editSupplierButtonText.setText("Edit");
            } else {
                // Populate the fields with the selected supplier's details
                supplierIDIncrement.setText(String.valueOf(selectedSupplier.getSupplierId()));
                supplierNameField.setText(selectedSupplier.getSupplierName());
                supplierAddressField.setText(selectedSupplier.getSupplierAddress());
                supplierContactField.setText(selectedSupplier.getSupplierContact());
                supplierEmailField.setText(selectedSupplier.getSupplierEmail());

                editSupplierButtonText.setText("Konfirmasi");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a supplier to edit.");
        }
    }

    @FXML
    void resetSupplierButton(ActionEvent event) {
        editSupplierButtonText.setText("EDIT");
        supplierIDIncrement.clear();
        supplierNameField.clear();
        supplierAddressField.clear();
        supplierContactField.clear();
        supplierEmailField.clear();
    }

    @FXML
    void removeSupplierButton(ActionEvent event) {
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this supplier?", "Delete Supplier",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Supplier supplier = supplierTable.getSelectionModel().getSelectedItem();
            SupplierDao supplierDao = new SupplierDao();
            supplierDao.removeSupplier(supplier);
            supplierTable.getItems().remove(supplier);
            JOptionPane.showMessageDialog(null, "Supplier deleted.");
        } else {
            JOptionPane.showMessageDialog(null, "Deletion cancelled.");
        }

    }

    void supplierSearch() {
        FilteredList<Supplier> filteredData = new FilteredList<>(supplierTable.getItems());
        supplierSearchField.textProperty()
                .addListener((observable, oldValue, newValue) -> filteredData.setPredicate(supplier -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (supplier.getSupplierName().toLowerCase().contains(lowerCaseFilter)
                            || supplier.getSupplierAddress().toLowerCase().contains(lowerCaseFilter)
                            || supplier.getSupplierContact().toLowerCase().contains(lowerCaseFilter)
                            || supplier.getSupplierEmail().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                }));
        supplierTable.setItems(filteredData);
    }

    @FXML
    void initialize() {
        // Supplier Table
        supplierTablePopulator.populateSupplierTable(supplierIDCol, supplierNameCol, supplierAddressCol,
                supplierContactCol, supplierEmailCol, supplierTable);
        // Add this in your initialize method
        supplierTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                editSupplierButtonText.setText("Edit");
                supplierIDIncrement.clear();
                supplierNameField.clear();
                supplierAddressField.clear();
                supplierContactField.clear();
                supplierEmailField.clear();
            }
        });

        supplierSearch();
    }
}
