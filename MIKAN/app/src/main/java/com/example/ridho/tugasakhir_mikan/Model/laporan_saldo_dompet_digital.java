package com.example.ridho.tugasakhir_mikan.Model;

public class laporan_saldo_dompet_digital {
    String heading, deskripsi, idLaporan, userid;
    Integer saldo;

    public laporan_saldo_dompet_digital(String heading, String deskripsi, String idLaporan, String userid, Integer saldo) {
        this.heading = heading;
        this.deskripsi = deskripsi;
        this.idLaporan = idLaporan;
        this.userid = userid;
        this.saldo = saldo;
    }

    public laporan_saldo_dompet_digital() {
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getidLaporan() {
        return idLaporan;
    }

    public void setidLaporan(String idLaporan) {
        this.idLaporan = idLaporan;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }
}

