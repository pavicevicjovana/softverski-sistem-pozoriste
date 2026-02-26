/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import domen.KartaPredstave;
import domen.Korisnik;
import domen.Prodavac;
import domen.Racun;
import domen.StavkaRacuna;
import domen.TipKorisnika;
import forme.FormMode;
import forme.KreirajRacunForma;
import forme.model.ModelTabeleStavkaRacuna;
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
public class KreirajRacunController {

    private KreirajRacunForma krf;

    public KreirajRacunController(KreirajRacunForma krf) {
        this.krf = krf;
        addActionListeners();
    }

    public void otvoriKreirajRacunFormu(FormMode mode) {
        prepareView(mode);

        krf.setVisible(true);
    }

    private void addActionListeners() {
        krf.addBtnDodajStavkuActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajStavku(e);
            }

            private void dodajStavku(ActionEvent e) {

                KartaPredstave kartaPredstave = (KartaPredstave) krf.getCmbKarta().getSelectedItem();
                int brojKarata = Integer.parseInt(krf.getTxtBrojKarata().getText());
                double cenaKarte = kartaPredstave.getCena();
                double iznos = brojKarata * cenaKarte;

                StavkaRacuna stavka = new StavkaRacuna();
                stavka.setBrojKarata(brojKarata);
                stavka.setCenaKarte(cenaKarte);
                stavka.setIznos(iznos);
                stavka.setKartaPredstave(kartaPredstave);

                ModelTabeleStavkaRacuna mtsr = (ModelTabeleStavkaRacuna) krf.getTblStavke().getModel();
                mtsr.dodajStavku(stavka);

            }
        });

        krf.addBtnObrisiStavkuActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obrisiStavku(e);
            }

            private void obrisiStavku(ActionEvent e) {
                int red = krf.getTblStavke().getSelectedRow();
                if (red == -1) {
                    JOptionPane.showMessageDialog(krf, "Sistem ne moze da pronadje stavku!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ModelTabeleStavkaRacuna mtsr = (ModelTabeleStavkaRacuna) krf.getTblStavke().getModel();
                StavkaRacuna stavka = mtsr.getLista().get(red);
                mtsr.obrisiStavku(stavka);

            }
        });

        krf.addBtnKreirajRacunActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelTabeleStavkaRacuna mtsr = (ModelTabeleStavkaRacuna) krf.getTblStavke().getModel();
                Prodavac prodavac = (Prodavac) krf.getCmbProdavac().getSelectedItem();
                Korisnik korisnik = (Korisnik) krf.getCmbKorisnik().getSelectedItem();
                String datumString = krf.getTxtDatum().getText();
                LocalDate datumTransakcije = null;

                if (!datumString.isEmpty()) {
                    try {
                        datumTransakcije = LocalDate.parse(datumString, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(krf, "Sitem ne moze da zapamti racun", "Greska", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(krf, "Sitem ne moze da zapamti racun", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (mtsr.getLista().isEmpty()) {
                    JOptionPane.showMessageDialog(krf, "Sitem ne moze da zapamti racun", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double ukupanIznos = 0;
                double popust = 0;

                for (StavkaRacuna stavkaRacuna : mtsr.getLista()) {
                    ukupanIznos += stavkaRacuna.getIznos();

                }
                if (korisnik != null && korisnik.getTipKorisnika().equals(TipKorisnika.student)) {
                    popust = ukupanIznos * 0.05;
                }
                if (korisnik != null && korisnik.getTipKorisnika().equals(TipKorisnika.penzioner)) {
                    popust = ukupanIznos * 0.2;
                }
                double konacanIznos = ukupanIznos - popust;
                krf.getTxtUkupanIznos().setText(ukupanIznos + "");
                krf.getTxtIznosSaPopusto().setText(konacanIznos + "");

                Racun racun = new Racun(-1, datumTransakcije, konacanIznos, prodavac, korisnik, popust);
                racun.setStavke(mtsr.getLista());

                try {
                    komunikacija.Komunikacija.getInstance().kreirajRacun(racun);
                    JOptionPane.showMessageDialog(krf, "Sistem je zapamtio racun.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(krf, "Sistem ne moze da zapamti racun", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        krf.addBtnPromeniRacunActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promeni(e);
            }

            private void promeni(ActionEvent e) {
                ModelTabeleStavkaRacuna mtsr = (ModelTabeleStavkaRacuna) krf.getTblStavke().getModel();

                int idRacun;
                try {
                    idRacun = Integer.parseInt(krf.getTxtID().getText().trim());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(krf, "Sistem ne moze da zapamti racun", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Prodavac prodavac = (Prodavac) krf.getCmbProdavac().getSelectedItem();
                Korisnik korisnik = (Korisnik) krf.getCmbKorisnik().getSelectedItem();

                String datumString = krf.getTxtDatum().getText().trim();
                LocalDate datumTransakcije;

                if (datumString.isEmpty()) {
                    JOptionPane.showMessageDialog(krf, "Sistem ne moze da zapamti racun", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    datumTransakcije = LocalDate.parse(datumString, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(krf, "Datum mora biti u formatu dd.MM.yyyy.", "Greska", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (mtsr.getLista().isEmpty()) {
                    JOptionPane.showMessageDialog(krf, "Sistem ne moze da zapamti racun", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // izracunaj iznose
                double ukupanBezPopusta = 0;
                for (StavkaRacuna s : mtsr.getLista()) {
                    if (s.getKartaPredstave() == null || s.getBrojKarata() <= 0) {
                        JOptionPane.showMessageDialog(krf, "Sistem ne moze da zapamti racun", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    ukupanBezPopusta += s.getIznos();
                }

                double popust = 0;
                if (korisnik != null && korisnik.getTipKorisnika().equals(TipKorisnika.student)) {
                    popust = ukupanBezPopusta * 0.05;
                }
                if (korisnik != null && korisnik.getTipKorisnika().equals(TipKorisnika.penzioner)) {
                    popust = ukupanBezPopusta * 0.2;
                }

                double konacanIznos = ukupanBezPopusta - popust;

                krf.getTxtUkupanIznos().setText(String.valueOf(ukupanBezPopusta));
                krf.getTxtIznosSaPopusto().setText(String.valueOf(konacanIznos));

                Racun racunZaIzmenu = new Racun(idRacun, datumTransakcije, konacanIznos, prodavac, korisnik, popust);
                racunZaIzmenu.setStavke(mtsr.getLista());

                try {
                    komunikacija.Komunikacija.getInstance().promeniRacun(racunZaIzmenu);
                    JOptionPane.showMessageDialog(krf, "Sistem je zapamtio racun.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    
                    krf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(krf, "Sistem ne moze da zapamti racun", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void popuniComboBox() {
        List<Korisnik> listaKorisnika = komunikacija.Komunikacija.getInstance().ucitajKorisnike();
        List<Prodavac> listaProdavaca = komunikacija.Komunikacija.getInstance().ucitajProdavce();
        List<KartaPredstave> listaKarata = komunikacija.Komunikacija.getInstance().ucitajKarte();

        krf.getCmbKorisnik().removeAllItems();
        for (Korisnik korisnik : listaKorisnika) {
            krf.getCmbKorisnik().addItem(korisnik);
        }

        krf.getCmbProdavac().removeAllItems();
        for (Prodavac prodavac : listaProdavaca) {
            krf.getCmbProdavac().addItem(prodavac);
        }

        krf.getCmbKarta().removeAllItems();
        for (KartaPredstave kartaPredstave : listaKarata) {
            krf.getCmbKarta().addItem(kartaPredstave);
        }

        ModelTabeleStavkaRacuna mtsr = new ModelTabeleStavkaRacuna();
        krf.getTblStavke().setModel(mtsr);
    }

    private void prepareView(FormMode mode) {
        popuniComboBox();

        krf.getCmbKarta().setSelectedIndex(-1);
        krf.getCmbKorisnik().setSelectedIndex(-1);

        switch (mode) {
            case kreiraj:
                Prodavac ulogovani = cordinator.Cordinator.getInstance().getUlogovani();
                krf.getCmbProdavac().setSelectedItem(ulogovani);
                krf.getCmbProdavac().setEnabled(false);

                krf.getTxtIznosSaPopusto().setEnabled(false);
                krf.getBtnPromeniRacun().setVisible(false);
                krf.getBtnKreirajRacun().setVisible(true);
                krf.getTxtID().setEnabled(false);
                krf.getTxtUkupanIznos().setEnabled(false);
                krf.getTxtDatum().setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
                break;
            case promeni:
                Racun racun = (Racun) cordinator.Cordinator.getInstance().vratiParametar("racun");
                System.out.println("Prosledjen racun :" + racun.getIdRacun());

                krf.getTxtID().setText(racun.getIdRacun() + "");
                krf.getTxtDatum().setText(racun.getDatumTransakcije().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
                krf.getCmbProdavac().setSelectedItem(racun.getProdavac());
                krf.getCmbProdavac().setEnabled(false);
                krf.getTxtIznosSaPopusto().setEnabled(false);
                krf.getTxtIznosSaPopusto().setText(String.valueOf(racun.getUkupanIznos()));

                krf.getTxtUkupanIznos().setText(racun.getUkupanIznos() + "");
                krf.getCmbKorisnik().setSelectedItem(racun.getKorisnik());
                System.out.println("stavke racuna : " + racun.getStavke());
                krf.getTblStavke().setModel(new ModelTabeleStavkaRacuna(racun.getStavke()));
                krf.getBtnPromeniRacun().setVisible(true);
                krf.getBtnKreirajRacun().setVisible(false);
                krf.getTxtID().setEnabled(false);
                krf.getTxtUkupanIznos().setEnabled(false);
                break;
            default:
                System.out.println("Greska");
        }
    }
}
