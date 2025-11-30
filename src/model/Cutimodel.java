package model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cutimodel {
    
    private static final Logger LOGGER = Logger.getLogger(Cutimodel.class.getName());

    // 1. AJUKAN CUTI (Menggunakan tanggal_mulai dan tanggal_selesai)
    public boolean ajukanCuti(String idKaryawan, String tglMulai, String tglSelesai, String keterangan) {
        // Query disesuaikan dengan tabel cuti yang memiliki tanggal_mulai dan tanggal_selesai
        String sql = "INSERT INTO cuti(id_karyawan, tanggal_mulai, tanggal_selesai, keterangan, status) VALUES (?, ?, ?, ?, 'Pending')";
        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, idKaryawan);
            pst.setString(2, tglMulai);
            pst.setString(3, tglSelesai); // Tambahan kolom: tanggal_selesai
            pst.setString(4, keterangan);

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error ajukan cuti", e);
            return false;
        }
    }
    
    // 2. GET DATA STATUS CUTI (Menggunakan View yang sudah didefinisikan)
    public ResultSet getStatusCuti() {
        // Menggunakan V_STATUS_CUTI
        String sql = "SELECT * FROM v_status_cuti ORDER BY created_at DESC";
        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            return pst.executeQuery();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal mengambil status cuti", e);
            return null;
        }
    }
    
    // 3. UPDATE STATUS CUTI
    public boolean updateStatusCuti(int idCuti, String statusBaru) {
        // Digunakan oleh HRD/Admin untuk menyetujui atau menolak
        String sql = "UPDATE cuti SET status = ? WHERE id = ?";
        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, statusBaru); // 'Disetujui' atau 'Ditolak'
            pst.setInt(2, idCuti); 

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal update status cuti", e);
            return false;
        }
    }
}