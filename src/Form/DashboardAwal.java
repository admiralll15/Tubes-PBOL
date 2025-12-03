package Form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import model.GUITemplate;
import model.UIScaler;

/**
 * DashboardAwal - Rewritten dengan hardcode, tema putih-biru
 * Welcome dashboard untuk login dan register
 */
public class DashboardAwal extends JFrame {
    
    private JButton loginButton;
    private JButton registerButton;
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(DashboardAwal.class.getName());
    
    public DashboardAwal() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        UIScaler.scaleContainer(this.getContentPane());
    }
    
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("STAFFLINK - Sistem Manajemen Absensi dan Penggajian");
        setResizable(true);
        
        // Main panel dengan gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradient dari primary ke primary_dark
                GradientPaint gradient = new GradientPaint(
                    0, 0, GUITemplate.PRIMARY,
                    0, getHeight(), GUITemplate.PRIMARY_DARK
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Decorative shapes
                g2d.setColor(new Color(34, 193, 230, 50));
                g2d.fillOval(-200, -200, 600, 600);
                g2d.fillOval(getWidth() - 300, getHeight() - 300, 600, 600);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        
        // Top logo/title section
        JPanel topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paintComponent(g);
                
                // Shadow effect
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.fillRect(0, getHeight() - 3, getWidth(), 3);
            }
        };
        topPanel.setLayout(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(80, 40, 80, 40));
        
        JLabel titleLabel = new JLabel("STAFFLINK");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 72));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        JLabel subtitleLabel = new JLabel("Sistem Manajemen Absensi & Penggajian");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        subtitleLabel.setForeground(GUITemplate.ACCENT_CYAN);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Center buttons dengan card style
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 50, 30, 50);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Login button dengan gradient
        loginButton = new JButton("✓ MASUK") {
            private boolean isHovered = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color startColor = isHovered ? GUITemplate.ACCENT_CYAN : GUITemplate.SUCCESS_GREEN;
                Color endColor = isHovered ? GUITemplate.SUCCESS_GREEN : new Color(39, 174, 96);
                GradientPaint gradient = new GradientPaint(
                    0, 0, startColor,
                    0, getHeight(), endColor
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                
                // Shadow
                if (isHovered) {
                    g2d.setColor(new Color(0, 0, 0, 50));
                    g2d.fillRoundRect(2, 2, getWidth() - 5, getHeight() - 3, 15, 15);
                }
                
                super.paintComponent(g);
            }
        };
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 24));
        loginButton.setPreferredSize(new Dimension(250, 70));
        loginButton.setForeground(Color.WHITE);
        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(evt -> btnMasukActionPerformed(evt));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(loginButton, gbc);
        
        // Register button dengan gradient
        registerButton = new JButton("+ DAFTAR") {
            private boolean isHovered = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color startColor = isHovered ? new Color(243, 102, 92) : GUITemplate.ERROR_RED;
                Color endColor = isHovered ? GUITemplate.ERROR_RED : new Color(192, 57, 43);
                GradientPaint gradient = new GradientPaint(
                    0, 0, startColor,
                    0, getHeight(), endColor
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                
                // Shadow
                if (isHovered) {
                    g2d.setColor(new Color(0, 0, 0, 50));
                    g2d.fillRoundRect(2, 2, getWidth() - 5, getHeight() - 3, 15, 15);
                }
                
                super.paintComponent(g);
            }
        };
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 24));
        registerButton.setPreferredSize(new Dimension(250, 70));
        registerButton.setForeground(Color.WHITE);
        registerButton.setOpaque(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(evt -> btnDaftarActionPerformed(evt));
        
        gbc.gridx = 1;
        centerPanel.add(registerButton, gbc);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        pack();
    }
    
    private void btnMasukActionPerformed(ActionEvent evt) {
        FormLogin2 login = new FormLogin2();
        login.setVisible(true);
        this.dispose();
    }
    
    private void btnDaftarActionPerformed(ActionEvent evt) {
        FormRegister2 register = new FormRegister2();
        register.setVisible(true);
        this.dispose();
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
        
        java.awt.EventQueue.invokeLater(() -> new DashboardAwal().setVisible(true));
    }
}