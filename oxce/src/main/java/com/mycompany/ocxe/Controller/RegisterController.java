package com.mycompany.ocxe.Controller;

import com.mycompany.ocxe.DAO.PenyelamDAO;
import com.mycompany.ocxe.Model.Penyelam;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;

public class RegisterController {

    @FXML
    private Button btnDaftar; // Tombol Daftar
    @FXML
    private TextField idUserField; // Field ID Penyelam
    @FXML
    private TextField namaField; // Field Nama Lengkap
    @FXML
    private TextField umurField; // Field Umur
    @FXML
    private TextField alamatField; // Field Alamat
    @FXML
    private TextField noHpField; // Field No HP
    @FXML
    private PasswordField passwordField; // Field Password
    @FXML
    private Hyperlink linkLogin;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            // Ambil data dari field
            String idPenyelam = idUserField.getText();
            String nama = namaField.getText();
            int umur = Integer.parseInt(umurField.getText());
            String alamat = alamatField.getText();
            String noHp = noHpField.getText();
            String password = passwordField.getText();

            // Validasi input
            if (idPenyelam.isEmpty() || nama.isEmpty() || alamat.isEmpty() || noHp.isEmpty() || password.isEmpty()) {
                showError("Semua field harus diisi!");
                return;
            }

            // Tetapkan role secara otomatis
            String role = "penyelam";

            // Buat objek Penyelam dengan role
            Penyelam penyelam = new Penyelam(idPenyelam, nama, umur, alamat, password, noHp, role);

            // Simpan ke database
            PenyelamDAO penyelamDAO = new PenyelamDAO();
            penyelamDAO.insertPenyelam(penyelam);

            // Tampilkan pesan sukses
            showAlert("Pendaftaran Berhasil", "Akun penyelam Anda telah berhasil dibuat!");

            // Pindah ke halaman login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml")); // Ganti dengan path yang sesuai
            Parent loginRoot = loader.load();
            Scene loginScene = new Scene(loginRoot);

            Stage currentStage = (Stage) btnDaftar.getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading Login: " + e.getMessage());
        } catch (NumberFormatException e) {
            showError("Umur harus berupa angka!");
        }
    }
    
    @FXML
    private void handleLoginAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml")); // Sesuaikan path jika berbeda
            Parent loginRoot = loader.load();
            Scene loginScene = new Scene(loginRoot);

            Stage currentStage = (Stage) linkLogin.getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading Login: " + e.getMessage());
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
