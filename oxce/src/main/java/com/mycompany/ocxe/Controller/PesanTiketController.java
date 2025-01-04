package com.mycompany.ocxe.Controller;

import com.mycompany.ocxe.DAO.PesanTiketDAO;
import com.mycompany.ocxe.Model.PesanTiket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class PesanTiketController {

    @FXML
    private ComboBox<String> cbDestinasi;

    @FXML
    private DatePicker dateTanggal;

    @FXML
    private ComboBox<String> cbWaktu;

    @FXML
    private Spinner<Integer> spQuantity;

    @FXML
    private Button btnPesan;

    @FXML
    private Button btnKembali;

    @FXML
    private Label lblTotalHarga;

    @FXML
    private Label lblSisaTiket;

    private final PesanTiketDAO tiketDAO = new PesanTiketDAO();

    @FXML
    public void initialize() {
        cbDestinasi.getItems().addAll(
            "Pulau A - Rp 500.000", 
            "Pulau B - Rp 750.000", 
            "Pulau C - Rp 1.000.000"
        );
        cbWaktu.getItems().addAll("Pagi", "Siang", "Sore");

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1);
        spQuantity.setValueFactory(valueFactory);

        cbDestinasi.setOnAction(event -> updateTotalHarga());
        spQuantity.valueProperty().addListener((obs, oldValue, newValue) -> updateTotalHarga());

        btnPesan.setOnAction(event -> pesanTiket());
        btnKembali.setOnAction(event -> navigateToBeranda());
        
        cbDestinasi.setOnAction(e -> updateSisaTiket());
        cbWaktu.setOnAction(e -> updateSisaTiket());
    }

    private void pesanTiket() {
        try {
            int idPenyelam = LoginController.idPenyelamLogin;
            int idDestinasi = cbDestinasi.getSelectionModel().getSelectedIndex() + 1;
            LocalDate tanggal = dateTanggal.getValue();
            int waktu = cbWaktu.getSelectionModel().getSelectedIndex() + 1;
            int quantity = spQuantity.getValue();

            if (idDestinasi == 0 || tanggal == null || waktu == 0) {
                showError("Harap lengkapi semua input!");
                return;
            }

            int harga = getHargaByIdDestinasi(idDestinasi);
            int idTiket = generateIdTiket();

            int sisaTiket = tiketDAO.getRemainingTickets(idDestinasi, Date.valueOf(tanggal), waktu);
            if (quantity > sisaTiket) {
                showError("Tiket tidak dapat dipesan. Sisa tiket yang tersedia: " + sisaTiket);
                return;
            }

            PesanTiket tiket = new PesanTiket(idTiket, idPenyelam, idDestinasi, Date.valueOf(tanggal), waktu, quantity, harga, null);
            tiketDAO.insertPesanTiket(tiket);

            int totalHarga = harga * quantity;
            showSuccess("Tiket berhasil dipesan! Total Harga: Rp " + totalHarga);
            clearForm();
            updateSisaTiket();
        } catch (Exception e) {
            showError("Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateSisaTiket() {
        try {
            int idDestinasi = cbDestinasi.getSelectionModel().getSelectedIndex() + 1;
            LocalDate tanggal = dateTanggal.getValue();
            int waktu = cbWaktu.getSelectionModel().getSelectedIndex() + 1;

            if (idDestinasi != 0 && tanggal != null && waktu != 0) {
                int sisaTiket = tiketDAO.getRemainingTickets(idDestinasi, Date.valueOf(tanggal), waktu);
                lblSisaTiket.setText("Sisa Tiket: " + sisaTiket);
            } else {
                lblSisaTiket.setText("Sisa Tiket: -");
            }
        } catch (Exception e) {
            lblSisaTiket.setText("Sisa Tiket: Error");
            e.printStackTrace();
        }
    }

    private void updateTotalHarga() {
        try {
            int idDestinasi = cbDestinasi.getSelectionModel().getSelectedIndex() + 1;
            int harga = getHargaByIdDestinasi(idDestinasi);
            int quantity = spQuantity.getValue();
            int totalHarga = harga * quantity;
            lblTotalHarga.setText("Rp " + totalHarga);
        } catch (Exception e) {
            lblTotalHarga.setText("Rp 0");
        }
    }

    private int getHargaByIdDestinasi(int idDestinasi) {
        switch (idDestinasi) {
            case 1: return 500000;
            case 2: return 750000;
            case 3: return 1000000;
            default: return 0;
        }
    }

    private void clearForm() {
        cbDestinasi.setValue(null);
        dateTanggal.setValue(null);
        cbWaktu.setValue(null);
        spQuantity.getValueFactory().setValue(1);
        lblTotalHarga.setText("Rp 0");
    }

    private void navigateToBeranda() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Beranda.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnKembali.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showError("Gagal kembali ke Beranda: " + e.getMessage());
        }
    }

    private int generateIdTiket() {
        // ID acak untuk simulasi
        return (int) (Math.random() * 100000);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukses");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
