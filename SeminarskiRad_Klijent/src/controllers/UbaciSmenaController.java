/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import domen.Smena;
import forme.FormMode;
import forme.UbaciSmenaForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author Jovana
 */
public class UbaciSmenaController {

    private UbaciSmenaForma usf;

    public UbaciSmenaController(UbaciSmenaForma usf) {
        this.usf = usf;
        addActionListeners();
    }

    private void addActionListeners() {
        usf.addBtnUbaciActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ubaci(e);
            }

            private void ubaci(ActionEvent e) {

                String naziv = usf.getTxtNaziv().getText().trim();
                if(naziv == null || naziv.isEmpty() ){
                    JOptionPane.showMessageDialog(usf, "Sistem ne moze da zapamti smenu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LocalTime vremePocetka=null,vremeZavrsetka=null;
                double satnica=0;
                try {
                    vremePocetka = LocalTime.parse(usf.getTxtVremePocetka().getText().trim(), DateTimeFormatter.ofPattern("HH:mm:ss"));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(usf, "Sistem ne moze da zapamti smenu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    vremeZavrsetka = LocalTime.parse(usf.getTxtVremeZavrsetka().getText().trim(), DateTimeFormatter.ofPattern("HH:mm:ss"));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(usf, "Sistem ne moze da zapamti smenu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    satnica = Double.parseDouble(usf.getTxtSatnica().getText().trim());
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(usf, "Sistem ne moze da zapamti smenu.", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int pocetak = vremePocetka.getHour()*60+vremePocetka.getMinute();
                int zavrsetak = vremeZavrsetka.getHour()*60+vremeZavrsetka.getMinute();
                int trajanjeMin = zavrsetak - pocetak;
                if(trajanjeMin<0){
                    trajanjeMin+=24*60;
                }
                if(trajanjeMin==0){
                    JOptionPane.showMessageDialog(usf, "Greksa, smena ne moze trajati 0 minuta\n", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int brojSati = trajanjeMin/60;
                Smena smena = new Smena(-1, naziv, brojSati, satnica, vremePocetka, vremeZavrsetka);
                
                try {
                    komunikacija.Komunikacija.getInstance().ubaciSmenu(smena);
                    JOptionPane.showMessageDialog(usf, "Sistem je zapamtio smenu\n", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    usf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(usf, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        usf.addBtnPromeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promeni(e);
            }

            private void promeni(ActionEvent e) {
                String naziv = usf.getTxtNaziv().getText().trim();
                LocalTime vremePocetka=null,vremeZavrsetka=null;
                double satnica=0;
                try {
                    vremePocetka = LocalTime.parse(usf.getTxtVremePocetka().getText().trim(), DateTimeFormatter.ofPattern("HH:mm:ss"));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(usf, "Greksa, vreme pocetka nije validno\n"+ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    vremeZavrsetka = LocalTime.parse(usf.getTxtVremeZavrsetka().getText().trim(), DateTimeFormatter.ofPattern("HH:mm:ss"));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(usf, "Greksa, vreme pocetka nije validno\n"+ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    satnica = Double.parseDouble(usf.getTxtSatnica().getText().trim());
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(usf, "Greksa, satnica mora biti broj\n"+ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
                int pocetak = vremePocetka.getHour()*60+vremePocetka.getMinute();
                int zavrsetak = vremeZavrsetka.getHour()*60+vremeZavrsetka.getMinute();
                int trajanjeMin = zavrsetak - pocetak;
                if(trajanjeMin<0){
                    trajanjeMin+=24*60;
                }
                if(trajanjeMin==0){
                    JOptionPane.showMessageDialog(usf, "Greksa, smena ne moze trajati 0 minuta\n", "Greska", JOptionPane.ERROR_MESSAGE);
                }
                int brojSati = trajanjeMin/60;
                int ID= Integer.parseInt(usf.getTxtID().getText());
                Smena smena = new Smena(ID, naziv, brojSati, satnica, vremePocetka, vremeZavrsetka);
                 try {
                    komunikacija.Komunikacija.getInstance().azurirajSmenu(smena);
                    JOptionPane.showMessageDialog(usf, "Sistem je zapamtio smenu\n", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    usf.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(usf, "Sistem ne moze da promeni smenu\n"+ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void otvoriFormu(FormMode formMode) {
        pripremiFormu(formMode);
        usf.setVisible(true);
    }

    private void pripremiFormu(FormMode formMode) {

        switch (formMode) {
            case kreiraj:
                usf.getBtnPromeni().setVisible(false);
                usf.getTxtID().setEnabled(false);
                break;
            case promeni:
                usf.getBtnUbaci().setVisible(false);
                usf.getTxtID().setEnabled(false);
                Smena smena = (Smena) cordinator.Cordinator.getInstance().vratiParametar("smena");
                usf.getTxtID().setText(smena.getIdSmena()+"");
                usf.getTxtNaziv().setText(smena.getNazivSmena());
                usf.getTxtSatnica().setText(smena.getSatnica() + "");
                usf.getTxtVremePocetka().setText(smena.getVremePocetka().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                usf.getTxtVremeZavrsetka().setText(smena.getVremeZavrsetka().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                break;
            default:
                return;
        }
    }

}
