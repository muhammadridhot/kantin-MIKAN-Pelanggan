package com.example.ridho.tugasakhir_mikan.Model;

public class menu {
    String idMenu,nama,image;
    Integer harga;

    public menu(String idMenu, String nama, String image, Integer harga) {
        this.idMenu = idMenu;
        this.nama = nama;
        this.image = image;
        this.harga = harga;
    }

    public menu() {
    }

    public String getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }
}
