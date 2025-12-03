package HRD;

import javax.swing.*;
import java.awt.*;
import model.GUITemplate;
import model.UIScaler;
import model.UserSession;

/**
 * HRDDashboard - Modern enterprise design dengan 6 fitur premium
 */
public class HRDDashboard extends JFrame {
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(HRDDashboard.class.getName());
    
    public HRDDashboard() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        UIScaler.scaleContainer(this.getContentPane());
    }
    
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("HRD Dashboard - STAFFLINK");
        setResizable(true);
        
        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(GUITemplate.BG_WHITE);
        
        // Header
        JPanel headerPanel = GUITemplate.createHeaderPanel("STAFFLINK - HRD DASHBOARD");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content area
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(GUITemplate.BG_WHITE);
        
        // Sidebar
        JPanel sidebar = createSidebar();
        contentArea.add(sidebar, BorderLayout.WEST);
        
        // Main content
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(GUITemplate.BG_WHITE);
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Welcome Banner
        String userName = UserSession.getUserName() != null ? UserSession.getUserName() : "HR Manager";
        JPanel welcomeBanner = GUITemplate.createWelcomeBanner(userName, "HRD", "Dashboard Aktif");
        welcomeBanner.setAlignmentX(Component.LEFT_ALIGNMENT);
        welcomeBanner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        mainContent.add(welcomeBanner);
        mainContent.add(Box.createVerticalStrut(20));
        
        // Info Cards
        JPanel infoCardsPanel = createInfoCards();
        infoCardsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainContent.add(infoCardsPanel);
        mainContent.add(Box.createVerticalStrut(25));
        
        // Feature Cards Section
        JPanel featureSectionLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        featureSectionLabel.setOpaque(false);
        JLabel sectionLabel = new JLabel("Menu Utama");
        sectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionLabel.setForeground(GUITemplate.TEXT_DARK);
        featureSectionLabel.add(sectionLabel);
        featureSectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        featureSectionLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        mainContent.add(featureSectionLabel);
        mainContent.add(Box.createVerticalStrut(10));
        
        JPanel featureCardsPanel = createFeatureCards();
        featureCardsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainContent.add(featureCardsPanel);
        mainContent.add(Box.createVerticalStrut(25));
        
        // Activity Feed
        JPanel activitySection = createActivityFeed();
        activitySection.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainContent.add(activitySection);
        
        mainContent.add(Box.createVerticalGlue());
        
        JScrollPane scrollPane = GUITemplate.createModernScrollPane(mainContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentArea.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(contentArea, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        pack();
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = GUITemplate.createSidebar(220);
        
        // Logo
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 30, 15));
        
        JLabel logoLabel = new JLabel("STAFFLINK");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logoLabel.setForeground(GUITemplate.TEXT_LIGHT);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subLabel = new JLabel("HRD Panel");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLabel.setForeground(new Color(255, 255, 255, 180));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        logoPanel.add(logoLabel);
        logoPanel.add(Box.createVerticalStrut(5));
        logoPanel.add(subLabel);
        
        sidebar.add(logoPanel);
        sidebar.add(Box.createVerticalStrut(10));
        
        JSeparator sep1 = new JSeparator();
        sep1.setForeground(new Color(255, 255, 255, 50));
        sep1.setMaximumSize(new Dimension(200, 1));
        sidebar.add(sep1);
        sidebar.add(Box.createVerticalStrut(20));
        
        // Menu items
        JButton btnHitungGaji = GUITemplate.createSidebarMenuItem("💰", "Hitung Gaji", false);
        btnHitungGaji.addActionListener(e -> openHitungGaji());
        sidebar.add(btnHitungGaji);
        sidebar.add(Box.createVerticalStrut(5));
        
        JButton btnIzinCuti = GUITemplate.createSidebarMenuItem("📋", "Izin Cuti", false);
        btnIzinCuti.addActionListener(e -> openFormIzinCuti());
        sidebar.add(btnIzinCuti);
        sidebar.add(Box.createVerticalStrut(5));
        
        JButton btnLaporanAbsensi = GUITemplate.createSidebarMenuItem("📊", "Laporan Absensi", false);
        btnLaporanAbsensi.addActionListener(e -> openLaporanAbsensi());
        sidebar.add(btnLaporanAbsensi);
        sidebar.add(Box.createVerticalStrut(5));
        
        JButton btnAbsensiHarian = GUITemplate.createSidebarMenuItem("✓", "Absensi Harian", false);
        btnAbsensiHarian.addActionListener(e -> openAbsensiHarian());
        sidebar.add(btnAbsensiHarian);
        
        sidebar.add(Box.createVerticalGlue());
        
        JSeparator sep2 = new JSeparator();
        sep2.setForeground(new Color(255, 255, 255, 50));
        sep2.setMaximumSize(new Dimension(200, 1));
        sidebar.add(sep2);
        sidebar.add(Box.createVerticalStrut(15));
        
        JButton btnLogout = GUITemplate.createSidebarMenuItem("🚪", "Logout", false);
        btnLogout.addActionListener(e -> logout());
        sidebar.add(btnLogout);
        sidebar.add(Box.createVerticalStrut(10));
        
        return sidebar;
    }
    
    private JPanel createInfoCards() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        
        JPanel card1 = GUITemplate.createInfoCard(
            "💰",
            "Rp 450M",
            "Total Gaji Bulan Ini",
            "150 karyawan",
            new Color(76, 175, 80),
            new Color(56, 142, 60)
        );
        
        JPanel card2 = GUITemplate.createInfoCard(
            "📝",
            "12",
            "Pending Cuti",
            "Perlu Review",
            new Color(3, 155, 229),
            new Color(2, 136, 209)
        );
        
        JPanel card3 = GUITemplate.createInfoCard(
            "✓",
            "95%",
            "Kehadiran Hari Ini",
            "142/150 hadir ↑",
            new Color(0, 172, 193),
            new Color(0, 151, 167)
        );
        
        JPanel card4 = GUITemplate.createInfoCard(
            "📄",
            "150",
            "Slip Gaji Terproses",
            "Semua selesai ✓",
            GUITemplate.PRIMARY,
            GUITemplate.PRIMARY_DARK
        );
        
        panel.add(card1);
        panel.add(card2);
        panel.add(card3);
        panel.add(card4);
        
        return panel;
    }
    
    private JPanel createFeatureCards() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));
        
        JPanel card1 = GUITemplate.createGradientCard(
            "💰",
            "Hitung Gaji",
            "Proses dan hitung gaji karyawan",
            new Color(76, 175, 80),
            new Color(56, 142, 60)
        );
        card1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openHitungGaji();
            }
        });
        
        JPanel card2 = GUITemplate.createGradientCard(
            "📋",
            "Izin Cuti",
            "Kelola permohonan izin dan cuti",
            new Color(156, 39, 176),
            new Color(123, 31, 162)
        );
        card2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openFormIzinCuti();
            }
        });
        
        JPanel card3 = GUITemplate.createGradientCard(
            "📊",
            "Laporan Absensi",
            "Monitor dan analisis kehadiran",
            new Color(33, 150, 243),
            new Color(25, 118, 210)
        );
        card3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openLaporanAbsensi();
            }
        });
        
        JPanel card4 = GUITemplate.createGradientCard(
            "✓",
            "Absensi Harian",
            "Kelola absensi harian karyawan",
            GUITemplate.PRIMARY,
            GUITemplate.PRIMARY_DARK
        );
        card4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openAbsensiHarian();
            }
        });
        
        panel.add(card1);
        panel.add(card2);
        panel.add(card3);
        panel.add(card4);
        
        return panel;
    }
    
    private JPanel createActivityFeed() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setOpaque(false);
        JLabel header = new JLabel("Aktivitas Terkini");
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setForeground(GUITemplate.TEXT_DARK);
        headerPanel.add(header);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        section.add(headerPanel);
        
        JPanel activityContainer = new JPanel();
        activityContainer.setLayout(new BoxLayout(activityContainer, BoxLayout.Y_AXIS));
        activityContainer.setBackground(GUITemplate.BG_WHITE);
        activityContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUITemplate.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        activityContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        
        JPanel item1 = GUITemplate.createActivityItem(
            "💰",
            "Gaji karyawan bulan Desember dihitung - 150 karyawan",
            "1 jam lalu",
            GUITemplate.SUCCESS_GREEN
        );
        
        JPanel item2 = GUITemplate.createActivityItem(
            "📝",
            "Permohonan cuti dari 'Ani Wijaya' disetujui",
            "Hari ini, 11:30",
            GUITemplate.PRIMARY
        );
        
        JPanel item3 = GUITemplate.createActivityItem(
            "📄",
            "Slip gaji dikirim ke 150 karyawan via email",
            "Hari ini, 10:00",
            GUITemplate.SUCCESS_GREEN
        );
        
        JPanel item4 = GUITemplate.createActivityItem(
            "📋",
            "12 permohonan cuti baru menunggu review",
            "Hari ini, 09:00",
            GUITemplate.WARNING_YELLOW
        );
        
        JPanel item5 = GUITemplate.createActivityItem(
            "✓",
            "142 dari 150 karyawan sudah absen hari ini",
            "Hari ini, 08:30",
            GUITemplate.SUCCESS_GREEN
        );
        
        activityContainer.add(item1);
        activityContainer.add(Box.createVerticalStrut(2));
        activityContainer.add(new JSeparator());
        activityContainer.add(item2);
        activityContainer.add(Box.createVerticalStrut(2));
        activityContainer.add(new JSeparator());
        activityContainer.add(item3);
        activityContainer.add(Box.createVerticalStrut(2));
        activityContainer.add(new JSeparator());
        activityContainer.add(item4);
        activityContainer.add(Box.createVerticalStrut(2));
        activityContainer.add(new JSeparator());
        activityContainer.add(item5);
        
        section.add(activityContainer);
        
        return section;
    }
    
    private void openHitungGaji() {
        FormHitungGaji form = new FormHitungGaji();
        form.setVisible(true);
    }
    
    private void openFormIzinCuti() {
        FormIzinCuti form = new FormIzinCuti();
        form.setVisible(true);
    }
    
    private void openLaporanAbsensi() {
        try {
            Class<?> clazz = Class.forName("Admin.laporanabsensi");
            Object obj = clazz.getDeclaredConstructor().newInstance();
            JFrame frame = (JFrame) obj;
            frame.setVisible(true);
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error opening laporan absensi", ex);
        }
    }
    
    private void openAbsensiHarian() {
        try {
            Class<?> clazz = Class.forName("shared.FormAbsensiKaryawan");
            Object obj = clazz.getDeclaredConstructor().newInstance();
            JFrame frame = (JFrame) obj;
            frame.setVisible(true);
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error opening absensi harian", ex);
        }
    }
    
    private void logout() {
        this.dispose();
        try {
            Class<?> clazz = Class.forName("Form.DashboardAwal");
            Object obj = clazz.getDeclaredConstructor().newInstance();
            JFrame frame = (JFrame) obj;
            frame.setVisible(true);
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Error logout", ex);
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
        
        java.awt.EventQueue.invokeLater(() -> new HRDDashboard().setVisible(true));
    }
}
