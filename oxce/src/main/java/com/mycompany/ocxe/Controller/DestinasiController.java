package com.mycompany.ocxe.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class DestinasiController {
    
    @FXML
    private Button btnBeranda;
    
    @FXML
    private Button btnLihatTiket;
    
    @FXML
    private Button btnLogOut;
    
    // Method untuk handle button klik
    public void handleBerandaAction(ActionEvent event) throws IOException {
        // Muat dan tampilkan panel Beranda.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Beranda.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBeranda.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleLihatTiketAction(ActionEvent event) throws IOException {
        // Muat dan tampilkan panel LihatTiket.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LihatTiket.fxml")); // Ganti dengan path yang sesuai
        Parent lihatTiketRoot = loader.load();
        Scene lihatTiketScene = new Scene(lihatTiketRoot);

        Stage currentStage = (Stage) btnLihatTiket.getScene().getWindow();
        currentStage.setScene(lihatTiketScene);
        currentStage.setTitle("Lihat Tiket");
    }

    public void handleLogOutAction(ActionEvent event) throws IOException {
        // Muat dan tampilkan panel Login.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml")); // Ganti dengan path yang sesuai
        Parent loginRoot = loader.load();
        Scene loginScene = new Scene(loginRoot);

        Stage currentStage = (Stage) btnLogOut.getScene().getWindow();
        currentStage.setScene(loginScene);
        currentStage.setTitle("Login");
    }
}
