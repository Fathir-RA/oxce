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
    private TextField txtIdPenyelam;

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
    private Label lblOutput;

    private final PesanTiketDAO tiketDAO = new PesanTiketDAO();

    @FXML
    public void initialize() {
        // Tambahkan opsi ke ComboBox destinasi
        cbDestinasi.getItems().addAll(
            "1 - Pulau A (Rp 500.000)", 
            "2 - Pulau B (Rp 750.000)", 
            "3 - Pulau C (Rp 1.000.000)"
        );

        // Tambahkan opsi ke ComboBox waktu sesi
        cbWaktu.getItems().addAll("1 - Pagi", "2 - Siang", "3 - Sore");

        // Atur nilai default Spinner untuk jumlah tiket (1-4)
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 4, 1);
        spQuantity.setValueFactory(valueFactory);

        // Tambahkan event listener pada tombol Pesan
        btnPesan.setOnAction(event -> pesanTiket());

        // Tambahkan event listener pada tombol Kembali
        btnKembali.setOnAction(event -> navigateToBeranda());
    }

    private void pesanTiket() {
        try {
            // Ambil input dari form
            int idPenyelam = Integer.parseInt(txtIdPenyelam.getText().trim());
            String selectedDestinasi = cbDestinasi.getValue();
            LocalDate selectedDate = dateTanggal.getValue();
            String selectedWaktu = cbWaktu.getValue();
            int quantity = spQuantity.getValue();

            // Validasi input
            if (selectedDestinasi == null || selectedDate == null || selectedWaktu == null) {
                lblOutput.setText("Harap lengkapi semua input!");
                return;
            }

            // Konversi data
            int idDestinasi = Character.getNumericValue(selectedDestinasi.charAt(0));
            int waktu = Character.getNumericValue(selectedWaktu.charAt(0));
            Date tanggal = Date.valueOf(selectedDate);

            // Hitung harga berdasarkan destinasi
            int harga;
            switch (idDestinasi) {
                case 1:
                    harga = 500000;
                    break;
                case 2:
                    harga = 750000;
                    break;
                case 3:
                    harga = 1000000;
                    break;
                default:
                    lblOutput.setText("Destinasi tidak valid!");
                    return;
            }

            // Buat objek PesanTiket
            PesanTiket tiket = new PesanTiket(
                generateIdTiket(), 
                idPenyelam, 
                idDestinasi, 
                tanggal, 
                waktu, 
                quantity, 
                harga, 
                null
            );

            // Simpan ke database
            tiketDAO.insertPesanTiket(tiket);
            
            int totalHarga = harga * quantity;
            lblOutput.setText("Tiket berhasil dipesan! Total Harga: Rp " + totalHarga);

            // Reset form
            clearForm();

            // Pindah ke Beranda
            navigateToBeranda();

        } catch (NumberFormatException e) {
            lblOutput.setText("ID Penyelam harus berupa angka!");
        } catch (Exception e) {
            lblOutput.setText("Terjadi kesalahan: " + e.getMessage());
        }
    }

    private void navigateToBeranda() {
        try {
            // Muat file FXML beranda
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Beranda.fxml"));
            Parent root = loader.load();

            // Ganti scene dengan Beranda.fxml
            Stage stage = (Stage) btnKembali.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            lblOutput.setText("Gagal kembali ke Beranda: " + e.getMessage());
        }
    }

    private int generateIdTiket() {
        // ID acak untuk simulasi
        return (int) (Math.random() * 100000);
    }

    private void clearForm() {
        txtIdPenyelam.clear();
        cbDestinasi.setValue(null);
        dateTanggal.setValue(null);
        cbWaktu.setValue(null);
        spQuantity.getValueFactory().setValue(1);
    }
}
