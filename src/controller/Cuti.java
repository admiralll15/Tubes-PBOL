/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.Cutimodel;
import java.sql.ResultSet;

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
        return model.ajukanCuti(id, tglMulai, tglSelesai, ket);
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