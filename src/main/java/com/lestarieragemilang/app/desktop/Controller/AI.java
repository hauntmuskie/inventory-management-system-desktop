package com.lestarieragemilang.app.desktop.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.application.Platform;
import com.lestarieragemilang.app.desktop.AI.CohereAI;
import com.lestarieragemilang.app.desktop.Dao.StockDao;
import com.lestarieragemilang.app.desktop.Entities.Stock;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AI extends CohereAI {

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
        // Disable the button to prevent multiple clicks
        aiGenerateButton.setDisable(true);
        aiResponse.setText("Generating report...");

        CompletableFuture.supplyAsync(() -> {
            StringBuilder reportContext = new StringBuilder();
            try {
                // Gather stock information
                List<Stock> stocks = stockDao.getAllStocks();
                reportContext.append("Total stocks: ").append(stocks.size()).append("\n")
                        .append("Total categories: ")
                        .append(stocks.stream().map(Stock::getStockOnCategoryID).distinct().count()).append("\n)")
                        .append("Total quantity: ")
                        .append(stocks.stream().map(Stock::getQuantity).mapToInt(Integer::parseInt).sum()).append("\n")
                        .append("Total purchase price: ")
                        .append(stocks.stream().map(Stock::getPurchasePrice).mapToDouble(Double::parseDouble).sum())
                        .append("\n")
                        .append("Total selling price: ")
                        .append(stocks.stream().map(Stock::getPurchaseSell).mapToDouble(Double::parseDouble).sum())
                        .append("\n")
                        .append("Average purchase price: ")
                        .append(stocks.stream().map(Stock::getPurchasePrice).mapToDouble(Double::parseDouble).average()
                                .orElse(0))
                        .append("\n")
                        .append("Average selling price: ")
                        .append(stocks.stream().map(Stock::getPurchaseSell).mapToDouble(Double::parseDouble).average()
                                .orElse(0))
                        .append("\n")
                        .append("Most expensive stock: ")
                        .append(stocks.stream()
                                .max((a, b) -> Double.compare(Double.parseDouble(a.getPurchasePrice()),
                                        Double.parseDouble(b.getPurchasePrice())))
                                .map(Stock::getStockID).orElse(0))
                        .append("\n")
                        .append("Cheapest stock: ")
                        .append(stocks.stream()
                                .min((a, b) -> Double.compare(Double.parseDouble(a.getPurchasePrice()),
                                        Double.parseDouble(b.getPurchasePrice())))
                                .map(Stock::getStockID).orElse(0))
                        .append("\n")
                        .append("Most profitable stock: ")
                        .append(stocks.stream()
                                .max((a, b) -> Double.compare(Double.parseDouble(a.getPurchaseSell()),
                                        Double.parseDouble(b.getPurchaseSell())))
                                .map(Stock::getStockID).orElse(0))
                        .append("\n")
                        .append("Least profitable stock: ")
                        .append(stocks.stream()
                                .min((a, b) -> Double.compare(Double.parseDouble(a.getPurchaseSell()),
                                        Double.parseDouble(b.getPurchaseSell())))
                                .map(Stock::getStockID).orElse(0))
                        .append("\n")
                        .append("Most sold stock: ")
                        .append(stocks.stream()
                                .max((a, b) -> Integer.compare(Integer.parseInt(a.getQuantity()),
                                        Integer.parseInt(b.getQuantity())))
                                .map(Stock::getStockID).orElse(0))
                        .append("\n")
                        .append("Least sold stock: ")
                        .append(stocks.stream()
                                .min((a, b) -> Integer.compare(Integer.parseInt(a.getQuantity()),
                                        Integer.parseInt(b.getQuantity())))
                                .map(Stock::getStockID).orElse(0))
                        .append("\n");
                // Generate AI report
                String prompt = "Hasilkan ringkasan laporan bisnis yang komprehensif berdasarkan data berikut dan berikan kesimpulan dengan detail:\n"
                        + reportContext.toString();
                return CohereAI.getChatResponse(prompt);
            } catch (Exception e) {
                return "Gagal membuat laporan: " + e.getMessage();
            }
        }).thenAccept(responseText -> {
            // Update UI on JavaFX Application Thread
            Platform.runLater(() -> {
                aiResponse.setText(responseText);
                aiGenerateButton.setDisable(false);
            });
        }).exceptionally(e -> {
            // Handle any exceptions in the async process
            Platform.runLater(() -> {
                aiResponse.setText("An error occurred: " + e.getMessage());
                aiGenerateButton.setDisable(false);
            });
            return null;
        });
    }
}