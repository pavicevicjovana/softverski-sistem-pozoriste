/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import domen.KartaPredstave;
import forme.FormMode;
import forme.KreirajKartuForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author Jovana
 */
public class KreirajKartuController {

    private KreirajKartuForma kkf;

    public KreirajKartuController(KreirajKartuForma kkf) {
        this.kkf = kkf;
        addActionListeners();
    }

    public void otvoriFormu(FormMode formMode) {
        pripremiFormu(formMode);
        kkf.setVisible(true);
    }

    private void addActionListeners() {
        kkf.addBtnKreirajActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kreiraj(e);
            }

            private void kreiraj(ActionEvent e) {
                String nazivPredstave = kkf.getTxtNazivPredstave().getText().trim();
                String zanr = kkf.getTxtZanr().getText().trim();
                String reditelj = kkf.getTxtReditelj().getText().trim();
                double cena;
                LocalDate datumOdrzavanja;
                try {
                    cena = Double.parseDouble(kkf.getTxtCena().getText().trim());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(kkf, "U polje cena, uneti samo brojeve!\n"+ex.getMessage(), "Greska", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    datumOdrzavanja = LocalDate.parse(kkf.getTxtDatumOdrzavanja().getText().trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy."));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(kkf, "U polje datum, uneti validan format!\n"+ex.getMessage(), "Greska", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                        
                
                

                KartaPredstave karta = new KartaPredstave(-1, nazivPredstave, zanr, reditelj, datumOdrzavanja, cena);

                try {
                    komunikacija.Komunikacija.getInstance().kreirajKartu(karta);
                    JOptionPane.showMessageDialog(kkf, "Sistem je kreirao kartu!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    kkf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(kkf, "Sistem ne moze da kreira kartu!\n"+ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);

                }

            }
        });
        
        kkf.addBtnPromeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                int ID = Integer.parseInt(kkf.getTxtID().getText());
                String nazivPredstave=kkf.getTxtNazivPredstave().getText().trim();
                String zanr = kkf.getTxtZanr().getText().trim();
                String reditelj = kkf.getTxtReditelj().getText().trim();
                double cena;
                LocalDate datumOdrzavanja ;
                
                try {
                    cena = Double.parseDouble(kkf.getTxtCena().getText().trim());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(kkf, "U polje cena, uneti samo brojeve!\n"+ex.getMessage(), "Greska", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    datumOdrzavanja = LocalDate.parse(kkf.getTxtDatumOdrzavanja().getText().trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy."));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(kkf, "U polje datum, uneti validan format!\n"+ex.getMessage(), "Greska", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                KartaPredstave karta = new KartaPredstave(ID, nazivPredstave, zanr, reditelj, datumOdrzavanja, cena);

                try {
                    komunikacija.Komunikacija.getInstance().azuzirajKartu(karta);
                    JOptionPane.showMessageDialog(kkf, "Sistem je promenio kartu!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    kkf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(kkf, "Sistem ne moze da promeni kartu!\n"+ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);

                }

            }
        });
    }

    private void pripremiFormu(FormMode formMode) {
        switch (formMode) {
            case kreiraj:
                kkf.getBtnPromeni().setEnabled(false);
                kkf.getBtnPromeni().setVisible(false);
                kkf.getTxtID().setEnabled(false);
                break;
            case promeni:
                kkf.getBtnKreiraj().setVisible(false);
                kkf.getBtnPromeni().setVisible(true);
                kkf.getTxtID().setEnabled(false);
                
                KartaPredstave karta = (KartaPredstave) cordinator.Cordinator.getInstance().vratiParametar("karta");
                kkf.getTxtID().setText(karta.getIdKarta()+"");
                kkf.getTxtNazivPredstave().setText(karta.getNazivPredstave());
                kkf.getTxtZanr().setText(karta.getZanr());
                kkf.getTxtReditelj().setText(karta.getReditelj());
                
                kkf.getTxtDatumOdrzavanja().setText(karta.getDatumOdrzavanja().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")).toString());
                kkf.getTxtCena().setText(karta.getCena()+"");
            default:
                return;
        }
    }

}
