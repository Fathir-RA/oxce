package com.mycompany.ocxe.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

public class DestinasiController {

    @FXML
    private Button btnPramuka;

    @FXML
    private Button btnSepa;

    @FXML
    private Button btnHarapan;

    @FXML
    private Button btnBeranda;

    @FXML
    private Button btnLihatTiket;

    @FXML
    private Button btnLogOut;

    @FXML
    private void initialize() {
        try {
            // Configure Pulau Pramuka Button

            // Configure Pulau Harapan Button
//            Image harapanImg = new Image(getClass().getResource("/images/PulauHarapan.JPG").toExternalForm());
//            ImageView harapanView = new ImageView(harapanImg);
//            harapanView.setFitWidth(80);
//            harapanView.setFitHeight(80);
//            btnHarapan.setGraphic(harapanView);
//            btnHarapan.setTooltip(new Tooltip("Pulau Harapan"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public void handlePramukaAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PulauPramuka.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnPramuka.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
      public void handleSepaAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PulauSepa.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnSepa.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
   

    public void handleHarapanAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PulauHarapan.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnHarapan.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void handleBerandaAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Beranda.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBeranda.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleLihatTiketAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LihatTiket.fxml"));
        Parent lihatTiketRoot = loader.load();
        Scene lihatTiketScene = new Scene(lihatTiketRoot);

        Stage currentStage = (Stage) btnLihatTiket.getScene().getWindow();
        currentStage.setScene(lihatTiketScene);
        currentStage.setTitle("Lihat Tiket");
    }

    public void handleLogOutAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent loginRoot = loader.load();
        Scene loginScene = new Scene(loginRoot);

        Stage currentStage = (Stage) btnLogOut.getScene().getWindow();
        currentStage.setScene(loginScene);
        currentStage.setTitle("Login");
    }
}
