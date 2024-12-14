package com.mycompany.ocxe.Controller;

import com.mycompany.ocxe.DAO.PesanTiketDAO;
import com.mycompany.ocxe.Model.PesanTiket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javafx.scene.control.Label;

public class LihatTiketController {

    @FXML
    private TableView<PesanTiket> tiketTable;

    @FXML
    private TableColumn<PesanTiket, Integer> idTiketColumn;

    @FXML
    private TableColumn<PesanTiket, Date> tanggalColumn;

    @FXML
    private TableColumn<PesanTiket, String> idDestinasiColumn;

    @FXML
    private TableColumn<PesanTiket, String> waktuColumn;

    @FXML
    private TableColumn<PesanTiket, Integer> quantityColumn;

    @FXML
    private TableColumn<PesanTiket, Double> hargaColumn;

    @FXML
    private TableColumn<PesanTiket, Double> totalHargaColumn;

    @FXML
    private Button kembaliButton;

    @FXML
    private void initialize() {
        // Inisialisasi kolom dengan PropertyValueFactory
        idTiketColumn.setCellValueFactory(new PropertyValueFactory<>("idTiket"));
        tanggalColumn.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        idDestinasiColumn.setCellValueFactory(new PropertyValueFactory<>("idDestinasi"));
        waktuColumn.setCellValueFactory(new PropertyValueFactory<>("waktu"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));
        totalHargaColumn.setCellValueFactory(new PropertyValueFactory<>("totalHarga"));

        // Tampilkan data tiket
        PesanTiketDAO tiketDAO = new PesanTiketDAO();
        List<PesanTiket> tiketList = tiketDAO.getTiketByPenyelam(LoginController.idPenyelamLogin);
        if (tiketList.isEmpty()) {
            Label label = new Label("Anda belum memesan tiket");
            label.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
            tiketTable.setPlaceholder(label);
        } else {
            tiketTable.getItems().addAll(tiketList);
        }
    }

    @FXML
    private void handleKembaliAction() {
        try {
            // Muat file FXML Home
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Beranda.fxml"));
            Parent root = loader.load();

            // Ganti scene ke Home.fxml
            Stage stage = (Stage) kembaliButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error kembali ke Home: " + e.getMessage());
        }
    }
}
