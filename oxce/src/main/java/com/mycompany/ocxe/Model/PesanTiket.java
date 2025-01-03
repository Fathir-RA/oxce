package com.mycompany.ocxe.Model;

import java.sql.Date;

public class PesanTiket {
    private int idTiket;
    private int idPenyelam;
    private int idDestinasi;
    private Date tanggal;
    private int waktu;
    private int quantity;
    private int harga;
    private Integer idPembayaran; // Menggunakan Integer untuk mengakomodasi nilai NULL
    private String namaPenyelam;

    public PesanTiket(int idTiket, int idPenyelam, int idDestinasi, Date tanggal, int waktu, int quantity, int harga, Integer idPembayaran) {
        this.idTiket = idTiket;
        this.idPenyelam = idPenyelam;
        this.idDestinasi = idDestinasi;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.quantity = quantity;
        this.harga = harga;
        this.idPembayaran = idPembayaran;
    }
    
    // Konstruktor baru dengan nama penyelam
    public PesanTiket(int idTiket, int idPenyelam, String namaPenyelam, int idDestinasi, Date tanggal, int waktu, int quantity, int harga, Integer idPembayaran) {
        this.idTiket = idTiket;
        this.idPenyelam = idPenyelam;
        this.namaPenyelam = namaPenyelam;
        this.idDestinasi = idDestinasi;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.quantity = quantity;
        this.harga = harga;
        this.idPembayaran = idPembayaran;
    }

    // Getter dan Setter untuk semua atribut
    public int getIdTiket() {
        return idTiket;
    }

    public void setIdTiket(int idTiket) {
        this.idTiket = idTiket;
    }

    public int getIdPenyelam() {
        return idPenyelam;
    }

    public void setIdPenyelam(int idPenyelam) {
        this.idPenyelam = idPenyelam;
    }

    public int getIdDestinasi() {
        return idDestinasi;
    }

    public void setIdDestinasi(int idDestinasi) {
        this.idDestinasi = idDestinasi;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public int getWaktu() {
        return waktu;
    }

    public void setWaktu(int waktu) {
        this.waktu = waktu;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 1 || quantity > 4) {
            throw new IllegalArgumentException("Jumlah tiket harus antara 1 hingga 4.");
        }
        this.quantity = quantity;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        if (harga <= 0) {
            throw new IllegalArgumentException("Harga tiket tidak bisa kurang dari atau sama dengan 0.");
        }
        this.harga = harga;
    }
    
   public double getTotalHarga() {
        return quantity * harga; // Menghitung total harga
    }
    
    // Tambahkan getter untuk totalHarga
    public Double getTotalHargaProperty() {
        return Double.valueOf(getTotalHarga());
    }
    
    // Getter dan setter untuk properti
    public String getNamaPenyelam() {
        return namaPenyelam;
    }

    public void setNamaPenyelam(String namaPenyelam) {
        this.namaPenyelam = namaPenyelam;
    }

    public Integer getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(Integer idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    @Override
    public String toString() {
        return "PesanTiket{" +
                "idTiket=" + idTiket +
                ", idPenyelam=" + idPenyelam +
                ", idDestinasi=" + idDestinasi +
                ", tanggal=" + tanggal +
                ", waktu=" + waktu +
                ", quantity=" + quantity +
                ", harga=" + harga +
                ", idPembayaran=" + idPembayaran +
                '}';
    }
}
