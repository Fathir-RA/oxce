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
    private Hyperlink linkRegister;

    @FXML
    private TextField txtIdPenyelam;

    @FXML
    private PasswordField txtPassword;

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
            idPenyelamLogin = Integer.parseInt(penyelam.getIdPenyelam());

            // Tentukan tampilan berdasarkan role
            String role = penyelam.getRole();
            String fxmlFile;
            if ("penyelam".equalsIgnoreCase(role)) {
                fxmlFile = "/fxml/Beranda.fxml";
            } else if ("pendamping".equalsIgnoreCase(role)) {
                fxmlFile = "/fxml/BerandaAdmin.fxml";
            } else {
                showError("Role tidak valid!");
                return;
            }

            // Pindah ke tampilan yang sesuai
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                currentStage.setScene(scene);
                currentStage.setTitle("Beranda");
            } catch (IOException e) {
                e.printStackTrace();
                showError("Error loading tampilan: " + e.getMessage());
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
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
