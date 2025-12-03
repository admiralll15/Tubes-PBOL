package Admin;

import javax.swing.*;
import java.awt.*;
import model.GUITemplate;
import model.UIScaler;
import model.UserSession;

/**
 * AdminDashboard - Modern enterprise design dengan 6 fitur premium:
 * 1. Info cards dengan statistik
 * 2. Animasi hover dinamis
 * 3. Sidebar navigation
 * 4. Welcome banner
 * 5. Recent activity feed
 * 6. Gradient cards dengan icons
 */
public class AdminDashboard extends JFrame {
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(AdminDashboard.class.getName());
    
    public AdminDashboard() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        UIScaler.scaleContainer(this.getContentPane());
    }
    
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Dashboard - STAFFLINK");
        setResizable(true);
        
        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(GUITemplate.BG_WHITE);
        
        // Header
        JPanel headerPanel = GUITemplate.createHeaderPanel("STAFFLINK - ADMIN DASHBOARD");
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content area (Sidebar + Main Content)
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(GUITemplate.BG_WHITE);
        
        // ===== SIDEBAR NAVIGATION =====
        JPanel sidebar = createSidebar();
        contentArea.add(sidebar, BorderLayout.WEST);
        
        // ===== MAIN CONTENT AREA =====
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(GUITemplate.BG_WHITE);
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Welcome Banner
        String userName = UserSession.getUserName() != null ? UserSession.getUserName() : "Administrator";
        JPanel welcomeBanner = GUITemplate.createWelcomeBanner(userName, "Admin", "Dashboard Aktif");
        welcomeBanner.setAlignmentX(Component.LEFT_ALIGNMENT);
        welcomeBanner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        mainContent.add(welcomeBanner);
        mainContent.add(Box.createVerticalStrut(20));
        
        // ===== INFO CARDS =====
        JPanel infoCardsPanel = createInfoCards();
        infoCardsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainContent.add(infoCardsPanel);
        mainContent.add(Box.createVerticalStrut(25));
        
        // ===== FEATURE CARDS (Navigation) =====
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
        
        // ===== RECENT ACTIVITY FEED =====
        JPanel activitySection = createActivityFeed();
        activitySection.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainContent.add(activitySection);
        
        // Add glue to push everything to top
        mainContent.add(Box.createVerticalGlue());
        
        // Wrap main content in scroll pane
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
        
        // Logo/Title area
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 30, 15));
        
        JLabel logoLabel = new JLabel("STAFFLINK");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logoLabel.setForeground(GUITemplate.TEXT_LIGHT);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subLabel = new JLabel("Admin Panel");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLabel.setForeground(new Color(255, 255, 255, 180));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        logoPanel.add(logoLabel);
        logoPanel.add(Box.createVerticalStrut(5));
        logoPanel.add(subLabel);
        
        sidebar.add(logoPanel);
        sidebar.add(Box.createVerticalStrut(10));
        
        // Separator
        JSeparator sep1 = new JSeparator();
        sep1.setForeground(new Color(255, 255, 255, 50));
        sep1.setMaximumSize(new Dimension(200, 1));
        sidebar.add(sep1);
        sidebar.add(Box.createVerticalStrut(20));
        
        // Menu items
        JButton btnAbsensi = GUITemplate.createSidebarMenuItem("✓", "Absensi Harian", false);
        btnAbsensi.addActionListener(e -> openAbsensiHarian());
        sidebar.add(btnAbsensi);
        sidebar.add(Box.createVerticalStrut(5));
        
        JButton btnDataKaryawan = GUITemplate.createSidebarMenuItem("👥", "Data Karyawan", false);
        btnDataKaryawan.addActionListener(e -> openDataKaryawan());
        sidebar.add(btnDataKaryawan);
        sidebar.add(Box.createVerticalStrut(5));
        
        JButton btnLaporanAbsensi = GUITemplate.createSidebarMenuItem("📊", "Laporan Absensi", false);
        btnLaporanAbsensi.addActionListener(e -> openLaporanAbsensi());
        sidebar.add(btnLaporanAbsensi);
        sidebar.add(Box.createVerticalStrut(5));
        
        JButton btnLaporanGaji = GUITemplate.createSidebarMenuItem("💰", "Laporan Gaji", false);
        btnLaporanGaji.addActionListener(e -> openLaporanPenggajian());
        sidebar.add(btnLaporanGaji);
        
        // Push logout to bottom
        sidebar.add(Box.createVerticalGlue());
        
        // Separator
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
        
        // Card 1: Total Karyawan
        JPanel card1 = GUITemplate.createInfoCard(
            "👥",
            "150",
            "Total Karyawan",
            "+5 bulan ini ↑",
            GUITemplate.PRIMARY,
            GUITemplate.PRIMARY_DARK
        );
        
        // Card 2: Absensi Hari Ini
        JPanel card2 = GUITemplate.createInfoCard(
            "✓",
            "142",
            "Hadir Hari Ini",
            "95% kehadiran ↑",
            new Color(0, 172, 193),
            new Color(0, 151, 167)
        );
        
        // Card 3: Pending Approval
        JPanel card3 = GUITemplate.createInfoCard(
            "📋",
            "5",
            "Pending Approval",
            "Perlu Tindakan",
            new Color(3, 155, 229),
            new Color(2, 136, 209)
        );
        
        // Card 4: Laporan Bulan Ini
        JPanel card4 = GUITemplate.createInfoCard(
            "📊",
            "100%",
            "Laporan Lengkap",
            "Semua terproses ✓",
            new Color(38, 166, 154),
            new Color(0, 150, 136)
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
        
        // Card 1: Absensi Harian
        JPanel card1 = GUITemplate.createGradientCard(
            "✓",
            "Absensi Harian",
            "Kelola kehadiran karyawan harian",
            GUITemplate.PRIMARY,
            GUITemplate.PRIMARY_DARK
        );
        card1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openAbsensiHarian();
            }
        });
        
        // Card 2: Data Karyawan
        JPanel card2 = GUITemplate.createGradientCard(
            "👥",
            "Data Karyawan",
            "Manajemen data dan profil karyawan",
            new Color(156, 39, 176),
            new Color(123, 31, 162)
        );
        card2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openDataKaryawan();
            }
        });
        
        // Card 3: Laporan Absensi
        JPanel card3 = GUITemplate.createGradientCard(
            "📊",
            "Laporan Absensi",
            "Lihat dan export laporan kehadiran",
            new Color(33, 150, 243),
            new Color(25, 118, 210)
        );
        card3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openLaporanAbsensi();
            }
        });
        
        // Card 4: Laporan Penggajian
        JPanel card4 = GUITemplate.createGradientCard(
            "💰",
            "Laporan Penggajian",
            "Kelola dan monitor penggajian",
            new Color(76, 175, 80),
            new Color(56, 142, 60)
        );
        card4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openLaporanPenggajian();
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
        
        // Section header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setOpaque(false);
        JLabel header = new JLabel("Aktivitas Terkini");
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setForeground(GUITemplate.TEXT_DARK);
        headerPanel.add(header);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        section.add(headerPanel);
        
        // Activity container with border
        JPanel activityContainer = new JPanel();
        activityContainer.setLayout(new BoxLayout(activityContainer, BoxLayout.Y_AXIS));
        activityContainer.setBackground(GUITemplate.BG_WHITE);
        activityContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GUITemplate.BORDER_LIGHT, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        activityContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        
        // Activity items
        JPanel item1 = GUITemplate.createActivityItem(
            "👤",
            "Data karyawan 'Budi Santoso' ditambahkan",
            "2 jam lalu",
            GUITemplate.PRIMARY
        );
        
        JPanel item2 = GUITemplate.createActivityItem(
            "📊",
            "Laporan absensi November berhasil digenerate",
            "Hari ini, 10:30",
            GUITemplate.SUCCESS_GREEN
        );
        
        JPanel item3 = GUITemplate.createActivityItem(
            "📋",
            "5 permohonan cuti menunggu approval",
            "Hari ini, 09:15",
            GUITemplate.WARNING_YELLOW
        );
        
        JPanel item4 = GUITemplate.createActivityItem(
            "✓",
            "142 karyawan telah absen hari ini",
            "Hari ini, 08:00",
            GUITemplate.SUCCESS_GREEN
        );
        
        JPanel item5 = GUITemplate.createActivityItem(
            "💰",
            "Proses penggajian bulan Desember dimulai",
            "Kemarin, 14:00",
            GUITemplate.INFO_BLUE
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
    
    private void openDataKaryawan() {
        FormDataKaryawan form = new FormDataKaryawan();
        form.setVisible(true);
    }
    
    private void openLaporanAbsensi() {
        laporanabsensi form = new laporanabsensi();
        form.setVisible(true);
    }
    
    private void openLaporanPenggajian() {
        laporanpenggajian form = new laporanpenggajian();
        form.setVisible(true);
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
        
        java.awt.EventQueue.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}
