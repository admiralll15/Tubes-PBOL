package model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Template utility class untuk membuat GUI hardcoded dengan tema modern dan
 * menarik
 */
public class GUITemplate {

    // ==================== PREMIUM BLUE & WHITE CORPORATE THEME
    // ====================
    // Professional color palette optimized for large corporate environments

    // Primary Colors - Vibrant Professional Blue Tones
    public static final Color PRIMARY = new Color(30, 136, 229); // #1E88E5 - Bright Blue (Primary actions)
    public static final Color PRIMARY_DARK = new Color(13, 71, 161); // #0D47A1 - Deep Blue (Accents)
    public static final Color ACCENT_CYAN = new Color(66, 165, 245); // #42A5F5 - Sky Blue (Highlights)
    public static final Color ACCENT_PINK = new Color(25, 118, 210); // #1976D2 - Blue-toned (Errors)
    public static final Color ACCENT_ORANGE = new Color(3, 155, 229); // #039BE5 - Ocean Blue (Warnings)

    // Background Colors - Clean White with Subtle Blue Tints
    public static final Color BG_GRADIENT_START = new Color(227, 242, 253); // #E3F2FD - Ultra Light Blue
    public static final Color BG_GRADIENT_END = new Color(255, 255, 255); // #FFFFFF - Pure White
    public static final Color BG_SECONDARY = new Color(250, 250, 250); // #FAFAFA - Off-White (Secondary panels)

    // Backward compatibility aliases
    public static final Color BG_WHITE = new Color(255, 255, 255); // #FFFFFF - Pure White
    public static final Color HEADER_BLUE = PRIMARY; // Bright Blue header
    public static final Color ACCENT_BLUE = PRIMARY;

    // Text Colors - Dark Blue for Professional Look
    public static final Color TEXT_DARK = new Color(26, 35, 126); // #1A237E - Dark Navy Blue (primary text)
    public static final Color TEXT_LIGHT = new Color(255, 255, 255); // #FFFFFF - Pure White (text on blue)
    public static final Color TEXT_SECONDARY = new Color(69, 90, 100); // #455A64 - Darker Blue Gray for better contrast
    public static final Color TEXT_MUTED = new Color(144, 164, 174); // #90A4AE - Light Blue Gray (tertiary)
    public static final Color TEXT_LIGHT_MUTED = new Color(255, 255, 255, 230); // White with higher opacity for clarity

    // Border Colors - Subtle Blue Tones
    public static final Color BORDER_LIGHT = new Color(187, 222, 251); // #BBDEFB - Light Blue (borders)
    public static final Color BORDER_FOCUS = new Color(30, 136, 229); // #1E88E5 - Bright Blue (focus)
    public static final Color BORDER_DARK = new Color(144, 202, 249); // #90CAF9 - Medium Light Blue

    // Action Colors - Blue-Themed Professional Palette
    public static final Color SUCCESS_GREEN = new Color(0, 172, 193); // #00ACC1 - Cyan (Success)
    public static final Color WARNING_YELLOW = new Color(3, 155, 229); // #039BE5 - Ocean Blue (Warnings)
    public static final Color ERROR_RED = new Color(25, 118, 210); // #1976D2 - Blue-toned (Errors)
    public static final Color INFO_BLUE = new Color(30, 136, 229); // #1E88E5 - Bright Blue (Info)

    // Glass Effect - Blue-Tinted Transparency
    public static final Color GLASS_OVERLAY = new Color(30, 136, 229, 20); // rgba(30,136,229,0.08)
    public static final Color GLASS_BORDER = new Color(30, 136, 229, 77); // rgba(30,136,229,0.3)

    // Font sizes
    public static final int FONT_TITLE = 28;
    public static final int FONT_HEADING = 20;
    public static final int FONT_LABEL = 14;
    public static final int FONT_BODY = 13;

    // Font - Modern sans-serif
    public static final Font FONT_TITLE_BOLD = new Font("Segoe UI", Font.BOLD, FONT_TITLE);
    public static final Font FONT_HEADING_BOLD = new Font("Segoe UI", Font.BOLD, FONT_HEADING);
    public static final Font FONT_LABEL_BOLD = new Font("Segoe UI", Font.BOLD, FONT_LABEL);
    public static final Font FONT_LABEL_REGULAR = new Font("Segoe UI", Font.PLAIN, FONT_LABEL);
    public static final Font FONT_BODY_REGULAR = new Font("Segoe UI", Font.PLAIN, FONT_BODY);

    // Utility untuk shadow & border radius
    private static final Stroke STROKE_BORDER = new BasicStroke(1.5f);

    /**
     * Buat header panel dengan gradient dan shadow effect
     */
    public static JPanel createHeaderPanel(String title) {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient dari PRIMARY ke PRIMARY_DARK
                GradientPaint gradient = new GradientPaint(
                        0, 0, PRIMARY,
                        0, getHeight(), PRIMARY_DARK);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Shadow effect di bawah
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.fillRect(0, getHeight() - 3, getWidth(), 3);
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_TITLE_BOLD);
        titleLabel.setForeground(TEXT_LIGHT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        return headerPanel;
    }

    /**
     * Buat label dengan styling standar
     */
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL_REGULAR);
        label.setForeground(TEXT_SECONDARY); // Light gray for dark backgrounds
        return label;
    }

    /**
     * Buat text field dengan rounded corner dan hover effect
     */
    public static JTextField createTextField() {
        JTextField textField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background - Pure white for clean look
                g2d.setColor(BG_WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);

                // Border - Blue theme
                g2d.setColor(hasFocus() ? BORDER_FOCUS : BORDER_LIGHT);
                g2d.setStroke(new BasicStroke(hasFocus() ? 2.0f : 1.5f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);

                super.paintComponent(g);
            }
        };
        textField.setFont(FONT_BODY_REGULAR);
        textField.setForeground(TEXT_DARK);
        textField.setCaretColor(PRIMARY);
        textField.setOpaque(false);
        textField.setBorder(new EmptyBorder(8, 12, 8, 12));
        textField.setPreferredSize(new Dimension(0, 42));
        return textField;
    }

    /**
     * Buat password field dengan rounded corner dan hover effect
     */
    public static JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background - Pure white for clean look
                g2d.setColor(BG_WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);

                // Border with focus state - Blue theme
                g2d.setColor(hasFocus() ? BORDER_FOCUS : BORDER_LIGHT);
                g2d.setStroke(new BasicStroke(hasFocus() ? 2.0f : 1.5f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);

                super.paintComponent(g);
            }
        };
        passwordField.setFont(FONT_BODY_REGULAR);
        passwordField.setForeground(TEXT_DARK);
        passwordField.setCaretColor(PRIMARY);
        passwordField.setOpaque(false);
        passwordField.setBorder(new EmptyBorder(8, 12, 8, 12));
        passwordField.setPreferredSize(new Dimension(0, 42));
        return passwordField;
    }

    /**
     * Buat button dengan gradient, rounded corner, dan hover effect
     */
    public static JButton createButton(String text) {
        JButton button = new JButton(text) {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient button - Vibrant Blue theme
                Color startColor = isHovered ? ACCENT_CYAN : PRIMARY; // Sky blue on hover
                Color endColor = isHovered ? PRIMARY : PRIMARY_DARK; // Deep blue gradient
                GradientPaint gradient = new GradientPaint(
                        0, 0, startColor,
                        0, getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

                // Shadow - Subtle
                g2d.setColor(new Color(0, 0, 0, isHovered ? 40 : 25));
                g2d.fillRoundRect(2, 2, getWidth() - 5, getHeight() - 3, 10, 10);

                // Text
                super.paintComponent(g);
            }
        };
        button.setFont(FONT_LABEL_BOLD);
        button.setForeground(TEXT_LIGHT);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(new EmptyBorder(10, 18, 10, 18));
        button.setPreferredSize(new Dimension(140, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Buat button warna hijau (success/action)
     */
    public static JButton createSuccessButton(String text) {
        JButton button = new JButton(text) {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient button - Cyan Success
                Color startColor = isHovered ? new Color(38, 198, 218) : SUCCESS_GREEN;
                Color endColor = isHovered ? SUCCESS_GREEN : new Color(0, 151, 167);
                GradientPaint gradient = new GradientPaint(
                        0, 0, startColor,
                        0, getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

                // Shadow - Subtle
                g2d.setColor(new Color(0, 0, 0, isHovered ? 35 : 20));
                g2d.fillRoundRect(2, 2, getWidth() - 5, getHeight() - 3, 10, 10);

                // Text
                super.paintComponent(g);
            }
        };
        button.setFont(FONT_LABEL_BOLD);
        button.setForeground(TEXT_LIGHT);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(new EmptyBorder(8, 16, 8, 16));
        button.setPreferredSize(new Dimension(140, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Buat button warna merah (delete/cancel)
     */
    public static JButton createErrorButton(String text) {
        JButton button = new JButton(text) {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient button - Blue-toned Error
                Color startColor = isHovered ? new Color(66, 165, 245) : ERROR_RED;
                Color endColor = isHovered ? ERROR_RED : new Color(21, 101, 192);
                GradientPaint gradient = new GradientPaint(
                        0, 0, startColor,
                        0, getHeight(), endColor);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

                // Shadow - Subtle
                g2d.setColor(new Color(0, 0, 0, isHovered ? 35 : 20));
                g2d.fillRoundRect(2, 2, getWidth() - 5, getHeight() - 3, 10, 10);

                // Text
                super.paintComponent(g);
            }
        };
        button.setFont(FONT_LABEL_BOLD);
        button.setForeground(TEXT_LIGHT);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(new EmptyBorder(8, 16, 8, 16));
        button.setPreferredSize(new Dimension(140, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Buat combo box dengan rounded corner dan styling modern
     */
    public static JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(FONT_BODY_REGULAR);
        comboBox.setForeground(TEXT_DARK);
        comboBox.setBackground(BG_WHITE); // Light background for contrast
        comboBox.setBorder(BorderFactory.createLineBorder(BORDER_LIGHT, 1));
        comboBox.setPreferredSize(new Dimension(150, 42)); // FIXED: was 0, now 150
        return comboBox;
    }

    /**
     * Buat text area dengan rounded corner
     */
    public static JTextArea createTextArea(int rows, int cols) {
        JTextArea textArea = new JTextArea(rows, cols);
        textArea.setFont(FONT_BODY_REGULAR);
        textArea.setForeground(TEXT_DARK);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createLineBorder(BORDER_LIGHT, 1));
        return textArea;
    }

    /**
     * Buat label dengan styling untuk section heading
     */
    public static JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_HEADING_BOLD);
        label.setForeground(TEXT_LIGHT); // White for dark backgrounds
        return label;
    }

    /**
     * Buat label dengan styling untuk section heading (Dark Text)
     */
    public static JLabel createDarkSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_HEADING_BOLD);
        label.setForeground(TEXT_DARK); // Dark for white backgrounds
        return label;
    }

    /**
     * Buat panel dengan border dan rounded effect (card-like)
     */
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(TEXT_LIGHT);
        panel.setBorder(BorderFactory.createLineBorder(BORDER_LIGHT, 1));
        return panel;
    }

    /**
     * Buat panel konten dengan gradient background
     */
    public static JPanel createContentPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Subtle gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, BG_GRADIENT_START,
                        0, getHeight(), BG_GRADIENT_END);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        return panel;
    }

    /**
     * Buat panel dengan glassmorphism effect (semi-transparent dengan shadow)
     */
    public static JPanel createGlassmorphismPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow effect - Subtle blue-tinted
                g2d.setColor(new Color(30, 136, 229, 40));
                g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 15, 15);

                // Glassmorphism background - Blue-tinted white
                g2d.setColor(new Color(227, 242, 253, 180)); // Light blue tint
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

                // Gradient overlay for depth - Blue gradient
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(30, 136, 229, 25),
                        0, getHeight(), new Color(255, 255, 255, 15));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

                // Border - Blue theme
                g2d.setColor(GLASS_BORDER);
                g2d.setStroke(new BasicStroke(1.0f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    /**
     * Buat rounded button dengan gradient dan shadow
     */
    public static JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Determine color based on state
                Color bgColor = PRIMARY;
                if (!isEnabled()) {
                    bgColor = new Color(200, 200, 200);
                } else if (getModel().isPressed()) {
                    bgColor = PRIMARY_DARK;
                } else if (getModel().isArmed() || getModel().isSelected()) {
                    bgColor = new Color(100, 115, 255);
                }

                // Shadow
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 10, 10);

                // Background gradient
                GradientPaint gradient = new GradientPaint(
                        0, 0, bgColor,
                        0, getHeight(), new Color(Math.max(bgColor.getRed() - 20, 0),
                                Math.max(bgColor.getGreen() - 20, 0),
                                Math.max(bgColor.getBlue() - 20, 0)));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

                // Text
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.setColor(Color.WHITE);
                g2d.setFont(FONT_LABEL_BOLD);
                g2d.drawString(getText(), x, y);
            }
        };
        button.setFont(FONT_LABEL_BOLD);
        button.setForeground(TEXT_LIGHT);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setPreferredSize(new Dimension(0, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Buat panel dengan rounded corner dan subtle shadow
     */
    public static JPanel createRoundedPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow - Blue-tinted shadow
                g2d.setColor(new Color(30, 136, 229, 30));
                g2d.fillRoundRect(2, 2, getWidth() - 3, getHeight() - 3, 15, 15);

                // Background - Pure white with subtle blue tint
                g2d.setColor(new Color(250, 252, 255));
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

                // Border - Light blue
                g2d.setColor(BORDER_LIGHT);
                g2d.setStroke(new BasicStroke(1.0f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    /**
     * Buat table dengan styling modern
     */
    public static JTable createModernTable(javax.swing.table.TableModel model) {
        JTable table = new JTable(model);
        table.setFont(FONT_BODY_REGULAR);
        table.setForeground(TEXT_DARK); // Dark text for white background
        table.setBackground(BG_WHITE); // Pure white background
        table.setRowHeight(40);
        table.setShowVerticalLines(true); // Show vertical lines between columns
        table.setShowHorizontalLines(true);
        table.setGridColor(BORDER_LIGHT); // Light blue grid lines
        table.setIntercellSpacing(new Dimension(1, 1)); // Add spacing for both vertical and horizontal lines
        table.setSelectionBackground(new Color(227, 242, 253)); // Light blue selection
        table.setSelectionForeground(TEXT_DARK);

        // Header styling - Dark theme
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JLabel label = new JLabel(value.toString());
            label.setFont(FONT_LABEL_BOLD);
            label.setForeground(TEXT_LIGHT); // White text
            label.setBackground(PRIMARY); // Bright blue header
            label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_DARK), // Deep blue bottom border
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)));
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            return label;
        });

        return table;
    }

    /**
     * Buat scroll pane modern tanpa border kasar
     */
    public static JScrollPane createModernScrollPane(Component view) {
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_LIGHT));
        scrollPane.getViewport().setBackground(BG_WHITE); // Pure white
        return scrollPane;
    }

    /**
     * Buat enhanced button dengan efek scale dan shadow yang lebih baik
     */
    public static JButton createEnhancedButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            private boolean isHovered = false;
            private boolean isPressed = false;

            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        isHovered = true;
                        repaint();
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        isHovered = false;
                        repaint();
                    }

                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        isPressed = true;
                        repaint();
                    }

                    public void mouseReleased(java.awt.event.MouseEvent evt) {
                        isPressed = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                // Scale effect calculation
                int shadowGap = 4;
                int shadowOffset = isPressed ? 1 : (isHovered ? 4 : 2);
                int roundness = 15;

                // Draw Shadow
                g2d.setColor(new Color(0, 0, 0, 40));
                g2d.fillRoundRect(2, shadowOffset, width - 4, height - shadowGap, roundness, roundness);

                // Draw Button Background
                int yOffset = isPressed ? 2 : 0;

                Color color1 = baseColor;
                Color color2 = new Color(Math.max(baseColor.getRed() - 20, 0),
                        Math.max(baseColor.getGreen() - 20, 0),
                        Math.max(baseColor.getBlue() - 20, 0));

                if (isHovered && !isPressed) {
                    color1 = new Color(Math.min(baseColor.getRed() + 15, 255),
                            Math.min(baseColor.getGreen() + 15, 255),
                            Math.min(baseColor.getBlue() + 15, 255));
                }

                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, yOffset, width, height - shadowGap, roundness, roundness);

                // Draw Text
                FontMetrics fm = g2d.getFontMetrics();
                Rectangle2D r = fm.getStringBounds(getText(), g2d);
                int x = (width - (int) r.getWidth()) / 2;
                int y = (height - shadowGap - (int) r.getHeight()) / 2 + fm.getAscent() + yOffset;

                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                g2d.drawString(getText(), x, y);
            }
        };

        button.setFont(FONT_LABEL_BOLD);
        button.setForeground(Color.WHITE);
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(160, 45));

        return button;
    }

    // ==================== NEW DASHBOARD COMPONENTS ====================

    /**
     * Create info card dengan statistik untuk dashboard
     * 
     * @param icon          Emoji atau simbol icon
     * @param value         Nilai utama (angka besar)
     * @param label         Label/deskripsi
     * @param trend         Indikator trend (misalnya "+5 ↑" atau "95% ↑")
     * @param gradientStart Warna gradient awal
     * @param gradientEnd   Warna gradient akhir
     */
    public static JPanel createInfoCard(String icon, String value, String label, String trend, Color gradientStart,
            Color gradientEnd) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Shadow
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, 15, 15);

                // Gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, gradientStart,
                        getWidth(), getHeight(), gradientEnd);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

                // Border
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        card.setPreferredSize(new Dimension(200, 120));

        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(TEXT_LIGHT);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Label
        JLabel textLabel = new JLabel(label);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textLabel.setForeground(new Color(255, 255, 255, 240));
        textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Trend
        JLabel trendLabel = new JLabel(trend);
        trendLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        trendLabel.setForeground(new Color(255, 255, 255, 230));
        trendLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(iconLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(2));
        card.add(textLabel);
        card.add(Box.createVerticalStrut(2));
        card.add(trendLabel);

        return card;
    }

    /**
     * Create modern gradient card untuk navigation dengan animasi
     * 
     * @param icon        Large icon (emoji)
     * @param title       Card title
     * @param description Short description
     * @param gradient1   Gradient start color
     * @param gradient2   Gradient end color
     */
    public static JPanel createGradientCard(String icon, String title, String description, Color gradient1,
            Color gradient2) {
        JPanel card = new JPanel() {
            private boolean isHovered = false;
            private float scale = 1.0f;

            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        isHovered = true;
                        scale = 1.03f;
                        repaint();
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        isHovered = false;
                        scale = 1.0f;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int shadowDepth = isHovered ? 8 : 4;

                // Shadow
                g2d.setColor(new Color(0, 0, 0, isHovered ? 30 : 20));
                g2d.fillRoundRect(shadowDepth, shadowDepth, getWidth() - shadowDepth * 2,
                        getHeight() - shadowDepth * 2, 20, 20);

                // Gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, gradient1,
                        getWidth(), getHeight(), gradient2);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                // Shine effect
                GradientPaint shine = new GradientPaint(
                        0, 0, new Color(255, 255, 255, 40),
                        0, getHeight() / 2, new Color(255, 255, 255, 0));
                g2d.setPaint(shine);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() / 2, 20, 20);
            }
        };

        card.setLayout(null); // Absolute positioning for custom layout
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(220, 140));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        iconLabel.setBounds(15, 15, 50, 50);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_LIGHT);
        titleLabel.setBounds(15, 70, 190, 25);

        // Description
        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        descLabel.setForeground(new Color(255, 255, 255, 240));
        descLabel.setBounds(15, 95, 190, 30);

        // Arrow indicator
        JLabel arrowLabel = new JLabel("→");
        arrowLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        arrowLabel.setForeground(new Color(255, 255, 255, 230));
        arrowLabel.setBounds(185, 110, 20, 20);

        card.add(iconLabel);
        card.add(titleLabel);
        card.add(descLabel);
        card.add(arrowLabel);

        return card;
    }

    /**
     * Create sidebar menu item
     * 
     * @param icon     Icon emoji
     * @param text     Menu text
     * @param isActive Whether this menu item is currently active
     */
    public static JButton createSidebarMenuItem(String icon, String text, boolean isActive) {
        JButton button = new JButton() {
            private boolean isHovered = false;

            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        if (!isActive) {
                            isHovered = true;
                            repaint();
                        }
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        isHovered = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background
                if (isActive) {
                    g2d.setColor(new Color(255, 255, 255, 25));
                    g2d.fillRoundRect(5, 0, getWidth() - 10, getHeight(), 8, 8);

                    // Active indicator bar
                    g2d.setColor(TEXT_LIGHT);
                    g2d.fillRoundRect(0, getHeight() / 4, 4, getHeight() / 2, 2, 2);
                } else if (isHovered) {
                    g2d.setColor(new Color(255, 255, 255, 15));
                    g2d.fillRoundRect(5, 0, getWidth() - 10, getHeight(), 8, 8);
                }

                // Render icon and text manually
                g2d.setColor(TEXT_LIGHT);
                g2d.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
                g2d.drawString(icon, 15, getHeight() / 2 + 6);

                g2d.setFont(new Font("Segoe UI", isActive ? Font.BOLD : Font.PLAIN, 13));
                g2d.drawString(text, 50, getHeight() / 2 + 5);
            }
        };

        button.setPreferredSize(new Dimension(200, 50));
        button.setMaximumSize(new Dimension(200, 50));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    /**
     * Create welcome banner dengan user info
     * 
     * @param userName Nama user
     * @param role     Role user (Admin/HRD/Karyawan)
     * @param status   Status singkat
     */
    public static JPanel createWelcomeBanner(String userName, String role, String status) {
        JPanel banner = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(227, 242, 253),
                        getWidth(), 0, new Color(255, 255, 255));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Border
                g2d.setColor(BORDER_LIGHT);
                g2d.setStroke(new BasicStroke(1.0f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };

        banner.setLayout(new BorderLayout());
        banner.setOpaque(false);
        banner.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        banner.setPreferredSize(new Dimension(0, 120));

        // Left panel - Greeting
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        JLabel greetingLabel = new JLabel("👤 Selamat Datang, " + userName + "!");
        greetingLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        greetingLabel.setForeground(PRIMARY_DARK);
        greetingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel roleLabel = new JLabel("Role: " + role);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        roleLabel.setForeground(TEXT_SECONDARY);
        roleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(greetingLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(roleLabel);

        // Right panel - Date and status
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String currentDate = dateFormat.format(new Date());
        String currentTime = timeFormat.format(new Date()) + " WIB";

        JLabel dateLabel = new JLabel("📅 " + currentDate);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateLabel.setForeground(TEXT_SECONDARY);
        dateLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel timeLabel = new JLabel("⏰ " + currentTime);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.setForeground(TEXT_SECONDARY);
        timeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel statusLabel = new JLabel("✓ " + status);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setForeground(SUCCESS_GREEN);
        statusLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        rightPanel.add(dateLabel);
        rightPanel.add(Box.createVerticalStrut(3));
        rightPanel.add(timeLabel);
        rightPanel.add(Box.createVerticalStrut(3));
        rightPanel.add(statusLabel);

        banner.add(leftPanel, BorderLayout.WEST);
        banner.add(rightPanel, BorderLayout.EAST);

        return banner;
    }

    /**
     * Create activity feed item
     * 
     * @param icon        Icon emoji
     * @param text        Activity description
     * @param timestamp   Timestamp text (e.g., "2 jam lalu")
     * @param accentColor Color for icon/accent
     */
    public static JPanel createActivityItem(String icon, String text, String timestamp, Color accentColor) {
        JPanel item = new JPanel();
        item.setLayout(new BorderLayout(10, 5));
        item.setOpaque(false);
        item.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        // Icon label
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        iconLabel.setPreferredSize(new Dimension(25, 25));

        // Text panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel textLabel = new JLabel("<html>" + text + "</html>");
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        textLabel.setForeground(TEXT_DARK);
        textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel timeLabel = new JLabel(timestamp);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        timeLabel.setForeground(TEXT_MUTED);
        timeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(textLabel);
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(timeLabel);

        item.add(iconLabel, BorderLayout.WEST);
        item.add(textPanel, BorderLayout.CENTER);

        return item;
    }

    /**
     * Create sidebar panel dengan gradient background
     * 
     * @param width Lebar sidebar
     */
    public static JPanel createSidebar(int width) {
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, PRIMARY,
                        0, getHeight(), PRIMARY_DARK);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(width, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        return sidebar;
    }

    /**
     * Create section header panel with label
     */
    public static JPanel createSectionHeaderPanel(String title) {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setOpaque(false);
        JLabel header = createDarkSectionLabel(title);
        headerPanel.add(header);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return headerPanel;
    }

    /**
     * Create sidebar logo panel
     */
    public static JPanel createSidebarLogo(String title, String subtitle) {
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 30, 15));

        JLabel logoLabel = new JLabel(title);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logoLabel.setForeground(TEXT_LIGHT);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel(subtitle);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subLabel.setForeground(TEXT_LIGHT_MUTED);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoPanel.add(logoLabel);
        logoPanel.add(Box.createVerticalStrut(5));
        logoPanel.add(subLabel);

        return logoPanel;
    }

    /**
     * Create sidebar separator
     */
    public static JSeparator createSidebarSeparator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 50));
        sep.setMaximumSize(new Dimension(200, 1));
        return sep;
    }
}
