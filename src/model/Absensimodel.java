package model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Absensimodel {
    
    private static final Logger LOGGER = Logger.getLogger(Absensimodel.class.getName());
    
    // 1. CHECK-IN (Menyimpan jam masuk)
    public boolean checkIn(String idKaryawan, String nama, String jabatan, String tanggal, String jamMasuk) {
        // Query disesuaikan dengan tabel absensi_karyawan. 
        // Menggunakan INSERT dengan kolom yang dibutuhkan: nama, id_karyawan, jabatan, tanggal, jam_masuk, status (default 'Hadir')
        String sql = "INSERT INTO absensi_karyawan (nama, id_karyawan, jabatan, tanggal, jam_masuk, status) VALUES (?, ?, ?, ?, ?, 'Hadir')";
        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, nama);
            pst.setString(2, idKaryawan);
            pst.setString(3, jabatan);
            pst.setString(4, tanggal);
            pst.setString(5, jamMasuk); 

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            // Error ini mungkin terjadi jika absensi untuk tanggal dan karyawan yang sama sudah ada (UNIQUE KEY)
            LOGGER.log(Level.SEVERE, "Error saat Check-In: " + e.getMessage(), e);
            return false;
        }
    }
    
    // 2. CHECK-OUT (Mengupdate jam pulang)
    public boolean checkOut(String idKaryawan, String tanggal, String jamPulang) {
        // Query menggunakan UPDATE berdasarkan id_karyawan dan tanggal
        String sql = "UPDATE absensi_karyawan SET jam_pulang = ? WHERE id_karyawan = ? AND tanggal = ?";
        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, jamPulang);
            pst.setString(2, idKaryawan);
            pst.setString(3, tanggal); 

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saat Check-Out: " + e.getMessage(), e);
            return false;
        }
    }
    
    // 3. GET DATA LAPORAN (Menggunakan View yang sudah didefinisikan)
    public ResultSet getLaporanAbsensi() {
        // Menggunakan V_LAPORAN_ABSENSI
        String sql = "SELECT * FROM v_laporan_absensi ORDER BY tanggal DESC, id_karyawan";
        try {
            Connection conn = koneksi.getKoneksi();
            PreparedStatement pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = pst.executeQuery();
            return rs;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal mengambil laporan absensi", e);
            return null;
        }
    }
    
    // 4. SIMPAN ABSENSI (Untuk form absensi umum)
    public boolean simpanAbsensi(String idKaryawan, java.sql.Date tanggal, String status, String keterangan) {
        String sql = "INSERT INTO absensi_karyawan (id_karyawan, tanggal, status, keterangan) VALUES (?, ?, ?, ?)";
        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, idKaryawan);
            pst.setDate(2, tanggal);
            pst.setString(3, status);
            pst.setString(4, keterangan);

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error simpan absensi: " + e.getMessage(), e);
            return false;
        }
    }
}