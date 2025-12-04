package shared;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import model.Lemburmodel;
import model.GUITemplate;
import model.UIScaler;
import model.UserSession;

/**
 * FormLembur - Form input lembur karyawan
 * Mirip dengan FormAbsensiKaryawan
 */
public class FormLembur extends JFrame {
    
    private Lemburmodel lemburModel = new Lemburmodel();
    
    private JTextField idField;
    private JTextField namaField;
    private JTextField jabatanField;
    private JTextField tanggalField;
    private JTextField jamLemburField;
    private JButton simpanButton;
    private JButton batalButton;
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(FormLembur.class.getName());

    public FormLembur() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        model.AppIcon.setFrameIcon(this);
        UIScaler.scaleContainer(this.getContentPane());
        
        // Load user session
        String idKaryawan = UserSession.getKaryawanId();
        String namaKaryawan = UserSession.getNamaKaryawan();
        String jabatanKaryawan = UserSession.getJabatanKaryawan();
        
        idField.setText(idKaryawan);
        namaField.setText(namaKaryawan);
        jabatanField.setText(jabatanKaryawan);
        tanggalField.setText(new java.text.SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date()));
        
        idField.setEditable(false);
        namaField.setEditable(false);
        jabatanField.setEditable(false);
        tanggalField.setEditable(false);
    }
    
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form Lembur Karyawan");
        setResizable(true);
        
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, GUITemplate.PRIMARY,
                    getWidth(), getHeight(), GUITemplate.ACCENT_CYAN
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                g2d.setColor(new Color(255, 255, 255, 60));
                g2d.fillOval(-250, -150, 700, 700);
                g2d.fillOval(getWidth() - 150, getHeight() - 250, 700, 700);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);
        
        // Header
        JPanel headerPanel = GUITemplate.createHeaderPanel("FORM LEMBUR");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        
        JPanel contentPanel = GUITemplate.createRoundedPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Container for content panel to add padding
        JPanel contentContainer = new JPanel(new BorderLayout());
        contentContainer.setOpaque(false);
        contentContainer.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        contentContainer.add(contentPanel, BorderLayout.CENTER);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 10, 12, 10);
        int row = 0;
        
        // ID
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblId = GUITemplate.createLabel("ID Karyawan:");
        lblId.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblId, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        idField = GUITemplate.createTextField();
        idField.setEditable(false);
        contentPanel.add(idField, gbc);
        row++;
        
        // Nama
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblNama = GUITemplate.createLabel("Nama:");
        lblNama.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblNama, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        namaField = GUITemplate.createTextField();
        namaField.setEditable(false);
        contentPanel.add(namaField, gbc);
        row++;
        
        // Jabatan
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblJabatan = GUITemplate.createLabel("Jabatan:");
        lblJabatan.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblJabatan, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        jabatanField = GUITemplate.createTextField();
        jabatanField.setEditable(false);
        contentPanel.add(jabatanField, gbc);
        row++;
        
        // Tanggal
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblTanggal = GUITemplate.createLabel("Tanggal:");
        lblTanggal.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblTanggal, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        tanggalField = GUITemplate.createTextField();
        tanggalField.setEditable(false);
        contentPanel.add(tanggalField, gbc);
        row++;
        
        // Jam Lembur
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblJamLembur = GUITemplate.createLabel("Jam Lembur:");
        lblJamLembur.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblJamLembur, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        jamLemburField = GUITemplate.createTextField();
        jamLemburField.setToolTipText("Masukkan jumlah jam lembur (contoh: 2, 3, 4)");
        contentPanel.add(jamLemburField, gbc);
        row++;
        
        // Buttons
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 20, 10);
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        
        simpanButton = GUITemplate.createEnhancedButton("SIMPAN", GUITemplate.SUCCESS_GREEN);
        simpanButton.setPreferredSize(new Dimension(120, 45));
        simpanButton.addActionListener(evt -> btnSimpanActionPerformed(evt));
        buttonPanel.add(simpanButton);
        
        batalButton = GUITemplate.createEnhancedButton("BATAL", GUITemplate.ERROR_RED);
        batalButton.setPreferredSize(new Dimension(120, 45));
        batalButton.addActionListener(evt -> btnBatalActionPerformed(evt));
        buttonPanel.add(batalButton);
        
        JButton backButton = GUITemplate.createEnhancedButton("← KEMBALI", GUITemplate.PRIMARY);
        backButton.setPreferredSize(new Dimension(120, 45));
        backButton.addActionListener(e -> {
            this.dispose();
            String role = UserSession.getJabatanKaryawan();
            try {
                if ("Admin".equals(role)) {
                    new Admin.AdminDashboard().setVisible(true);
                } else if ("HRD".equals(role)) {
                    new HRD.HRDDashboard().setVisible(true);
                } else {
                    new Karyawan.KaryawanDashboard().setVisible(true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        buttonPanel.add(backButton);
        
        contentPanel.add(buttonPanel, gbc);
        
        scrollPane.setViewportView(contentContainer);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        pack();
    }
    
    private void btnSimpanActionPerformed(ActionEvent evt) {
        String idKaryawan = idField.getText();
        String nama = namaField.getText();
        String jabatan = jabatanField.getText();
        String jamLemburStr = jamLemburField.getText();
        
        // Validasi input
        if (jamLemburStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jam lembur tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int jamLembur = Integer.parseInt(jamLemburStr);
            
            if (jamLembur <= 0) {
                JOptionPane.showMessageDialog(this, "Jam lembur harus lebih dari 0!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            boolean sukses = lemburModel.simpanLembur(
                idKaryawan, 
                nama, 
                jabatan,
                new Date(System.currentTimeMillis()), 
                jamLembur
            );
            
            if (sukses) {
                JOptionPane.showMessageDialog(this, "Data lembur berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                jamLemburField.setText(""); // Reset field
            } else {
                JOptionPane.showMessageDialog(this, "Data lembur gagal disimpan!", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jam lembur harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error simpan lembur", ex);
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void btnBatalActionPerformed(ActionEvent evt) {
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
        
        java.awt.EventQueue.invokeLater(() -> new FormLembur().setVisible(true));
    }
}
