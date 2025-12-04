package Form;

import model.Usersmodel;
import model.GUITemplate;
import model.UIScaler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * FormLogin2 - Rewritten dengan hardcode, tema putih-biru, responsive layout
 * Fungsi login tetap sama, hanya GUI yang diubah
 */
public class FormLogin2 extends JFrame {
    
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private JButton loginButton;
    private JButton registerButton;
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(FormLogin2.class.getName());
    
    public FormLogin2() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        model.AppIcon.setFrameIcon(this);
        UIScaler.scaleContainer(this.getContentPane());
    }
    
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login - Sistem Manajemen Absensi dan Penggajian");
        setResizable(true);
        
        // Main container dengan gradient background lebih menarik
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
                
                // Tambah decorative shapes untuk visual interest
                g2d.setColor(new Color(255, 255, 255, 80));
                g2d.fillOval(-300, -200, 800, 800);
                g2d.fillOval(getWidth() - 200, getHeight() - 300, 700, 700);
                
                // Overlay gradient halus untuk depth
                GradientPaint overlay = new GradientPaint(
                    0, 0, new Color(88, 101, 242, 100),
                    0, getHeight(), new Color(34, 193, 230, 100)
                );
                g2d.setPaint(overlay);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        
        // Header dengan gradient
        JPanel headerPanel = GUITemplate.createHeaderPanel("STAFFLINK - LOGIN");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content panel
        JPanel contentPanel = GUITemplate.createRoundedPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        
        // Container for content panel to add padding
        JPanel contentContainer = new JPanel(new BorderLayout());
        contentContainer.setOpaque(false);
        contentContainer.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));
        contentContainer.add(contentPanel, BorderLayout.CENTER);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.weightx = 1.0;
        
        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 15, 40, 15);
        JLabel titleLabel = new JLabel("Masuk ke Akun Anda");
        titleLabel.setFont(GUITemplate.FONT_HEADING_BOLD);
        titleLabel.setForeground(GUITemplate.PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(titleLabel, gbc);
        
        // Email label & field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.insets = new Insets(15, 15, 15, 15);
        JLabel emailLabel = GUITemplate.createLabel("📧 Email:");
        emailLabel.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        emailField = GUITemplate.createTextField();
        contentPanel.add(emailField, gbc);
        
        // Password label & field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        JLabel passLabel = GUITemplate.createLabel("🔒 Password:");
        passLabel.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(passLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        passwordField = GUITemplate.createPasswordField();
        contentPanel.add(passwordField, gbc);
        
        // Role label & combo
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        JLabel roleLabel = GUITemplate.createLabel("👤 Pilih Role:");
        roleLabel.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(roleLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        roleCombo = GUITemplate.createComboBox(new String[]{"Admin", "HRD", "Karyawan"});
        contentPanel.add(roleCombo, gbc);
        
        // Buttons panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(40, 15, 20, 15);
        gbc.weightx = 1.0;
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(1, 2, 30, 0));
        
        loginButton = GUITemplate.createEnhancedButton("✓ LOGIN", GUITemplate.SUCCESS_GREEN);
        loginButton.setPreferredSize(new Dimension(150, 50));
        loginButton.addActionListener(evt -> btnmasukActionPerformed(evt));
        buttonsPanel.add(loginButton);
        
        registerButton = GUITemplate.createEnhancedButton("+ DAFTAR", GUITemplate.PRIMARY);
        registerButton.setPreferredSize(new Dimension(150, 50));
        registerButton.addActionListener(evt -> btnDaftarActionPerformed(evt));
        buttonsPanel.add(registerButton);
        
        contentPanel.add(buttonsPanel, gbc);
        
        // Spacer
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        contentPanel.add(Box.createVerticalGlue(), gbc);
        
        mainPanel.add(new JScrollPane(contentContainer), BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        pack();
    }
    
    private void btnmasukActionPerformed(ActionEvent evt) {
        String username = emailField.getText().trim();
        String password = String.valueOf(passwordField.getPassword());
        String selectedRole = roleCombo.getSelectedItem().toString();
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email dan password tidak boleh kosong!", 
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Usersmodel model = new Usersmodel();
        String role = model.login(username, password);
        
        if (role == null) {
            JOptionPane.showMessageDialog(this, "Email atau Password salah!", 
                "Gagal Login", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!role.equals(selectedRole)) {
            JOptionPane.showMessageDialog(this, "Jabatan tidak sesuai dengan akun Anda!", 
                "Gagal Login", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Navigate to dashboard - using reflection to avoid import issues
        try {
            Class<?> dashboardClass = null;
            switch (role) {
                case "Admin":
                    dashboardClass = Class.forName("Admin.AdminDashboard");
                    break;
                case "HRD":
                    dashboardClass = Class.forName("HRD.HRDDashboard");
                    break;
                case "Karyawan":
                    dashboardClass = Class.forName("Karyawan.KaryawanDashboard");
                    break;
            }
            
            if (dashboardClass != null) {
                Object dashboardInstance = dashboardClass.getDeclaredConstructor().newInstance();
                JFrame dashboard = (JFrame) dashboardInstance;
                dashboard.setVisible(true);
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error navigating to dashboard", ex);
            JOptionPane.showMessageDialog(this, "Error membuka dashboard: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        this.dispose();
    }
    
    private void btnDaftarActionPerformed(ActionEvent evt) {
        FormRegister2 fr = new FormRegister2();
        fr.setVisible(true);
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
        
        java.awt.EventQueue.invokeLater(() -> new FormLogin2().setVisible(true));
    }
}
