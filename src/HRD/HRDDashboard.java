package HRD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import model.GUITemplate;
import model.UIScaler;

/**
 * HRDDashboard - Rewritten dengan hardcode, tema putih-biru
 * Main dashboard untuk HRD user navigation
 */
public class HRDDashboard extends JFrame {
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(HRDDashboard.class.getName());
    
    public HRDDashboard() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        UIScaler.scaleContainer(this.getContentPane());
    }
    
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("HRD Dashboard - Sistem Manajemen Absensi dan Penggajian");
        setResizable(true);
        
        // Main panel dengan gradient background menarik
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradient utama dari PRIMARY ke ACCENT_CYAN
                GradientPaint gradient = new GradientPaint(
                    0, 0, GUITemplate.PRIMARY,
                    getWidth(), getHeight(), GUITemplate.ACCENT_CYAN
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Decorative circles untuk visual interest
                g2d.setColor(new Color(255, 255, 255, 60));
                g2d.fillOval(-250, -150, 700, 700);
                g2d.fillOval(getWidth() - 150, getHeight() - 250, 700, 700);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        
        // Header
        JPanel headerPanel = GUITemplate.createHeaderPanel("HRD DASHBOARD");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Dashboard buttons
        JPanel dashboardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Glassmorphism background
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                
                // Border
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 30, 30);
            }
        };
        dashboardPanel.setLayout(new GridBagLayout());
        dashboardPanel.setOpaque(false);
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Button Hitung Gaji
        gbc.gridx = 0;
        gbc.gridy = 0;
        JButton btnHitungGaji = createDashboardButton("💰 Hitung Gaji", GUITemplate.ACCENT_BLUE);
        btnHitungGaji.addActionListener(evt -> openHitungGaji());
        dashboardPanel.add(btnHitungGaji, gbc);
        
        // Button Izin Cuti
        gbc.gridx = 1;
        gbc.gridy = 0;
        JButton btnIzinCuti = createDashboardButton("📋 Izin Cuti", new Color(156, 39, 176));
        btnIzinCuti.addActionListener(evt -> openFormIzinCuti());
        dashboardPanel.add(btnIzinCuti, gbc);
        
        // Button Laporan Absensi
        gbc.gridx = 0;
        gbc.gridy = 1;
        JButton btnLaporanAbsensi = createDashboardButton("📊 Laporan Absensi", new Color(33, 150, 243));
        btnLaporanAbsensi.addActionListener(evt -> openLaporanAbsensi());
        dashboardPanel.add(btnLaporanAbsensi, gbc);
        
        // Button Absensi Harian
        gbc.gridx = 1;
        gbc.gridy = 1;
        JButton btnAbsensiHarian = createDashboardButton("✓ Absensi Harian", new Color(76, 175, 80));
        btnAbsensiHarian.addActionListener(evt -> openAbsensiHarian());
        dashboardPanel.add(btnAbsensiHarian, gbc);
        
        // Button Logout
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton btnLogout = GUITemplate.createErrorButton("🚪 LOGOUT");
        btnLogout.setPreferredSize(new Dimension(150, 50));
        btnLogout.addActionListener(evt -> logout());
        dashboardPanel.add(btnLogout, gbc);
        
        mainPanel.add(dashboardPanel, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        pack();
    }
    
    private JButton createDashboardButton(String text, Color color) {
        JButton btn = new JButton(text) {
            private boolean isHovered = false;
            private boolean isPressed = false;

            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        isHovered = true;
                        repaint();
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        isHovered = false;
                        repaint();
                    }
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        isPressed = true;
                        repaint();
                    }
                    public void mouseReleased(java.awt.event.MouseEvent evt) {
                        isPressed = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                int width = getWidth();
                int height = getHeight();
                
                // Scale effect calculation
                int shadowGap = 5;
                int shadowOffset = isPressed ? 2 : (isHovered ? 6 : 3);
                int roundness = 20;

                // Draw Shadow
                g2d.setColor(new Color(0, 0, 0, 40));
                g2d.fillRoundRect(4, shadowOffset, width - 8, height - shadowGap, roundness, roundness);

                // Draw Button Background
                int yOffset = isPressed ? 3 : 0;
                
                Color baseColor = color;
                Color color1 = baseColor;
                Color color2 = new Color(Math.max(baseColor.getRed() - 30, 0), 
                                       Math.max(baseColor.getGreen() - 30, 0), 
                                       Math.max(baseColor.getBlue() - 30, 0));
                
                if (isHovered && !isPressed) {
                    color1 = new Color(Math.min(baseColor.getRed() + 20, 255), 
                                     Math.min(baseColor.getGreen() + 20, 255), 
                                     Math.min(baseColor.getBlue() + 20, 255));
                }

                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRoundRect(2, yOffset, width - 4, height - shadowGap, roundness, roundness);
                
                // Shine effect
                GradientPaint shine = new GradientPaint(0, 0, new Color(255, 255, 255, 50), 0, height/2, new Color(255, 255, 255, 0));
                g2d.setPaint(shine);
                g2d.fillRoundRect(2, yOffset, width - 4, height/2, roundness, roundness);

                // Text
                FontMetrics fm = g2d.getFontMetrics(getFont());
                Rectangle2D r = fm.getStringBounds(getText(), g2d);
                int x = (width - (int) r.getWidth()) / 2;
                int y = (height - shadowGap - (int) r.getHeight()) / 2 + fm.getAscent() + yOffset;
                
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                g2d.drawString(getText(), x, y);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(220, 90)); // Slightly larger
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private void openHitungGaji() {
        FormHitungGaji form = new FormHitungGaji();
        form.setVisible(true);
    }
    
    private void openFormIzinCuti() {
        FormIzinCuti form = new FormIzinCuti();
        form.setVisible(true);
    }
    
    private void openLaporanAbsensi() {
        try {
            Class<?> clazz = Class.forName("Admin.laporanabsensi");
            Object obj = clazz.getDeclaredConstructor().newInstance();
            JFrame frame = (JFrame) obj;
            frame.setVisible(true);
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error opening laporan absensi", ex);
        }
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
    
    private void logout() {
        this.dispose();
        try {
            Class<?> clazz = Class.forName("Form.DashboardAwal");
            Object obj = clazz.getDeclaredConstructor().newInstance();
            JFrame frame = (JFrame) obj;
            frame.setVisible(true);
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error logout", ex);
        }
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> new HRDDashboard().setVisible(true));
    }
}
