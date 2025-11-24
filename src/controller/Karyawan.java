/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.Karyawanmodel;
/**
 *
 * @author PUTRI SAHARA T
 */
public class Karyawan {
    private Karyawanmodel model = new Karyawanmodel();

    public boolean tambah(String nama, String jabatan) {
        if (nama.isEmpty() || jabatan.isEmpty()) {
            return false;
        }
        return model.tambahKaryawan(nama, jabatan);
    }
}
