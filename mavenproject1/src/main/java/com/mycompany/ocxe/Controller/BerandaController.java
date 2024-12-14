package com.mycompany.ocxe.Controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class BerandaController {

    @FXML
    private Hyperlink linkLogOut;

    @FXML
    private Hyperlink linkLihatDestinasi;

    @FXML
    private Hyperlink linkLihatTiketSaya;

    @FXML
    private Button btnPesan;

    @FXML
    private void handleLogOutAction(ActionEvent event) {
        // Tindakan ketika link Log Out diklik
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml")); // Ganti dengan path yang sesuai
            Parent loginRoot = loader.load();
            Scene loginScene = new Scene(loginRoot);

            Stage currentStage = (Stage) linkLogOut.getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading Login: " + e.getMessage());
        }
    }

    @FXML
    private void handleLihatDestinasiAction(ActionEvent event) {
        // Tindakan ketika link Lihat Destinasi diklik
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Destinasi.fxml")); // Ganti dengan path yang sesuai
            Parent destinasiRoot = loader.load();
            Scene destinasiScene = new Scene(destinasiRoot);

            Stage currentStage = (Stage) linkLihatDestinasi.getScene().getWindow();
            currentStage.setScene(destinasiScene);
            currentStage.setTitle("Destinasi");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading Destinasi: " + e.getMessage());
        }
    }

    @FXML
    private void handleLihatTiketSayaAction(ActionEvent event) {
        // Tindakan ketika link Lihat Tiket Saya diklik
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LihatTiket.fxml")); // Ganti dengan path yang sesuai
            Parent lihatTiketRoot = loader.load();
            Scene lihatTiketScene = new Scene(lihatTiketRoot);

            Stage currentStage = (Stage) linkLihatTiketSaya.getScene().getWindow();
            currentStage.setScene(lihatTiketScene);
            currentStage.setTitle("Lihat Tiket");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading Lihat Tiket: " + e.getMessage());
        }
    }

    @FXML
    private void handlePesanSekarangAction(ActionEvent event) {
        // Tindakan ketika tombol Pesan Sekarang diklik
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PesanTiket.fxml")); // Ganti dengan path yang sesuai
            Parent pesanTiketRoot = loader.load();
            Scene pesanTiketScene = new Scene(pesanTiketRoot);

            Stage currentStage = (Stage) btnPesan.getScene().getWindow();
            currentStage.setScene(pesanTiketScene);
            currentStage.setTitle("Pesan Tiket");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading Pesan Tiket: " + e.getMessage());
        }
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informasi");
        alert.setContentText(message);
        alert.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.show();
    }
}