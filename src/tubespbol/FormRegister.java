package tubespbol;

import database.Koneksi;
import java.sql.*;
import javax.swing.*;

public class FormRegister extends JFrame {
    private JTextField txtNip, txtNama, txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnKembali;

    public FormRegister() {
        setTitle("Form Register Karyawan");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblNip = new JLabel("NIP:");
        lblNip.setBounds(50, 40, 100, 25);
        add(lblNip);

        txtNip = new JTextField();
        txtNip.setBounds(150, 40, 180, 25);
        add(txtNip);

        JLabel lblNama = new JLabel("Nama:");
        lblNama.setBounds(50, 80, 100, 25);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(150, 80, 180, 25);
        add(txtNama);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(50, 120, 100, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(150, 120, 180, 25);
        add(txtEmail);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 160, 100, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 160, 180, 25);
        add(txtPassword);

        btnRegister = new JButton("Daftar");
        btnRegister.setBounds(150, 200, 90, 30);
        add(btnRegister);

        btnKembali = new JButton("Kembali");
        btnKembali.setBounds(250, 200, 90, 30);
        add(btnKembali);

        btnRegister.addActionListener(e -> registerUser());
        btnKembali.addActionListener(e -> {
            new FormLogin().setVisible(true);
            dispose();
        });
    }

    private void registerUser() {
        String nip = txtNip.getText();
        String nama = txtNama.getText();
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());

        if (nip.isEmpty() || nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }

        try (Connection conn = Koneksi.getKoneksi()) {
            String sql = "INSERT INTO users (nip, nama_lengkap, email, password, role) VALUES (?, ?, ?, ?, 'karyawan')";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, nip);
            pst.setString(2, nama);
            pst.setString(3, email);
            pst.setString(4, password); // nanti bisa diubah ke hash
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registrasi berhasil!");
            new FormLogin().setVisible(true);
            dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal daftar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new FormRegister().setVisible(true);
    }
}
