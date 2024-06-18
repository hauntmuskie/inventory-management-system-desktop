package com.lestarieragemilang.app.desktop.Configurations.ReportConfiguration;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lestarieragemilang.app.desktop.Configurations.DatabaseConfiguration;

public class JasperLoader extends DatabaseConfiguration {

  private static final Logger LOGGER = Logger.getLogger(JasperLoader.class.getName());

  public void showJasperReport(String reportFile, MouseEvent event) {
    try {
      JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile);
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), getConnection());

      final JasperViewer viewer = new JasperViewer(jasperPrint, false);
      Platform.runLater(() -> viewer.setVisible(true));

    } catch (JRException e) {
      LOGGER.log(Level.SEVERE, "Error loading Jasper Report", e);
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText(null);
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
  }
}