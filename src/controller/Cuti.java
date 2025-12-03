/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.Cutimodel;
import java.sql.ResultSet;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author 
 */
public class Cuti {
    private Cutimodel model = new Cutimodel();

    // Method disesuaikan dengan Model (Terima Tgl Mulai & Selesai)
    public boolean ajukan(String id, String tglMulai, String tglSelesai, String ket) {
        if(id.isEmpty() || tglMulai.isEmpty() || tglSelesai.isEmpty() || ket.isEmpty()){
            return false;
        }
        
        try {
            // Convert String to java.sql.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date sqlTglMulai = new Date(sdf.parse(tglMulai).getTime());
            Date sqlTglSelesai = new Date(sdf.parse(tglSelesai).getTime());
            
            // Call model with correct parameters (id, tujuan, tglMulai, tglSelesai, keterangan)
            return model.ajukanCuti(id, "Cuti", sqlTglMulai, sqlTglSelesai, ket);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Method untuk Admin/HRD melihat data cuti
    public ResultSet getDaftarCuti() {
        return model.getStatusCuti();
    }
    
    // Method untuk update status (Disetujui/Ditolak)
    public boolean updateStatus(int idCuti, String status) {
        return model.updateStatusCuti(idCuti, status);
    }
}