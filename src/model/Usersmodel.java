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
public class Usersmodel {
public String login(String username, String password) {
    try {
        String sql = "SELECT role FROM user WHERE username=? AND password=?";
        PreparedStatement pst = koneksi.getKoneksi().prepareStatement(sql);
        pst.setString(1, username);
        pst.setString(2, password);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getString("role"); // Admin / HRD / Karyawan
        }

    } catch (SQLException e) {
        System.out.println("Error login: " + e.getMessage());
    }

    return null;

}
    public boolean register(String id, String email, String password, String role) {
    Connection conn = koneksi.getKoneksi();
    String sql = "INSERT INTO user (id, email, password, role) VALUES (?, ?, ?, ?)";

    try {
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, id);
        ps.setString(2, email);
        ps.setString(3, password);
        ps.setString(4, role);

        ps.executeUpdate();
        return true;

    } catch (SQLException e) {
        System.out.println("Error register: " + e.getMessage());
        return false;
    }}
}
