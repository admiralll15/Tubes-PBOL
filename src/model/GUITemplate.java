package model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Rectangle2D;

/**
 * Template utility class untuk membuat GUI hardcoded dengan tema modern dan menarik
 */
public class GUITemplate {
    
    // ==================== DARK CORPORATE THEME ====================
    // Professional color palette for large corporate environments
    
    // Primary Colors - Muted Professional Tones
    public static final Color PRIMARY = new Color(71, 85, 105);            // #475569 - Muted Slate (Primary actions)
    public static final Color PRIMARY_DARK = new Color(30, 41, 59);        // #1E293B - Dark Slate (Main background)
    public static final Color ACCENT_CYAN = new Color(71, 85, 105);        // #475569 - Steel Gray (Info)
    public static final Color ACCENT_PINK = new Color(220, 38, 38);        // #DC2626 - Muted Red (Errors)
    public static final Color ACCENT_ORANGE = new Color(217, 119, 6);      // #D97706 - Muted Orange (Warnings)
    
    // Background Colors - Very Dark, Subtle Gradients
    public static final Color BG_GRADIENT_START = new Color(30, 41, 59);   // #1E293B - Dark Slate
    public static final Color BG_GRADIENT_END = new Color(51, 65, 85);     // #334155 - Medium Slate
    public static final Color BG_SECONDARY = new Color(51, 65, 85);        // #334155 - Secondary panels
    
    // Backward compatibility aliases
    public static final Color BG_WHITE = new Color(226, 232, 240);         // #E2E8F0 - Subtle Gray (not white)
    public static final Color HEADER_BLUE = new Color(51, 65, 85);         // Darker header
    public static final Color ACCENT_BLUE = PRIMARY;
    
    // Text Colors - High Contrast for Dark Theme
    public static final Color TEXT_DARK = new Color(15, 23, 42);           // #0F172A - Dark text (for light backgrounds)
    public static final Color TEXT_LIGHT = new Color(255, 255, 255);       // #FFFFFF - Pure White (primary text on dark)
    public static final Color TEXT_SECONDARY = new Color(148, 163, 184);   // #94A3B8 - Muted Gray (labels, secondary)
    public static final Color TEXT_MUTED = new Color(100, 116, 139);       // #64748B - Darker muted (tertiary)
    
    // Border Colors - Very Subtle
    public static final Color BORDER_LIGHT = new Color(71, 85, 105);       // #475569 - Steel (borders)
    public static final Color BORDER_FOCUS = new Color(100, 116, 139);     // #64748B - Muted focus
    public static final Color BORDER_DARK = new Color(51, 65, 85);         // #334155 - Darker border
    
    // Action Colors - Muted, Professional
    public static final Color SUCCESS_GREEN = new Color(34, 197, 94);      // #22C55E - Muted Green
    public static final Color WARNING_YELLOW = new Color(234, 179, 8);     // #EAB308 - Muted Yellow
    public static final Color ERROR_RED = new Color(220, 38, 38);          // #DC2626 - Muted Red
    public static final Color INFO_BLUE = new Color(71, 85, 105);          // #475569 - Muted Blue
    
    // Glass Effect - Subtle Transparency
    public static final Color GLASS_OVERLAY = new Color(255, 255, 255, 13); // rgba(255,255,255,0.05)
    public static final Color GLASS_BORDER = new Color(71, 85, 105, 77);    // rgba(71,85,105,0.3)
    
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
                    0, getHeight(), PRIMARY_DARK
                );
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
                
                // Background dengan rounded corner - Light for contrast on dark theme
                g2d.setColor(BG_WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                
                // Border - Subtle steel tone
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
                
                // Background - Light for contrast on dark theme
                g2d.setColor(BG_WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                
                // Border with focus state
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
                
                // Gradient button - Muted Slate theme (conservative)
                Color startColor = isHovered ? new Color(100, 116, 139) : PRIMARY; // Subtle lighten on hover
                Color endColor = isHovered ? PRIMARY : new Color(51, 65, 85); // Darker on hover
                GradientPaint gradient = new GradientPaint(
                    0, 0, startColor,
                    0, getHeight(), endColor
                );
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
                
                // Gradient button - Muted Green
                Color startColor = isHovered ? new Color(74, 222, 128) : SUCCESS_GREEN;
                Color endColor = isHovered ? SUCCESS_GREEN : new Color(22, 163, 74);
                GradientPaint gradient = new GradientPaint(
                    0, 0, startColor,
                    0, getHeight(), endColor
                );
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
                
                // Gradient button - Muted Red
                Color startColor = isHovered ? new Color(248, 113, 113) : ERROR_RED;
                Color endColor = isHovered ? ERROR_RED : new Color(185, 28, 28);
                GradientPaint gradient = new GradientPaint(
                    0, 0, startColor,
                    0, getHeight(), endColor
                );
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
        comboBox.setPreferredSize(new Dimension(0, 42));
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
                    0, getHeight(), BG_GRADIENT_END
                );
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
                
                // Shadow effect - Deeper for dark theme
                g2d.setColor(new Color(0, 0, 0, 60));
                g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 15, 15);
                
                // Glassmorphism background - Very subtle white overlay on dark
                g2d.setColor(new Color(255, 255, 255, 13)); // GLASS_OVERLAY
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                
                // Gradient overlay for depth - Muted Slate tint
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(71, 85, 105, 15),
                    0, getHeight(), new Color(51, 65, 85, 8)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                
                // Border - Steel tone
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
                                             Math.max(bgColor.getBlue() - 20, 0))
                );
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
                
                // Shadow - Deeper for dark theme
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(2, 2, getWidth() - 3, getHeight() - 3, 15, 15);
                
                // Background - Very subtle white tint on dark
                g2d.setColor(new Color(255, 255, 255, 15));
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                
                // Border - Steel tone
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
        table.setForeground(TEXT_SECONDARY); // Light text for dark theme
        table.setBackground(BG_SECONDARY); // Dark slate background
        table.setRowHeight(40);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(BORDER_DARK); // Subtle grid lines
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionBackground(new Color(59, 130, 246, 40)); // Ocean Blue selection
        table.setSelectionForeground(TEXT_LIGHT);
        
        // Header styling - Dark theme
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JLabel label = new JLabel(value.toString());
            label.setFont(FONT_LABEL_BOLD);
            label.setForeground(TEXT_LIGHT); // White text
            label.setBackground(PRIMARY_DARK); // Deep navy header
            label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY), // Ocean Blue bottom border
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
            ));
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
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_DARK));
        scrollPane.getViewport().setBackground(BG_SECONDARY); // Dark slate
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
}
