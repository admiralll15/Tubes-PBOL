/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Usersmodel;
import Admin.AdminDashboard;
import HRD.HRDDashboard;
import Karyawan.KaryawanDashboard;

import javax.swing.JOptionPane;

/**
 *
 * @author 
 */
public class users {
    private Usersmodel model = new Usersmodel();

    public void login(String username, String password, javax.swing.JFrame formLogin) {

        String role = model.login(username, password);

        if (role == null) {
            JOptionPane.showMessageDialog(formLogin, 
                    "Username atau Password salah!");
            return;
        }

        // PERBAIKAN: Hapus .toLowerCase() karena di database role-nya "Admin" (Huruf besar Awal)
        // Dan di case bawah Anda menggunakan "Admin".
        // "Admin".toLowerCase() hasilnya "admin". "admin" != "Admin".
        
        switch (role) { 
            case "Admin":
                new AdminDashboard().setVisible(true);
                formLogin.dispose();
                break;

            case "HRD":
                new HRDDashboard().setVisible(true);
                formLogin.dispose();
                break;

            case "Karyawan":
                new KaryawanDashboard().setVisible(true);
                formLogin.dispose();
                break;

            default:
                JOptionPane.showMessageDialog(formLogin, 
                        "Role tidak dikenal: " + role);
        }
    }
}