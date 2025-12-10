package HRD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Lemburmodel;
import model.GUITemplate;
import model.UIScaler;

/**
 * laporanlembur - Laporan lembur dalam bentuk tabel dengan filter
 * Menggunakan view v_laporan_lembur dari database
 */
public class laporanlembur extends JFrame {
    
    private Lemburmodel lemburModel = new Lemburmodel();
    private JTable dataTable;
    private JComboBox<String> bulanCombo;
    private JComboBox<String> tahunCombo;
    private JButton filterButton;
    private JButton resetButton;
    private JButton exportButton;
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(laporanlembur.class.getName());

    public laporanlembur() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        model.AppIcon.setFrameIcon(this);
        loadDataLembur();
        UIScaler.scaleContainer(this.getContentPane());
    }
    
    private void loadDataLembur() {
        // Kolom sesuai v_laporan_lembur: id, id_karyawan, nama, jabatan, tanggal, jam_lembur, created_at
        String[] kolom = {"ID", "ID Karyawan", "Nama", "Jabatan", "Tanggal", "Jam Lembur"};
        DefaultTableModel model = new DefaultTableModel(null, kolom);
        
        try {
            java.sql.ResultSet rs = lemburModel.getLaporanLembur();
            
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
                        rs.getInt("id"),
                        rs.getString("id_karyawan"),
                        rs.getString("nama"),
                        rs.getString("jabatan"),
                        rs.getString("tanggal"),
                        rs.getInt("jam_lembur") + " jam"
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
            
            // Explicitly ensure grid lines are visible
            dataTable.setShowGrid(true);
            dataTable.setShowVerticalLines(true);
            dataTable.setShowHorizontalLines(true);
            dataTable.setIntercellSpacing(new Dimension(1, 1));
            dataTable.setGridColor(GUITemplate.BORDER_LIGHT);
            
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "Tidak ada data lembur", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error loading data", e);
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void filterDataLembur() {
        String bulanText = (String) bulanCombo.getSelectedItem();
        String tahun = (String) tahunCombo.getSelectedItem();
        
        // Convert nama bulan ke angka (1-12)
        String[] namaBulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        int bulan = 1;
        for (int i = 0; i < namaBulan.length; i++) {
            if (namaBulan[i].equals(bulanText)) {
                bulan = i + 1;
                break;
            }
        }
        
        String[] kolom = {"ID", "ID Karyawan", "Nama", "Jabatan", "Tanggal", "Jam Lembur"};
        DefaultTableModel model = new DefaultTableModel(null, kolom);
        
        try {
            java.sql.ResultSet rs = lemburModel.getLaporanLemburByPeriod(bulan, tahun);
            
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
                        rs.getInt("id"),
                        rs.getString("id_karyawan"),
                        rs.getString("nama"),
                        rs.getString("jabatan"),
                        rs.getString("tanggal"),
                        rs.getInt("jam_lembur") + " jam"
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
            dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            
            // Explicitly ensure grid lines are visible
            dataTable.setShowGrid(true);
            dataTable.setShowVerticalLines(true);
            dataTable.setShowHorizontalLines(true);
            dataTable.setIntercellSpacing(new Dimension(1, 1));
            dataTable.setGridColor(GUITemplate.BORDER_LIGHT);
            
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "Tidak ada data lembur untuk " + bulanText + " " + tahun, "Informasi", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Berhasil memuat " + rowCount + " data untuk " + bulanText + " " + tahun, "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error loading filtered data", e);
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Laporan Lembur");
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
        JPanel headerPanel = GUITemplate.createHeaderPanel("LAPORAN LEMBUR");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Filter panel
        JPanel filterPanel = GUITemplate.createRoundedPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel lblBulan = GUITemplate.createLabel("Bulan:");
        lblBulan.setFont(GUITemplate.FONT_LABEL_BOLD);
        filterPanel.add(lblBulan);
        
        bulanCombo = GUITemplate.createComboBox(new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"});
        bulanCombo.setPreferredSize(new Dimension(150, 40));
        filterPanel.add(bulanCombo);
        
        JLabel lblTahun = GUITemplate.createLabel("Tahun:");
        lblTahun.setFont(GUITemplate.FONT_LABEL_BOLD);
        filterPanel.add(lblTahun);
        
        tahunCombo = GUITemplate.createComboBox(new String[]{"2025", "2024", "2023", "2022", "2021", "2020"});
        tahunCombo.setPreferredSize(new Dimension(100, 40));
        filterPanel.add(tahunCombo);
        
        filterButton = GUITemplate.createEnhancedButton("FILTER", GUITemplate.PRIMARY);
        filterButton.setPreferredSize(new Dimension(100, 40));
        filterButton.addActionListener(evt -> filterDataLembur());
        filterPanel.add(filterButton);
        
        resetButton = GUITemplate.createEnhancedButton("RESET", GUITemplate.WARNING_YELLOW);
        resetButton.setPreferredSize(new Dimension(100, 40));
        resetButton.addActionListener(evt -> loadDataLembur());
        filterPanel.add(resetButton);
        
        exportButton = GUITemplate.createEnhancedButton("EXPORT", GUITemplate.SUCCESS_GREEN);
        exportButton.setPreferredSize(new Dimension(100, 40));
        exportButton.addActionListener(evt -> exportLembur());
        filterPanel.add(exportButton);
        
        JButton backButton = GUITemplate.createEnhancedButton("← KEMBALI", GUITemplate.PRIMARY);
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.addActionListener(e -> {
            this.dispose();
            new HRD.HRDDashboard().setVisible(true);
        });
        filterPanel.add(backButton);
        
        // Container for filter to add padding
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
    
    private void exportLembur() {
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(this);
            
            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = fc.getSelectedFile().getAbsolutePath() + ".txt";
                
                try (PrintWriter writer = new PrintWriter(new FileOutputStream(filePath))) {
                    writer.println("=".repeat(80));
                    writer.println("LAPORAN LEMBUR KARYAWAN");
                    writer.println("=".repeat(80));
                    writer.println();
                    
                    // Write table header
                    writer.printf("%-5s %-12s %-20s %-15s %-12s %-10s%n",
                        "ID", "ID Kary", "Nama", "Jabatan", "Tanggal", "Jam Lembur");
                    writer.println("-".repeat(80));
                    
                    // Write table data
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        writer.printf("%-5s %-12s %-20s %-15s %-12s %-10s%n",
                            dataTable.getValueAt(i, 0),
                            dataTable.getValueAt(i, 1),
                            dataTable.getValueAt(i, 2),
                            dataTable.getValueAt(i, 3),
                            dataTable.getValueAt(i, 4),
                            dataTable.getValueAt(i, 5));
                    }
                    
                    writer.println();
                    writer.println("=".repeat(80));
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
        
        java.awt.EventQueue.invokeLater(() -> new laporanlembur().setVisible(true));
    }
}
