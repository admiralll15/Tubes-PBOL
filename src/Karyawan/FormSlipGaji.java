package Karyawan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import model.koneksi;
import model.UserSession;
import model.GUITemplate;
import model.UIScaler;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * FormSlipGaji - Rewritten dengan hardcode, tema putih-biru
 * Lihat slip gaji dan export PDF
 */
public class FormSlipGaji extends JFrame {
    
    private JTextField idField;
    private JTextField namaField;
    private JTextField jabatanField;
    private JComboBox<String> bulanCombo;
    private JComboBox<String> tahunCombo;
    private JButton lihatButton;
    private JButton exportButton;
    private JButton batalButton;
    private JTextArea detailArea;
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(FormSlipGaji.class.getName());
    
    public FormSlipGaji() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        UIScaler.scaleContainer(this.getContentPane());
        
        // Load user session
        String idSaya = UserSession.getKaryawanId();
        String namaSaya = UserSession.getNamaKaryawan();
        String jabatanSaya = UserSession.getJabatanKaryawan();
        
        idField.setText(idSaya);
        namaField.setText(namaSaya);
        jabatanField.setText(jabatanSaya);
        
        idField.setEditable(false);
        namaField.setEditable(false);
        jabatanField.setEditable(false);
    }
    
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Slip Gaji - Sistem Manajemen Penggajian");
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
        JPanel headerPanel = GUITemplate.createHeaderPanel("SLIP GAJI");
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
        
        // Bulan
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblBulan = GUITemplate.createLabel("Bulan:");
        lblBulan.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblBulan, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        bulanCombo = GUITemplate.createComboBox(new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"});
        contentPanel.add(bulanCombo, gbc);
        row++;
        
        // Tahun
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblTahun = GUITemplate.createLabel("Tahun:");
        lblTahun.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblTahun, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        tahunCombo = GUITemplate.createComboBox(new String[]{"2025", "2024", "2023", "2022", "2021", "2020"});
        contentPanel.add(tahunCombo, gbc);
        row++;
        
        // Detail area
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 10, 20, 10);
        detailArea = new JTextArea(10, 50);
        detailArea.setEditable(false);
        detailArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        detailArea.setBackground(new Color(245, 245, 245));
        JScrollPane scrollDetail = new JScrollPane(detailArea);
        contentPanel.add(scrollDetail, gbc);
        row++;
        
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
        
        lihatButton = GUITemplate.createEnhancedButton("LIHAT", GUITemplate.PRIMARY);
        lihatButton.setPreferredSize(new Dimension(120, 45));
        lihatButton.addActionListener(evt -> btnLihatActionPerformed(evt));
        buttonPanel.add(lihatButton);
        
        exportButton = GUITemplate.createEnhancedButton("EXPORT PDF", GUITemplate.SUCCESS_GREEN);
        exportButton.setPreferredSize(new Dimension(120, 45));
        exportButton.addActionListener(evt -> btnExportActionPerformed(evt));
        buttonPanel.add(exportButton);
        
        batalButton = GUITemplate.createEnhancedButton("BATAL", GUITemplate.ERROR_RED);
        batalButton.setPreferredSize(new Dimension(120, 45));
        batalButton.addActionListener(evt -> btnBatalActionPerformed(evt));
        buttonPanel.add(batalButton);
        
        contentPanel.add(buttonPanel, gbc);
        
        scrollPane.setViewportView(contentContainer);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        pack();
    }
    
    private void btnLihatActionPerformed(ActionEvent evt) {
        String bulan = bulanCombo.getSelectedItem().toString();
        String tahun = tahunCombo.getSelectedItem().toString();
        String idKaryawan = idField.getText();
        
        try {
            Connection conn = koneksi.getKoneksi();
            String query = "SELECT gaji_pokok, jam_lembur, upah_lembur_perjam, potongan FROM gaji WHERE id_karyawan = ? AND bulan = ? AND tahun = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, idKaryawan);
            ps.setString(2, bulan);
            ps.setString(3, tahun);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int gajiPokok = rs.getInt("gaji_pokok");
                int jamLembur = rs.getInt("jam_lembur");
                int upahLemburPerJam = rs.getInt("upah_lembur_perjam");
                int potongan = rs.getInt("potongan");
                
                int totalLembur = jamLembur * upahLemburPerJam;
                int totalGaji = gajiPokok + totalLembur - potongan;
                
                StringBuilder detail = new StringBuilder();
                detail.append("=== SLIP GAJI ===\n\n");
                detail.append("Periode: ").append(bulan).append(" ").append(tahun).append("\n\n");
                detail.append("Gaji Pokok       : Rp ").append(gajiPokok).append("\n");
                detail.append("Jam Lembur       : ").append(jamLembur).append(" jam\n");
                detail.append("Upah/Jam         : Rp ").append(upahLemburPerJam).append("\n");
                detail.append("Total Lembur     : Rp ").append(totalLembur).append("\n");
                detail.append("Potongan         : Rp ").append(potongan).append("\n");
                detail.append("------------------------------\n");
                detail.append("TOTAL GAJI BERSIH : Rp ").append(totalGaji).append("\n");
                
                detailArea.setText(detail.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Gaji tidak ditemukan untuk periode ini", "Info", JOptionPane.INFORMATION_MESSAGE);
                detailArea.setText("");
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error lihat gaji", ex);
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void btnExportActionPerformed(ActionEvent evt) {
        if (detailArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Silahkan lihat gaji terlebih dahulu", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(this);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = fc.getSelectedFile().getAbsolutePath() + ".txt";
                
                try (PrintWriter writer = new PrintWriter(new FileOutputStream(filePath))) {
                    writer.println("SLIP GAJI");
                    writer.println("==================================");
                    writer.println(detailArea.getText());
                    writer.println("==================================");
                    JOptionPane.showMessageDialog(this, "File berhasil disimpan: " + filePath, "Sukses", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    logger.log(java.util.logging.Level.SEVERE, "Error export file", e);
                    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error export", ex);
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
        
        java.awt.EventQueue.invokeLater(() -> new FormSlipGaji().setVisible(true));
    }
}
