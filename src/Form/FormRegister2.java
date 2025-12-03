package Form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import model.Usersmodel;
import model.GUITemplate;
import model.UIScaler;

/**
 * FormRegister2 - Rewritten dengan hardcode, tema putih-biru
 * Fungsi register tetap sama, hanya GUI yang diubah
 */
public class FormRegister2 extends JFrame {
    
    private JTextField namaField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> roleCombo;
    private JButton registerButton;
    private JButton cancelButton;
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(FormRegister2.class.getName());
    
    public FormRegister2() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        UIScaler.scaleContainer(this.getContentPane());
    }
    
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Register - Sistem Manajemen Absensi dan Penggajian");
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
                
                // Decorative shapes untuk visual depth
                g2d.setColor(new Color(255, 255, 255, 80));
                g2d.fillOval(-300, -200, 800, 800);
                g2d.fillOval(getWidth() - 200, getHeight() - 300, 700, 700);
                
                // Overlay gradient untuk dimensi
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
        
        // Header
        JPanel headerPanel = GUITemplate.createHeaderPanel("STAFFLINK - REGISTER");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content panel
        JPanel contentPanel = GUITemplate.createRoundedPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        
        // Container for content panel to add padding
        JPanel contentContainer = new JPanel(new BorderLayout());
        contentContainer.setOpaque(false);
        contentContainer.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        contentContainer.add(contentPanel, BorderLayout.CENTER);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.weightx = 1.0;
        
        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 15, 30, 15);
        JLabel titleLabel = new JLabel("Buat Akun Baru");
        titleLabel.setFont(GUITemplate.FONT_HEADING_BOLD);
        titleLabel.setForeground(GUITemplate.PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(titleLabel, gbc);
        
        // Nama
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.insets = new Insets(12, 15, 12, 15);
        JLabel namaLabel = GUITemplate.createLabel("👤 Nama:");
        namaLabel.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(namaLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        namaField = GUITemplate.createTextField();
        contentPanel.add(namaField, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        JLabel emailLabel = GUITemplate.createLabel("📧 Email:");
        emailLabel.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        emailField = GUITemplate.createTextField();
        contentPanel.add(emailField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        JLabel passLabel = GUITemplate.createLabel("🔒 Password:");
        passLabel.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(passLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        passwordField = GUITemplate.createPasswordField();
        contentPanel.add(passwordField, gbc);
        
        // Confirm Password
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        JLabel confirmLabel = GUITemplate.createLabel("🔒 Konfirmasi:");
        confirmLabel.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(confirmLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        confirmPasswordField = GUITemplate.createPasswordField();
        contentPanel.add(confirmPasswordField, gbc);
        
        // Role
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.3;
        JLabel roleLabel = GUITemplate.createLabel("👥 Pilih Role:");
        roleLabel.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(roleLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        roleCombo = GUITemplate.createComboBox(new String[]{"Admin", "HRD", "Karyawan"});
        contentPanel.add(roleCombo, gbc);
        
        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(40, 15, 20, 15);
        gbc.weightx = 1.0;
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(1, 2, 30, 0));
        
        registerButton = GUITemplate.createEnhancedButton("✓ DAFTAR", GUITemplate.SUCCESS_GREEN);
        registerButton.setPreferredSize(new Dimension(150, 50));
        registerButton.addActionListener(evt -> btnDaftarActionPerformed(evt));
        buttonsPanel.add(registerButton);
        
        cancelButton = GUITemplate.createEnhancedButton("✕ BATAL", GUITemplate.ERROR_RED);
        cancelButton.setPreferredSize(new Dimension(150, 50));
        cancelButton.addActionListener(evt -> btnbatalActionPerformed(evt));
        buttonsPanel.add(cancelButton);
        
        contentPanel.add(buttonsPanel, gbc);
        
        // Spacer
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        contentPanel.add(Box.createVerticalGlue(), gbc);
        
        mainPanel.add(new JScrollPane(contentContainer), BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        pack();
    }
    
    private void btnDaftarActionPerformed(ActionEvent evt) {
        String nama = namaField.getText().trim();
        String email = emailField.getText().trim();
        String password = String.valueOf(passwordField.getPassword());
        String konfirmasi = String.valueOf(confirmPasswordField.getPassword());
        String role = roleCombo.getSelectedItem().toString();
        
        if (nama.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", 
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!password.equals(konfirmasi)) {
            JOptionPane.showMessageDialog(this, "Password tidak sama!", 
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Usersmodel model = new Usersmodel();
        boolean sukses = model.register(nama, email, password, role);
        
        if (sukses) {
            JOptionPane.showMessageDialog(this, "Registrasi berhasil!", 
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            FormLogin2 login = new FormLogin2();
            login.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registrasi gagal!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void btnbatalActionPerformed(ActionEvent evt) {
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
        
        java.awt.EventQueue.invokeLater(() -> new FormRegister2().setVisible(true));
    }
}
