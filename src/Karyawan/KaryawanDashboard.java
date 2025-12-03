package Karyawan;

import javax.swing.*;
import java.awt.*;
import model.GUITemplate;
import model.UIScaler;
import model.UserSession;

/**
 * KaryawanDashboard - Modern enterprise design dengan 6 fitur premium
 */
public class KaryawanDashboard extends JFrame {
    
    private static final java.util.logging.Logger logger = 
        java.util.logging.Logger.getLogger(KaryawanDashboard.class.getName());
    
    public KaryawanDashboard() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        UIScaler.scaleContainer(this.getContentPane());
    }
    
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Karyawan Dashboard - STAFFLINK");
        setResizable(true);
        
        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(GUITemplate.BG_WHITE);
        
        // Header
        JPanel headerPanel = GUITemplate.createHeaderPanel("STAFFLINK - KARYAWAN DASHBOARD");
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
        String userName = UserSession.getUserName() != null ? UserSession.getUserName() : "Karyawan";
        JPanel welcomeBanner = GUITemplate.createWelcomeBanner(userName, "Karyawan", "Sudah Absen Hari Ini");
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
        
        JLabel subLabel = new JLabel("Employee Panel");
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
        JButton btnPengajuanCuti = GUITemplate.createSidebarMenuItem("📝", "Pengajuan Cuti", false);
        btnPengajuanCuti.addActionListener(e -> openPengajuanCuti());
        sidebar.add(btnPengajuanCuti);
        sidebar.add(Box.createVerticalStrut(5));
        
        JButton btnSlipGaji = GUITemplate.createSidebarMenuItem("💰", "Slip Gaji", false);
        btnSlipGaji.addActionListener(e -> openSlipGaji());
        sidebar.add(btnSlipGaji);
        sidebar.add(Box.createVerticalStrut(5));
        
        JButton btnStatusCuti = GUITemplate.createSidebarMenuItem("✓", "Status Cuti", false);
        btnStatusCuti.addActionListener(e -> openStatusCuti());
        sidebar.add(btnStatusCuti);
        sidebar.add(Box.createVerticalStrut(5));
        
        JButton btnAbsensi = GUITemplate.createSidebarMenuItem("📊", "Absensi Harian", false);
        btnAbsensi.addActionListener(e -> openAbsensiHarian());
        sidebar.add(btnAbsensi);
        
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
            "🏖️",
            "12",
            "Saldo Cuti",
            "Sisa hari cuti",
            new Color(3, 155, 229),
            new Color(2, 136, 209)
        );
        
        JPanel card2 = GUITemplate.createInfoCard(
            "📅",
            "22/23",
            "Kehadiran Bulan Ini",
            "96% kehadiran ↑",
            new Color(0, 172, 193),
            new Color(0, 151, 167)
        );
        
        JPanel card3 = GUITemplate.createInfoCard(
            "💵",
            "Tersedia",
            "Gaji Bulan Ini",
            "Sudah diterima ✓",
            new Color(76, 175, 80),
            new Color(56, 142, 60)
        );
        
        JPanel card4 = GUITemplate.createInfoCard(
            "📝",
            "1",
            "Pengajuan Cuti",
            "Menunggu Approval",
            GUITemplate.WARNING_YELLOW,
            new Color(251, 176, 64)
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
            "📝",
            "Pengajuan Cuti",
            "Ajukan permohonan cuti atau izin",
            new Color(156, 39, 176),
            new Color(123, 31, 162)
        );
        card1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openPengajuanCuti();
            }
        });
        
        JPanel card2 = GUITemplate.createGradientCard(
            "💰",
            "Slip Gaji",
            "Lihat dan download slip gaji",
            new Color(76, 175, 80),
            new Color(56, 142, 60)
        );
        card2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openSlipGaji();
            }
        });
        
        JPanel card3 = GUITemplate.createGradientCard(
            "✓",
            "Status Cuti",
            "Cek status permohonan cuti Anda",
            new Color(33, 150, 243),
            new Color(25, 118, 210)
        );
        card3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openStatusCuti();
            }
        });
        
        JPanel card4 = GUITemplate.createGradientCard(
            "📊",
            "Absensi Harian",
            "Lihat riwayat absensi Anda",
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
            "✓",
            "Anda sudah absen masuk hari ini pukul 08:05 WIB",
            "Hari ini, 08:05",
            GUITemplate.SUCCESS_GREEN
        );
        
        JPanel item2 = GUITemplate.createActivityItem(
            "💰",
            "Slip gaji bulan November tersedia untuk diunduh",
            "2 hari lalu",
            GUITemplate.PRIMARY
        );
        
        JPanel item3 = GUITemplate.createActivityItem(
            "📝",
            "Permohonan cuti Anda (15-17 Des) sedang diproses",
            "Kemarin, 14:30",
            GUITemplate.WARNING_YELLOW
        );
        
        JPanel item4 = GUITemplate.createActivityItem(
            "✓",
            "Permohonan cuti Anda (1-3 Des) telah disetujui",
            "5 hari lalu",
            GUITemplate.SUCCESS_GREEN
        );
        
        JPanel item5 = GUITemplate.createActivityItem(
            "📅",
            "Kehadiran bulan ini: 22 dari 23 hari kerja (96%)",
            "1 minggu lalu",
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
    
    private void openPengajuanCuti() {
        FormPengajuanCuti form = new FormPengajuanCuti();
        form.setVisible(true);
    }
    
    private void openSlipGaji() {
        FormSlipGaji form = new FormSlipGaji();
        form.setVisible(true);
    }
    
    private void openStatusCuti() {
        FormStatusCuti form = new FormStatusCuti();
        form.setVisible(true);
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
        
        java.awt.EventQueue.invokeLater(() -> new KaryawanDashboard().setVisible(true));
    }
}
