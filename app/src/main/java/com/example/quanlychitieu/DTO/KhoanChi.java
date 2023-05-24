package com.example.quanlychitieu.DTO;

import java.util.Date;

public class KhoanChi {
    //Tạo đối tượng khoản chi gồm các thuộc tính
    public int idTenLoaiChi;
    public int idKhoanChi;
    public String tenKhoanChi;
    public String noiDung;
    public float soTien;
    public Date ngayChi;
    //Khai báo các tên bảng, tên cột, để thuận tiện hơn trong việc tạo database và chỉ cần gọi lên
    public static  final String TB_NAME = "tb_khoanchi";
    public static  final String COL_NAME_ID_PK = "idKhoanChi";
    public static  final String COL_NAME_ID_FK = "idTenLoaiChi";
    public static  final String COL_NAME_TEN = "tenKhoanChi";
    public static  final String COL_NAME_NOIDUNG = "noiDung";
    public static  final String COL_NAME_SOTIEN = "soTien";
    public static  final String COL_NAME_NGAYCHI = "ngayChi";
    //constructor
    public KhoanChi(int idTenLoaiChi, int idKhoanChi, String tenKhoanChi, String noiDung, float soTien, Date ngayChi) {
        this.idTenLoaiChi = idTenLoaiChi;
        this.idKhoanChi = idKhoanChi;
        this.tenKhoanChi = tenKhoanChi;
        this.noiDung = noiDung;
        this.soTien = soTien;
        this.ngayChi = ngayChi;
    }
    //getter/ setter
    public KhoanChi() {
    }

    public int getIdTenLoaiChi() {
        return idTenLoaiChi;
    }

    public void setIdTenLoaiChi(int idTenLoaiChi) {
        this.idTenLoaiChi = idTenLoaiChi;
    }

    public int getIdKhoanChi() {
        return idKhoanChi;
    }

    public void setIdKhoanChi(int idKhoanChi) {
        this.idKhoanChi = idKhoanChi;
    }

    public String getTenKhoanChi() {
        return tenKhoanChi;
    }

    public void setTenKhoanChi(String tenKhoanChi) {
        this.tenKhoanChi = tenKhoanChi;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public float getSoTien() {
        return soTien;
    }

    public void setSoTien(float soTien) {
        this.soTien = soTien;
    }

    public Date getNgayChi() {
        return ngayChi;
    }

    public void setNgayChi(Date ngayChi) {
        this.ngayChi = ngayChi;
    }
}
