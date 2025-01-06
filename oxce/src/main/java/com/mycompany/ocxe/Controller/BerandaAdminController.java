package com.mycompany.ocxe.Controller;

import com.mycompany.ocxe.DAO.PenyelamDAO;
import com.mycompany.ocxe.DAO.PesanTiketDAO;
import com.mycompany.ocxe.Model.Penyelam;
import com.mycompany.ocxe.Model.PesanTiket;
import com.mycompany.ocxe.DAO.DestinasiDAO;
import com.mycompany.ocxe.Model.Destinasi;
import java.io.ByteArrayInputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class BerandaAdminController {

    @FXML
    private Button btnLogOut;
    @FXML
    private TextField idPendampingField; // Field ID Pendamping
    @FXML
    private TextField namaField; // Field Nama
    @FXML
    private TextField umurField; // Field Umur
    @FXML
    private TextField alamatField; // Field Alamat
    @FXML
    private TextField noHpField; // Field No HP
    @FXML
    private PasswordField passwordField; // Field Password
    @FXML
    private Button btnDaftarPendamping; // Tombol Daftar Pendamping
    @FXML
    private TableView<PesanTiket> tiketTable;

    @FXML
    private TableColumn<PesanTiket, Integer> idTiketColumn;
    @FXML
    private TableColumn<PesanTiket, String> namaPenyelamColumn; // Kolom untuk Nama Penyelam
    @FXML
    private TableColumn<PesanTiket, Date> tanggalColumn;
    @FXML
    private TableColumn<PesanTiket, String> idDestinasiColumn;
    @FXML
    private TableColumn<PesanTiket, String> waktuColumn;
    @FXML
    private TableColumn<PesanTiket, Integer> quantityColumn;
    @FXML
    private TableColumn<PesanTiket, Double> totalHargaColumn;

    @FXML
    private Button btnHapusDestinasi; // Tambahkan ini untuk tombol reset

    @FXML
    private TextField namaDestinasiField, kategoriField, gambarField;

    @FXML
    private TableView<Destinasi> destinasiTable;

    @FXML
    private TableColumn<Destinasi, Integer> idColumn;
    @FXML
    private TableColumn<Destinasi, byte[]> namaColumn, kategoriColumn, gambarColumn;

    @FXML
    private Button btnTambah, btnPerbarui, btnHapus;
    
    private final PesanTiketDAO tiketDAO = new PesanTiketDAO();
    private final DestinasiDAO destinasiDAO = new DestinasiDAO();
    private ObservableList<Destinasi> destinasiList;

    @FXML
    private ComboBox<String> destinasiComboBox; // ComboBox untuk memilih destinasi
    @FXML
    private DatePicker tanggalDatePicker; // DatePicker untuk memilih tanggal
    
    @FXML
    private Label labelTiketTerjual;
    @FXML
    private Label labelPendapatan;
    @FXML
    private Label labelDestinasiPopuler;
    @FXML
    private Label labelDestinasiDetails;
    
    @FXML
    public void initialize() {
        // Mengisi ComboBox dengan daftar destinasi dari model
        List<Destinasi> destinasiOptions = Destinasi.getDestinasiOptions();
        destinasiOptions.forEach(destinasi -> destinasiComboBox.getItems().add(destinasi.getNama()));

        // Menghubungkan kolom dengan properti model
        idTiketColumn.setCellValueFactory(new PropertyValueFactory<>("idTiket"));
        namaPenyelamColumn.setCellValueFactory(new PropertyValueFactory<>("namaPenyelam"));
        tanggalColumn.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        idDestinasiColumn.setCellValueFactory(new PropertyValueFactory<>("idDestinasi"));
        waktuColumn.setCellValueFactory(new PropertyValueFactory<>("waktu"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalHargaColumn.setCellValueFactory(new PropertyValueFactory<>("totalHargaProperty"));
        
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idDestinasi"));
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("nama"));
        kategoriColumn.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        gambarColumn.setCellValueFactory(new PropertyValueFactory<>("gambar"));


        // Menggunakan converter untuk menampilkan waktu
        waktuColumn.setCellValueFactory(cellData -> {
            int waktu = cellData.getValue().getWaktu();
            String waktuString = getWaktuById(waktu);
            return new SimpleStringProperty(waktuString);
        });
        
        // Menggunakan converter untuk menampilkan destinasi
        idDestinasiColumn.setCellValueFactory(cellData -> {
            int idDestinasi = cellData.getValue().getIdDestinasi();
            String destinasi = getDestinasiById(idDestinasi);
            return new SimpleStringProperty(destinasi);
        });

        // Menggunakan ImageTableCell untuk kolom gambar
        gambarColumn.setCellFactory(column -> new ImageTableCell());
        gambarColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getGambar()));

        // Load data destinasi ke tabel dan ComboBox
        loadDestinasiData();
        informasiSingkat();

        // Menambahkan listener untuk tabel destinasi
        destinasiTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillDestinasiFields(newValue);
            }
        });

        // Menambahkan listener ke ComboBox untuk memperbarui data berdasarkan pilihan destinasi
        destinasiComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filterTiketDataByDestinasi(newValue);
            }
        });
    //Batas initialize   
    }
    
    private void informasiSingkat() {
        // Ambil statistik dari DAO
        Map<String, Object> statistik = tiketDAO.getStatisik();

        // Update label berdasarkan data statistik
        labelTiketTerjual.setText("Total Tiket Terjual: " + statistik.get("totalTiketTerjual"));
        labelPendapatan.setText("Total Pendapatan: Rp " + statistik.get("totalPendapatan"));
        labelDestinasiPopuler.setText("Destinasi Populer: " + statistik.get("destinasiPopuler"));

        // Menampilkan informasi detail untuk setiap destinasi
        List<Map<String, Object>> destinasiDetails = (List<Map<String, Object>>) statistik.get("destinasiDetails");
        StringBuilder details = new StringBuilder("Rangking Destinasi Populer:\n");

        for (int i = 0; i < destinasiDetails.size(); i++) {
            Map<String, Object> destinasi = destinasiDetails.get(i);
            details.append(i + 1).append(". ").append(destinasi.get("destinasi")).append(" - Tiket Terjual: ")
                .append(destinasi.get("totalTiket")).append(", Pendapatan: Rp ").append(destinasi.get("totalPendapatan")).append("\n");
        }

        // Update labelDestinasiDetails dengan hasil ranking destinasi
        labelDestinasiDetails.setText(details.toString());
    }


    
        public class ImageTableCell extends TableCell<Destinasi, byte[]> {
        @Override
        protected void updateItem(byte[] item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                Image image = new Image(new ByteArrayInputStream(item));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100); // Set width
                imageView.setFitHeight(100); // Set height
                setGraphic(imageView);
            }
        }
    }

    private void fillDestinasiFields(Destinasi destinasi) {
        namaDestinasiField.setText(destinasi.getNama());
        kategoriField.setText(destinasi.getKategori());
        gambarField.setText(destinasi.getGambar() != null ? "Gambar tersedia" : "Tidak ada gambar");
    }

    @FXML
    private void handleTambahDestinasi(ActionEvent event) {
        String nama = namaDestinasiField.getText();
        String kategori = kategoriField.getText();
        String gambarPath = gambarField.getText();

        if (!nama.isEmpty() && !kategori.isEmpty() && !gambarPath.isEmpty()) {
            byte[] gambar = convertImageToByteArray(gambarPath);
            Destinasi destinasi = new Destinasi(0, nama, kategori, gambar);
            destinasiDAO.insertDestinasi(destinasi);
            loadDestinasiData();
            resetDestinasiForm();
        } else {
            showAlert("Form Tidak Lengkap", "Mohon isi semua field sebelum menambahkan destinasi.");
        }
    }

    @FXML
    private void handlePerbaruiDestinasi(ActionEvent event) {
        Destinasi selectedDestinasi = destinasiTable.getSelectionModel().getSelectedItem();
        if (selectedDestinasi != null) {
            String nama = namaDestinasiField.getText();
            String kategori = kategoriField.getText();
            String gambarPath = gambarField.getText();

            if (!nama.isEmpty() && !kategori.isEmpty() && !gambarPath.isEmpty()) {
                byte[] gambar = convertImageToByteArray(gambarPath);
                selectedDestinasi.setNama(nama);
                selectedDestinasi.setKategori(kategori);
                selectedDestinasi.setGambar(gambar);
                destinasiDAO.updateDestinasi(selectedDestinasi);
                loadDestinasiData();
                resetDestinasiForm();
            } else {
                showAlert("Form Tidak Lengkap", "Mohon isi semua field sebelum memperbarui destinasi.");
            }
        } else {
            showAlert("Tidak Ada Data Dipilih", "Pilih destinasi yang ingin diperbarui.");
        }
    }

    @FXML
    private void handleHapusDestinasi(ActionEvent event) {
        Destinasi selectedDestinasi = destinasiTable.getSelectionModel().getSelectedItem();
        if (selectedDestinasi != null) {
            // Dialog konfirmasi sebelum menghapus
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konfirmasi Hapus");
            alert.setHeaderText("Anda yakin ingin menghapus destinasi ini?");
            alert.setContentText("Destinasi yang dihapus tidak dapat dikembalikan.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Jika pengguna mengonfirmasi, hapus destinasi
                destinasiDAO.deleteDestinasi(selectedDestinasi.getIdDestinasi());
                loadDestinasiData(); // Muat ulang data destinasi setelah penghapusan
                resetDestinasiForm(); // Reset form setelah penghapusan
            }
        } else {
        showAlert("Tidak Ada Data Dipilih", "Pilih destinasi yang ingin dihapus.");
        }
    }
    
    @FXML
    private void handleResetFormDestinasi(ActionEvent event) {
        resetDestinasiForm();
    }

    private void resetDestinasiForm() {
        namaDestinasiField.clear();
        kategoriField.clear();
        gambarField.clear();
        destinasiTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleSelectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        
        if (selectedFile != null) {
            gambarField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void filterTiketDataByDestinasi(String selectedDestinasi) {
        List<PesanTiket> tiketList = tiketDAO.getAllTiket();
        Integer idDestinasi = destinasiList.stream()
            .filter(destinasi -> destinasi.getNama().equals(selectedDestinasi))
            .map(Destinasi::getIdDestinasi)
            .findFirst()
            .orElse(null);

        List<PesanTiket> filteredList = tiketList.stream()
            .filter(tiket -> idDestinasi == null || tiket.getIdDestinasi() == idDestinasi)
            .collect(Collectors.toList());

        tiketTable.getItems().setAll(filteredList);
    }

    private void loadDestinasiData() {
        destinasiList = FXCollections.observableArrayList(destinasiDAO.getAllDestinasi());
        destinasiTable.setItems(destinasiList);
    }

    private byte[] convertImageToByteArray(String imagePath) {
        File file = new File(imagePath);
        byte[] imageBytes = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            imageBytes = new byte[(int) file.length()];
            fis.read(imageBytes);
        } catch (IOException e) {
            showAlert("Error", "Gagal mengonversi gambar: " + e.getMessage());
        }
        return imageBytes;
    }
    
    @FXML
    private void handleFilterChange(ActionEvent event) {
        String selectedDestinasi = destinasiComboBox.getValue();
        Date selectedDate = tanggalDatePicker.getValue() != null ? Date.valueOf(tanggalDatePicker.getValue()) : null;

        if (selectedDestinasi == null && selectedDate == null) {
            // Jika tidak ada filter, muat semua tiket
            loadTiketData();
        } else {
            // Jika ada filter, gunakan data yang difilter
            filterTiketData(selectedDestinasi, selectedDate);
        }
    }


    private void filterTiketData(String destinasi, Date tanggal) {
        List<PesanTiket> tiketList = tiketDAO.getAllTiket();

        // Hardcode pemetaan destinasi
        Map<String, Integer> destinasiMap = new HashMap<>();
        destinasiMap.put("Pulau Pramuka", 1);
        destinasiMap.put("Pulau Sepa", 2);
        destinasiMap.put("Pulau Harapan", 3);

        // Dapatkan ID destinasi dari pilihan ComboBox
        Integer idDestinasi = destinasi != null ? destinasiMap.get(destinasi) : null;

        // Filter berdasarkan ID destinasi dan tanggal
        List<PesanTiket> filteredList = tiketList.stream()
            .filter(tiket -> (idDestinasi == null || tiket.getIdDestinasi() == idDestinasi) &&
                         (tanggal == null || tiket.getTanggal().equals(tanggal)))
            .collect(Collectors.toList());

        // Tampilkan data yang difilter ke dalam TableView
        tiketTable.getItems().setAll(filteredList);
    }

    private String getDestinasiById(int idDestinasi) {
        switch (idDestinasi) {
            case 1: return "Pulau Pramuka";
            case 2: return "Pulau Sepa";
            case 3: return "Pulau Harapan";
            default: return "Tidak Diketahui";
        }
    }

    private void loadTiketData() {
        PesanTiketDAO tiketDAO = new PesanTiketDAO();
        List<PesanTiket> tiketList = tiketDAO.getAllTiket();  // Ambil data tiket
        tiketTable.getItems().setAll(tiketList);
    }

    private String getWaktuById(int waktu) {
        // Konversi kode waktu ke string yang dapat dipahami
        switch (waktu) {
            case 1: return "Pagi";
            case 2: return "Siang";
            case 3: return "Sore";
            default: return "Tidak Diketahui";
        }
    }
    
    
    @FXML
    private void handleResetAction(ActionEvent event) {
        // Reset ComboBox dan DatePicker
        destinasiComboBox.setValue(null); // Mengosongkan pilihan destinasi
        tanggalDatePicker.setValue(null); // Mengosongkan tanggal yang dipilih

        // Muat ulang data tiket tanpa filter
        loadTiketData();
    }

    @FXML
    private void hadleLogOutAction(ActionEvent event) {
        // Tindakan ketika link Log Out diklik
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml")); // Ganti dengan path yang sesuai
            Parent loginRoot = loader.load();
            Scene loginScene = new Scene(loginRoot);

            Stage currentStage = (Stage) btnLogOut.getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading Login: " + e.getMessage());
        }
    }

    @FXML
    private void handleRegisterPendamping(ActionEvent event) {
        try {
            // Ambil data dari field
            String idPendamping = idPendampingField.getText();
            String nama = namaField.getText();
            int umur = Integer.parseInt(umurField.getText());
            String alamat = alamatField.getText();
            String noHp = noHpField.getText();
            String password = passwordField.getText();

            // Validasi input
            if (idPendamping.isEmpty() || nama.isEmpty() || alamat.isEmpty() || noHp.isEmpty() || password.isEmpty()) {
                showError("Semua field harus diisi!");
                return;
            }

           // Tetapkan role secara otomatis
            String role = "pendamping";

            // Buat objek Pendamping dengan role
            Penyelam penyelam = new Penyelam(idPendamping, nama, umur, alamat, password, noHp, role);

            // Simpan ke database
            PenyelamDAO penyelamDAO = new PenyelamDAO();
            penyelamDAO.insertPenyelam(penyelam);

            // Tampilkan pesan sukses
            showAlert("Pendaftaran Berhasil", "Akun pendamping Anda telah berhasil dibuat!");
            
            // Reset form
            resetForm();

        } catch (NumberFormatException e) {
            showError("Umur harus berupa angka!");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Terjadi kesalahan: " + e.getMessage());
        }
    }
    

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetForm() {
        idPendampingField.clear();
        namaField.clear();
        umurField.clear();
        alamatField.clear();
        noHpField.clear();
        passwordField.clear();
    }
}
