/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Jovana
 */
public class KartaPredstave extends ApstraktniDomenskiObjekat {

    private int idKarta;
    private String nazivPredstave;
    private String zanr;
    private String reditelj;
    private LocalDate datumOdrzavanja;
    private double cena;

    public KartaPredstave() {
    }

    public KartaPredstave(int idKarta, String nazivPredstave, String zanr, String reditelj, LocalDate datumOdrzavanja, double cena) {
        this.idKarta = idKarta;
        this.nazivPredstave = nazivPredstave;
        this.zanr = zanr;
        this.reditelj = reditelj;
        this.datumOdrzavanja = datumOdrzavanja;
        this.cena = cena;
    }

    public int getIdKarta() {
        return idKarta;
    }

    public void setIdKarta(int idKarta) {
        this.idKarta = idKarta;
    }

    public String getNazivPredstave() {
        return nazivPredstave;
    }

    public void setNazivPredstave(String nazivPredstave) {
        this.nazivPredstave = nazivPredstave;
    }

    public String getZanr() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }

    public String getReditelj() {
        return reditelj;
    }

    public void setReditelj(String reditelj) {
        this.reditelj = reditelj;
    }

    public LocalDate getDatumOdrzavanja() {
        return datumOdrzavanja;
    }

    public void setDatumOdrzavanja(LocalDate datumOdrzavanja) {
        this.datumOdrzavanja = datumOdrzavanja;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return nazivPredstave;
    }

    @Override
    public String nazivTabele() {
        return " kartaPredstave ";
    }

    @Override
    public String alijas() {
        return " kp ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista= new ArrayList<>();
        
        while(rs.next()){
            int idKarta=rs.getInt("kartapredstave.idKarta");
            String naziv = rs.getString("kartapredstave.nazivPredstave");
            String zanr = rs.getString("kartapredstave.zanr");
            String reditelj = rs.getString("kartapredstave.reditelj");
            LocalDate datumO=rs.getDate("kartapredstave.datumOdrzavanja").toLocalDate();
            double cena = rs.getDouble("kartapredstave.cena");
            KartaPredstave kp =new KartaPredstave(idKarta, naziv, zanr, reditelj, datumO, cena);
            lista.add(kp);
        }
        
        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (nazivPredstave, zanr, reditelj, datumOdrzavanja, cena) "; // nazivPredstave, zanr, reditelj, datumOdrzavanja, cena
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + nazivPredstave + "', '" + zanr + "', '" + reditelj + "', '" + datumOdrzavanja.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "', " + cena;
    }

    @Override
    public String vrednostiZaUpdate() {
        return " nazivPredstave = '" + nazivPredstave + "', zanr = '" + zanr + "', "
                + "reditelj = '" + reditelj + "', " + "datumOdrzavanja= '" + datumOdrzavanja.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "', cena=" + cena +" WHERE "+vratiPrimarniKljuc();

    }

    @Override
    public String vratiPrimarniKljuc() {
        return " kartapredstave.idKarta= " + idKarta;
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
