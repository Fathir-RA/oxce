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
import javafx.event.ActionEvent;
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
    private Button btnDestinasi;
    
    @FXML
    private Button btnBeranda;
    
    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnPesan;
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
    private void handleLihatDestinasiAction(ActionEvent event) {
        // Tindakan ketika link Lihat Destinasi diklik
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Destinasi.fxml")); // Ganti dengan path yang sesuai
            Parent destinasiRoot = loader.load();
            Scene destinasiScene = new Scene(destinasiRoot);

            Stage currentStage = (Stage) btnDestinasi.getScene().getWindow();
            currentStage.setScene(destinasiScene);
            currentStage.setTitle("Destinasi");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading Destinasi: " + e.getMessage());
        }
    }

    @FXML
    private void handleBerandaAction(ActionEvent event) {
        // Tindakan ketika link Lihat Tiket Saya diklik
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Beranda.fxml")); // Ganti dengan path yang sesuai
            Parent lihatTiketRoot = loader.load();
            Scene lihatTiketScene = new Scene(lihatTiketRoot);

            Stage currentStage = (Stage) btnBeranda.getScene().getWindow();
            currentStage.setScene(lihatTiketScene);
            currentStage.setTitle("Lihat Tiket");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading Lihat Tiket: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogOutAction(ActionEvent event) {
        // Tindakan ketika tombol Pesan Sekarang diklik
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml")); // Ganti dengan path yang sesuai
            Parent pesanTiketRoot = loader.load();
            Scene pesanTiketScene = new Scene(pesanTiketRoot);

            Stage currentStage = (Stage) btnLogOut.getScene().getWindow();
            currentStage.setScene(pesanTiketScene);
            currentStage.setTitle("Pesan Tiket");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading Pesan Tiket: " + e.getMessage());
        }
    }

    private void showError(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
