package com.pnp.siasismobile.model;

public class Event {
    private String id_info;
    private String nama_event;
    private String gambar_event;
    private String deskripsi;

    public Event(String id_info, String nama_event, String gambar_event, String deskripsi) {
        this.id_info = id_info;
        this.nama_event = nama_event;
        this.gambar_event = gambar_event;
        this.deskripsi = deskripsi;
    }


    public String getId_info() {
        return id_info;
    }

    public void setId_info(String id_info) {
        this.id_info = id_info;
    }

    public String getNama_event() {
        return nama_event;
    }

    public void setNama_event(String nama_event) {
        this.nama_event = nama_event;
    }

    public String getGambar_event() {
        return gambar_event;
    }

    public void setGambar_event(String gambar_event) {
        this.gambar_event = gambar_event;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
