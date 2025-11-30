/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.Absensimodel;
import java.sql.ResultSet;

/**
 *
 * @author 
 */
public class Absensi {
    private Absensimodel model;

    public Absensi() {
        model = new Absensimodel();
    }

    // 1. Fungsi Check-In (Absen Masuk)
    public boolean checkIn(String idKaryawan, String nama, String jabatan, String tanggal, String jamMasuk) {
        if (idKaryawan.isEmpty() || tanggal.isEmpty() || jamMasuk.isEmpty()) {
            System.out.println("Data Check-In tidak lengkap!");
            return false;
        }
        return model.checkIn(idKaryawan, nama, jabatan, tanggal, jamMasuk);
    }

    // 2. Fungsi Check-Out (Absen Pulang)
    public boolean checkOut(String idKaryawan, String tanggal, String jamPulang) {
        if (idKaryawan.isEmpty() || tanggal.isEmpty() || jamPulang.isEmpty()) {
            System.out.println("Data Check-Out tidak lengkap!");
            return false;
        }
        return model.checkOut(idKaryawan, tanggal, jamPulang);
    }
    
    // 3. Ambil Laporan Absensi (Untuk ditampilkan di Tabel Admin/HRD)
    public ResultSet getLaporan() {
        return model.getLaporanAbsensi();
    }
}