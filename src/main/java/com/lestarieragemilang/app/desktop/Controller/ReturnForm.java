package com.lestarieragemilang.app.desktop.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReturnForm {
    @FXML
    private DatePicker returnDate;

    @FXML
    private TableColumn<?, ?> returnDateCol;

    @FXML
    private TableColumn<?, ?> returnIDCol;

    @FXML
    private TextField returnIDIncrement;

    @FXML
    private TableColumn<?, ?> returnInvoiceCol;

    @FXML
    private JFXComboBox<?> returnInvoicePurchasing;

    @FXML
    private JFXRadioButton returnIsBuy;

    @FXML
    private JFXRadioButton returnIsSell;

    @FXML
    private TableColumn<?, ?> returnReasonCol;

    @FXML
    private TableView<?> returnTable;

    @FXML
    private TableColumn<?, ?> returnTotalPriceCol;

    @FXML
    private TableColumn<?, ?> returnTotalStockCol;

    @FXML
    private TableColumn<?, ?> returnTypeCol;

    @FXML
    private TextArea reuturnReasonField;

    @FXML
    private JFXButton searchStockButton;

    @FXML
    void addReturnButton(ActionEvent event) {

    }

    @FXML
    void editReturnButton(ActionEvent event) {

    }

    @FXML
    void removeReturnButton(ActionEvent event) {

    }

    @FXML
    void resetReturnButton(ActionEvent event) {

    }
}
