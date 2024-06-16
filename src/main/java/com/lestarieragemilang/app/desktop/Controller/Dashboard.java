package com.lestarieragemilang.app.desktop.Controller;

import java.io.IOException;
import java.util.Optional;

import com.lestarieragemilang.app.desktop.Utilities.Redirect;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Dashboard {

    @FXML
    private AnchorPane setScene;

    @FXML
    public void initialize() throws IOException {
        Redirect.page("stock", setScene);
    }

    @FXML
    void isExitApp(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    void setSceneStock(MouseEvent event) throws IOException {
        Redirect.page("stock", setScene);
    }

    @FXML
    void setSceneSupplier(MouseEvent event) throws IOException {
        Redirect.page("supplier", setScene);
    }

    @FXML
    void setSceneCategory(MouseEvent event) throws IOException {
        Redirect.page("category", setScene);
    }

    @FXML
    void setSceneCustomer(MouseEvent event) throws IOException {
        Redirect.page("customer", setScene);
    }

    @FXML
    void setSceneTransactions(MouseEvent event) throws IOException {
        Redirect.page("transactions", setScene);
    }

    @FXML
    void setSceneReturn(MouseEvent event) throws IOException {
        Redirect.page("return", setScene);
    }

    @FXML
    void setSceneReport(MouseEvent event) throws IOException {
        Redirect.page("laporan", setScene);
    }

}
