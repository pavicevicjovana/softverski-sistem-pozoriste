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
public class Prodavac extends ApstraktniDomenskiObjekat {

    private int idProdavac;
    private String ime;
    private String prezime;
    private String kontaktTelefon;
    private double plata;
    private String email;
    private String lozinka;

    public Prodavac() {
    }

    public Prodavac(int idProdavac, String ime, String prezime, String kontaktTelefon, double plata, String email, String lozinka) {
        this.idProdavac = idProdavac;
        this.ime = ime;
        this.prezime = prezime;
        this.kontaktTelefon = kontaktTelefon;
        this.plata = plata;
        this.email = email;
        this.lozinka = lozinka;
    }

    public int getIdProdavac() {
        return idProdavac;
    }

    public void setIdProdavac(int idProdavac) {
        this.idProdavac = idProdavac;
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

    public String getKontaktTelefon() {
        return kontaktTelefon;
    }

    public void setKontaktTelefon(String kontaktTelefon) {
        this.kontaktTelefon = kontaktTelefon;
    }

    public double getPlata() {
        return plata;
    }

    public void setPlata(double plata) {
        this.plata = plata;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    @Override
    public String nazivTabele() {
        return " prodavac ";
    }

    @Override
    public String alijas() {
        return " p ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        ArrayList<ApstraktniDomenskiObjekat> lista = new ArrayList<>();

        while (rs.next()) {
            int idProdavac = rs.getInt("prodavac.idProdavac");
            String ime = rs.getString("prodavac.ime");
            String prezime = rs.getString("prodavac.prezime");
            String kontaktTelefon = rs.getString("prodavac.kontaktTelefon");
            double plata= rs.getDouble("prodavac.plata");
            String email = rs.getString("prodavac.email");
            String lozinka =rs.getString("prodavac.lozinka");
            
            Prodavac prodavac = new Prodavac(idProdavac, ime, prezime, kontaktTelefon, plata, email, lozinka);
            lista.add(prodavac);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (ime, prezime, kontaktTelefon, plata, email, lozinka) ";

    }

    @Override
    public String vrednostiZaInsert() {
        //ime, prezime, kontaktTelefon, plata, email, lozinka
        return "'" + ime + "', '" + prezime + "', '" + kontaktTelefon + "', " + plata + ", '" + email + "', '" + lozinka + "'";
    }

    @Override
    public String vrednostiZaUpdate() {
        return "ime='" + ime + "', prezime='" + prezime + "', kontaktTelefon='" + kontaktTelefon + "', plata=" + plata + ", email='" + email + "', lozinka='" + lozinka + "'" + " WHERE "+vratiPrimarniKljuc();
    }

    @Override
    public String vratiPrimarniKljuc() {
        return " prodavac.idProdavac= " + idProdavac;
    }

    @Override
    public String uslovZaSelect() {
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Prodavac other = (Prodavac) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return Objects.equals(this.lozinka, other.lozinka);
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        return null;
    }

}
