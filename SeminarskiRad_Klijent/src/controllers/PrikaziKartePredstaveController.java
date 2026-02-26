/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import domen.KartaPredstave;
import forme.PrikaziKartePredstaveForma;
import forme.model.ModelTabeleKartePredstave;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jovana
 */
public class PrikaziKartePredstaveController {

    private PrikaziKartePredstaveForma pkpf;

    public PrikaziKartePredstaveController(PrikaziKartePredstaveForma pkpf) {
        this.pkpf = pkpf;
        addActionListeners();
    }

    public void otvoriFormu() {
        pripremiFormu();
        pkpf.setVisible(true);
    }

    private void pripremiFormu() {
        List<KartaPredstave> karte = komunikacija.Komunikacija.getInstance().ucitajKarte();
        ModelTabeleKartePredstave mtkp = new ModelTabeleKartePredstave(karte);
        pkpf.getTblKarte().setModel(mtkp);
    }

    private void addActionListeners() {
        pkpf.addBtnObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pkpf.getTblKarte().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pkpf, "Sisten ne moze da obrise kartu", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    ModelTabeleKartePredstave mtkp = (ModelTabeleKartePredstave) pkpf.getTblKarte().getModel();
                    KartaPredstave karta = mtkp.getLista().get(red);
                    JOptionPane.showMessageDialog(pkpf, "Sistem je našao kartu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        komunikacija.Komunikacija.getInstance().obrisiKartu(karta);
                        JOptionPane.showMessageDialog(pkpf, "Sistem je obrisao kartu", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(pkpf, "Sistem ne moze da obrise kartu", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            }

        });

        pkpf.addBtnPromeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pkpf.getTblKarte().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pkpf, "Sisten ne moze da promeni kartu", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    ModelTabeleKartePredstave mtkp = (ModelTabeleKartePredstave) pkpf.getTblKarte().getModel();
                    KartaPredstave karta = mtkp.getLista().get(red);
                    JOptionPane.showMessageDialog(pkpf, "Sistem je našao kartu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                    cordinator.Cordinator.getInstance().dodajParametar("karta", karta);
                    cordinator.Cordinator.getInstance().otvoriPromeniKartuFormu();

                }

            }

        });
        
        pkpf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    try {
                    
                    String nazivPredstave = pkpf.getTxtNazivPredstave().getText().trim();
                    String zanr = pkpf.getTxtZanr().getText().trim();
                    String reditelj = pkpf.getTxtReditelj1().getText().trim();
                    String datumString = pkpf.getTxtDatumOdrzavanja1().getText().trim();
                    LocalDate datumOdrzavanja=null;
                    double cena=0;
                    if(!pkpf.getTxtDatumOdrzavanja1().getText().isEmpty()){
                        datumOdrzavanja = LocalDate.parse(datumString, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
                    }
                    if(!pkpf.getTxtCenaOd().getText().isEmpty()){
                        cena = Double.parseDouble(pkpf.getTxtCenaOd().getText().trim());
                    }
                    
                    
                    ModelTabeleKartePredstave mtkp = (ModelTabeleKartePredstave) pkpf.getTblKarte().getModel();
                    mtkp.pretrazi(nazivPredstave,zanr,reditelj,datumOdrzavanja,cena);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pkpf, "Greska: "+ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
                    
                

            }

        });
        pkpf.addBtnResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pkpf.getTxtZanr().setText("");
                pkpf.getTxtNazivPredstave().setText("");
                pkpf.getTxtReditelj1().setText("");
                pkpf.getTxtDatumOdrzavanja1().setText("");
                pkpf.getTxtCenaOd().setText("");
                
                pripremiFormu();
            }
        });
    }

    public void osveziFormu() {
        pripremiFormu();
    }

}
