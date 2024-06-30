package com.lestarieragemilang.app.desktop.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.application.Platform;
import com.lestarieragemilang.app.desktop.AI.Gemini; // Use Gemini instead of CohereAI
import com.lestarieragemilang.app.desktop.Dao.StockDao;
import com.lestarieragemilang.app.desktop.Entities.Stock;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AI extends Gemini {
        @FXML
        private JFXButton aiGenerateButton;

        @FXML
        private Label aiResponse;

        private StockDao stockDao = new StockDao();

        @FXML
        private void initialize() {
        }

        @FXML
        void aiGenerateReportButton(ActionEvent event) {
                aiGenerateButton.setDisable(true);
                aiResponse.setText("Menghasilkan laporan...");
                CompletableFuture.supplyAsync(() -> {
                        StringBuilder reportContext = new StringBuilder();
                        try {
                                List<Stock> stocks = stockDao.getAllStocks();
                                reportContext.append("Total stocks: ").append(stocks.size()).append("\n");
                                for (Stock stock : stocks) {
                                        reportContext.append("Purchase Price: ").append(stock.getPurchasePrice())
                                                        .append("\n");
                                        reportContext.append("Selling Price: ").append(stock.getPurchaseSell())
                                                        .append("\n");
                                }

                                String prompt = "Hasilkan laporan bisnis yang komprehensif berdasarkan data berikut dan berikan kesimpulan dengan detail. (Avoid Table):\n"
                                                + reportContext.toString();
                                String jsonResponse = Gemini.sendRequest(prompt);
                                return Gemini.processResponse(jsonResponse);
                        } catch (Exception e) {
                                return "Failed to generate report: " + e.getMessage();
                        }
                }).thenAccept(responseText -> {
                        Platform.runLater(() -> {
                                aiResponse.setText(responseText);
                                aiGenerateButton.setDisable(false);
                        });
                }).exceptionally(e -> {
                        Platform.runLater(() -> {
                                aiResponse.setText("An error occurred: " + e.getMessage());
                                aiGenerateButton.setDisable(false);
                        });
                        return null;
                });
        }
}