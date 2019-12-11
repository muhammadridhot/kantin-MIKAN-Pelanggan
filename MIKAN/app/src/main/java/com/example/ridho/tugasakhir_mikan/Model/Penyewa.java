package com.example.ridho.tugasakhir_mikan.Model;

public class Penyewa {
    private String userid,email,password,nama,kantin,no_stan,no_rekening,image,status,masa_sewa;
    private Integer saldo;

    public Penyewa(String userid, String email, String password, String nama, String kantin, String no_stan, String image) {
        this.userid = userid;
        this.email = email;
        this.password = password;
        this.nama = nama;
        this.kantin = kantin;
        this.no_stan = no_stan;
        this.no_rekening = "";
        this.image = image;
        this.status = "Tidak Aktif";
        this.masa_sewa = "";
        this.saldo = 0;
    }

    public Penyewa() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKantin() {
        return kantin;
    }

    public void setKantin(String kantin) {
        this.kantin = kantin;
    }

    public String getNo_stan() {
        return no_stan;
    }

    public void setNo_stan(String no_stan) {
        this.no_stan = no_stan;
    }

    public String getNo_rekening() {
        return no_rekening;
    }

    public void setNo_rekening(String no_rekening) {
        this.no_rekening = no_rekening;
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

    public String getMasa_sewa() {
        return masa_sewa;
    }

    public void setMasa_sewa(String masa_sewa) {
        this.masa_sewa = masa_sewa;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }
}
