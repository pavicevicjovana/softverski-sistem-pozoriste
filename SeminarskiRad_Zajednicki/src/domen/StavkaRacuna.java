/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jovana
 */
public class StavkaRacuna extends ApstraktniDomenskiObjekat {

    private Racun racun;
    private int rb;
    private double iznos;
    private double cenaKarte;
    private int brojKarata;
    private KartaPredstave kartaPredstave;

    public StavkaRacuna() {
    }

    public StavkaRacuna(Racun racun, int rb, double iznos, double cenaKarte, int brojKarata, KartaPredstave kartaPredstave) {
        this.racun = racun;
        this.rb = rb;
        this.iznos = iznos;
        this.cenaKarte = cenaKarte;
        this.brojKarata = brojKarata;
        this.kartaPredstave = kartaPredstave;
    }

    public KartaPredstave getKartaPredstave() {
        return kartaPredstave;
    }

    public void setKartaPredstave(KartaPredstave kartaPredstave) {
        this.kartaPredstave = kartaPredstave;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public double getCenaKarte() {
        return cenaKarte;
    }

    public void setCenaKarte(double cenaKarte) {
        this.cenaKarte = cenaKarte;
    }

    public int getBrojKarata() {
        return brojKarata;
    }

    public void setBrojKarata(int brojKarata) {
        this.brojKarata = brojKarata;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public Racun getRacun() {
        return racun;
    }

    @Override
    public String nazivTabele() {
        return " stavkaracuna ";
    }

    @Override
    public String alijas() {
        return " sr ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();

        while (rs.next()) {
            int rb = rs.getInt("stavkaracuna.rb");
            double iznos = rs.getDouble("stavkaracuna.iznos");
            int brojKarata = rs.getInt("stavkaracuna.brojKarata");
            double cenaKarte = rs.getDouble("stavkaracuna.cenaKarte");

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

            //ucitavanje karte:
            int idKarta = rs.getInt("kartapredstave.idKarta");
            String nazivK = rs.getString("kartapredstave.nazivPredstave");
            String zanr = rs.getString("kartapredstave.zanr");
            String reditelj = rs.getString("kartapredstave.reditelj");
            LocalDate datumO = rs.getDate("kartapredstave.datumOdrzavanja").toLocalDate();
            double cena = rs.getDouble("kartapredstave.cena");
            KartaPredstave kp = new KartaPredstave(idKarta, nazivK, zanr, reditelj, datumO, cena);

            StavkaRacuna sr = new StavkaRacuna(racun, rb, iznos, cenaKarte, brojKarata, kp);

            lista.add(sr);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (rb, racun, iznos, cenaKarte, brojKarata, kartaPredstave) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return rb +", "+racun.getIdRacun() + ", " + iznos + ", " + cenaKarte + ", " + brojKarata + ", " + kartaPredstave.getIdKarta();
    }

    @Override
    public String vrednostiZaUpdate() {
        return "iznos=" + iznos + ", brojKarata=" + brojKarata + ", cenaKarte=" + cenaKarte + ", kartaPredstave=" + kartaPredstave.getIdKarta() + " WHERE " + vratiPrimarniKljuc();
    }

    @Override
    public String vratiPrimarniKljuc() {
        return " racun=" + racun.getIdRacun() + " AND rb=" + rb;
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
