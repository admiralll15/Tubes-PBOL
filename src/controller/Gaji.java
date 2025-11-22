/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import model.Gajimodel;
/**
 *
 * @author PUTRI SAHARA T
 */
public class Gaji {

    private Gajimodel model = new Gajimodel();

    public boolean hitungDanSimpan(String id, int gapok, int lembur, int pot) {
        return model.hitungDanSimpan(id, gapok, lembur, pot);
    }
}
