import model.GUITemplate;
import Admin.AdminDashboard;

public class TestRepro {
    public static void main(String[] args) {
        System.out.println("Testing GUITemplate initialization...");
        try {
            System.out.println("Color: " + GUITemplate.PRIMARY);
            System.out.println("GUITemplate initialized successfully.");
        } catch (Throwable t) {
            System.out.println("GUITemplate failed:");
            t.printStackTrace();
        }

        System.out.println("Testing AdminDashboard initialization...");
        try {
            new AdminDashboard();
            System.out.println("AdminDashboard initialized successfully.");
        } catch (Throwable t) {
            System.out.println("AdminDashboard failed:");
            t.printStackTrace();
        }
    }
}
