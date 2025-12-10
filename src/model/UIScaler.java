package model;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class untuk scale UI components agar sesuai dengan layar penuh
 */
public class UIScaler {
    
    // Scale factor untuk semua komponen (1.5x lebih besar)
    private static final float SCALE_FACTOR = 1.5f;
    
    /**
     * Scale semua komponen dalam sebuah container
     */
    public static void scaleContainer(Container container) {
        scaleComponent(container);
    }
    
    /**
     * Recursive function untuk scale component dan semua childnya
     */
    private static void scaleComponent(Component comp) {
        // Scale font
        if (comp instanceof JComponent) {
            Font currentFont = comp.getFont();
            if (currentFont != null) {
                int newSize = (int) (currentFont.getSize() * SCALE_FACTOR);
                Font scaledFont = new Font(currentFont.getName(), currentFont.getStyle(), newSize);
                comp.setFont(scaledFont);
            }
        }
        
        // Scale button size
        if (comp instanceof JButton) {
            JButton btn = (JButton) comp;
            Dimension size = btn.getPreferredSize();
            if (size != null) {
                btn.setPreferredSize(new Dimension(
                    (int) (size.width * SCALE_FACTOR),
                    (int) (size.height * SCALE_FACTOR)
                ));
            }
        }
        
        // Scale text field height
        if (comp instanceof JTextField) {
            JTextField field = (JTextField) comp;
            Dimension size = field.getPreferredSize();
            if (size != null) {
                field.setPreferredSize(new Dimension(
                    size.width,
                    (int) (size.height * SCALE_FACTOR * 0.8f) // Sedikit lebih rendah dari SCALE_FACTOR
                ));
            }
        }
        
        // Scale password field height
        if (comp instanceof JPasswordField) {
            JPasswordField field = (JPasswordField) comp;
            Dimension size = field.getPreferredSize();
            if (size != null) {
                field.setPreferredSize(new Dimension(
                    size.width,
                    (int) (size.height * SCALE_FACTOR * 0.8f)
                ));
            }
        }
        
        // Scale combo box
        if (comp instanceof JComboBox) {
            JComboBox<?> combo = (JComboBox<?>) comp;
            Dimension size = combo.getPreferredSize();
            if (size != null) {
                combo.setPreferredSize(new Dimension(
                    size.width,
                    (int) (size.height * SCALE_FACTOR * 0.8f)
                ));
            }
        }
        
        // Recursive untuk semua child components
        if (comp instanceof Container) {
            Container container = (Container) comp;
            for (Component child : container.getComponents()) {
                scaleComponent(child);
            }
        }
    }
}
