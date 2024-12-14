package com.mycompany.ocxe.Controller;

import com.mycompany.ocxe.DAO.PenyelamDAO;
import com.mycompany.ocxe.Model.Penyelam;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

public class LoginController {

    public static int idPenyelamLogin; // Variabel untuk menyimpan ID penyelam yang saat ini sedang login

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink linkRegister; // Tambahkan hyperlink untuk register

    @FXML
    private TextField txtIdPenyelam; // TextField untuk ID Penyelam
    @FXML
    private PasswordField txtPassword; // PasswordField untuk Password

    private PenyelamDAO penyelamDAO = new PenyelamDAO(); // Inisialisasi DAO

    @FXML
    private void handleButtonAction(ActionEvent event) {
        // Tindakan ketika tombol login diklik
        String idPenyelam = txtIdPenyelam.getText();
        String password = txtPassword.getText();

        // Verifikasi kredensial
        Penyelam penyelam = penyelamDAO.getPenyelam(idPenyelam);
        if (penyelam != null && penyelam.getPassword().equals(password)) {
            // Jika kredensial valid, simpan ID penyelam yang saat ini sedang login
            try {
                idPenyelamLogin = Integer.parseInt(penyelam.getIdPenyelam());
            } catch (NumberFormatException e) {
                System.out.println("Error: ID penyelam tidak dapat diubah menjadi integer.");
            }

            // Pindah ke beranda
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/beranda.fxml"));
                Parent berandaRoot = loader.load();
                Scene beranda = new Scene(berandaRoot);

                Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                currentStage.setScene(beranda);
                currentStage.setTitle("Beranda");
            } catch (IOException e) {
                e.printStackTrace();
                showError("Error loading Beranda: " + e.getMessage());
            }
        } else {
            // Jika kredensial tidak valid, tampilkan pesan kesalahan
            showError("ID atau Password salah. Silakan coba lagi.");
        }
    }

    @FXML
    private void handleRegisterAction(ActionEvent event) {
        // Tindakan ketika link register diklik
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml")); // Ganti dengan path yang sesuai
            Parent registerRoot = loader.load();
            Scene registerScene = new Scene(registerRoot);

            Stage currentStage = (Stage) linkRegister.getScene().getWindow();
            currentStage.setScene(registerScene);
            currentStage.setTitle("Register");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading Register: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.show();
    }
}