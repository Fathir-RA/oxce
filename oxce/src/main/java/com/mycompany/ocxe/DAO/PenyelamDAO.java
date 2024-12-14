package com.mycompany.ocxe.DAO;

import com.mycompany.ocxe.Model.Penyelam;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PenyelamDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/oxcee"; 
    private String jdbcUsername = "root"; 
    private String jdbcPassword = "";
    
    // Koneksi ke database
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Menambahkan penyelam
    public void insertPenyelam(Penyelam penyelam) {
        String sql = "INSERT INTO penyelam (id_penyelam, nama, umur, alamat, password, no_hp) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, penyelam.getIdPenyelam());
            preparedStatement.setString(2, penyelam.getNama());
            preparedStatement.setInt(3, penyelam.getUmur());
            preparedStatement.setString(4, penyelam.getAlamat());
            preparedStatement.setString(5, penyelam.getPassword());
            preparedStatement.setString(6, penyelam.getNoHp());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mendapatkan penyelam berdasarkan id
    public Penyelam getPenyelam(String id) {
        Penyelam penyelam = null;
        String sql = "SELECT * FROM penyelam WHERE id_penyelam = ?";  // Query untuk mendapatkan penyelam berdasarkan ID
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                penyelam = new Penyelam(
                    resultSet.getString("id_penyelam"),
                    resultSet.getString("nama"),
                    resultSet.getInt("umur"),
                    resultSet.getString("alamat"),
                    resultSet.getString("password"),
                    resultSet.getString("no_hp")  // Mengambil no_hp
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return penyelam;
    }
}
