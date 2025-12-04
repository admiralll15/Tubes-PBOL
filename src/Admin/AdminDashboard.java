package Admin;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(AdminDashboard.class.getName());

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(850, 650); // Adjusted height slightly to accommodate new button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        model.AppIcon.setFrameIcon(this); // Set application icon

        initComponents();
    }

    private void initComponents() {

        // Background gradien biru
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(13, 139, 240),
                        0, getHeight(), new Color(0, 95, 210));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Use GridBagLayout for centering
        mainPanel.setLayout(new GridBagLayout());

        // Content Panel to hold Title and Buttons
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false); // Transparent so gradient shows

        // Title
        JLabel title = new JLabel("ADMIN DASHBOARD");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(40)); // Spacing

        // Button Dimensions
        Dimension btnSize = new Dimension(280, 50);

        // Tombol Karyawan
        JButton btnKaryawan = new JButton("👥 DATA KARYAWAN");
        btnKaryawan.setPreferredSize(btnSize);
        btnKaryawan.setMaximumSize(btnSize);
        btnKaryawan.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButtonGradient(btnKaryawan, new Color(0, 198, 167), new Color(0, 114, 255));
        contentPanel.add(btnKaryawan);
        contentPanel.add(Box.createVerticalStrut(20));

        // Tombol Laporan Absensi
        JButton btnLaporanAbsensi = new JButton("📊 LAPORAN ABSENSI");
        btnLaporanAbsensi.setPreferredSize(btnSize);
        btnLaporanAbsensi.setMaximumSize(btnSize);
        btnLaporanAbsensi.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButtonGradient(btnLaporanAbsensi, new Color(0, 198, 167), new Color(0, 114, 255));
        contentPanel.add(btnLaporanAbsensi);
        contentPanel.add(Box.createVerticalStrut(20));

        // Tombol Laporan Gaji
        JButton btnLaporanGaji = new JButton("💰 LAPORAN PENGGAJIAN");
        btnLaporanGaji.setPreferredSize(btnSize);
        btnLaporanGaji.setMaximumSize(btnSize);
        btnLaporanGaji.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButtonGradient(btnLaporanGaji, new Color(0, 198, 167), new Color(0, 114, 255));
        contentPanel.add(btnLaporanGaji);
        contentPanel.add(Box.createVerticalStrut(20));

        // Tombol Absensi Harian (NEW)
        JButton btnAbsensiHarian = new JButton("✓ ABSENSI HARIAN");
        btnAbsensiHarian.setPreferredSize(btnSize);
        btnAbsensiHarian.setMaximumSize(btnSize);
        btnAbsensiHarian.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButtonGradient(btnAbsensiHarian, new Color(0, 198, 167), new Color(0, 114, 255));
        contentPanel.add(btnAbsensiHarian);
        contentPanel.add(Box.createVerticalStrut(20));

        // Tombol Form Lembur (NEW)
        JButton btnFormLembur = new JButton("⏰ FORM LEMBUR");
        btnFormLembur.setPreferredSize(btnSize);
        btnFormLembur.setMaximumSize(btnSize);
        btnFormLembur.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButtonGradient(btnFormLembur, new Color(0, 198, 167), new Color(0, 114, 255));
        contentPanel.add(btnFormLembur);
        contentPanel.add(Box.createVerticalStrut(20));

        // Tombol Slip Gaji
        JButton btnSlipGaji = new JButton("💰 SLIP GAJI");
        btnSlipGaji.setPreferredSize(btnSize);
        btnSlipGaji.setMaximumSize(btnSize);
        btnSlipGaji.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButtonGradient(btnSlipGaji, new Color(0, 198, 167), new Color(0, 114, 255));
        contentPanel.add(btnSlipGaji);
        contentPanel.add(Box.createVerticalStrut(20));

        // Tombol Pengajuan Cuti
        JButton btnPengajuanCuti = new JButton("📋 PENGAJUAN CUTI");
        btnPengajuanCuti.setPreferredSize(btnSize);
        btnPengajuanCuti.setMaximumSize(btnSize);
        btnPengajuanCuti.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButtonGradient(btnPengajuanCuti, new Color(0, 198, 167), new Color(0, 114, 255));
        contentPanel.add(btnPengajuanCuti);
        contentPanel.add(Box.createVerticalStrut(20));

        // Tombol Logout
        JButton btnLogout = new JButton("🚪 LOGOUT");
        btnLogout.setPreferredSize(btnSize);
        btnLogout.setMaximumSize(btnSize);
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButtonGradient(btnLogout, new Color(255, 95, 109), new Color(0, 114, 255));
        contentPanel.add(btnLogout);

        // Add contentPanel to mainPanel (centered by default in GridBagLayout)
        mainPanel.add(contentPanel);

        add(mainPanel);

        // Aksi tombol
        btnKaryawan.addActionListener(e -> new FormDataKaryawan().setVisible(true));

        btnLaporanAbsensi.addActionListener(e -> new laporanabsensi().setVisible(true));

        btnLaporanGaji.addActionListener(e -> new laporanpenggajian().setVisible(true));

        btnAbsensiHarian.addActionListener(e -> openAbsensiHarian());

        btnFormLembur.addActionListener(e -> openFormLembur());

        btnSlipGaji.addActionListener(e -> openSlipGaji());

        btnPengajuanCuti.addActionListener(e -> openPengajuanCuti());

        btnLogout.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(
                    this, "Yakin Logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION)
                dispose();
        });
    }

    // Styling tombol dengan gradient
    private void styleButtonGradient(JButton btn, Color startColor, Color endColor) {
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBorder(BorderFactory.createEmptyBorder());

        btn.setContentAreaFilled(false);
        btn.setOpaque(false);

        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;

                GradientPaint gp = new GradientPaint(
                        0, 0, startColor,
                        btn.getWidth(), btn.getHeight(), endColor);

                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, btn.getWidth(), btn.getHeight(), 15, 15);

                super.paint(g, c);
            }
        });
    }

    private void openAbsensiHarian() {
        try {
            Class<?> clazz = Class.forName("shared.FormAbsensiKaryawan");
            Object obj = clazz.getDeclaredConstructor().newInstance();
            JFrame frame = (JFrame) obj;
            frame.setVisible(true);
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error opening absensi harian", ex);
        }
    }

    private void openFormLembur() {
        try {
            Class<?> clazz = Class.forName("shared.FormLembur");
            Object obj = clazz.getDeclaredConstructor().newInstance();
            JFrame frame = (JFrame) obj;
            frame.setVisible(true);
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error opening form lembur", ex);
        }
    }

    private void openSlipGaji() {
        try {
            Class<?> clazz = Class.forName("Karyawan.FormSlipGaji");
            Object obj = clazz.getDeclaredConstructor().newInstance();
            JFrame frame = (JFrame) obj;
            frame.setVisible(true);
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error opening slip gaji", ex);
        }
    }

    private void openPengajuanCuti() {
        try {
            Class<?> clazz = Class.forName("Karyawan.FormPengajuanCuti");
            Object obj = clazz.getDeclaredConstructor().newInstance();
            JFrame frame = (JFrame) obj;
            frame.setVisible(true);
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error opening pengajuan cuti", ex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}
