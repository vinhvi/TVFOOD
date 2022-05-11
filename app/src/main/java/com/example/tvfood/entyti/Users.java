package com.example.tvfood.entyti;

public class Users {

    private String hoTen;
    private String email;
    private String sdt;
    private String diaChiSN;
    private String diaChiPQ;
    private String pass;

    public Users(String hoTen, String email, String sdt, String diaChiSN, String diaChiPQ, String pass) {
        this.hoTen = hoTen;
        this.email = email;
        this.sdt = sdt;
        this.diaChiSN = diaChiSN;
        this.diaChiPQ = diaChiPQ;
        this.pass = pass;
    }

    public Users(String hoTen, String email, String sdt, String diaChiSN, String diaChiPQ) {
        this.hoTen = hoTen;
        this.email = email;
        this.sdt = sdt;
        this.diaChiSN = diaChiSN;
        this.diaChiPQ = diaChiPQ;
    }

    public String getDiaChiSN() {
        return diaChiSN;
    }

    public void setDiaChiSN(String diaChiSN) {
        this.diaChiSN = diaChiSN;
    }

    public String getDiaChiPQ() {
        return diaChiPQ;
    }

    public void setDiaChiPQ(String diaChiPQ) {
        this.diaChiPQ = diaChiPQ;
    }

    public Users() {
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }


    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
