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
public class Cutimodel {
public boolean ajukanCuti(String idKaryawan, String tanggal, String keterangan) {
        try {
            String sql = "INSERT INTO cuti(id_karyawan, tanggal, keterangan, status) VALUES (?, ?, ?, 'Pending')";
            PreparedStatement pst = koneksi.getKoneksi().prepareStatement(sql);

            pst.setString(1, idKaryawan);
            pst.setString(2, tanggal);
            pst.setString(3, keterangan);

            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error ajukan cuti: " + e.getMessage());
            return false;
        }
    }

}
