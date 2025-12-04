package Karyawan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Cutimodel;
import model.GUITemplate;
import model.UIScaler;
import model.UserSession;
import model.koneksi;

/**
 * FormStatusCuti - Karyawan melihat status permohonan cuti mereka
 */
public class FormStatusCuti extends JFrame {
    
    private Cutimodel cutiModel = new Cutimodel();
    private JTable dataTable;
    private JLabel statusLabel;
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(FormStatusCuti.class.getName());

    public FormStatusCuti() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        model.AppIcon.setFrameIcon(this);
        loadDataStatusCuti();
        UIScaler.scaleContainer(this.getContentPane());
    }
    
    private void loadDataStatusCuti() {
        String[] kolom = {"ID", "Tgl Mulai", "Tgl Selesai", "Jumlah Hari", "Keterangan", "Status"};
        DefaultTableModel model = new DefaultTableModel(null, kolom);
        
        try {
            String idKaryawan = UserSession.getKaryawanId();
            
            if (idKaryawan == null || idKaryawan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Session tidak valid", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Query untuk ambil semua cuti karyawan ini - kolom 'tujuan' TIDAK ADA
            String sql = "SELECT id, tanggal_mulai, tanggal_selesai, " +
                        "DATEDIFF(tanggal_selesai, tanggal_mulai) + 1 AS jumlah_hari, " +
                        "keterangan, status FROM cuti WHERE id_karyawan = ? ORDER BY created_at DESC";
            java.sql.Connection conn = koneksi.getKoneksi();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, idKaryawan);
            ResultSet rs = pst.executeQuery();
            
            if (rs == null) {
                statusLabel.setText("Tidak ada data cuti");
                dataTable.setModel(model);
                return;
            }
            
            List<Object[]> dataList = new ArrayList<>();
            int totalCuti = 0;
            int disetujui = 0;
            int ditolak = 0;
            int pending = 0;
            
            while (rs.next()) {
                String status = rs.getString("status");
                dataList.add(new Object[] {
                    rs.getString("id"),
                    rs.getString("tanggal_mulai"),
                    rs.getString("tanggal_selesai"),
                    rs.getInt("jumlah_hari") + " hari",
                    rs.getString("keterangan"),
                    status
                });
                totalCuti++;
                if ("Disetujui".equals(status)) disetujui++;
                else if ("Ditolak".equals(status)) ditolak++;
                else if ("Pending".equals(status)) pending++;
            }
            
            for (Object[] row : dataList) {
                model.addRow(row);
            }
            
            // Update status label
            statusLabel.setText(String.format("Total Ajuan: %d | ✓ Disetujui: %d | ✗ Ditolak: %d | ⧗ Pending: %d", 
                totalCuti, disetujui, ditolak, pending));
            
            dataTable.setModel(model);
            // Styling handled by createModernTable
            dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            dataTable.setGridColor(new Color(230, 230, 230));
            dataTable.setShowGrid(true);
            
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error loading status cuti", e);
            statusLabel.setText("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error loading data: " + e.getMessage() + "\n\nDetail: " + e.getClass().getSimpleName(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Print to console for debugging
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Status Permohonan Cuti Saya");
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
        JPanel headerPanel = GUITemplate.createHeaderPanel("STATUS PERMOHONAN CUTI");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Status Summary Panel
        JPanel summaryPanel = GUITemplate.createRoundedPanel();
        summaryPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        statusLabel = new JLabel("Memuat data...");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        statusLabel.setForeground(GUITemplate.PRIMARY);
        summaryPanel.add(statusLabel);
        
        // Container for summary
        JPanel summaryContainer = new JPanel(new BorderLayout());
        summaryContainer.setOpaque(false);
        summaryContainer.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 40));
        summaryContainer.add(summaryPanel, BorderLayout.CENTER);
        
        mainPanel.add(summaryContainer, BorderLayout.NORTH);
        
        // Table
        dataTable = GUITemplate.createModernTable(new DefaultTableModel());
        dataTable.setSelectionBackground(new Color(200, 220, 255));
        
        JScrollPane scrollPane = GUITemplate.createModernScrollPane(dataTable);
        
        JPanel tablePanel = GUITemplate.createRoundedPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Container for table
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        tableContainer.add(tablePanel, BorderLayout.CENTER);
        
        // Add back button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        buttonPanel.setOpaque(false);
        JButton backButton = GUITemplate.createEnhancedButton("← KEMBALI", GUITemplate.PRIMARY);
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.addActionListener(e -> {
            this.dispose();
            new Karyawan.KaryawanDashboard().setVisible(true);
        });
        buttonPanel.add(backButton);
        tableContainer.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(tableContainer, BorderLayout.CENTER);
        
        // Footer dengan keterangan
        JPanel footerPanel = new JPanel();
        footerPanel.setOpaque(false);
        footerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        
        JLabel keterangan = new JLabel("Keterangan Status: ✓ Disetujui (Cuti Disetujui) | ✗ Ditolak (Cuti Ditolak) | ⧗ Pending (Menunggu Persetujuan)");
        keterangan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        keterangan.setForeground(Color.WHITE);
        footerPanel.add(keterangan);
        
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
        pack();
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
        
        java.awt.EventQueue.invokeLater(() -> new FormStatusCuti().setVisible(true));
    }
}
