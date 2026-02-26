/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jovana
 */
public class Smena extends ApstraktniDomenskiObjekat {

    private int idSmena;
    private String nazivSmena;
    private int brojSati;
    private double satnica;
    private LocalTime vremePocetka;
    private LocalTime vremeZavrsetka;

    public Smena() {
    }

    public Smena(int idSmena, String nazivSmena, int brojSati, double satnica, LocalTime vremePocetka, LocalTime vremeZavrsetka) {
        this.idSmena = idSmena;
        this.nazivSmena = nazivSmena;
        this.brojSati = brojSati;
        this.satnica = satnica;
        this.vremePocetka = vremePocetka;
        this.vremeZavrsetka = vremeZavrsetka;
    }

    public int getIdSmena() {
        return idSmena;
    }

    public void setIdSmena(int idSmena) {
        this.idSmena = idSmena;
    }

    public String getNazivSmena() {
        return nazivSmena;
    }

    public void setNazivSmena(String nazivSmena) {
        this.nazivSmena = nazivSmena;
    }

    public int getBrojSati() {
        return brojSati;
    }

    public void setBrojSati(int brojSati) {
        this.brojSati = brojSati;
    }

    public double getSatnica() {
        return satnica;
    }

    public void setSatnica(double satnica) {
        this.satnica = satnica;
    }

    public LocalTime getVremePocetka() {
        return vremePocetka;
    }

    public LocalTime getVremeZavrsetka() {
        return vremeZavrsetka;
    }

    public void setVremePocetka(LocalTime vremePocetka) {
        this.vremePocetka = vremePocetka;
    }

    public void setVremeZavrsetka(LocalTime vremeZavrsetka) {
        this.vremeZavrsetka = vremeZavrsetka;
    }

    @Override
    public String toString() {
        return nazivSmena;
    }

    @Override
    public String nazivTabele() {
        return " smena ";
    }

    @Override
    public String alijas() {
        return " s ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();

        while (rs.next()) {
            int idSmena = rs.getInt("smena.idSmena");
            String nazivSmene = rs.getString("smena.nazivSmene");
            int brSati = rs.getInt("smena.brojSati");
            double satnica = rs.getDouble("smena.satnica");
            LocalTime vremeP = rs.getTime("smena.vremePocetka").toLocalTime();
            LocalTime vremeZ = rs.getTime("smena.vremeZavrsetka").toLocalTime();

            Smena smena = new Smena(idSmena, nazivSmene, brSati, satnica, vremeP, vremeZ);
            lista.add(smena);

        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (nazivSmene, brojSati, satnica, vremePocetka, vremeZavrsetka) ";

    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + nazivSmena + "', " + brojSati + ", " + satnica + ", '" + vremePocetka.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "', '" + vremeZavrsetka.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "'";
    }

    @Override
    public String vrednostiZaUpdate() {
        return "nazivSmene='" + nazivSmena + "', brojSati=" + brojSati + ", satnica=" + satnica + ", vremePocetka='" + vremePocetka.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "', vremeZavrsetka='" + vremeZavrsetka.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "'" +" WHERE "+vratiPrimarniKljuc();
    }

    @Override
    public String vratiPrimarniKljuc() {
        return " smena.idSmena=" + idSmena;
    }

    @Override
    public String uslovZaSelect() {
        return "";
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        return null;
    }

}
