package com.example.shoppedemo;

import java.io.Serializable;

public class MatHang implements Serializable {
    private String ten;
    private int gia;
    private int hinhAnh;
    private String email;

    public MatHang() {
    }

    public MatHang(String ten, int gia, int hinhAnh, String email) {
        this.ten = ten;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
        this.email = email;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
