/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.Gajimodel;
import java.sql.ResultSet;

/**
 *
 * @author 
 */
public class Gaji {

    private Gajimodel model = new Gajimodel();

    // Parameter diperlengkap sesuai Gajimodel.java
    public boolean hitungDanSimpan(String id, int gapok, int jamLembur, int upahLemburPerJam, int pot, String bulan, String tahun) {
        if(id.isEmpty() || bulan.isEmpty() || tahun.isEmpty()){
            return false;
        }
        // Memanggil model dengan parameter lengkap
        return model.hitungDanSimpan(id, gapok, jamLembur, upahLemburPerJam, pot, bulan, tahun);
    }
    
    // Untuk menampilkan history gaji di tabel
    public ResultSet getLaporanGaji() {
        return model.getLaporanPenggajian();
    }
}