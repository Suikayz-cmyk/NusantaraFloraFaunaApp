package com.example.nusantaraflorafaunaapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tabel_endemik")
public class Endemik {

    @PrimaryKey
    @NonNull
    private String id;
    private String tipe;
    private String nama;
    private String nama_latin;
    private String famili;
    private String genus;
    private String deskripsi;
    private String asal;
    private String sebaran;
    private String foto;
    private String status;

    // Constructor, Getter, dan Setter sangat wajib untuk Room
    public Endemik(@NonNull String id, String tipe, String nama, String nama_latin,
                   String famili, String genus, String deskripsi, String asal,
                   String sebaran, String foto, String status) {
        this.id = id;
        this.tipe = tipe;
        this.nama = nama;
        this.nama_latin = nama_latin;
        this.famili = famili;
        this.genus = genus;
        this.deskripsi = deskripsi;
        this.asal = asal;
        this.sebaran = sebaran;
        this.foto = foto;
        this.status = status;
    }

    // --- Generate Getter and Setter di bawah ini (Gunakan Alt+Insert di Android Studio) ---
    @NonNull
    public String getId() { return id; }
    public void setId(@NonNull String id) { this.id = id; }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_latin() {
        return nama_latin;
    }

    public void setNama_latin(String nama_latin) {
        this.nama_latin = nama_latin;
    }

    public String getFamili() {
        return famili;
    }

    public void setFamili(String famili) {
        this.famili = famili;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getAsal() {
        return asal;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }

    public String getSebaran() {
        return sebaran;
    }

    public void setSebaran(String sebaran) {
        this.sebaran = sebaran;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}