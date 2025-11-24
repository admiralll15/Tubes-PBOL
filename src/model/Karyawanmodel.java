/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.*;
/**
 *
 * @author PUTRI SAHARA T
 */
public class Karyawanmodel {
 public ResultSet getAllKaryawan() {
        try {
            String sql = "SELECT * FROM karyawan";
            PreparedStatement pst = koneksi.getKoneksi().prepareStatement(sql);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error load karyawan: " + e.getMessage());
            return null;
        }
    }

    public boolean tambahKaryawan(String nama, String jabatan) {
        try {
            String sql = "INSERT INTO karyawan(nama, jabatan) VALUES (?, ?)";
            PreparedStatement pst = koneksi.getKoneksi().prepareStatement(sql);

            pst.setString(1, nama);
            pst.setString(2, jabatan);

            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error tambah: " + e.getMessage());
            return false;
        }
    }
}
