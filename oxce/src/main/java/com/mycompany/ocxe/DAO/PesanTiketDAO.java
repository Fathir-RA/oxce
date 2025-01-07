package com.mycompany.ocxe.DAO;

import com.mycompany.ocxe.Model.PesanTiket;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PesanTiketDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/oxcee";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "";

    // Mendapatkan koneksi ke database
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Mendapatkan sisa tiket yang dapat dipesan
    public int getRemainingTickets(int idDestinasi, Date tanggal, int waktu) {
        String sql = "SELECT COALESCE(4 - SUM(quantity), 4) AS remaining_tickets " +
                     "FROM pesantiket WHERE id_destinasi = ? AND tanggal = ? AND waktu = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idDestinasi);
            preparedStatement.setDate(2, tanggal);
            preparedStatement.setInt(3, waktu);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("remaining_tickets");
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal mendapatkan sisa tiket: " + e.getMessage());
        }
        return 4; // Default jika tidak ada pemesanan
    }
    
    // Metode kedua: Mengambil sisa tiket berdasarkan nama destinasi dan waktu
    public int getRemainingTickets(String destinasi, String waktu) throws SQLException {
        String sql = "SELECT tiket.kuota - COALESCE(SUM(pesan_tiket.quantity), 0) AS sisa_tiket " +
                     "FROM tiket " +
                     "LEFT JOIN pesan_tiket ON tiket.id_tiket = pesan_tiket.id_tiket " +
                     "WHERE tiket.destinasi = ? AND tiket.waktu = ? " +
                     "GROUP BY tiket.id_tiket, tiket.kuota";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, destinasi);
            stmt.setString(2, waktu);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("sisa_tiket");
                } else {
                    return 0; // Jika tidak ada data, anggap tiket habis
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal mendapatkan sisa tiket berdasarkan nama destinasi: " + e.getMessage());
            throw e;
        }
    }

    // Membatasi pemesanan tiket di sesi yang sama, tanggal yang sama, dan destinasi yang sama
    public boolean canBookTickets(PesanTiket tiket) {
        String sqlSessionLimit = "SELECT SUM(quantity) AS total_tickets FROM pesantiket WHERE id_penyelam = ? AND tanggal = ? AND waktu = ? AND id_destinasi = ?";

        try (Connection connection = getConnection()) {
            // Cek batas pemesanan tiket per sesi
            try (PreparedStatement sessionStatement = connection.prepareStatement(sqlSessionLimit)) {
                sessionStatement.setInt(1, tiket.getIdPenyelam());
                sessionStatement.setDate(2, tiket.getTanggal());
                sessionStatement.setInt(3, tiket.getWaktu());
                sessionStatement.setInt(4, tiket.getIdDestinasi());

                try (ResultSet sessionResult = sessionStatement.executeQuery()) {
                    if (sessionResult.next() && sessionResult.getInt("total_tickets") + tiket.getQuantity() > 4) {
                        System.out.println("Pemesanan melebihi batas maksimal 4 tiket per sesi untuk tanggal dan destinasi yang sama.");
                        return false;
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Gagal memeriksa batas pemesanan tiket: " + e.getMessage());
            return false;
        }

        return true;
    }

    // Menambahkan pesan tiket dengan batasan
    public void insertPesanTiket(PesanTiket tiket) {
        if (!canBookTickets(tiket)) {
            System.out.println("Pemesanan tiket tidak dapat dilanjutkan karena melebihi batas yang diizinkan.");
            return;
        }

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
    
    public List<PesanTiket> getAllTiket() {
        List<PesanTiket> tiketList = new ArrayList<>();
        String sql = "SELECT pt.id_tiket, pt.id_penyelam, p.nama AS nama_penyelam, pt.id_destinasi, pt.tanggal, pt.waktu, pt.quantity, pt.harga, pt.id_pembayaran " +
                     "FROM pesantiket pt " +
                     "JOIN penyelam p ON pt.id_penyelam = p.id_penyelam";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int idTiket = resultSet.getInt("id_tiket");
                int idPenyelam = resultSet.getInt("id_penyelam");
                String namaPenyelam = resultSet.getString("nama_penyelam");
                int idDestinasi = resultSet.getInt("id_destinasi");
                Date tanggal = resultSet.getDate("tanggal");
                int waktu = resultSet.getInt("waktu");
                int quantity = resultSet.getInt("quantity");
                int harga = resultSet.getInt("harga");
                Integer idPembayaran = resultSet.getInt("id_pembayaran");

                // Gunakan konstruktor baru yang menerima nama penyelam
                PesanTiket tiket = new PesanTiket(idTiket, idPenyelam, namaPenyelam, idDestinasi, tanggal, waktu, quantity, harga, idPembayaran);
                tiketList.add(tiket);
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data tiket: " + e.getMessage());
        }
        return tiketList;
    }

    public Map<String, Object> getStatisik() {
    Map<String, Object> statistics = new HashMap<>();
    
    // Query untuk mendapatkan jumlah total tiket terjual
    String totalTiketQuery = "SELECT SUM(quantity) AS total_tiket FROM pesantiket";
    
    // Query untuk mendapatkan total pendapatan berdasarkan harga dan quantity
    String totalPendapatanQuery = "SELECT SUM(quantity * harga) AS total_pendapatan FROM pesantiket";
    
    // Query untuk mendapatkan destinasi yang paling banyak dipesan berdasarkan id_destinasi
    String destinasiPopulerQuery = 
        "SELECT id_destinasi, COUNT(*) AS jumlah_pemesanan " +
        "FROM pesantiket " +
        "GROUP BY id_destinasi " +
        "ORDER BY jumlah_pemesanan DESC " +
        "LIMIT 1";
    
    // Query untuk mendapatkan jumlah tiket dan total pendapatan per destinasi
    String tiketPerDestinasiQuery = 
        "SELECT id_destinasi, SUM(quantity) AS total_tiket, SUM(quantity * harga) AS total_pendapatan " +
        "FROM pesantiket " +
        "GROUP BY id_destinasi " +
        "ORDER BY total_tiket DESC";
    
    try (Connection connection = getConnection()) {
        // Total Tiket Terjual
        try (PreparedStatement stmt = connection.prepareStatement(totalTiketQuery);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                statistics.put("totalTiketTerjual", rs.getInt("total_tiket"));
            }
        }

        // Total Pendapatan
        try (PreparedStatement stmt = connection.prepareStatement(totalPendapatanQuery);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                statistics.put("totalPendapatan", rs.getInt("total_pendapatan"));
            }
        }

        // Destinasi Populer
        try (PreparedStatement stmt = connection.prepareStatement(destinasiPopulerQuery);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int idDestinasiPopuler = rs.getInt("id_destinasi");
                String destinasiPopuler = "";
                
                // Tentukan nama destinasi berdasarkan id_destinasi
                switch (idDestinasiPopuler) {
                    case 1:
                        destinasiPopuler = "Pulau Pramuka";
                        break;
                    case 2:
                        destinasiPopuler = "Pulau Sepa";
                        break;
                    case 3:
                        destinasiPopuler = "Pulau Harapan";
                        break;
                    default:
                        destinasiPopuler = "Tidak Dikenal";
                        break;
                }

                statistics.put("destinasiPopuler", destinasiPopuler);
            } else {
                statistics.put("destinasiPopuler", "Tidak Ada Data");
            }
        }

        // Informasi Tiket dan Pendapatan per Destinasi
        List<Map<String, Object>> destinasiDetails = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(tiketPerDestinasiQuery);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> destinasi = new HashMap<>();
                int idDestinasi = rs.getInt("id_destinasi");
                int totalTiket = rs.getInt("total_tiket");
                int totalPendapatan = rs.getInt("total_pendapatan");

                // Tentukan nama destinasi berdasarkan id_destinasi
                String destinasiName = "";
                switch (idDestinasi) {
                    case 1:
                        destinasiName = "Pulau Pramuka";
                        break;
                    case 2:
                        destinasiName = "Pulau Sepa";
                        break;
                    case 3:
                        destinasiName = "Pulau Harapan";
                        break;
                    default:
                        destinasiName = "Tidak Dikenal";
                        break;
                }

                destinasi.put("destinasi", destinasiName);
                destinasi.put("totalTiket", totalTiket);
                destinasi.put("totalPendapatan", totalPendapatan);
                destinasiDetails.add(destinasi);
            }
        }

        statistics.put("destinasiDetails", destinasiDetails);
        
    } catch (SQLException e) {
        System.err.println("Gagal mendapatkan statistik: " + e.getMessage());
        // Return default values in case of an error
        statistics.put("totalTiketTerjual", 0);
        statistics.put("totalPendapatan", 0);
        statistics.put("destinasiPopuler", "Tidak Ada Data");
        statistics.put("destinasiDetails", new ArrayList<>());
    }

    return statistics;
}

}
