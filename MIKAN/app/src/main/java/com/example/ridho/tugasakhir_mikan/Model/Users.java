package com.example.ridho.tugasakhir_mikan.Model;

public class Users {
    String userid,idMiso,pass,nama,email,no_hp,image,isPartner;
    Integer saldo;

    public Users(String userid, String idMiso, String pass, String nama, String email, String image) {
        this.userid = userid;
        this.idMiso = idMiso;
        this.pass = pass;
        this.nama = nama;
        this.email = email;
        this.no_hp = "";
        this.image = image;
        this.isPartner = "false";
        this.saldo = 0;
    }

    public Users() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIdMiso() {
        return idMiso;
    }

    public void setIdMiso(String idMiso) {
        this.idMiso = idMiso;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsPartner() {
        return isPartner;
    }

    public void setIsPartner(String isPartner) {
        this.isPartner = isPartner;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }
}
