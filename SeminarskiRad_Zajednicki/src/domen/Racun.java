/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jovana
 */
public class Racun extends ApstraktniDomenskiObjekat {

    private int idRacun;
    private LocalDate datumTransakcije;
    private double ukupanIznos;
    private Prodavac prodavac;
    private Korisnik korisnik;
    private double popust;
    private List<StavkaRacuna> stavke = new ArrayList<>();

    public Racun() {
    }

    public Racun(int idRacun, LocalDate datumTransakcije, double ukupanIznos, Prodavac prodavac, Korisnik korisnik, double popust) {
        this.idRacun = idRacun;
        this.datumTransakcije = datumTransakcije;
        this.ukupanIznos = ukupanIznos;
        this.prodavac = prodavac;
        this.korisnik = korisnik;
        this.popust = popust;
    }

    public int getIdRacun() {
        return idRacun;
    }

    public void setIdRacun(int idRacun) {
        this.idRacun = idRacun;
    }

    public LocalDate getDatumTransakcije() {
        return datumTransakcije;
    }

    public void setDatumTransakcije(LocalDate datumTransakcije) {
        this.datumTransakcije = datumTransakcije;
    }

    public double getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public Prodavac getProdavac() {
        return prodavac;
    }

    public void setProdavac(Prodavac prodavac) {
        this.prodavac = prodavac;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public List<StavkaRacuna> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaRacuna> stavke) {
        this.stavke = stavke;
    }

    public double getPopust() {
        return popust;
    }

    public void setPopust(double popust) {
        this.popust = popust;
    }

    @Override
    public String nazivTabele() {
        return " racun ";
    }

    @Override
    public String alijas() {
        return " r ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();

        while (rs.next()) {

            //ucitavanje racuna
            int racunID = rs.getInt("racun.idRacun");
            LocalDate datumTransakcije = rs.getDate("racun.datumTransakcije").toLocalDate();
            double ukupanIznos = rs.getDouble("racun.ukupanIznos");
            // ucitavanje mesta 

            int idMesto = rs.getInt("mesto.idMesto");
            String naziv = rs.getString("mesto.naziv");
            Mesto mesto = new Mesto(idMesto, naziv);

            //ucitavanje korisnika
            String ime = rs.getString("korisnik.ime");
            String prezime = rs.getString("korisnik.prezime");
            String email = rs.getString("korisnik.email");
            TipKorisnika tip = TipKorisnika.valueOf(rs.getString("korisnik.tipKorisnika"));
            Korisnik k = new Korisnik(rs.getInt("korisnik.idKorisnik"), ime, prezime, email, tip, mesto);

            //ucitavanje prodavca
            int idProdavac = rs.getInt("prodavac.idProdavac");
            String imeP = rs.getString("prodavac.ime");
            String prezimeP = rs.getString("prodavac.prezime");
            String kontaktTelefon = rs.getString("prodavac.kontaktTelefon");
            double plata = rs.getDouble("prodavac.plata");
            String emailP = rs.getString("prodavac.email");
            String lozinka = rs.getString("prodavac.lozinka");

            Prodavac prodavac = new Prodavac(idProdavac, imeP, prezimeP, kontaktTelefon, plata, emailP, lozinka);
            Racun racun = new Racun(racunID, datumTransakcije, ukupanIznos, prodavac, k, rs.getDouble("racun.popust"));

            racun.setStavke(new ArrayList<>());
            lista.add(racun);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (datumTransakcije, ukupanIznos, prodavac, korisnik, popust) ";
    }

    @Override
    public String vrednostiZaInsert() {

        return "'" + datumTransakcije.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "', " + ukupanIznos + ", " + prodavac.getIdProdavac() + ", " + korisnik.getIdKorisnik() + ", " + popust;
    }

    @Override
    public String vrednostiZaUpdate() {
        return "datumTransakcije='" + datumTransakcije.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) 
                + "' ,ukupanIznos=" + ukupanIznos 
                + ", prodavac=" + prodavac.getIdProdavac() 
                + ", korisnik=" + korisnik.getIdKorisnik() 
                + ", " + "popust=" + popust
                + " WHERE " + vratiPrimarniKljuc();
    }

    @Override
    public String vratiPrimarniKljuc() {
        return " racun.idRacun=" + idRacun;
    }

    @Override
    public String uslovZaSelect() {
        return "";
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        return null;
    }

    @Override
    public String toString() {
        return "Racun{" + "idRacun=" + idRacun + ", datumTransakcije=" + datumTransakcije + ", ukupanIznos=" + ukupanIznos + ", prodavac=" + prodavac + ", korisnik=" + korisnik + ", popust=" + popust + ", stavke=" + stavke + '}';
    }

}
