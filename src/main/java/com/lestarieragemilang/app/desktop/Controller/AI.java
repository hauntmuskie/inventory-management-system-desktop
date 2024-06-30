package com.lestarieragemilang.app.desktop.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.lestarieragemilang.app.desktop.Api.Gemini;
import com.lestarieragemilang.app.desktop.Configurations.DatabaseConfiguration;
import com.lestarieragemilang.app.desktop.Dao.SteelShopDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AI extends Gemini {

        private static final Logger LOGGER = Logger.getLogger(AI.class.getName());
        private static final String REPORT_GENERATION_ERROR = "Gagal membuat laporan: ";
        private static final String REPORT_GENERATION_START = "Menghasilkan laporan...";
        private static final String ERROR_MESSAGE = "Terjadi kesalahan: ";

        @FXML
        private JFXButton aiGenerateButton;

        @FXML
        private Label aiResponse;

        @FXML
        private void initialize() {
        }

        @FXML
        void aiGenerateReportButton(ActionEvent event) {
                aiGenerateButton.setDisable(true);
                aiResponse.setText(REPORT_GENERATION_START);
                CompletableFuture.supplyAsync(() -> {
                        try (Connection connection = DatabaseConfiguration.getConnection()) {
                                SteelShopDao steelShopDao = new SteelShopDao(connection);
                                String businessData = steelShopDao.generateReport();
                                String prompt = generatePrompt(businessData);
                                String jsonResponse = Gemini.sendRequest(prompt);
                                return Gemini.processResponse(jsonResponse);
                        } catch (SQLException e) {
                                LOGGER.log(Level.SEVERE, REPORT_GENERATION_ERROR, e);
                                return REPORT_GENERATION_ERROR + e.getMessage();
                        } catch (Exception e) {
                                LOGGER.log(Level.SEVERE, ERROR_MESSAGE, e);
                                return ERROR_MESSAGE + e.getMessage();
                        }
                }).thenAccept(responseText -> {
                        Platform.runLater(() -> {
                                aiResponse.setText(responseText);
                                aiGenerateButton.setDisable(false);
                        });
                }).exceptionally(e -> {
                        LOGGER.log(Level.SEVERE, ERROR_MESSAGE, e);
                        Platform.runLater(() -> {
                                aiResponse.setText(ERROR_MESSAGE + e.getMessage());
                                aiGenerateButton.setDisable(false);
                        });
                        return null;
                });
        }

        private String generatePrompt(String businessData) {
                return "Harap analisis data bisnis berikut dan buat laporan yang komprehensif. Laporan tersebut harus mencakup analisis data secara rinci, termasuk tren, pola, dan outlier. Laporan ini juga harus mencakup ringkasan temuan-temuan utama dan rekomendasi untuk strategi bisnis masa depan. Hindari menggunakan tabel untuk menyajikan data, ganti steel shop dengna PT. LESTARI ERA GEMILANG. Ini datanya:\n"
                                + businessData;
        }
}
