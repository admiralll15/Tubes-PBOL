package HRD;

import model.Gajimodel;
import model.Karyawanmodel;
import model.GUITemplate;
import model.UIScaler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * FormHitungGaji - Rewritten dengan hardcode, tema putih-biru
 * Calculate dan simpan gaji karyawan
 */
public class FormHitungGaji extends JFrame {
    
    private Gajimodel gajiModel = new Gajimodel();
    private Karyawanmodel karyModel = new Karyawanmodel();
    
    // Fields
    private JTextField idField;
    private JTextField namaField;
    private JTextField gajiPokokField;
    private JTextField jamLemburField;
    private JTextField upahLemburPerJamField;
    private JTextField potonganField;
    private JTextField totalGajiField;
    private JComboBox<String> bulanCombo;
    private JComboBox<String> tahunCombo;
    private JButton hitungButton;
    private JButton simpanButton;
    private JButton batalButton;
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(FormHitungGaji.class.getName());
    
    public FormHitungGaji() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        totalGajiField.setEditable(false);
        UIScaler.scaleContainer(this.getContentPane());
    }
    
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hitung Gaji - Sistem Manajemen Penggajian");
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
        JPanel headerPanel = GUITemplate.createHeaderPanel("HITUNG GAJI");
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
        gbc.weightx = 1.0;
        int row = 0;
        
        // ID Karyawan
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblId = GUITemplate.createLabel("ID Karyawan:");
        lblId.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblId, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        idField = GUITemplate.createTextField();
        contentPanel.add(idField, gbc);
        row++;
        
        // Nama Karyawan
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
        
        // Gaji Pokok
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblGapok = GUITemplate.createLabel("Gaji Pokok:");
        lblGapok.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblGapok, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        gajiPokokField = GUITemplate.createTextField();
        contentPanel.add(gajiPokokField, gbc);
        row++;
        
        // Jam Lembur
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblJam = GUITemplate.createLabel("Jam Lembur:");
        lblJam.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblJam, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        jamLemburField = GUITemplate.createTextField();
        contentPanel.add(jamLemburField, gbc);
        row++;
        
        // Upah Lembur Per Jam
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblUpah = GUITemplate.createLabel("Upah Lembur/Jam:");
        lblUpah.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblUpah, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        upahLemburPerJamField = GUITemplate.createTextField();
        contentPanel.add(upahLemburPerJamField, gbc);
        row++;
        
        // Potongan
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblPotongan = GUITemplate.createLabel("Potongan:");
        lblPotongan.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblPotongan, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        potonganField = GUITemplate.createTextField();
        contentPanel.add(potonganField, gbc);
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
        
        // Total Gaji Bersih (read-only)
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel totalLabel = GUITemplate.createLabel("Total Gaji Bersih:");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentPanel.add(totalLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        totalGajiField = GUITemplate.createTextField();
        totalGajiField.setEditable(false);
        totalGajiField.setBackground(new Color(240, 240, 240));
        contentPanel.add(totalGajiField, gbc);
        row++;
        
        // Spacer
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(Box.createVerticalGlue(), gbc);
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
        
        hitungButton = GUITemplate.createEnhancedButton("HITUNG", GUITemplate.PRIMARY);
        hitungButton.setPreferredSize(new Dimension(120, 45));
        hitungButton.addActionListener(evt -> btnHitungActionPerformed(evt));
        buttonPanel.add(hitungButton);
        
        simpanButton = GUITemplate.createEnhancedButton("SIMPAN", GUITemplate.SUCCESS_GREEN);
        simpanButton.setPreferredSize(new Dimension(120, 45));
        simpanButton.addActionListener(evt -> btnSimpanActionPerformed(evt));
        buttonPanel.add(simpanButton);
        
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
    
    // Method untuk menghitung gaji bersih - PRESERVED FROM ORIGINAL
    private int hitungGajiBersih() {
        try {
            int gajiPokok = gajiPokokField.getText().isEmpty() ? 0 : Integer.parseInt(gajiPokokField.getText());
            int jamLembur = jamLemburField.getText().isEmpty() ? 0 : Integer.parseInt(jamLemburField.getText());
            int upahLemburPerJam = upahLemburPerJamField.getText().isEmpty() ? 0 : Integer.parseInt(upahLemburPerJamField.getText());
            int potongan = potonganField.getText().isEmpty() ? 0 : Integer.parseInt(potonganField.getText());
            
            int totalLembur = jamLembur * upahLemburPerJam;
            int totalGaji = gajiPokok + totalLembur - potongan;
            
            totalGajiField.setText("Rp " + totalGaji);
            return totalGaji;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan hanya angka!", "Error Input", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    
    private void btnHitungActionPerformed(ActionEvent evt) {
        hitungGajiBersih();
    }
    
    private void btnSimpanActionPerformed(ActionEvent evt) {
        String idKaryawan = idField.getText();
        String bulan = bulanCombo.getSelectedItem().toString();
        String tahun = tahunCombo.getSelectedItem().toString();
        
        if (idKaryawan.isEmpty() || gajiPokokField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap isi ID Karyawan dan Gaji Pokok!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        hitungGajiBersih();
        
        try {
            int gajiPokok = Integer.parseInt(gajiPokokField.getText());
            int jamLembur = jamLemburField.getText().isEmpty() ? 0 : Integer.parseInt(jamLemburField.getText());
            int upahLemburPerJam = upahLemburPerJamField.getText().isEmpty() ? 0 : Integer.parseInt(upahLemburPerJamField.getText());
            int potongan = potonganField.getText().isEmpty() ? 0 : Integer.parseInt(potonganField.getText());
            
            boolean sukses = gajiModel.hitungDanSimpan(idKaryawan, gajiPokok, jamLembur, upahLemburPerJam, potongan, bulan, tahun);
            
            if (sukses) {
                JOptionPane.showMessageDialog(this, "Gaji Berhasil Disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan. ID Karyawan mungkin salah atau Gaji bulan ini sudah ada.", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch(Exception e) {
            System.out.println("Error simpan: " + e.getMessage());
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
        
        java.awt.EventQueue.invokeLater(() -> new FormHitungGaji().setVisible(true));
    }
}
