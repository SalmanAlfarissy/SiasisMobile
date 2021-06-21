package com.pnp.siasismobile.model;

public class Alumni {
    private String id_alumni;
    private String nama;
    private String angkatan;
    private String pekerjaan;
    private String alamat;
    private String foto;

    public Alumni(String id_alumni, String nama, String angkatan, String pekerjaan, String alamat, String foto) {
        this.id_alumni = id_alumni;
        this.nama = nama;
        this.angkatan = angkatan;
        this.pekerjaan = pekerjaan;
        this.alamat = alamat;
        this.foto = foto;
    }

    public String getId_alumni() {
        return id_alumni;
    }

    public void setId_alumni(String id_alumni) {
        this.id_alumni = id_alumni;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(String angkatan) {
        this.angkatan = angkatan;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
