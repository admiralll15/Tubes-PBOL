/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.Absensimodel;
/**
 *
 * @author PUTRI SAHARA T
 */
public class Absensi {
private Absensimodel model;

    public Absensi() {
        model = new Absensimodel();
    }

    public boolean simpan(String idKar, String tanggal, String status) {
        if (idKar.isEmpty() || tanggal.isEmpty() || status.isEmpty()) {
            System.out.println("Isi semua data!");
            return false;
        }
        return model.simpanAbsensi(idKar, tanggal, status);
    }
}
