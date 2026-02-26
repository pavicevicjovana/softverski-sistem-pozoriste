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
public class Mesto extends ApstraktniDomenskiObjekat {

    private int idMesto;
    private String naziv;

    public Mesto() {
    }

    public Mesto(int idMesto, String naziv) {
        this.idMesto = idMesto;
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getIdMesto() {
        return idMesto;
    }

    public void setIdMesto(int idMesto) {
        this.idMesto = idMesto;
    }

    @Override
    public String toString() {
        return naziv;
    }

    @Override
    public String nazivTabele() {
        return " mesto ";
    }

    @Override
    public String alijas() {
        return " m ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        ArrayList<ApstraktniDomenskiObjekat> lista = new ArrayList<>();

        while (rs.next()) {
            Mesto m = new Mesto(rs.getInt("mesto.idMesto"),
                    rs.getString("mesto.naziv"));

            lista.add(m);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (naziv) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return " '" + naziv + "' ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " naziv = '" + naziv + "' ";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return " mesto.idMesto = " + idMesto;
    }

    @Override
    public String uslovZaSelect() {
        return "";
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
        final Mesto other = (Mesto) obj;
        return Objects.equals(this.naziv, other.naziv);
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        return null;
    }

}
