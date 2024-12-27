package com.mycompany.ocxe.DAO;

import com.mycompany.ocxe.Model.Destinasi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DestinasiDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/oxcee";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "";

    // Mendapatkan koneksi ke database
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // CREATE: Menambahkan destinasi
    public void insertDestinasi(Destinasi destinasi) {
        String sql = "INSERT INTO destinasi (nama, kategori, gambar) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, destinasi.getNama());
            preparedStatement.setString(2, destinasi.getKategori());
            preparedStatement.setBytes(3, destinasi.getGambar()); // Menggunakan byte[] untuk gambar
            preparedStatement.executeUpdate();

            System.out.println("Destinasi berhasil ditambahkan ke database.");
        } catch (SQLException e) {
            System.err.println("Gagal menambahkan destinasi: " + e.getMessage());
        }
    }

    // READ: Mendapatkan semua destinasi
    public List<Destinasi> getAllDestinasi() {
        List<Destinasi> destinasiList = new ArrayList<>();
        String query = "SELECT id_destinasi, nama, kategori, gambar FROM destinasi"; // Pastikan nama kolom sesuai

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int idDestinasi = rs.getInt("id_destinasi");
                String nama = rs.getString("nama");
                String kategori = rs.getString("kategori");
                byte[] gambar = rs.getBytes("gambar"); // Menggunakan gambar sebagai byte[]

                Destinasi destinasi = new Destinasi(idDestinasi, nama, kategori, gambar);
                destinasiList.add(destinasi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destinasiList;
    }

    // READ: Mendapatkan destinasi berdasarkan ID
    public Destinasi getDestinasiById(int idDestinasi) {
        String sql = "SELECT * FROM destinasi WHERE id_destinasi = ?"; // Pastikan nama kolom sesuai
        Destinasi destinasi = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idDestinasi);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    destinasi = new Destinasi(
                        resultSet.getInt("id_destinasi"),
                        resultSet.getString("nama"),
                        resultSet.getString("kategori"),
                        resultSet.getBytes("gambar") // Menggunakan gambar sebagai byte[]
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal mendapatkan destinasi: " + e.getMessage());
        }
        return destinasi;
    }

    // UPDATE: Memperbarui destinasi
    public void updateDestinasi(Destinasi destinasi) {
        String sql = "UPDATE destinasi SET nama = ?, kategori = ?, gambar = ? WHERE id_destinasi = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, destinasi.getNama());
            preparedStatement.setString(2, destinasi.getKategori());
            preparedStatement.setBytes(3, destinasi.getGambar()); // Menggunakan byte[] untuk gambar
            preparedStatement.setInt(4, destinasi.getIdDestinasi());
            preparedStatement.executeUpdate();

            System.out.println("Destinasi berhasil diperbarui.");
        } catch (SQLException e) {
            System.err.println("Gagal memperbarui destinasi: " + e.getMessage());
        }
    }

    // DELETE: Menghapus destinasi berdasarkan ID
    public void deleteDestinasi(int idDestinasi) {
        String sql = "DELETE FROM destinasi WHERE id_destinasi = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idDestinasi);
            preparedStatement.executeUpdate();

            System.out.println("Destinasi berhasil dihapus.");
        } catch (SQLException e) {
            System.err.println("Gagal menghapus destinasi: " + e.getMessage());
        }
    }
}
