package com.example.ridho.tugasakhir_mikan.Model;

import java.util.ArrayList;

public class notifikasi_penyewa {
    private String idPenyewa,idOrder,idPelanggan, namaPemesan,meja_makan,tgl_pesan,image,status, metodePembayaran;
    private Integer total;
    private ArrayList<Orders> menu;

    public notifikasi_penyewa(String idPenyewa, String idOrder, String idPelanggan,String namaPemesan, String meja_makan, String tgl_pesan, Integer total, ArrayList<Orders> menu,String image,String status, String metodePembayaran) {
        this.idPenyewa = idPenyewa;
        this.idOrder = idOrder;
        this.idPelanggan =idPelanggan;
        this.namaPemesan = namaPemesan;
        this.meja_makan = meja_makan;
        this.tgl_pesan = tgl_pesan;
        this.total = total;
        this.menu = menu;
        this.image = image;
        this.status = status;
        this.metodePembayaran = metodePembayaran;
    }

    public notifikasi_penyewa() {
    }

    public String getIdPenyewa() {
        return idPenyewa;
    }

    public void setIdPenyewa(String idPenyewa) {
        this.idPenyewa = idPenyewa;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getNamaPemesan() {
        return namaPemesan;
    }

    public void setNamaPemesan(String namaPemesan) {
        this.namaPemesan = namaPemesan;
    }

    public String getMeja_makan() {
        return meja_makan;
    }

    public void setMeja_makan(String meja_makan) {
        this.meja_makan = meja_makan;
    }

    public String getTgl_pesan() {
        return tgl_pesan;
    }

    public void setTgl_pesan(String tgl_pesan) {
        this.tgl_pesan = tgl_pesan;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public ArrayList<Orders> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Orders> menu) {
        this.menu = menu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }
}
