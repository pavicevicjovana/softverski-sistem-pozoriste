/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import domen.Prodavac;
import forme.FormMode;
import forme.KreirajProdavcaForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Jovana
 */
public class KreirajProdavcaController {

    private KreirajProdavcaForma kpf;

    public KreirajProdavcaController(KreirajProdavcaForma kpf) {
        this.kpf = kpf;
        addActionListeners();
    }

    private void addActionListeners() {
        kpf.addBtnKreirajActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kreiraj(e);
            }

            private void kreiraj(ActionEvent e) {
                String ime = kpf.getTxtIme().getText().trim();
                String prezime = kpf.getTxtPrezime().getText().trim();
                String email = kpf.getTxtEmail().getText().trim();
                String kontakt = kpf.getTxtKontakt().getText().trim();
                
                String lozinka1 = String.copyValueOf(kpf.getTxtPassword().getPassword());
                String lozinka2= String.copyValueOf(kpf.getTxtPasswordPotvrda().getPassword());
                if(!lozinka1.equals(lozinka2)){
                    JOptionPane.showMessageDialog(kpf, "Lozinke se ne poklapaju", "Upozorenje", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                Prodavac prodavac = new Prodavac(-1, ime, prezime, kontakt, 0, email, lozinka2);
                try {
                    komunikacija.Komunikacija.getInstance().kreirajProdavca(prodavac);
                    JOptionPane.showMessageDialog(kpf, "Sistem je zapamtio prodavca", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    kpf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(kpf, "Sistem ne može da zapamti prodavca\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        kpf.addBtnPromeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promeni(e);
            }

            private void promeni(ActionEvent e) {
                int ID= Integer.parseInt(kpf.getTxtID().getText());
                String ime = kpf.getTxtIme().getText().trim();
                String prezime = kpf.getTxtPrezime().getText().trim();
                String email = kpf.getTxtEmail().getText().trim();
                String kontakt = kpf.getTxtKontakt().getText().trim();
                String password = String.copyValueOf(kpf.getTxtPassword().getPassword());
                
                Prodavac prodavacIzmena = new Prodavac(ID, ime, prezime, kontakt, 0, email, password);
                
                try {
                    komunikacija.Komunikacija.getInstance().promeniProdavca(prodavacIzmena);
                    JOptionPane.showMessageDialog(kpf, "Sistem je zapamtio prodavca", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    kpf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(kpf, "Sistem ne može da zapamti prodavca\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
                
                
            }
        });
    }

    public void otvoriFormu(FormMode formMode) {
        
        prepareView(formMode);
        kpf.setVisible(true);
    }

    private void prepareView(FormMode formMode) {
        switch (formMode) {
            case kreiraj:
                kpf.getBtnIzmeni().setVisible(false);
                kpf.getTxtID().setEnabled(false);
                kpf.setVisible(true);
                break;
            case promeni:
                Prodavac prodavac = (Prodavac) cordinator.Cordinator.getInstance().vratiParametar("prodavac");
                kpf.getTxtID().setEnabled(false);
                kpf.getBtnKreiraj().setVisible(false);
                kpf.getTxtPassword().setEnabled(false);
                kpf.getTxtPasswordPotvrda().setVisible(false);
                kpf.getTxtID().setText(prodavac.getIdProdavac()+"");
                kpf.getTxtIme().setText(prodavac.getIme());
                kpf.getTxtPrezime().setText(prodavac.getPrezime());
                kpf.getTxtKontakt().setText(prodavac.getKontaktTelefon());
                kpf.getTxtEmail().setText(prodavac.getEmail());
                kpf.getTxtPassword().setText(prodavac.getLozinka());
                kpf.setVisible(true);
                break;
            default:
                return;
        }
    }

}
