package com.lestarieragemilang.app.desktop.Controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.lestarieragemilang.app.desktop.Utilities.Redirect;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Dashboard {

    @FXML
    private AnchorPane setScene;


    @FXML
    void kategoriTab(ActionEvent event) {

    }

    @FXML
    void pelangganTab(ActionEvent event) {

    }

    @FXML
    void pembelianTab(ActionEvent event) {

    }

    @FXML
    void penerimaanTab(ActionEvent event) {

    }

    @FXML
    void penjualanTab(ActionEvent event) {

    }

    @FXML
    void stokBesiTab(ActionEvent event) {

    }

    @FXML
    void supplierTab(ActionEvent event) {

    }

    @FXML
    public void initialize() throws IOException {
        Redirect.page("data", setScene);
    }

    @FXML
    void isExitApp(MouseEvent event) {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
                JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @FXML
    void setSceneData(MouseEvent event) throws IOException {
        Redirect.page("data", setScene);
    }

}
