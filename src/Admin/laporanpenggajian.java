package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Gajimodel;
import model.GUITemplate;
import model.UIScaler;

/**
 * laporanpenggajian - Rewritten dengan hardcode, tema putih-biru
 * Laporan penggajian dalam bentuk tabel dengan export PDF
 */
public class laporanpenggajian extends JFrame {
    
    private Gajimodel gajiModel = new Gajimodel();
    private JTable dataTable;
    private JComboBox<String> bulanCombo;
    private JComboBox<String> tahunCombo;
    private JButton filterButton;
    private JButton exportButton;
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(laporanpenggajian.class.getName());

    public laporanpenggajian() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        loadDataGaji();
        UIScaler.scaleContainer(this.getContentPane());
    }
    
    private void loadDataGaji() {
        String[] kolom = {"ID", "Nama", "Gaji Pokok", "Lembur", "Potongan", "Total", "Bulan", "Tahun"};
        DefaultTableModel model = new DefaultTableModel(null, kolom);
        
        try {
            java.sql.ResultSet rs = gajiModel.getLaporanPenggajian();
            
            if (rs == null) {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan atau koneksi database gagal", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                dataTable.setModel(model);
                return;
            }
            
            List<Object[]> dataList = new ArrayList<>();
            int rowCount = 0;
            while (rs.next()) {
                try {
                    dataList.add(new Object[] {
                        rs.getString("id_karyawan"),
                        rs.getString("nama"),
                        "Rp " + rs.getInt("gaji_pokok"),
                        rs.getInt("jam_lembur") + " jam",
                        "Rp " + rs.getInt("potongan"),
                        "Rp " + rs.getInt("total"),
                        rs.getString("bulan"),
                        rs.getString("tahun")
                    });
                    rowCount++;
                } catch (SQLException columnError) {
                    logger.log(java.util.logging.Level.WARNING, "Skipped row due to column error", columnError);
                    continue;
                }
            }
            
            for (Object[] row : dataList) {
                model.addRow(row);
            }
            
            dataTable.setModel(model);
            // Styling handled by createModernTable
            dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "Tidak ada data penggajian", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error loading data", e);
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Laporan Penggajian");
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
        JPanel headerPanel = GUITemplate.createHeaderPanel("LAPORAN PENGGAJIAN");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Filter panel
        JPanel filterPanel = GUITemplate.createRoundedPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel lblBulan = GUITemplate.createLabel("Bulan:");
        lblBulan.setFont(GUITemplate.FONT_LABEL_BOLD);
        filterPanel.add(lblBulan);
        
        bulanCombo = GUITemplate.createComboBox(new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"});
        filterPanel.add(bulanCombo);
        
        JLabel lblTahun = GUITemplate.createLabel("Tahun:");
        lblTahun.setFont(GUITemplate.FONT_LABEL_BOLD);
        filterPanel.add(lblTahun);
        
        tahunCombo = GUITemplate.createComboBox(new String[]{"2025", "2024", "2023", "2022", "2021", "2020"});
        filterPanel.add(tahunCombo);
        
        filterButton = GUITemplate.createEnhancedButton("FILTER", GUITemplate.PRIMARY);
        filterButton.setPreferredSize(new Dimension(100, 40));
        filterButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Filter functionality"));
        filterPanel.add(filterButton);
        
        exportButton = GUITemplate.createEnhancedButton("EXPORT PDF", GUITemplate.SUCCESS_GREEN);
        exportButton.setPreferredSize(new Dimension(120, 40));
        exportButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Export PDF functionality"));
        filterPanel.add(exportButton);
        
        // Container for filter
        JPanel filterContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filterContainer.setOpaque(false);
        filterContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        filterContainer.add(filterPanel);
        
        mainPanel.add(filterContainer, BorderLayout.NORTH);
        
        // Table
        dataTable = GUITemplate.createModernTable(new DefaultTableModel());
        JScrollPane scrollPane = GUITemplate.createModernScrollPane(dataTable);
        
        JPanel tablePanel = GUITemplate.createRoundedPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Container for table
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        tableContainer.add(tablePanel, BorderLayout.CENTER);
        
        // Combine header and filter
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setOpaque(false);
        topContainer.add(headerPanel, BorderLayout.NORTH);
        topContainer.add(filterContainer, BorderLayout.CENTER);
        
        mainPanel.add(topContainer, BorderLayout.NORTH);
        mainPanel.add(tableContainer, BorderLayout.CENTER);
        
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
        
        java.awt.EventQueue.invokeLater(() -> new laporanpenggajian().setVisible(true));
    }
}
