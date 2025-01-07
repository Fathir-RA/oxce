package com.mycompany.ocxe.Controller;

import com.mycompany.ocxe.DAO.DestinasiDAO;
import com.mycompany.ocxe.Model.Destinasi;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PulauPramukaController {

    @FXML
    private HBox floraContainer;

    @FXML
    private HBox faunaContainer;

    @FXML
    private Button btnKembali;

    private final DestinasiDAO destinasiDAO = new DestinasiDAO();

    @FXML
    public void initialize() {
        try {
            loadDestinasiByKategori("PulauPramuka-Flora", floraContainer);
            loadDestinasiByKategori("PulauPramuka-Fauna", faunaContainer);
        } catch (Exception e) {
            System.err.println("Error during initialization");
            e.printStackTrace();
        }
    }

    private void loadDestinasiByKategori(String kategori, HBox container) {
        List<Destinasi> destinasiList = destinasiDAO.getDestinasiByKategori(kategori);

        if (destinasiList == null || destinasiList.isEmpty()) {
            System.out.println("No destinations found for category: " + kategori);
            return;
        }

        for (Destinasi destinasi : destinasiList) {
            try {
                ImageView imageView = new ImageView();
                if (destinasi.getGambar() != null) {
                    imageView.setImage(new Image(new ByteArrayInputStream(destinasi.getGambar())));
                }
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                imageView.setPreserveRatio(true);

                Text namaText = new Text(destinasi.getNama());
                namaText.setStyle("-fx-font-size: 18px; -fx-text-alignment: center;");

                VBox itemBox = new VBox(imageView, namaText);
                itemBox.setSpacing(5);
                itemBox.setStyle("-fx-alignment: center;");

                container.getChildren().add(itemBox);
            } catch (Exception e) {
                System.err.println("Error loading destination: " + destinasi.getNama());
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handleKembaliAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Destinasi.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnKembali.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load Destinasi.fxml");
            e.printStackTrace();
        }
    }
}