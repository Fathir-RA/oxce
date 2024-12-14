package com.mycompany.ocxe.Model;

public class Penyelam {
    private String id_penyelam;
    private String nama;
    private int umur;
    private String alamat;
    private String password;
    private String no_hp; 

    // Constructor
    public Penyelam(String id_penyelam, String nama, int umur, String alamat, String password, String no_hp) {
        this.id_penyelam = id_penyelam;
        this.nama = nama;
        this.umur = umur;
        this.alamat = alamat;
        this.password = password;
        this.no_hp = no_hp; // Inisialisasi no_hp
    }

    // Getter and Setter methods
    public String getIdPenyelam() {
        return id_penyelam;
    }

    public void setIdPenyelam(String id_penyelam) {
        this.id_penyelam = id_penyelam;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNoHp() { // Getter untuk no_hp
        return no_hp;
    }

    public void setNoHp(String no_hp) { // Setter untuk no_hp
        this.no_hp = no_hp;
    }

    @Override
    public String toString() {
        return "Penyelam{" +
                "id_penyelam='" + id_penyelam + '\'' +
                ", nama='" + nama + '\'' +
                ", umur=" + umur +
                ", alamat='" + alamat + '\'' +
                ", password='" + password + '\'' +
                ", no_hp='" + no_hp + '\'' + // Menampilkan no_hp
                '}';
    }
}