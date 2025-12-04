package shared;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import model.Absensimodel;
import model.GUITemplate;
import model.UIScaler;
import model.UserSession;

/**
 * FormAbsensiKaryawan - Rewritten dengan hardcode, tema putih-biru
 * Form absensi harian untuk karyawan
 */
public class FormAbsensiKaryawan extends JFrame {
    
    private Absensimodel absenModel = new Absensimodel();
    
    private JTextField idField;
    private JTextField namaField;
    private JTextField tanggalField;
    private JComboBox<String> statusCombo;
    private JTextArea keteranganArea;
    private JButton simpanButton;
    private JButton batalButton;
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(FormAbsensiKaryawan.class.getName());

    public FormAbsensiKaryawan() {
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
        tanggalField.setText(new java.text.SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date()));
        
        idField.setEditable(false);
        namaField.setEditable(false);
        tanggalField.setEditable(false);
    }
    
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Absensi Harian Karyawan");
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
        JPanel headerPanel = GUITemplate.createHeaderPanel("ABSENSI HARIAN");
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
        
        // Status
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.25;
        JLabel lblStatus = GUITemplate.createLabel("Status Absensi:");
        lblStatus.setFont(GUITemplate.FONT_LABEL_BOLD);
        contentPanel.add(lblStatus, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.75;
        statusCombo = GUITemplate.createComboBox(new String[]{"Hadir", "Izin", "Sakit", "Cuti", "Tanpa Keterangan"});
        contentPanel.add(statusCombo, gbc);
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
    
    private void btnSimpanActionPerformed(ActionEvent evt) {
        String idKaryawan = idField.getText();
        String status = statusCombo.getSelectedItem().toString();
        String keterangan = keteranganArea.getText();
        
        try {
            boolean sukses = absenModel.simpanAbsensi(idKaryawan, new Date(System.currentTimeMillis()), status, keterangan);
            
            if (sukses) {
                JOptionPane.showMessageDialog(this, "Absensi berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Absensi gagal disimpan!", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error simpan absensi", ex);
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
        
        java.awt.EventQueue.invokeLater(() -> new FormAbsensiKaryawan().setVisible(true));
    }
}
