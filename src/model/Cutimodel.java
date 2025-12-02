package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cutimodel {
    
    // 1. Ambil Data untuk Tabel (Pakai View v_status_cuti)
    public ResultSet getStatusCuti() {
        // Kita hanya ambil yang statusnya 'Pending' agar HRD fokus memproses yang belum beres
        String sql = "SELECT * FROM v_status_cuti WHERE status = 'Pending' ORDER BY created_at DESC";
        try {
            Connection conn = koneksi.getKoneksi();
            PreparedStatement pst = conn.prepareStatement(sql);
            return pst.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error Get Cuti: " + e.getMessage());
            return null;
        }
    }

    // 2. Update Status (Untuk Tombol Terima/Tolak)
    public boolean updateStatusCuti(int idCuti, String statusBaru) {
        String sql = "UPDATE cuti SET status = ? WHERE id = ?";
        try {
            Connection conn = koneksi.getKoneksi();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, statusBaru); // 'Disetujui' atau 'Ditolak'
            pst.setInt(2, idCuti);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error Update Cuti: " + e.getMessage());
            return false;
        }
    }
}