/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import domen.Mesto;
import forme.KreirajMestoForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Jovana
 */
public class KreirajMestoController {
    private final KreirajMestoForma kmf;

    public KreirajMestoController(KreirajMestoForma kmf) {
        this.kmf = kmf;
        addActionListeners();
    }

    private void addActionListeners() {
        kmf.addBtnKreirajMestoActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kreiraj(e);
            }

            private void kreiraj(ActionEvent e) {
                String naziv = kmf.getTxtNaziv().getText().trim();
                Mesto mesto = new Mesto(-1, naziv);
                
                
                try {
                    komunikacija.Komunikacija.getInstance().kreirajMesto(mesto);
                    JOptionPane.showMessageDialog(kmf, "Sistem je zapamtio mesto", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    kmf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(kmf, "Sitem ne moze da kreira mesto\n"+ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    public void otvoriFormu(){
        kmf.setVisible(true);
    }
}
