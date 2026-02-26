/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import domen.Prodavac;
import forme.PrikazProdavcaForma;
import forme.model.ModelTabeleProdavac;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jovana
 */
public class PrikazProdavcaController {

    private PrikazProdavcaForma ppf;

    public PrikazProdavcaController(PrikazProdavcaForma ppf) {
        this.ppf = ppf;
        addActionListener();
    }

    private void addActionListener() {
        ppf.addBtnObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obrisi(e);
            }

            private void obrisi(ActionEvent e) {
                int selektovaniRed = ppf.getTblProdavac().getSelectedRow();
                if (selektovaniRed == -1) {
                    JOptionPane.showMessageDialog(ppf, "Sisten ne moze da obrise prodavca", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    ModelTabeleProdavac mtp = (ModelTabeleProdavac) ppf.getTblProdavac().getModel();
                    Prodavac prodavac = mtp.getLista().get(selektovaniRed);
                    JOptionPane.showMessageDialog(ppf, "Sistem je našao korisnika.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        komunikacija.Komunikacija.getInstance().obrisiProdavca(prodavac);
                        JOptionPane.showMessageDialog(ppf, "Sistem je obrisao korisnika.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                        osveziFormu();
                        
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ppf, "Sistem ne moze da obrise korisnika.\n"+ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }

                }

            }
        });
        ppf.addBtnPromeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promeni(e);
            }

            private void promeni(ActionEvent e) {
                int selektovaniRed = ppf.getTblProdavac().getSelectedRow();
                if (selektovaniRed == -1) {
                    JOptionPane.showMessageDialog(ppf, "Sisten ne moze da promeni prodavca", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    ModelTabeleProdavac mtp = (ModelTabeleProdavac) ppf.getTblProdavac().getModel();
                    Prodavac prodavac = mtp.getLista().get(selektovaniRed);
                    JOptionPane.showMessageDialog(ppf, "Sistem je našao korisnika.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                    cordinator.Cordinator.getInstance().dodajParametar("prodavac", prodavac);
                    cordinator.Cordinator.getInstance().otvoriPromeniProdavcaFormu();

                }

            }
        });
        
        ppf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pretrazi(e);
                
            }

            private void pretrazi(ActionEvent e) {
                String ime = ppf.getTxtIme().getText().trim();
                String prezime = ppf.getTxtPrezime().getText().trim();
                String email = ppf.getTxtEmail().getText().trim();
                String kontakt = ppf.getTxtKontakt().getText().trim();
                ModelTabeleProdavac mtp = (ModelTabeleProdavac) ppf.getTblProdavac().getModel();
                
                mtp.pretrazi(ime, prezime, kontakt, email);
            }
        });
        ppf.addBtnRefreshActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ppf.getTxtIme().setText("");
                ppf.getTxtPrezime().setText("");
                ppf.getTxtKontakt().setText("");
                ppf.getTxtEmail().setText("");
                prepareView();
            }
        });
    }

    public void otvoriFormu() {
        prepareView();
        ppf.setVisible(true);
    }

    private void prepareView() {
        List<Prodavac> listaProdavca = komunikacija.Komunikacija.getInstance().ucitajProdavce();
        ModelTabeleProdavac mtp = new ModelTabeleProdavac(listaProdavca);
        ppf.getTblProdavac().setModel(mtp);
        
        
    }

    public void osveziFormu() {
        prepareView();
    }

}
