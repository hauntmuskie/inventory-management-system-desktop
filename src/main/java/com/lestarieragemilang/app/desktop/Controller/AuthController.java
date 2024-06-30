package com.lestarieragemilang.app.desktop.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.lestarieragemilang.app.desktop.Dao.AuthDao;
import com.lestarieragemilang.app.desktop.Utilities.Redirect;

public class AuthController {
    @FXML
    private Label greetingText;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label localTimeApp;

    @FXML
    private StackPane authStackPane;
    @FXML
    private VBox loginView;
    @FXML
    private VBox registerView;

    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;

    @FXML
    private TextField registerEmail;
    @FXML
    private TextField registerUsername;
    @FXML
    private PasswordField registerPassword;
    @FXML
    private PasswordField confirmPassword;

    @FXML
    private ListView<String> profileListView;

    private ObservableList<String> profiles = FXCollections.observableArrayList();

    private static final NavigableMap<Integer, String> GREETINGS = new TreeMap<>();

    public void populateProfiles() {
        AuthDao authDao = new AuthDao();
        List<String> profiles = authDao.fetchProfiles();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (String profile : profiles) {
            // Split using the "||" delimiter
            String[] parts = profile.split("\\|\\|");
            if (parts.length < 2) {
                System.err.println("Invalid profile format: " + profile);
                continue;
            }
            String email = parts[0].trim();
            String username = parts[1].trim();
            // Format as "[email] - [username]"
            observableList.add("[" + email + "] - [" + username + "]");
        }
        profileListView.setItems(observableList);
    }

    static {
        GREETINGS.put(0, "Selamat pagi, Admin!");
        GREETINGS.put(12, "Selamat siang, Admin!");
        GREETINGS.put(15, "Selamat sore, Admin!");
        GREETINGS.put(18, "Selamat malam, Admin!");
    }

    private final AuthDao authDao = new AuthDao();

    public void initialize() {
        populateProfiles();
    }
    
    @FXML
    private void loginToApp() throws IOException {
        if (authDao.loginRepo(loginUsername.getText(), loginPassword.getText())) {
            Redirect.page("dashboard", anchorPane);
        } else {
            Alert confirmationDialog = new Alert(Alert.AlertType.ERROR);
            confirmationDialog.getDialogPane().setPrefSize(450, 250);

            confirmationDialog.setTitle("Login Gagal");
            confirmationDialog.setHeaderText("Username Atau Password Anda Salah!!!...");

            confirmationDialog.getButtonTypes().setAll(ButtonType.YES);
            confirmationDialog.showAndWait();
        }
    }

    @FXML
    private void registerAdmin() {
        if (!registerPassword.getText().equals(confirmPassword.getText())) {
            System.out.println("Passwords do not match!");
            return;
        }
        System.out.println("Attempting to register: " + registerUsername.getText());
        if (authDao.registerRepo(registerUsername.getText(), registerEmail.getText(), registerUsername.getText(),
                registerPassword.getText())) {
            profiles.add(registerUsername.getText());
            // refresh
            profileListView.setItems(profiles); // Set the items again to trigger refresh
            Alert confirmationDialog = new Alert(Alert.AlertType.INFORMATION);
            confirmationDialog.getDialogPane().setPrefSize(450, 250);
    
            confirmationDialog.setTitle("Register Berhasil");
            confirmationDialog.setHeaderText(" Anda berhasil mendaftar!!!...");
    
            confirmationDialog.getButtonTypes().setAll(ButtonType.YES);
            confirmationDialog.showAndWait();
        } else {
            Alert confirmationDialog = new Alert(Alert.AlertType.ERROR);
            confirmationDialog.getDialogPane().setPrefSize(450, 250);
    
            confirmationDialog.setTitle("Register Gagal");
            confirmationDialog.setHeaderText("Gagal mendaftar!!!...");
    
            confirmationDialog.getButtonTypes().setAll(ButtonType.YES);
            confirmationDialog.showAndWait();
        }
    }

    @FXML
    private void showRegisterView() {
        loginView.setVisible(false);
        registerView.setVisible(true);
    }

    @FXML
    private void showLoginView() {
        registerView.setVisible(false);
        loginView.setVisible(true);
    }

    @FXML
    public void exitApp() {
        System.exit(0);
    }
}
