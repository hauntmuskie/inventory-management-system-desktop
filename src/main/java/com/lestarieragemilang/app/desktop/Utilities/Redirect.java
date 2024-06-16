package com.lestarieragemilang.app.desktop.Utilities;

import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class Redirect {
  public static void page(String page, AnchorPane anchorPane) throws IOException {
    if (anchorPane == null) {
      throw new IllegalArgumentException("anchorPane cannot be null");
    }
  
    String path = "/com/lestarieragemilang/app/desktop/" + page + ".fxml";
  
    InputStream resourceAsStream = Redirect.class.getResourceAsStream(path);
  
    if (resourceAsStream == null) {
      throw new IOException("Resource not found: " + path);
    }
  
    FXMLLoader loader = new FXMLLoader();
    Parent root = loader.load(resourceAsStream);
  
    anchorPane.getChildren().setAll(root);
  }
  
}