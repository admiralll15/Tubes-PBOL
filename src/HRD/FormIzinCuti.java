package HRD;

import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import model.Cutimodel;
import model.GUITemplate;
import model.UIScaler;

public class FormIzinCuti extends JFrame {
    
    private Cutimodel cutiModel = new Cutimodel();
    private JTable dataTable;
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(FormIzinCuti.class.getName());

    public FormIzinCuti() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        model.AppIcon.setFrameIcon(this);
        loadDataCuti();
        UIScaler.scaleContainer(this.getContentPane());
    }
    
    private void loadDataCuti() {
        String[] kolom = {"ID", "Nama", "Jabatan", "Tgl Mulai", "Tgl Selesai", "Keterangan", "Status", "Aksi"};
        
        DefaultTableModel model = new DefaultTableModel(null, kolom) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Only Aksi column is editable
            }
        };

        try {
            ResultSet rs = cutiModel.getStatusCuti();
            
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
                    rs.getString("tanggal_mulai"),
                    rs.getString("tanggal_selesai"),
                    rs.getString("keterangan"),
                    rs.getString("status"),
                    "Aksi"
                });
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
            
            // Render buttons in Aksi column
            dataTable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
            dataTable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), this));
            
            // Set column widths
            dataTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            dataTable.getColumnModel().getColumn(1).setPreferredWidth(120);
            dataTable.getColumnModel().getColumn(7).setPreferredWidth(150);

        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error loading data", e);
            JOptionPane.showMessageDialog(this, "Gagal memload data: " + e.getMessage());
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Laporan Izin dan Cuti");
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
        JPanel headerPanel = GUITemplate.createHeaderPanel("LAPORAN IZIN DAN CUTI");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
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
        tableContainer.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        tableContainer.add(tablePanel, BorderLayout.CENTER);
        
        mainPanel.add(tableContainer, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        pack();
    }
    
    // Button Renderer untuk kolom Aksi
    private static class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton btnSetuju, btnTolak;
        
        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
            setBackground(GUITemplate.BG_WHITE);
            
            btnSetuju = new JButton("Setuju");
            btnSetuju.setBackground(new Color(76, 175, 80));
            btnSetuju.setForeground(Color.WHITE);
            btnSetuju.setFocusPainted(false);
            btnSetuju.setFont(new Font("Arial", Font.PLAIN, 11));
            
            btnTolak = new JButton("Tolak");
            btnTolak.setBackground(new Color(244, 67, 54));
            btnTolak.setForeground(Color.WHITE);
            btnTolak.setFocusPainted(false);
            btnTolak.setFont(new Font("Arial", Font.PLAIN, 11));
            
            add(btnSetuju);
            add(btnTolak);
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
        private JButton btnSetuju, btnTolak;
        private JPanel panel;
        private FormIzinCuti parent;
        private int idCuti;
        private Cutimodel cutiModel = new Cutimodel();
        
        public ButtonEditor(JCheckBox checkBox, FormIzinCuti parent) {
            super(checkBox);
            this.parent = parent;
            
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
            panel.setBackground(GUITemplate.BG_WHITE);
            
            btnSetuju = new JButton("Setuju");
            btnSetuju.setBackground(new Color(76, 175, 80));
            btnSetuju.setForeground(Color.WHITE);
            btnSetuju.setFocusPainted(false);
            btnSetuju.setFont(new Font("Arial", Font.PLAIN, 11));
            
            btnTolak = new JButton("Tolak");
            btnTolak.setBackground(new Color(244, 67, 54));
            btnTolak.setForeground(Color.WHITE);
            btnTolak.setFocusPainted(false);
            btnTolak.setFont(new Font("Arial", Font.PLAIN, 11));
            
            btnSetuju.addActionListener(e -> handleSetuju());
            btnTolak.addActionListener(e -> handleTolak());
            
            panel.add(btnSetuju);
            panel.add(btnTolak);
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, 
                                                     int row, int column) {
            try {
                idCuti = Integer.parseInt((String) table.getValueAt(row, 0));
            } catch (Exception e) {
                idCuti = 0;
            }
            if (isSelected) {
                panel.setBackground(new Color(200, 220, 255));
            } else {
                panel.setBackground(GUITemplate.BG_WHITE);
            }
            return panel;
        }
        
        private void handleSetuju() {
            int result = JOptionPane.showConfirmDialog(parent, "Setujui permohonan cuti #" + idCuti + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (cutiModel.updateStatusCuti(idCuti, "Disetujui")) {
                    JOptionPane.showMessageDialog(parent, "✓ Permohonan cuti #" + idCuti + " DISETUJUI\n\nKaryawan akan menerima notifikasi", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    parent.loadDataCuti(); // Refresh table
                } else {
                    JOptionPane.showMessageDialog(parent, "✗ Gagal mengupdate status cuti", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            fireEditingStopped();
        }
        
        private void handleTolak() {
            int result = JOptionPane.showConfirmDialog(parent, "Tolak permohonan cuti #" + idCuti + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                if (cutiModel.updateStatusCuti(idCuti, "Ditolak")) {
                    JOptionPane.showMessageDialog(parent, "✗ Permohonan cuti #" + idCuti + " DITOLAK\n\nKaryawan akan menerima notifikasi", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    parent.loadDataCuti(); // Refresh table
                } else {
                    JOptionPane.showMessageDialog(parent, "✗ Gagal mengupdate status cuti", "Error", JOptionPane.ERROR_MESSAGE);
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
        
        java.awt.EventQueue.invokeLater(() -> new FormIzinCuti().setVisible(true));
    }
}
