package com.lestarieragemilang.app.desktop.Utilities;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class Redirect {

  public static void page(String page, AnchorPane anchorPane) throws IOException {
    if (anchorPane == null) {
      throw new IllegalArgumentException("anchorPane cannot be null");
    }

    URL resource = Redirect.class.getResource("/com/lestarieragemilang/app/desktop/" + page + ".fxml");
    if (resource == null) {
      throw new IOException("Resource not found: " + page + ".fxml");
    }

    FXMLLoader loader = new FXMLLoader(resource);
    Parent root = loader.load();

    anchorPane.getChildren().clear();
    anchorPane.getChildren().add(root);
  }

}
