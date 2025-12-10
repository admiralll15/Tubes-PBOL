package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Karyawanmodel {
    
    private Connection conn = koneksi.getKoneksi();
    private static final Logger LOGGER = Logger.getLogger(Karyawanmodel.class.getName());

    // Fungsi untuk mendapatkan ID karyawan baru (K001, K002, ...)
    public String generateNewKaryawanId() throws SQLException {
        String query = "SELECT MAX(id) FROM karyawan";
        String newId = "K001"; // Default

        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId != null && maxId.matches("K\\d+")) {
                    int num = Integer.parseInt(maxId.substring(1)) + 1;
                    newId = String.format("K%03d", num);
                }
            }
        }
        return newId;
    }

    // 1. CREATE: Menambahkan Karyawan Baru
    public boolean insertKaryawan(String nama, String jabatan) {
        String sql = "INSERT INTO karyawan (id, nama, jabatan, status) VALUES (?, ?, ?, 'Aktif')";
        try {
            String idKaryawan = generateNewKaryawanId();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idKaryawan);
            ps.setString(2, nama);
            ps.setString(3, jabatan);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal menambahkan karyawan", e);
            return false;
        }
    }

    // 2. READ: Mengambil Semua Data Karyawan
    public ResultSet getAllKaryawan() {
        String sql = "SELECT id, nama, jabatan, status FROM karyawan ORDER BY id";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal mengambil data karyawan", e);
            return null;
        }
    }
    
    // 3. UPDATE: Mengubah Data Karyawan
    public boolean updateKaryawan(String id, String nama, String jabatan) {
        String sql = "UPDATE karyawan SET nama=?, jabatan=?, updated_at=CURRENT_TIMESTAMP() WHERE id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, jabatan);
            ps.setString(3, id);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal mengubah data karyawan", e);
            return false;
        }
    }
    
    // 4. DELETE: Menghapus Data Karyawan
    public boolean deleteKaryawan(String id) {
        String sql = "DELETE FROM karyawan WHERE id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal menghapus karyawan", e);
            return false;
        }
    }
    
    // 5. UPDATE STATUS: Mengaktifkan/Menonaktifkan Akun Karyawan
    public boolean updateStatusAkun(String id, String status) {
        String sql = "UPDATE karyawan SET status = ?, updated_at = CURRENT_TIMESTAMP() WHERE id = ?";
        try {
            Connection newConn = koneksi.getKoneksi();
            PreparedStatement ps = newConn.prepareStatement(sql);
            ps.setString(1, status); // 'Aktif' atau 'Nonaktif'
            ps.setString(2, id);
            LOGGER.log(Level.INFO, "Status akun " + id + " diubah menjadi " + status);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal mengubah status akun karyawan", e);
            return false;
        }
    }
}