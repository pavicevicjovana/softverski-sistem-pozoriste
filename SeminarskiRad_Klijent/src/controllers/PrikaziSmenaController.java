/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import domen.Smena;
import forme.PrikaziSmenuForma;
import forme.model.ModelTabeleSmena;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jovana
 */
public class PrikaziSmenaController {

    private PrikaziSmenuForma psf;

    public PrikaziSmenaController(PrikaziSmenuForma psf) {
        this.psf = psf;
        addActionListeners();
    }

    public void otvoriFormu() {
        pripremiFormu();
        psf.setVisible(true);
    }

    private void pripremiFormu() {
        List<Smena> smene = komunikacija.Komunikacija.getInstance().ucitajSmene();
        ModelTabeleSmena mts = new ModelTabeleSmena(smene);
        psf.getTblSmena().setModel(mts);
    }

    private void addActionListeners() {
        psf.addBtnObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obrisi(e);
            }

            private void obrisi(ActionEvent e) {
                int red = psf.getTblSmena().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(psf, "Sisten ne moze da promeni smenu", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ModelTabeleSmena mts = (ModelTabeleSmena) psf.getTblSmena().getModel();
                Smena smena = mts.getLista().get(red);
                JOptionPane.showMessageDialog(psf, "Sistem je našao smenu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                try {
                    komunikacija.Komunikacija.getInstance().obrisiSmenu(smena);
                    JOptionPane.showMessageDialog(psf, "Sistem je obrisao smenu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    pripremiFormu();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(psf, "Sisten ne moze da obrise smenu\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        psf.addBtnPromeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promeni(e);
            }

            private void promeni(ActionEvent e) {
                int red = psf.getTblSmena().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(psf, "Sisten ne moze da promeni smenu", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    ModelTabeleSmena mts = (ModelTabeleSmena) psf.getTblSmena().getModel();
                    Smena smena = mts.getLista().get(red);
                    JOptionPane.showMessageDialog(psf, "Sistem je našao smenu.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    cordinator.Cordinator.getInstance().dodajParametar("smena", smena);
                    cordinator.Cordinator.getInstance().otvoriPromeniSmenaFormu();
                }

            }
        });
        psf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String naziv = psf.getTxtNaziv().getText().trim();
                LocalTime vremePocetka = null, vremeZavrsetka = null;
                double satnica = 0;
                try {
                    if (!psf.getTxtVremePocetka().getText().isEmpty()) {
                        vremePocetka = LocalTime.parse(psf.getTxtVremePocetka().getText().trim(), DateTimeFormatter.ofPattern("HH:mm:ss"));
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(psf, "Greksa, vreme pocetka nije validno\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    if (!psf.getTxtVremeZavrsetka().getText().isEmpty()) {
                        vremeZavrsetka = LocalTime.parse(psf.getTxtVremeZavrsetka().getText().trim(), DateTimeFormatter.ofPattern("HH:mm:ss"));
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(psf, "Greksa, vreme pocetka nije validno\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    if (!psf.getTxtSatnica().getText().isEmpty()) {
                        satnica = Double.parseDouble(psf.getTxtSatnica().getText().trim());
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(psf, "Greksa, satnica mora biti broj\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
                ModelTabeleSmena mts = (ModelTabeleSmena) psf.getTblSmena().getModel();
                mts.pretrazi(naziv, satnica, vremePocetka, vremeZavrsetka);

            }
        });
        psf.addBtnResetujiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                psf.getTxtNaziv().setText("");
                psf.getTxtSatnica().setText("");
                psf.getTxtVremePocetka().setText("");
                psf.getTxtVremeZavrsetka().setText("");
                pripremiFormu();
            }
        });
    }

    public void osveziFormu() {
        pripremiFormu();
    }
}
