/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import domen.Racun;
import domen.StavkaRacuna;
import forme.PrikazRacunaForme;
import forme.model.ModelTabeleRacuni;
import forme.model.ModelTabeleStavkaRacuna;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jovana
 */
public class PrikazRacunaController {

    private final PrikazRacunaForme prf;

    public PrikazRacunaController(PrikazRacunaForme prf) {
        this.prf = prf;
        addActionListeners();

        //addMouseListener();
    }

    private void addActionListeners() {

        prf.getTblRacuni().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int red = prf.getTblRacuni().rowAtPoint(e.getPoint());

                if (red >= 0) {
                    prf.getTblRacuni().setRowSelectionInterval(red, red);
                    JOptionPane.showMessageDialog(prf, "Sistem je nasao racun", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        prf.getTblRacuni().getParent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                prf.getTblRacuni().clearSelection();
                JOptionPane.showMessageDialog(prf, "Sistem ne moze da nadje racun", "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });

        prf.addBtnPretraziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String korisnik = prf.getTxtKorisnik().getText().trim();
                String prodavac = prf.getTxtProdavac().getText().trim();
                String karta = prf.getTxtKarta().getText().trim();
                String datumString = prf.getTxtDatum().getText().trim();
                LocalDate datum = null;
                if (!datumString.isEmpty()) {

                    try {
                        datum = LocalDate.parse(datumString, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Datum mora biti u fomatu dd.MM.yyyy.");
                    }
                }

                try {
                    List<Racun> racuni = komunikacija.Komunikacija.getInstance().pretraziRacune(korisnik, prodavac, datum, karta);
                    
                    if (racuni == null || racuni.isEmpty()) {
                        prf.getTblRacuni().setModel(new ModelTabeleRacuni());
                        prf.getTblRacuni().setModel(new ModelTabeleRacuni(null));
                        JOptionPane.showMessageDialog(prf, "Sistem ne moze da nadje racune po zadatim kriterijumima", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    prf.getTblRacuni().setModel(new ModelTabeleRacuni(racuni));
                    JOptionPane.showMessageDialog(prf, "Sistem je nasao racune po zadatim kriterijumima", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    
                     prf.getTblRacuni().setModel(new ModelTabeleRacuni(null));
                    JOptionPane.showMessageDialog(prf, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }

                /*ModelTabeleRacuni mtr = (ModelTabeleRacuni) prf.getTblRacuni().getModel();
                if (karta != null && !karta.isEmpty()) {
                    for (Racun r : mtr.getLista()) {
                        if (r.getStavke() == null || r.getStavke().isEmpty()) {
                            //List<StavkaRacuna> stavke
                               //     = komunikacija.Komunikacija.getInstance().ucitajStavke(r.getIdRacun());
                           // r.setStavke(stavke);
                        }
                    }
                }

                mtr.pretrazi(korisnik, prodavac, datum, karta);
                 */
 /*prf.getTblRacuni().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int red = prf.getTblRacuni().rowAtPoint(e.getPoint());

                        if (red >= 0) {
                            prf.getTblRacuni().setRowSelectionInterval(red, red);
                            JOptionPane.showMessageDialog(prf, "Sistem je nasao racun", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });
                prf.getTblRacuni().getParent().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        prf.getTblRacuni().clearSelection();
                        JOptionPane.showMessageDialog(prf, "Sistem ne moze da nadje racun", "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                });*/
            }
        });
        prf.addbtnResetujActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prf.getTxtKorisnik().setText("");
                prf.getTxtProdavac().setText("");
                prf.getTxtDatum().setText("");
                prf.getTxtKarta().setText("");
                pripremiFormu();
            }
        });
        prf.addbtnPrikaziStavkeActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = prf.getTblRacuni().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(null, "Morate izabrati racun!");
                    return;
                }
                if (red != -1) {
                    ModelTabeleRacuni mtr = (ModelTabeleRacuni) prf.getTblRacuni().getModel();
                    Racun racun = mtr.getLista().get(red);
                    List<StavkaRacuna> stavke = racun.getStavke();
                    ModelTabeleStavkaRacuna mtsr = new ModelTabeleStavkaRacuna(stavke);
                    prf.getTblStavke().setModel(mtsr);
                }
            }
        });
        prf.addbtnPromeniStavkeActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promeni(e);
            }

            private void promeni(ActionEvent e) {
                int red = prf.getTblRacuni().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(prf, "Morate izabrati racun!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(prf, "Sistem je nasao racun", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                ModelTabeleRacuni mtr = (ModelTabeleRacuni) prf.getTblRacuni().getModel();
                Racun racun = mtr.getLista().get(red);
                //List<StavkaRacuna> stavke = komunikacija.Komunikacija.getInstance().ucitajStavke(racun.getIdRacun());
                //racun.setStavke(stavke);
                cordinator.Cordinator.getInstance().dodajParametar("racun", racun);

                cordinator.Cordinator.getInstance().otvoriPromeniRacunFromu();

            }
        });

    }

    private void addMouseListener() {

        //prf.getTblRacuni().addMouseListener(new MouseAdapter() {
        //    @Override
        //    public void mouseClicked(MouseEvent e) {
        //        int red = prf.getTblRacuni().getSelectedRow();
        //        if (red != -1) {
        //            ModelTabeleRacuni mtr = (ModelTabeleRacuni) prf.getTblRacuni().getModel();
        //            Racun racun = mtr.getLista().get(red);
        //            List<StavkaRacuna> stavke = komunikacija.Komunikacija.getInstance().ucitajStavke(racun.getIdRacun());
        //           ModelTabeleStavkeRacuna mtsr = new ModelTabeleStavkeRacuna(stavke);
        //           prf.getTblStavke().setModel(mtsr);
        //        }
        //     }
        //  });
    }

    public void otvoriFormu() {
        pripremiFormu();
        prf.setVisible(true);
    }

    public void pripremiFormu() {
        List<Racun> racuni = komunikacija.Komunikacija.getInstance().ucitajRacune();
        ModelTabeleRacuni mtr = new ModelTabeleRacuni(racuni);
        prf.getTblRacuni().setModel(mtr);

        List<StavkaRacuna> stavke = new ArrayList<>();
        ModelTabeleStavkaRacuna mtsr = new ModelTabeleStavkaRacuna(stavke);
        prf.getTblStavke().setModel(mtsr);

    }

    public void osveziFormu() {
        pripremiFormu();
    }

}
