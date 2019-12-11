package com.example.ridho.tugasakhir_mikan.Model;

public class topup_saldo {
    private String idTopUp,idPelanggan,tgl_topup,status,jenisATM;
    private Integer nominal;

    public topup_saldo(String idTopUp, String idPelanggan, String tgl_topup, String status, Integer nominal, String jenisATM) {
        this.idTopUp = idTopUp;
        this.idPelanggan = idPelanggan;
        this.tgl_topup = tgl_topup;
        this.status = status;
        this.nominal = nominal;
        this.jenisATM = jenisATM;
    }

    public topup_saldo() {
    }

    public String getJenisATM() {
        return jenisATM;
    }

    public void setJenisATM(String jenisATM) {
        this.jenisATM = jenisATM;
    }

    public String getIdTopUp() {
        return idTopUp;
    }

    public void setIdTopUp(String idTopUp) {
        this.idTopUp = idTopUp;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getTgl_topup() {
        return tgl_topup;
    }

    public void setTgl_topup(String tgl_topup) {
        this.tgl_topup = tgl_topup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }
}
