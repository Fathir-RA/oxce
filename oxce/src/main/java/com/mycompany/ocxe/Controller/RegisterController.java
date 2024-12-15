package com.mycompany.ocxe.Controller;

import com.mycompany.ocxe.DAO.PenyelamDAO;
import com.mycompany.ocxe.Model.Penyelam;
import java.io.IOException;
import java.lang.reflect.Field;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class RegisterController {

    @FXML
    private Button btnDaftar; // Tombol Daftar
    @FXML
    private TextField idUserField; // Field ID User
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
    private void handleButtonAction(ActionEvent event) {
        // Ambil data dari field
        String idUser  = idUserField.getText();
        String nama = namaField.getText();
        int umur = Integer.parseInt(umurField.getText());
        String alamat = alamatField.getText();
        String noHp = noHpField.getText();
        String password = passwordField.getText();

        // Buat objek Penyelam
        Penyelam penyelam = new Penyelam(idUser , nama, umur, alamat, password, noHp);

        // Simpan ke database
        PenyelamDAO penyelamDAO = new PenyelamDAO();
        penyelamDAO.insertPenyelam(penyelam);

        // Tampilkan pesan sukses
        showAlert("Pendaftaran Berhasil", "Akun Anda telah berhasil dibuat!");

        // Pindah ke halaman login
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml")); // Ganti dengan path yang sesuai
            Parent loginRoot = loader.load();
            Scene loginScene = new Scene(loginRoot);

            Stage currentStage = (Stage) btnDaftar.getScene().getWindow();
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
