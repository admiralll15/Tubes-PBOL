/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.Karyawanmodel;
import java.sql.ResultSet;

/**
 *
 * @author 
 */
public class Karyawan {
    private Karyawanmodel model = new Karyawanmodel();

    // 1. Tambah Karyawan (CREATE)
    public boolean tambah(String nama, String jabatan) {
        if (nama.isEmpty() || jabatan.isEmpty()) {
            return false;
        }
        return model.insertKaryawan(nama, jabatan);
    }
    
    // 2. Ambil Data Karyawan (READ)
    public ResultSet tampilkanData() {
        return model.getAllKaryawan();
    }
    
    // 3. Ubah Data Karyawan (UPDATE)
    public boolean ubah(String id, String nama, String jabatan) {
        if (id.isEmpty()) return false;
        return model.updateKaryawan(id, nama, jabatan);
    }
    
    // 4. Hapus Karyawan (DELETE)
    public boolean hapus(String id) {
        if (id.isEmpty()) return false;
        return model.deleteKaryawan(id);
    }
}