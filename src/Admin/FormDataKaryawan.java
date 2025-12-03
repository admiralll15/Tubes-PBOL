package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import model.Karyawanmodel;
import model.GUITemplate;
import model.UIScaler;

/**
 * FormDataKaryawan - Rewritten dengan hardcode, tema putih-biru
 * Data karyawan dalam bentuk tabel
 */
public class FormDataKaryawan extends JFrame {
    
    private Karyawanmodel karyModel = new Karyawanmodel();
    private JTable dataTable;
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(FormDataKaryawan.class.getName());

    public FormDataKaryawan() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        loadDataKaryawan();
        UIScaler.scaleContainer(this.getContentPane());
    }
    
    private void loadDataKaryawan() {
        String[] kolom = {"ID", "Nama", "Jabatan", "Aksi"};
        DefaultTableModel model = new DefaultTableModel(null, kolom) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only Aksi column is editable
            }
        };
        
        try {
            java.sql.ResultSet rs = karyModel.getAllKaryawan();
            
            if (rs == null) {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                dataTable.setModel(model);
                return;
            }
            
            List<Object[]> dataList = new ArrayList<>();
            while (rs.next()) {
                dataList.add(new Object[] {
                    rs.getString("id"),
                    rs.getString("nama"),
                    rs.getString("jabatan"),
                    "Aksi"
                });
            }
            
            for (Object[] row : dataList) {
                model.addRow(row);
            }
            
            dataTable.setModel(model);
            // Styling handled by createModernTable
            dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            dataTable.setGridColor(new Color(230, 230, 230));
            dataTable.setShowGrid(true);
            
            // Render buttons in Aksi column
            dataTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
            dataTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), this));
            
            // Set column widths
            dataTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            dataTable.getColumnModel().getColumn(1).setPreferredWidth(150);
            dataTable.getColumnModel().getColumn(2).setPreferredWidth(120);
            dataTable.getColumnModel().getColumn(3).setPreferredWidth(150);
            
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error loading data", e);
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data Karyawan");
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
        JPanel headerPanel = GUITemplate.createHeaderPanel("DATA KARYAWAN");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Table
        dataTable = GUITemplate.createModernTable(new DefaultTableModel());
        dataTable.setSelectionBackground(new Color(200, 220, 255));
        
        JScrollPane scrollPane = GUITemplate.createModernScrollPane(dataTable);
        
        JPanel tablePanel = GUITemplate.createRoundedPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Container for table panel with padding
        JPanel contentContainer = new JPanel(new BorderLayout());
        contentContainer.setOpaque(false);
        contentContainer.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        contentContainer.add(tablePanel, BorderLayout.CENTER);
        
        mainPanel.add(contentContainer, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        pack();
    }
    
    // Button Renderer untuk kolom Aksi
    private static class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton btnAktifkan, btnNonaktifkan;
        
        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
            setBackground(GUITemplate.BG_WHITE);
            
            btnAktifkan = new JButton("Aktifkan");
            btnAktifkan.setBackground(new Color(76, 175, 80));
            btnAktifkan.setForeground(Color.WHITE);
            btnAktifkan.setFocusPainted(false);
            btnAktifkan.setFont(new Font("Arial", Font.PLAIN, 11));
            
            btnNonaktifkan = new JButton("Nonaktifkan");
            btnNonaktifkan.setBackground(new Color(244, 67, 54));
            btnNonaktifkan.setForeground(Color.WHITE);
            btnNonaktifkan.setFocusPainted(false);
            btnNonaktifkan.setFont(new Font("Arial", Font.PLAIN, 11));
            
            add(btnAktifkan);
            add(btnNonaktifkan);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                       boolean hasFocus, int row, int column) {
            if (isSelected) {
                setBackground(new Color(200, 220, 255));
            } else {
                setBackground(GUITemplate.BG_WHITE);
            }
            return this;
        }
    }
    
    // Button Editor untuk kolom Aksi
    private static class ButtonEditor extends DefaultCellEditor {
        private JButton btnAktifkan, btnNonaktifkan;
        private JPanel panel;
        private FormDataKaryawan parent;
        private String idKaryawan;
        private Karyawanmodel karyModel = new Karyawanmodel();
        
        public ButtonEditor(JCheckBox checkBox, FormDataKaryawan parent) {
            super(checkBox);
            this.parent = parent;
            
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
            panel.setBackground(GUITemplate.BG_WHITE);
            
            btnAktifkan = new JButton("Aktifkan");
            btnAktifkan.setBackground(new Color(76, 175, 80));
            btnAktifkan.setForeground(Color.WHITE);
            btnAktifkan.setFocusPainted(false);
            btnAktifkan.setFont(new Font("Arial", Font.PLAIN, 11));
            
            btnNonaktifkan = new JButton("Nonaktifkan");
            btnNonaktifkan.setBackground(new Color(244, 67, 54));
            btnNonaktifkan.setForeground(Color.WHITE);
            btnNonaktifkan.setFocusPainted(false);
            btnNonaktifkan.setFont(new Font("Arial", Font.PLAIN, 11));
            
            btnAktifkan.addActionListener(e -> handleAktifkan());
            btnNonaktifkan.addActionListener(e -> handleNonaktifkan());
            
            panel.add(btnAktifkan);
            panel.add(btnNonaktifkan);
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, 
                                                     int row, int column) {
            idKaryawan = (String) table.getValueAt(row, 0);
            if (isSelected) {
                panel.setBackground(new Color(200, 220, 255));
            } else {
                panel.setBackground(GUITemplate.BG_WHITE);
            }
            return panel;
        }
        
        private void handleAktifkan() {
            if (karyModel.updateStatusAkun(idKaryawan, "Aktif")) {
                JOptionPane.showMessageDialog(parent, "✓ Akun karyawan " + idKaryawan + " DIAKTIFKAN\n\nKaryawan dapat login kembali", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                parent.loadDataKaryawan(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(parent, "✗ Gagal mengaktifkan akun", "Error", JOptionPane.ERROR_MESSAGE);
            }
            fireEditingStopped();
        }
        
        private void handleNonaktifkan() {
            int result = JOptionPane.showConfirmDialog(parent, "Nonaktifkan akun " + idKaryawan + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (karyModel.updateStatusAkun(idKaryawan, "Nonaktif")) {
                    JOptionPane.showMessageDialog(parent, "✗ Akun karyawan " + idKaryawan + " DINONAKTIFKAN\n\nKaryawan tidak dapat login", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    parent.loadDataKaryawan(); // Refresh table
                } else {
                    JOptionPane.showMessageDialog(parent, "✗ Gagal menonaktifkan akun", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            fireEditingStopped();
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
        
        java.awt.EventQueue.invokeLater(() -> new FormDataKaryawan().setVisible(true));
    }
}
