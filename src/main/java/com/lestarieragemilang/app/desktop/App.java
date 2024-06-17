package com.lestarieragemilang.app.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static final String LOGIN_FXML = "dashboard";
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 650;
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML(LOGIN_FXML), WIDTH, HEIGHT);
        // addStylesheets("style.css");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.centerOnScreen();
        // enableDrag(stage);
        stage.show();
    }

    @SuppressWarnings("unused")
    private void addStylesheets(String stylesheet) {
    scene.getStylesheets().add(getClass().getResource(stylesheet).toExternalForm());
    }

    @SuppressWarnings("unused")
    private void enableDrag(Stage stage) {
        final double[] xOffset = new double[1];
        final double[] yOffset = new double[1];
        final double dragThreshold = 50; // The area on top of the app where dragging is allowed

        scene.setOnMousePressed(event -> {
            if (event.getSceneY() <= dragThreshold) {
                xOffset[0] = event.getSceneX();
                yOffset[0] = event.getSceneY();
            }
        });

        scene.setOnMouseDragged(event -> {
            if (yOffset[0] <= dragThreshold) {
                double newX = event.getScreenX() - xOffset[0];
                double newY = event.getScreenY() - yOffset[0];
                if (newX >= 0 && newY >= 0) {
                    stage.setX(newX);
                    stage.setY(newY);
                }
            }
        });
    }

    static void setRoot(String fxml) throws IOException {
        if (fxmlExists(fxml)) {
            scene.setRoot(loadFXML(fxml));
        } else {
            LOGGER.log(Level.SEVERE, "FXML file does not exist: {0}", fxml);
            throw new IOException("FXML file does not exist: " + fxml);
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static boolean fxmlExists(String fxml) {
        URL resource = App.class.getResource(fxml + ".fxml");
        return resource != null;
    }

    public static void main(String[] args) {
        launch();
    }
}