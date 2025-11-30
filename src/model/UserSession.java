package model;

public class UserSession {
    private static String u_id;          
    private static String u_role;        
    private static String k_id;          
    private static String k_nama;        
    private static String k_jabatan; // <-- TAMBAHAN BARU

    // Setter: Update untuk menerima Jabatan
    public static void setUserLogin(String id, String role, String idKaryawan, String namaKaryawan, String jabatanKaryawan) {
        u_id = id;
        u_role = role;
        k_id = idKaryawan;      
        k_nama = namaKaryawan;  
        k_jabatan = jabatanKaryawan; // Simpan jabatan
    }

    // Getter
    public static String getKaryawanId() { return k_id; }
    public static String getNamaKaryawan() { return k_nama; }
    public static String getJabatanKaryawan() { return k_jabatan; } // <-- GETTER BARU
    public static String getRole() { return u_role; }
    public static String getUserId() { return u_id; }
    
    public static void logout() {
        u_id = null; u_role = null; k_id = null; k_nama = null; k_jabatan = null;
    }
}