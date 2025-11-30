package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gajimodel {
    
    private static final Logger LOGGER = Logger.getLogger(Gajimodel.class.getName());

    // 1. HITUNG DAN SIMPAN (Disempurnakan sesuai skema penggajian)
    public boolean hitungDanSimpan(String idKaryawan, int gajiPokok, int jamLembur, 
                                  int upahLemburPerJam, int potongan, String bulan, String tahun) {
        try {
            // Hitung Lembut dan Total
            int lembur = jamLembur * upahLemburPerJam;
            int total = gajiPokok + lembur - potongan;

            // Query disesuaikan untuk memasukkan SEMUA kolom penting:
            String sql = "INSERT INTO penggajian(id_karyawan, gaji_pokok, jam_lembur, upah_lembur_per_jam, lembur, potongan, total, bulan, tahun) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (Connection conn = koneksi.getKoneksi();
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setString(1, idKaryawan);
                pst.setInt(2, gajiPokok);
                pst.setInt(3, jamLembur);
                pst.setInt(4, upahLemburPerJam);
                pst.setInt(5, lembur); // Hasil hitungan
                pst.setInt(6, potongan);
                pst.setInt(7, total); // Hasil hitungan
                pst.setString(8, bulan);
                pst.setString(9, tahun);

                return pst.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            // UNIQUE KEY constraint akan mencegah penggajian ganda untuk periode yang sama
            LOGGER.log(Level.SEVERE, "Error hitung dan simpan gaji", e);
            return false;
        }
    }
    
    // 2. GET DATA LAPORAN GAJI (Menggunakan View yang sudah didefinisikan)
    public ResultSet getLaporanPenggajian() {
        // Menggunakan V_LAPORAN_PENGGAJIAN
        String sql = "SELECT * FROM v_laporan_penggajian ORDER BY tahun DESC, created_at DESC";
        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            return pst.executeQuery();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal mengambil laporan penggajian", e);
            return null;
        }
    }
}