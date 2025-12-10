package model;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

/**
 * Utility class untuk set application icon
 */
public class AppIcon {
    
    private static Image iconImage = null;
    
    /**
     * Get application icon
     */
    public static Image getIcon() {
        if (iconImage == null) {
            try {
                // Try loading from classpath first
                URL iconURL = AppIcon.class.getResource("/resources/app_icon.png");
                
                if (iconURL != null) {
                    iconImage = new ImageIcon(iconURL).getImage();
                    System.out.println("✓ Icon loaded from classpath");
                } else {
                    // Fallback: try loading from file system
                    String projectPath = System.getProperty("user.dir");
                    File iconFile = new File(projectPath + "/src/resources/app_icon.png");
                    
                    if (iconFile.exists()) {
                        iconImage = new ImageIcon(iconFile.getAbsolutePath()).getImage();
                        System.out.println("✓ Icon loaded from file system: " + iconFile.getAbsolutePath());
                    } else {
                        System.err.println("✗ Icon file not found at: " + iconFile.getAbsolutePath());
                    }
                }
            } catch (Exception e) {
                System.err.println("✗ Failed to load app icon: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return iconImage;
    }
    
    /**
     * Set icon untuk JFrame
     */
    public static void setFrameIcon(JFrame frame) {
        Image icon = getIcon();
        if (icon != null) {
            frame.setIconImage(icon);
            System.out.println("✓ Icon set for frame: " + frame.getTitle());
        } else {
            System.err.println("✗ Cannot set icon - icon is null for frame: " + frame.getTitle());
        }
    }
}
