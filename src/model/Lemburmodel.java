package model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lemburmodel {
    
    private static final Logger LOGGER = Logger.getLogger(Lemburmodel.class.getName());
    
    // 1. SIMPAN LEMBUR
    public boolean simpanLembur(String idKaryawan, String nama, String jabatan, java.sql.Date tanggal, int jamLembur) {
        String sql = "INSERT INTO lembur (id_karyawan, nama, jabatan, tanggal, jam_lembur) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, idKaryawan);
            pst.setString(2, nama);
            pst.setString(3, jabatan);
            pst.setDate(4, tanggal);
            pst.setInt(5, jamLembur);

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error simpan lembur: " + e.getMessage(), e);
            return false;
        }
    }
    
    // 2. GET LAPORAN LEMBUR
    public ResultSet getLaporanLembur() {
        String sql = "SELECT * FROM v_laporan_lembur";
        try {
            Connection conn = koneksi.getKoneksi();
            PreparedStatement pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = pst.executeQuery();
            return rs;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal mengambil laporan lembur", e);
            return null;
        }
    }
    
    // 3. GET LAPORAN LEMBUR BY PERIOD
    public ResultSet getLaporanLemburByPeriod(int bulan, String tahun) {
        String sql = "SELECT * FROM v_laporan_lembur WHERE MONTH(tanggal) = ? AND YEAR(tanggal) = ?";
        try {
            Connection conn = koneksi.getKoneksi();
            PreparedStatement pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setInt(1, bulan);
            pst.setString(2, tahun);
            ResultSet rs = pst.executeQuery();
            return rs;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal mengambil laporan lembur dengan filter", e);
            return null;
        }
    }
}
