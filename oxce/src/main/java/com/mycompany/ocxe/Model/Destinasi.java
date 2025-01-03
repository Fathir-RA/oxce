package com.mycompany.ocxe.Model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.*;

/**
 * Model class representing a destination.
 */
public class Destinasi {
    private final IntegerProperty idDestinasi; // ID of the destination
    private final StringProperty nama; // Name of the destination
    private final StringProperty kategori; // Category of the destination
    private byte[] gambar; // Image data stored as byte array

    // Constructor
    public Destinasi(int idDestinasi, String nama, String kategori, byte[] gambar) {
        this.idDestinasi = new SimpleIntegerProperty(idDestinasi);
        this.nama = new SimpleStringProperty(nama);
        this.kategori = new SimpleStringProperty(kategori);
        this.gambar = gambar; // Store image as byte array
    }

    // Getter and Setter for ID
    public int getIdDestinasi() {
        return idDestinasi.get();
    }

    public void setIdDestinasi(int idDestinasi) {
        this.idDestinasi.set(idDestinasi);
    }

    // Getter and Setter for Name
    public String getNama() {
        return nama.get();
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    // Getter and Setter for Category
    public String getKategori() {
        return kategori.get();
    }

    public void setKategori(String kategori) {
        this.kategori.set(kategori);
    }

    // Getter and Setter for Image
    public byte[] getGambar() {
        return gambar;
    }

    public void setGambar(byte[] gambar) {
        this.gambar = gambar;
    }
    
    // Static method to get a list of destinations
    public static List<Destinasi> getDestinasiOptions() {
        List<Destinasi> destinasiList = new ArrayList<>();
        destinasiList.add(new Destinasi(1, "Destinasi A", "Kategori A", null));
        destinasiList.add(new Destinasi(2, "Destinasi B", "Kategori B", null));
        destinasiList.add(new Destinasi(3, "Destinasi C", "Kategori C", null));
        return destinasiList;
    }
}
