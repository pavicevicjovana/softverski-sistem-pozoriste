/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import domen.Korisnik;
import domen.Mesto;
import domen.TipKorisnika;
import forme.FormMode;
import forme.KreirajKorisnikaForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jovana
 */
public class KreirajKorisnikaController {

    private KreirajKorisnikaForma kkf;

    public KreirajKorisnikaController(KreirajKorisnikaForma kkf) {
        this.kkf = kkf;
        addActionListener();
    }

    public void otvoriFormu(FormMode mode) {
        pripremiFormu(mode);
        kkf.setVisible(true);
    }

    private void pripremiFormu(FormMode mode) {
        popuniComboBoxMesta();
        switch (mode) {
            case kreiraj:
                kkf.getBtnOmoguciIzmenu().setVisible(false);
                kkf.getTxtID().setEnabled(false);
                kkf.getBtnPromeni().setVisible(false);
                kkf.getBtnKreiraj().setVisible(true);
                kkf.getBtnKreiraj().setEnabled(true);
                break;
            case promeni:

                kkf.getTxtID().setEnabled(false);
                kkf.getBtnKreiraj().setVisible(false);
                kkf.getBtnPromeni().setVisible(true);
                kkf.getBtnPromeni().setEnabled(false);
                kkf.getTxtEmail().setEnabled(false);
                kkf.getTxtIme().setEnabled(false);
                kkf.getTxtPrezime().setEnabled(false);
                kkf.getCmbMesto().setEnabled(false);
                kkf.getCmbTipKorisnika().setEnabled(false);
                Korisnik korisnik = (Korisnik) cordinator.Cordinator.getInstance().vratiParametar("korisnik");
                kkf.getTxtID().setText(korisnik.getIdKorisnik() + "");
                kkf.getTxtIme().setText(korisnik.getIme());
                kkf.getTxtPrezime().setText(korisnik.getPrezime());
                kkf.getTxtEmail().setText(korisnik.getEmail());
                kkf.getCmbMesto().setSelectedItem(korisnik.getMesto());
                kkf.getCmbTipKorisnika().setSelectedItem(korisnik.getTipKorisnika());

                break;
            default:
                return;
        }

    }

    private void popuniComboBoxMesta() {
        List<Mesto> mesta = komunikacija.Komunikacija.getInstance().ucitajMesta();
        kkf.getCmbMesto().removeAllItems();
        for (Mesto mesto : mesta) {
            kkf.getCmbMesto().addItem(mesto);
        }
    }

    private void addActionListener() {
        kkf.kreirajKorisnikaAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kreiraj(e);
            }

            private void kreiraj(ActionEvent e) {
                String ime = kkf.getTxtIme().getText().trim();
                String prezime = kkf.getTxtPrezime().getText().trim();
                String email = kkf.getTxtEmail().getText().trim();

                if (kkf.getCmbTipKorisnika().getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(kkf, "Sistem ne moze da zapamti korisnika.", "Greska", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (kkf.getCmbTipKorisnika().getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(kkf, "Sistem ne moze da zapamti korisnika.", "Greska", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                TipKorisnika tip = (TipKorisnika) kkf.getCmbTipKorisnika().getSelectedItem();
                Mesto mesto = (Mesto) kkf.getCmbMesto().getSelectedItem();

                Korisnik korisnik = new Korisnik(-1, ime, prezime, email, tip, mesto);

                try {
                    komunikacija.Komunikacija.getInstance().kreirajKorisnika(korisnik);
                    JOptionPane.showMessageDialog(kkf, "Sistem je zapamtio korisnika", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    kkf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(kkf, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        kkf.promeniKorisnikaAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                izmeni(e);
            }

            private void izmeni(ActionEvent e) {
                int id = Integer.parseInt(kkf.getTxtID().getText());
                String ime = kkf.getTxtIme().getText().trim();
                String prezime = kkf.getTxtPrezime().getText().trim();
                String email = kkf.getTxtEmail().getText().trim();

                if (kkf.getCmbTipKorisnika().getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(kkf, "Morate izabrati Mesto!", "Greska", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (kkf.getCmbTipKorisnika().getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(kkf, "Morate izabrati Tip Korisnika!", "Greska", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                TipKorisnika tip = (TipKorisnika) kkf.getCmbTipKorisnika().getSelectedItem();
                Mesto mesto = (Mesto) kkf.getCmbMesto().getSelectedItem();

                Korisnik korisnik = new Korisnik(id, ime, prezime, email, tip, mesto);

                try {
                    komunikacija.Komunikacija.getInstance().promeniKorisnika(korisnik);
                    JOptionPane.showMessageDialog(kkf, "Sistem je zapamtio korisnika", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    kkf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(kkf, "Sistem ne moze da zapamti korisnika", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        kkf.addBtnOmoguciActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                omoguciIzmenu(e);
            }

            private void omoguciIzmenu(ActionEvent e) {
                kkf.getBtnPromeni().setEnabled(true);
                kkf.getTxtEmail().setEnabled(true);
                kkf.getTxtIme().setEnabled(true);
                kkf.getTxtPrezime().setEnabled(true);
                kkf.getCmbMesto().setEnabled(true);
                kkf.getCmbTipKorisnika().setEnabled(true);
                kkf.getBtnOmoguciIzmenu().setEnabled(false);
            }
        });
    }

}
