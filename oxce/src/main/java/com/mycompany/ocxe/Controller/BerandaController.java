package com.mycompany.ocxe.Controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BerandaController {

    @FXML
    private Button btnDestinasi;

    @FXML
    private Button btnLihatTiket;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnPesan;

    @FXML
    private ImageView imageSlider;

    private int currentImageIndex = 0;
    private final String[] images = {
        "/images/image1.JPG",
        "/images/image2.JPG",
        "/images/image3.JPG"
    };

    @FXML
    private void initialize() {
        // Set the first image in the slider
        if (images.length > 0) {
            imageSlider.setImage(new Image(images[currentImageIndex]));
            startImageSlider();
        }
    }

    private void startImageSlider() {
        Thread sliderThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000); // Change image every 3 seconds
                    currentImageIndex = (currentImageIndex + 1) % images.length;
                    javafx.application.Platform.runLater(() -> 
                        imageSlider.setImage(new Image(images[currentImageIndex]))
                    );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        sliderThread.setDaemon(true);
        sliderThread.start();
    }

    @FXML
    private void handleLogOutAction(ActionEvent event) {
        loadScene("/fxml/login.fxml", "Login", btnLogOut);
    }

    @FXML
    private void handleLihatDestinasiAction(ActionEvent event) {
        loadScene("/fxml/Destinasi.fxml", "Destinasi", btnDestinasi);
    }

    @FXML
    private void handleLihatTiketAction(ActionEvent event) {
        loadScene("/fxml/LihatTiket.fxml", "Lihat Tiket", btnLihatTiket);
    }

    @FXML
    private void handlePesanSekarangAction(ActionEvent event) {
        loadScene("/fxml/PesanTiket.fxml", "Pesan Tiket", btnPesan);
    }

    private void loadScene(String fxmlPath, String title, Button sourceButton) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) sourceButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading " + title + ": " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.show();
    }
}
