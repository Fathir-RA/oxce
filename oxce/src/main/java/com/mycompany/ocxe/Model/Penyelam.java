package com.mycompany.ocxe.Model;

public class Penyelam {
    private String id_penyelam;
    private String nama;
    private int umur;
    private String alamat;
    private String password;
    private String no_hp;
    private String role; // Tambahkan atribut role

    // Constructor dengan parameter
    public Penyelam(String id_penyelam, String nama, int umur, String alamat, String password, String no_hp, String role) {
        this.id_penyelam = id_penyelam;
        this.nama = nama;
        this.umur = umur;
        this.alamat = alamat;
        this.password = password;
        this.no_hp = no_hp;
        this.role = role; // Inisialisasi role
    }

    // Constructor
    public Penyelam(String id_penyelam, String nama, int umur, String alamat, String password, String no_hp) {
        this.id_penyelam = id_penyelam;
        this.nama = nama;
        this.umur = umur;
        this.alamat = alamat;
        this.password = password;
        this.no_hp = no_hp; // Inisialisasi no_hp
        this.role = role;
    }
    // Getter dan Setter untuk id_penyelam
    public String getIdPenyelam() {
        return id_penyelam;
    }

    public void setIdPenyelam(String id_penyelam) {
        this.id_penyelam = id_penyelam;
    }

    // Getter dan Setter untuk nama
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    // Getter dan Setter untuk umur
    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    // Getter dan Setter untuk alamat
    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    // Getter dan Setter untuk password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter dan Setter untuk no_hp
    public String getNoHp() {
        return no_hp;
    }

    public void setNoHp(String no_hp) {
        this.no_hp = no_hp;
    }

    // Getter dan Setter untuk role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Penyelam{" +
                "id_penyelam='" + id_penyelam + '\'' +
                ", nama='" + nama + '\'' +
                ", umur=" + umur +
                ", alamat='" + alamat + '\'' +
                ", password='" + password + '\'' +
                ", no_hp='" + no_hp + '\'' +
                ", role='" + role + '\'' + // Tambahkan role ke output
                '}';
    }
}
