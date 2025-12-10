package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Usersmodel {
    
    private static final Logger LOGGER = Logger.getLogger(Usersmodel.class.getName());

    // --- FUNGSI GENERATE ID USER (USR001) ---
    private String generateNewUserId() throws SQLException {
        String query = "SELECT MAX(id) FROM user";
        String newId = "USR001"; 
        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement pst = conn.prepareStatement(query); 
             ResultSet rs = pst.executeQuery()) {
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
    private String generateNewKaryawanId() throws SQLException {
        String query = "SELECT MAX(id) FROM karyawan";
        String newId = "K001"; 
        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement pst = conn.prepareStatement(query); 
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

    // --- LOGIN (Dengan Validasi Status) ---
    public String login(String username, String password) {
        String query = "SELECT u.id, u.password, u.role, u.id_karyawan, k.nama, k.jabatan, k.status " +
                       "FROM user u " +
                       "LEFT JOIN karyawan k ON u.id_karyawan = k.id " +
                       "WHERE u.email = ?";
        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String dbPass = rs.getString("password");
                String role = rs.getString("role");
                String status = rs.getString("status");
                
                if (password.equals(dbPass)) {
                    // Cek apakah akun aktif
                    if (status != null && status.equals("Nonaktif")) {
                        LOGGER.log(Level.WARNING, "Login ditolak: Akun sudah dinonaktifkan");
                        return "NONAKTIF";
                    }
                    
                    String userId = rs.getString("id");
                    String idKaryawan = rs.getString("id_karyawan"); 
                    String namaAsli = rs.getString("nama");          
                    String jabatanAsli = rs.getString("jabatan");
                    
                    UserSession.setUserLogin(userId, role, idKaryawan, namaAsli, jabatanAsli);
                    
                    return role;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal saat login", e);
        }
        return null;
    }

    // --- REGISTER ---
    public boolean register(String nama, String email, String password, String role) {
        String sqlKaryawan = "INSERT INTO karyawan (id, nama, jabatan, status) VALUES (?, ?, ?, 'Aktif')";
        String sqlUser = "INSERT INTO user (id, id_karyawan, username, email, password, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = koneksi.getKoneksi()) {
            conn.setAutoCommit(false);

            String idKaryawan = generateNewKaryawanId();
            String idUser = generateNewUserId();

            PreparedStatement psKar = conn.prepareStatement(sqlKaryawan);
            psKar.setString(1, idKaryawan);
            psKar.setString(2, nama);
            psKar.setString(3, role);
            psKar.executeUpdate();

            PreparedStatement psUser = conn.prepareStatement(sqlUser);
            psUser.setString(1, idUser);
            psUser.setString(2, idKaryawan);
            psUser.setString(3, nama);
            psUser.setString(4, email);
            psUser.setString(5, password);
            psUser.setString(6, role);
            
            int rowUser = psUser.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);

            return rowUser > 0;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error register:", e);
            return false;
        }
    }
}