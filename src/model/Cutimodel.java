package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cutimodel {
    
    private static final Logger LOGGER = Logger.getLogger(Cutimodel.class.getName());
    
    // 1. Ambil Data untuk Tabel (Menggunakan View v_status_cuti)
    public ResultSet getStatusCuti() {
        // View sudah benar, tidak ada kolom 'bulan' atau 'tujuan'
        String sql = "SELECT id, id_karyawan, nama, jabatan, tanggal_mulai, tanggal_selesai, " +
                     "jumlah_hari, keterangan, status, created_at " +
                     "FROM v_status_cuti WHERE status = 'Pending' ORDER BY created_at DESC";
        try {
            Connection conn = koneksi.getKoneksi();
            PreparedStatement pst = conn.prepareStatement(sql);
            return pst.executeQuery();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error Get Cuti: " + e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
    }

    // 2. Update Status (Untuk Tombol Terima/Tolak)
    public boolean updateStatusCuti(int idCuti, String statusBaru) {
        String sql = "UPDATE cuti SET status = ?, updated_at = CURRENT_TIMESTAMP() WHERE id = ?";
        try {
            Connection conn = koneksi.getKoneksi();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, statusBaru); // 'Disetujui' atau 'Ditolak'
            pst.setInt(2, idCuti);
            LOGGER.log(Level.INFO, "Status cuti " + idCuti + " diubah menjadi " + statusBaru);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error Update Cuti: " + e.getMessage(), e);
            return false;
        }
    }
    
    // 2b. Update Status Cuti dengan ID string (dari tabel)
    public boolean updateStatusCutiById(String idString, String statusBaru) {
        try {
            int id = Integer.parseInt(idString);
            return updateStatusCuti(id, statusBaru);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid ID format: " + idString, e);
            return false;
        }
    }
    
    // 3. Ajukan Cuti Baru (Untuk Employee)
    public boolean ajukanCuti(String idKaryawan, String tujuan, java.sql.Date tglMulai, java.sql.Date tglSelesai, String keterangan) {
        // Gabungkan tujuan dengan keterangan karena table cuti tidak punya kolom tujuan
        String deskripsiLengkap = tujuan;
        if (keterangan != null && !keterangan.trim().isEmpty()) {
            deskripsiLengkap += " - " + keterangan;
        }
        
        String sql = "INSERT INTO cuti (id_karyawan, tanggal_mulai, tanggal_selesai, keterangan, status) VALUES (?, ?, ?, ?, 'Pending')";
        try {
            Connection conn = koneksi.getKoneksi();
            if (conn == null) {
                LOGGER.log(Level.SEVERE, "Database connection failed");
                return false;
            }
            
            // Validasi input
            if (idKaryawan == null || idKaryawan.trim().isEmpty()) {
                LOGGER.log(Level.SEVERE, "ID Karyawan tidak boleh kosong");
                return false;
            }
            if (tujuan == null || tujuan.trim().isEmpty()) {
                LOGGER.log(Level.SEVERE, "Tujuan cuti tidak boleh kosong");
                return false;
            }
            if (tglMulai == null || tglSelesai == null) {
                LOGGER.log(Level.SEVERE, "Tanggal mulai atau selesai tidak boleh kosong");
                return false;
            }
            if (tglMulai.after(tglSelesai)) {
                LOGGER.log(Level.SEVERE, "Tanggal mulai tidak boleh lebih besar dari tanggal selesai");
                return false;
            }
            
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, idKaryawan);
            pst.setDate(2, tglMulai);
            pst.setDate(3, tglSelesai);
            pst.setString(4, deskripsiLengkap);
            
            int result = pst.executeUpdate();
            if (result > 0) {
                LOGGER.log(Level.INFO, "Cuti berhasil diajukan untuk karyawan: " + idKaryawan);
                return true;
            } else {
                LOGGER.log(Level.SEVERE, "Update tidak menghasilkan rows");
                return false;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error ajukan cuti: " + e.getMessage(), e);
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error ajukan cuti: " + e.getMessage(), e);
            e.printStackTrace();
            return false;
        }
    }
}