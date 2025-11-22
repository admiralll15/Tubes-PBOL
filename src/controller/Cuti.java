/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.Cutimodel;
/**
 *
 * @author PUTRI SAHARA T
 */
public class Cuti {
 private Cutimodel model = new Cutimodel();

    public boolean ajukan(String id, String tanggal, String ket) {
        return model.ajukanCuti(id, tanggal, ket);
    }  
}
