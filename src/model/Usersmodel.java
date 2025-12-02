package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Usersmodel {
    
    private Connection conn = koneksi.getKoneksi();
    private static final Logger LOGGER = Logger.getLogger(Usersmodel.class.getName());

    // --- FUNGSI GENERATE ID USER (USR001) ---
    private String generateNewUserId() throws SQLException {
        String query = "SELECT MAX(id) FROM user";
        String newId = "USR001"; 
        try (PreparedStatement pst = conn.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId != null && maxId.matches("USR\\d+")) {
                    int num = Integer.parseInt(maxId.substring(3)) + 1;
                    newId = String.format("USR%03d", num);
                }
            }
        }
        return newId;
    }

    // --- FUNGSI GENERATE ID KARYAWAN (K001) ---
    // Kita butuh ini agar saat register, ID karyawan juga dibuat otomatis
    private String generateNewKaryawanId() throws SQLException {
        String query = "SELECT MAX(id) FROM karyawan";
        String newId = "K001"; 
        try (PreparedStatement pst = conn.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
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

    // --- LOGIN (Tidak Berubah) ---
    public String login(String username, String password) {
        // Query Update: Tambahkan k.jabatan
        String query = "SELECT u.id, u.password, u.role, u.id_karyawan, k.nama, k.jabatan " +
                       "FROM user u " +
                       "LEFT JOIN karyawan k ON u.id_karyawan = k.id " +
                       "WHERE u.email = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String dbPass = rs.getString("password");
                String role = rs.getString("role");
                
                if (password.equals(dbPass)) {
                    String userId = rs.getString("id");
                    String idKaryawan = rs.getString("id_karyawan"); 
                    String namaAsli = rs.getString("nama");          
                    String jabatanAsli = rs.getString("jabatan"); // <-- AMBIL JABATAN
                    
                    // Simpan ke Session (termasuk jabatan)
                    UserSession.setUserLogin(userId, role, idKaryawan, namaAsli, jabatanAsli);
                    
                    return role;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal saat login", e);
        }
        return null;
    }

    // --- REGISTER (YANG DIUBAH BESAR-BESARAN) ---
    public boolean register(String nama, String email, String password, String role) {
        // Kita butuh Transaction karena akan insert ke 2 tabel
        // Jika insert ke karyawan sukses tapi ke user gagal, harus dibatalkan semua.
        
        String sqlKaryawan = "INSERT INTO karyawan (id, nama, jabatan) VALUES (?, ?, ?)";
        String sqlUser = "INSERT INTO user (id, id_karyawan, username, email, password, role) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false); // Matikan auto-save agar aman

            // 1. Generate ID Baru
            String idKaryawan = generateNewKaryawanId();
            String idUser = generateNewUserId();

            // 2. Simpan ke Tabel KARYAWAN dulu (Induk)
            PreparedStatement psKar = conn.prepareStatement(sqlKaryawan);
            psKar.setString(1, idKaryawan);
            psKar.setString(2, nama); // Nama dari form register
            psKar.setString(3, role); // Jabatan disamakan dengan Role dulu
            psKar.executeUpdate();

            // 3. Simpan ke Tabel USER (Anak) - Sertakan ID Karyawan tadi!
            PreparedStatement psUser = conn.prepareStatement(sqlUser);
            psUser.setString(1, idUser);
            psUser.setString(2, idKaryawan); // <--- INI KUNCINYA, BIAR TIDAK NULL
            psUser.setString(3, nama); // Username pakai nama dulu
            psUser.setString(4, email);
            psUser.setString(5, password);
            psUser.setString(6, role);
            
            int rowUser = psUser.executeUpdate();

            conn.commit(); // Simpan permanen
            conn.setAutoCommit(true); // Balikkan ke normal

            return rowUser > 0;

        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {} // Batalkan jika error
            LOGGER.log(Level.SEVERE, "Error register:", e);
            return false;
        }
    }
}