package com.lestarieragemilang.app.desktop.Controller;

import com.lestarieragemilang.app.desktop.Utilities.CategoryTablePopulator;
import com.lestarieragemilang.app.desktop.Utilities.CustomerTablePopulator;
import com.lestarieragemilang.app.desktop.Utilities.GenerateRandomID;
import com.lestarieragemilang.app.desktop.Utilities.SupplierTablePopulator;

import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.lestarieragemilang.app.desktop.Dao.CategoryDao;
import com.lestarieragemilang.app.desktop.Dao.CustomerDao;
import com.lestarieragemilang.app.desktop.Dao.SupplierDao;
import com.lestarieragemilang.app.desktop.Entities.Category;
import com.lestarieragemilang.app.desktop.Entities.Customer;
import com.lestarieragemilang.app.desktop.Entities.Supplier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Data {

    GenerateRandomID gen = new GenerateRandomID();
    @FXML
    private JFXButton editSupplierButtonText;

    // Supplier Table
    @FXML
    private TableColumn<Supplier, String> supplierIDCol, supplierNameCol, supplierAddressCol, supplierContactCol,
            supplierEmailCol;

    @FXML
    private TableView<Supplier> supplierTable;

    private SupplierTablePopulator supplierTablePopulator = new SupplierTablePopulator();

    @FXML
    TextField supplierIDIncrement, supplierNameField, supplierAddressField, supplierContactField, supplierEmailField;

    // Category Table
    @FXML
    private TableColumn<Category, String> categoryIDCol, brandCategoryCol, typeCategoryCol, sizeCategoryCol,
            weightCategoryCol, unitCategoryCol;

    @FXML
    private TableView<Category> categoryTable;

    private CategoryTablePopulator categoryTablePopulator = new CategoryTablePopulator();

    @FXML
    TextField categoryIDIncrement;

    @FXML
    JFXComboBox<String> categoryBrandDropDown, categoryTypeDropDown, categorySizeDropDown, categoryWeightDropDown,
            categoryUnitDropDown;

    // Customer Table
    @FXML
    private TableColumn<Customer, String> customerIDCol, customerNameCol, customerAddressCol, customerContactCol,
            customerEmailCol;

    @FXML
    TableView<Customer> customerTable;


    
    @FXML
    TextField customerIDIncrement, customerNameField, customerAddressField, customerContactField, customerEmailField;

    private final CustomerTablePopulator customerTablePopulator = new CustomerTablePopulator();

    // Method Begin

    // Customer Table

    @FXML
    void addCustomerButton(ActionEvent event) {
        Customer customer = new Customer(gen.generateRandomId(), customerNameField.getText(),
                customerAddressField.getText(),
                customerContactField.getText(), customerEmailField.getText());

        CustomerDao customerDao = new CustomerDao();
        customerDao.addCustomer(customer);

        customerTable.getItems().add(customer);
    }

    @FXML
    void editCustomerButton(ActionEvent event) {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            customerIDIncrement.setText(String.valueOf(selectedCustomer.getCustomerId()));
            customerNameField.setText(selectedCustomer.getCustomerName());
            customerAddressField.setText(selectedCustomer.getCustomerAddress());
            customerContactField.setText(selectedCustomer.getCustomerContact());
            customerEmailField.setText(selectedCustomer.getCustomerEmail());
        } 

    }

    @FXML
    void resetCustomerButton(ActionEvent event) {
        customerIDIncrement.clear();
        customerNameField.clear();
        customerAddressField.clear();
        customerContactField.clear();
        customerEmailField.clear();
    }

    @FXML
    void removeCustomerButton(ActionEvent event) {
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this customer?", "Delete Customer",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            CustomerDao customerDao = new CustomerDao();
            customerDao.removeCustomer(customer);
            customerTable.getItems().remove(customer);
            JOptionPane.showMessageDialog(null, "Customer deleted.");
        } else {
            JOptionPane.showMessageDialog(null, "Deletion cancelled.");
        }
    }

    // Category Table

    @FXML
    void addCategoryButton(ActionEvent event) {
        Category category = new Category(gen.generateRandomId(), categoryBrandDropDown.getValue(),
                categoryTypeDropDown.getValue(), categorySizeDropDown.getValue(), categoryWeightDropDown.getValue(),
                categoryUnitDropDown.getValue());
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.addCategory(category);

        categoryTable.getItems().add(category);
    }

    @FXML
    void editCategoryButton(ActionEvent event) {
        Category selectedCategory = categoryTable.getSelectionModel().getSelectedItem();

        if (selectedCategory != null) {
            categoryIDIncrement.setText(String.valueOf(selectedCategory.getCategoryId()));
            categoryBrandDropDown.setValue(selectedCategory.getCategoryBrand());
            categoryTypeDropDown.setValue(selectedCategory.getCategoryType());
            categorySizeDropDown.setValue(selectedCategory.getCategorySize());
            categoryWeightDropDown.setValue(selectedCategory.getCategoryWeight());
            categoryUnitDropDown.setValue(selectedCategory.getCategoryUnit());
        }
    }

    @FXML
    void resetCategoryButton(ActionEvent event) {
        categoryIDIncrement.clear();
        categoryBrandDropDown.setValue(null);
        categoryTypeDropDown.setValue(null);
        categorySizeDropDown.setValue(null);
        categoryWeightDropDown.setValue(null);
        categoryUnitDropDown.setValue(null);
    }

    @FXML
    void removeCategoryButton(ActionEvent event) {
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this category?", "Delete Category",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Category category = categoryTable.getSelectionModel().getSelectedItem();
            CategoryDao categoryDao = new CategoryDao();
            categoryDao.removeCategory(category);
            categoryTable.getItems().remove(category);
            JOptionPane.showMessageDialog(null, "Category deleted.");
        } else {
            JOptionPane.showMessageDialog(null, "Deletion cancelled.");
        }
    }

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

    @FXML
    public void initialize() {

        // Supplier Table
        supplierTablePopulator.populateSupplierTable(supplierIDCol, supplierNameCol, supplierAddressCol,
                supplierContactCol, supplierEmailCol, supplierTable);

        // Category Table
        categoryTablePopulator.populateCategoryTable(categoryIDCol, brandCategoryCol, typeCategoryCol, sizeCategoryCol,
                weightCategoryCol, unitCategoryCol, categoryTable);

        // Fetch category brands from the database
        CategoryDao categoryDao = new CategoryDao();
        ObservableList<Category> categories = FXCollections.observableArrayList(categoryDao.getAllCategoryBrands());
        ObservableList<String> categoryBrands = categories.stream()
                .map(Category::getCategoryBrand)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        categoryBrandDropDown.setItems(categoryBrands);

        // Customer Table
        customerTablePopulator.populateCustomerTable(customerIDCol, customerNameCol, customerAddressCol,
                customerContactCol, customerEmailCol, customerTable);

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
    }
}
