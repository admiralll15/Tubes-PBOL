package tubespbol;

import database.Koneksi;
import java.sql.*;
import javax.swing.*;

public class FormLogin extends JFrame {
    private JTextField txtEmailOrNip;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;

    public FormLogin() {
        setTitle("Login Sistem Penggajian");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblEmail = new JLabel("Email / NIP:");
        lblEmail.setBounds(50, 50, 100, 25);
        add(lblEmail);

        txtEmailOrNip = new JTextField();
        txtEmailOrNip.setBounds(150, 50, 180, 25);
        add(txtEmailOrNip);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 90, 100, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 90, 180, 25);
        add(txtPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 130, 80, 30);
        add(btnLogin);

        btnRegister = new JButton("Register");
        btnRegister.setBounds(240, 130, 90, 30);
        add(btnRegister);

        btnLogin.addActionListener(e -> loginUser());
        btnRegister.addActionListener(e -> {
            new FormRegister().setVisible(true);
            dispose();
        });
    }

    private void loginUser() {
        String emailOrNip = txtEmailOrNip.getText();
        String password = new String(txtPassword.getPassword());

        if (emailOrNip.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email/NIP dan Password wajib diisi!");
            return;
        }

        try (Connection conn = Koneksi.getKoneksi()) {
            String sql = "SELECT * FROM users WHERE (email=? OR nip=?) AND password=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, emailOrNip);
            pst.setString(2, emailOrNip);
            pst.setString(3, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login berhasil! Selamat datang, " + rs.getString("nama_lengkap"));
                // Bisa diarahkan ke halaman dashboard nanti
            } else {
                JOptionPane.showMessageDialog(this, "Email/NIP atau password salah!");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Kesalahan: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new FormLogin().setVisible(true);
    }
}
