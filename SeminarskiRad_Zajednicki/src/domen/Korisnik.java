/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Jovana
 */
public class Korisnik extends ApstraktniDomenskiObjekat {

    private int idKorisnik;
    private String ime;
    private String prezime;
    private String email;
    private TipKorisnika tipKorisnika;
    private Mesto mesto;

    public Korisnik() {
    }

    public Korisnik(int idKorisnik, String ime, String prezime, String email, TipKorisnika tipKorisnika, Mesto mesto) {
        this.idKorisnik = idKorisnik;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.tipKorisnika = tipKorisnika;
        this.mesto = mesto;
    }

    public int getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(int idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipKorisnika getTipKorisnika() {
        return tipKorisnika;
    }

    public void setTipKorisnika(TipKorisnika tipKorisnika) {
        this.tipKorisnika = tipKorisnika;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    @Override
    public String nazivTabele() {
        return " korisnik ";
    }

    @Override
    public String alijas() {
        return " k ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws SQLException {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();

        while (rs.next()) {
            int idKorisnik = rs.getInt("korisnik.idKorisnik");
            String ime = rs.getString("korisnik.ime");
            String prezime = rs.getString("korisnik.prezime");
            String email = rs.getString("korisnik.email");
            String tipKorisnika = rs.getString("korisnik.tipKorisnika");
            TipKorisnika tk = TipKorisnika.valueOf(tipKorisnika);

            int idMesto = rs.getInt("korisnik.mesto");
            String naziv = rs.getString("mesto.naziv");
            Mesto m = new Mesto(idMesto, naziv);
            Korisnik korisnik = new Korisnik(idKorisnik, ime, prezime, email, tk, m);
            lista.add(korisnik);

        }
        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (ime, prezime, email, tipKorisnika, mesto) ";
    }

    @Override
    public String vrednostiZaInsert() {
        ///ime,prezime, email, tipKorisnika,mesto
        return "'" + ime + "', '" + prezime + "', '" + email + "', '" + tipKorisnika + "', " + mesto.getIdMesto();
    }

    @Override
    public String vrednostiZaUpdate() {
        return "ime='" + ime + "', prezime='" + prezime + "', email='" + email + "', tipKorisnika='" + tipKorisnika + "', mesto=" + mesto.getIdMesto()+ " WHERE idKorisnik="+idKorisnik;
    }

    @Override
    public String vratiPrimarniKljuc() {
        return " korisnik.idKorisnik=" + idKorisnik;
    }

    @Override
    public String uslovZaSelect() {
        return "";
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Korisnik other = (Korisnik) obj;
        if (!Objects.equals(this.ime, other.ime)) {
            return false;
        }
        if (!Objects.equals(this.prezime, other.prezime)) {
            return false;
        }
        return Objects.equals(this.email, other.email);
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        return null;
    }

}
