package com.lestarieragemilang.app.desktop.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.lestarieragemilang.app.desktop.Utilities.Redirect;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Dashboard {

    @FXML
    private AnchorPane setScene;

    @FXML
    private Label localTimeApp;

    @FXML
    public void initialize() throws IOException {
        Redirect.page("stock", setScene);
        new FadeIn(setScene).play();
        showDateAndTime();
    }

    void showDateAndTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy '['HH:mm:ss']'", new Locale.Builder().setLanguageTag("id").build());

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalDateTime currentTime = LocalDateTime.now();
            localTimeApp.setText(currentTime.format(formatter));
        }),
                new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    @FXML
    void isExitApp(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            new FadeOut(setScene).play();
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(e -> System.exit(0));
            delay.play();
        }
    }

    @FXML
    void setSceneStock(MouseEvent event) throws IOException {
        Redirect.page("stock", setScene);
        new FadeIn(setScene).play();
    }

    @FXML
    void setSceneSupplier(MouseEvent event) throws IOException {
        Redirect.page("supplier", setScene);
        new FadeIn(setScene).play();
    }

    @FXML
    void setSceneCategory(MouseEvent event) throws IOException {
        Redirect.page("category", setScene);
        new FadeIn(setScene).play();
    }

    @FXML
    void setSceneCustomer(MouseEvent event) throws IOException {
        Redirect.page("customer", setScene);
        new FadeIn(setScene).play();
    }

    @FXML
    void setSceneTransactions(MouseEvent event) throws IOException {
        Redirect.page("transactions", setScene);
        new FadeIn(setScene).play();
    }

    @FXML
    void setSceneReturn(MouseEvent event) throws IOException {
        Redirect.page("return", setScene);
        new FadeIn(setScene).play();
    }

    @FXML
    void setSceneReport(MouseEvent event) throws IOException {
        Redirect.page("laporan", setScene);
        new FadeIn(setScene).play();
    }

}