package com.example.ridho.tugasakhir_mikan.Model;

import java.util.ArrayList;

public class Riwayat_Pesanan {
    private String idOrder,idPelanggan,idPenyewa,nama_stan,no_meja,metode_pembayaran,status;
    private Integer total_harga;
    private String tgl_pesan;
    private ArrayList<Orders>menu;

    public Riwayat_Pesanan(String idOrder, String idPelanggan,String idPenyewa, String nama_stan, String no_meja, String metode_pembayaran, String status, Integer total_harga, String tgl_pesan, ArrayList<Orders> menu) {
        this.idOrder = idOrder;
        this.idPelanggan = idPelanggan;
        this.idPenyewa = idPenyewa;
        this.nama_stan = nama_stan;
        this.no_meja = no_meja;
        this.metode_pembayaran = metode_pembayaran;
        this.status = status;
        this.total_harga = total_harga;
        this.tgl_pesan = tgl_pesan;
        this.menu = menu;
    }

    public Riwayat_Pesanan() {
    }


    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getidPelanggan() {
        return idPelanggan;
    }

    public void setidPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getIdPenyewa() {
        return idPenyewa;
    }

    public void setIdPenyewa(String idPenyewa) {
        this.idPenyewa = idPenyewa;
    }

    public String getNama_stan() {
        return nama_stan;
    }

    public void setNama_stan(String nama_stan) {
        this.nama_stan = nama_stan;
    }

    public String getNo_meja() {
        return no_meja;
    }

    public void setNo_meja(String no_meja) {
        this.no_meja = no_meja;
    }

    public String getmetode_pembayaran() {
        return metode_pembayaran;
    }

    public void setmetode_pembayaran(String metode_pembayaran) {
        this.metode_pembayaran = metode_pembayaran;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(Integer total_harga) {
        this.total_harga = total_harga;
    }

    public String getTgl_pesan() {
        return tgl_pesan;
    }

    public void setTgl_pesan(String tgl_pesan) {
        this.tgl_pesan = tgl_pesan;
    }

    public ArrayList<Orders> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Orders> menu) {
        this.menu = menu;
    }
}
