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
public class Absensimodel {
   public boolean simpanAbsensi(String idKaryawan, String tanggal, String status) {
        try {
            String sql = "INSERT INTO absensi(id_karyawan, tanggal, status) VALUES (?, ?, ?)";
            PreparedStatement pst = koneksi.getKoneksi().prepareStatement(sql);

            pst.setString(1, idKaryawan);
            pst.setString(2, tanggal);
            pst.setString(3, status);

            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error absensi: " + e.getMessage());
            return false;
        }
    }

    public ResultSet getDataAbsensi() {
        try {
            String sql = "SELECT a.*, k.nama FROM absensi a JOIN karyawan k ON a.id_karyawan=k.id";
            PreparedStatement pst = koneksi.getKoneksi().prepareStatement(sql);
            return pst.executeQuery();
        } catch (SQLException e) {
            return null;
        }
    } 
}
