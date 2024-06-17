package com.lestarieragemilang.app.desktop.Configurations.ReportConfiguration;

import javax.swing.JOptionPane;

import com.lestarieragemilang.app.desktop.Configurations.DatabaseConfiguration;

import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class JasperLoader {

  DatabaseConfiguration db = new DatabaseConfiguration();
  public void showJasperReport(String reportFile, MouseEvent event) {
    try {
      // Load the Jasper report
      JasperReport jasperReport = (JasperReport) JRLoader
          .loadObjectFromFile(reportFile);

      // Fill the report with data
      JasperPrint jasperPrint = JasperFillManager.fillReport(
          jasperReport, null, db.getConnection());

      // Show the report in a viewer
      final JasperViewer viewer = new JasperViewer(jasperPrint, false);
      Platform.runLater(() -> {
        viewer.setVisible(true);
      });

    } catch (JRException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}
