/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import domen.Korisnik;
import domen.Mesto;
import domen.TipKorisnika;
import forme.PrikazKorisnikaForma;
import forme.model.ModelTabeleKorisnici;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Jovana
 */
public class PrikazKorisnikaController {

    private final PrikazKorisnikaForma pkf;

    public PrikazKorisnikaController(PrikazKorisnikaForma pkf) {
        this.pkf = pkf;
        addActionListeners();
    }

    private void addActionListeners() {

        pkf.getTblKorisnici().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int red = pkf.getTblKorisnici().rowAtPoint(e.getPoint());

                if (red >= 0) {
                    pkf.getTblKorisnici().setRowSelectionInterval(red, red);
                    JOptionPane.showMessageDialog(pkf, "Sistem je nasao korisnika", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        pkf.getTblKorisnici().getParent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pkf.getTblKorisnici().clearSelection();
                JOptionPane.showMessageDialog(pkf, "Sistem ne moze da nadje korisnika", "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        pkf.addBtnObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pkf.getTblKorisnici().getSelectedRow();
                if (red == -1) {

                    JOptionPane.showMessageDialog(pkf, "Sisten ne moze da obrise korisnika.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    ModelTabeleKorisnici mtk = (ModelTabeleKorisnici) pkf.getTblKorisnici().getModel();
                    Korisnik korisnik = mtk.getLista().get(red);
                    //JOptionPane.showMessageDialog(pkf, "Sistem je našao korisnika.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        komunikacija.Komunikacija.getInstance().obrisiKorisnika(korisnik);
                        JOptionPane.showMessageDialog(pkf, "Sistem je obrisao korisnika.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(pkf, "Sistem ne moze da obrise korisnika.", "Greska", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
        pkf.addBtnPromeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pkf.getTblKorisnici().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(pkf, "Sisten ne moze da nadje korisnika", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {

                    pkf.getTblKorisnici().setRowSelectionInterval(red, red);
                    JOptionPane.showMessageDialog(pkf, "Sistem je nasao korisnika", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                    ModelTabeleKorisnici mtk = (ModelTabeleKorisnici) pkf.getTblKorisnici().getModel();
                    Korisnik korisnik = mtk.getLista().get(red);
                    //JOptionPane.showMessageDialog(pkf, "Sistem je našao korisnika.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                    cordinator.Cordinator.getInstance().dodajParametar("korisnik", korisnik);
                    cordinator.Cordinator.getInstance().otvoriPromeniKorisnikaFormu();

                }
            }
        });

        pkf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ime = pkf.getTxtIme().getText().trim();
                String prezime = pkf.getTxtPrezime().getText().trim();
                ModelTabeleKorisnici mtk = (ModelTabeleKorisnici) pkf.getTblKorisnici().getModel();
                Mesto mesto = (Mesto) pkf.getCmbMesta().getSelectedItem();
                String imeMesta = null;
                String tipString = null;
                if (mesto != null) {
                    imeMesta = mesto.getNaziv();
                }

                TipKorisnika tip = (TipKorisnika) pkf.getCmbTipKorisnika().getSelectedItem();

                if (tip != null) {
                    tipString = tip.toString();
                }
                try {
                    List<Korisnik> korisnici = komunikacija.Komunikacija.getInstance().pretraziKorisnika(ime, prezime, imeMesta, tipString);

                    if (korisnici == null || korisnici.isEmpty()) {
                        pkf.getTblKorisnici().setModel(new ModelTabeleKorisnici());
                        JOptionPane.showMessageDialog(
                                pkf,
                                "Sistem ne moze da nadje korisnike po zadatim kriterijumima",
                                "Greska",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }

                    if (korisnici == null || korisnici.isEmpty()) {
                        pkf.getTblKorisnici().setModel(new ModelTabeleKorisnici(null));
                        JOptionPane.showMessageDialog(pkf, "Sistem ne moze da nadje korisnike po zadatim kriterijumima", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    pkf.getTblKorisnici().setModel(new ModelTabeleKorisnici(korisnici));
                    JOptionPane.showMessageDialog(pkf, "Sistem je nasao korisnike po zadatim kriterijumima", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pkf, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }

                //mtk.pretrazi(ime, prezime, mesto, tip);
            }
        });
        pkf.addBtnResetujiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pkf.getTxtIme().setText("");
                pkf.getTxtPrezime().setText("");
                pkf.getCmbMesta().setSelectedIndex(-1);
                pkf.getCmbTipKorisnika().setSelectedIndex(-1);
                pripremiFormu();
            }
        });
    }

    public void otvoriFormu() {
        pripremiFormu();
        pkf.setVisible(true);
    }

    public void pripremiFormu() {
        List<Korisnik> korisnici = komunikacija.Komunikacija.getInstance().ucitajKorisnike();
        ModelTabeleKorisnici mtk = new ModelTabeleKorisnici(korisnici);
        pkf.getTblKorisnici().setModel(mtk);

        List<Mesto> mesta = komunikacija.Komunikacija.getInstance().ucitajMesta();
        pkf.getCmbMesta().removeAllItems();
        for (Mesto mesto : mesta) {
            pkf.getCmbMesta().addItem(mesto);
        }
        pkf.getCmbMesta().setSelectedIndex(-1);
        pkf.getCmbTipKorisnika().setSelectedIndex(-1);
    }

    public void osveziFormu() {
        pripremiFormu();
    }

}
