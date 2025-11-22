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
public class Gajimodel {
 public boolean hitungDanSimpan(String idKaryawan, int gajiPokok, int lembur, int potongan) {
        try {
            int total = gajiPokok + lembur - potongan;

            String sql = "INSERT INTO penggajian(id_karyawan, gaji_pokok, lembur, potongan, total) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = koneksi.getKoneksi().prepareStatement(sql);

            pst.setString(1, idKaryawan);
            pst.setInt(2, gajiPokok);
            pst.setInt(3, lembur);
            pst.setInt(4, potongan);
            pst.setInt(5, total);

            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error hitung gaji: " + e.getMessage());
            return false;
        }
    }
}
