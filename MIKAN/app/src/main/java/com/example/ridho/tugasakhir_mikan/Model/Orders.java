package com.example.ridho.tugasakhir_mikan.Model;

public class Orders {
    String idOrder,idUser,nama;
    Integer harga,jumlah,total;

    public Orders(String idOrder, String idUser, String nama, Integer harga, Integer jumlah) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public Orders() {
    }



    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getNama() {
        return nama;
    }

    public Integer getHarga() {
        return harga;
    }

    public Integer getJumlah() {
        return jumlah;
    }
}
