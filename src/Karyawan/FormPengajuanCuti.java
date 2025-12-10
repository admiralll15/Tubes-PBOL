package Karyawan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import model.GUITemplate;
import model.UIScaler;
import model.Cutimodel;
import model.UserSession;

/**
 * FormPengajuanCuti - Rewritten dengan hardcode, tema putih-biru
 * Pengajuan cuti dari karyawan
 */
public class FormPengajuanCuti extends JFrame {
    
    private JTextField idField;
    private JTextField namaField;
    private JTextField tujuanField;
    private JSpinner tanggalMulaiSpinner;
    private JSpinner tanggalSelesaiSpinner;
    private JTextArea keteranganArea;
    private JButton ajukanButton;
    private JButton batalButton;
    
    private Cutimodel cutiModel = new Cutimodel();
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(FormPengajuanCuti.class.getName());
    
    public FormPengajuanCuti() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        model.AppIcon.setFrameIcon(this);
        UIScaler.scaleContainer(this.getContentPane());
        
        // Load user session
        String idKaryawan = UserSession.getKaryawanId();
        String namaKaryawan = UserSession.getNamaKaryawan();
        
        idField.setText(idKaryawan);
        namaField.setText(namaKaryawan);
        
        idField.setEditable(false);
        namaField.setEditable(false);
    }
    
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pengajuan Cuti - Sistem Manajemen Cuti");
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
        JPanel headerPanel = GUITemplate.createHeaderPanel("PENGAJUAN CUTI");
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
        
        // Tujuan Cuti
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblTujuan = GUITemplate.createLabel("Tujuan Cuti:");
        lblTujuan.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblTujuan, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        tujuanField = GUITemplate.createTextField();
        contentPanel.add(tujuanField, gbc);
        row++;
        
        // Tanggal Mulai
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblMulai = GUITemplate.createLabel("Tanggal Mulai:");
        lblMulai.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblMulai, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        tanggalMulaiSpinner = new JSpinner(new javax.swing.SpinnerDateModel());
        JSpinner.DateEditor de1 = new JSpinner.DateEditor(tanggalMulaiSpinner, "dd-MM-yyyy");
        tanggalMulaiSpinner.setEditor(de1);
        contentPanel.add(tanggalMulaiSpinner, gbc);
        row++;
        
        // Tanggal Selesai
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblSelesai = GUITemplate.createLabel("Tanggal Selesai:");
        lblSelesai.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblSelesai, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        tanggalSelesaiSpinner = new JSpinner(new javax.swing.SpinnerDateModel());
        JSpinner.DateEditor de2 = new JSpinner.DateEditor(tanggalSelesaiSpinner, "dd-MM-yyyy");
        tanggalSelesaiSpinner.setEditor(de2);
        contentPanel.add(tanggalSelesaiSpinner, gbc);
        row++;
        
        // Keterangan
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 10, 20, 10);
        JLabel lblKet = GUITemplate.createLabel("Keterangan:");
        lblKet.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblKet, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = row + 1;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        keteranganArea = new JTextArea(5, 50);
        keteranganArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane scrollArea = new JScrollPane(keteranganArea);
        contentPanel.add(scrollArea, gbc);
        row += 2;
        
        // Buttons
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        
        ajukanButton = GUITemplate.createEnhancedButton("AJUKAN", GUITemplate.SUCCESS_GREEN);
        ajukanButton.setPreferredSize(new Dimension(120, 45));
        ajukanButton.addActionListener(evt -> btnAjukanActionPerformed(evt));
        buttonPanel.add(ajukanButton);
        
        batalButton = GUITemplate.createEnhancedButton("BATAL", GUITemplate.ERROR_RED);
        batalButton.setPreferredSize(new Dimension(120, 45));
        batalButton.addActionListener(evt -> btnBatalActionPerformed(evt));
        buttonPanel.add(batalButton);
        
        JButton backButton = GUITemplate.createEnhancedButton("← KEMBALI", GUITemplate.PRIMARY);
        backButton.setPreferredSize(new Dimension(120, 45));
        backButton.addActionListener(e -> {
            this.dispose();
            new Karyawan.KaryawanDashboard().setVisible(true);
        });
        buttonPanel.add(backButton);
        
        contentPanel.add(buttonPanel, gbc);
        
        scrollPane.setViewportView(contentContainer);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        pack();
    }
    
    private void btnAjukanActionPerformed(ActionEvent evt) {
        String idKaryawan = idField.getText();
        String tujuanCuti = tujuanField.getText();
        Date tglMulai = new Date(((java.util.Date) tanggalMulaiSpinner.getValue()).getTime());
        Date tglSelesai = new Date(((java.util.Date) tanggalSelesaiSpinner.getValue()).getTime());
        String keterangan = keteranganArea.getText();
        
        // Validasi input
        if (idKaryawan == null || idKaryawan.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID Karyawan tidak ditemukan! Pastikan Anda sudah login.", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (tujuanCuti == null || tujuanCuti.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tujuan cuti tidak boleh kosong!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (tglMulai == null || tglSelesai == null) {
            JOptionPane.showMessageDialog(this, "Tanggal mulai dan selesai harus diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (tglMulai.after(tglSelesai)) {
            JOptionPane.showMessageDialog(this, "Tanggal mulai tidak boleh lebih besar dari tanggal selesai!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            logger.log(java.util.logging.Level.INFO, "Mengajukan cuti untuk: " + idKaryawan + ", Tujuan: " + tujuanCuti);
            
            boolean sukses = cutiModel.ajukanCuti(idKaryawan, tujuanCuti, tglMulai, tglSelesai, keterangan);
            
            if (sukses) {
                JOptionPane.showMessageDialog(this, "✓ Pengajuan cuti berhasil diajukan!\n\nKaryawan dapat melihat status di menu 'Status Cuti'", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "✗ Pengajuan cuti gagal diajukan!\n\nMungkin terjadi error di database.\nCoba lagi atau hubungi admin.", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error ajukan cuti", ex);
            JOptionPane.showMessageDialog(this, "✗ Error: " + ex.getMessage() + "\n\nLihat log untuk detail lebih lanjut.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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
        
        java.awt.EventQueue.invokeLater(() -> new FormPengajuanCuti().setVisible(true));
    }
}
