package com.mycompany.ocxe.DAO;

import com.mycompany.ocxe.Model.PesanTiket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PesanTiketDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/oxcee";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "";

    // Mendapatkan koneksi ke database
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Menambahkan pesan tiket
    public void insertPesanTiket(PesanTiket tiket) {
        String sql = "INSERT INTO pesantiket (id_tiket, id_penyelam, id_destinasi, tanggal, waktu, quantity, harga, id_pembayaran) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, tiket.getIdTiket());
            preparedStatement.setInt(2, tiket.getIdPenyelam());
            preparedStatement.setInt(3, tiket.getIdDestinasi());
            preparedStatement.setDate(4, tiket.getTanggal());
            preparedStatement.setInt(5, tiket.getWaktu());
            preparedStatement.setInt(6, tiket.getQuantity());
            preparedStatement.setInt(7, tiket.getHarga());
            if (tiket.getIdPembayaran() != null) {
                preparedStatement.setInt(8, tiket.getIdPembayaran());
            } else {
                preparedStatement.setNull(8, Types.INTEGER);
            }

            preparedStatement.executeUpdate();
            System.out.println("Tiket berhasil ditambahkan ke database.");
        } catch (SQLException e) {
            System.err.println("Gagal menambahkan tiket: " + e.getMessage());
        }
    }

    // Mendapatkan tiket berdasarkan ID penyelam
    public List<PesanTiket> getTiketByPenyelam(int idPenyelam) {
        String sql = "SELECT * FROM pesantiket WHERE id_penyelam = ?";
        List<PesanTiket> tiketList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idPenyelam);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    PesanTiket tiket = new PesanTiket(
                        resultSet.getInt("id_tiket"),
                        resultSet.getInt("id_penyelam"),
                        resultSet.getInt("id_destinasi"),
                        resultSet.getDate("tanggal"),
                        resultSet.getInt("waktu"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("harga"),
                        resultSet.getInt("id_pembayaran") == 0 ? null : resultSet.getInt("id_pembayaran")
                    );
                    tiketList.add(tiket);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal mendapatkan tiket: " + e.getMessage());
        }

        if (tiketList.isEmpty()) {
            System.out.println("Tidak ditemukan tiket untuk penyelam dengan ID: " + idPenyelam);
        }
        return tiketList;
    }
}
