/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jovana
 */
public class ProdavacSmena extends ApstraktniDomenskiObjekat {

    private Prodavac prodavac;
    private Smena smena;
    private LocalDate datumPS;

    public ProdavacSmena() {
    }

    public ProdavacSmena(Prodavac prodavac, Smena smena, LocalDate datumPS) {
        this.prodavac = prodavac;
        this.smena = smena;
        this.datumPS = datumPS;
    }

    public Prodavac getProdavac() {
        return prodavac;
    }

    public void setProdavac(Prodavac prodavac) {
        this.prodavac = prodavac;
    }

    public Smena getSmena() {
        return smena;
    }

    public void setSmena(Smena smena) {
        this.smena = smena;
    }

    public LocalDate getDatumPS() {
        return datumPS;
    }

    public void setDatumPS(LocalDate datumPS) {
        this.datumPS = datumPS;
    }

    @Override
    public String nazivTabele() {
        return " prodavacsmena ";
    }

    @Override
    public String alijas() {
        return " ps ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();

        while (rs.next()) {
            int idProdavac = rs.getInt("prodavac.idProdavac");
            String ime = rs.getString("prodavac.ime");
            String prezime = rs.getString("prodavac.prezime");
            String kontaktTelefon = rs.getString("prodavac.kontaktTelefon");
            double plata = rs.getDouble("prodavac.plata");
            String email = rs.getString("prodavac.email");
            String lozinka = rs.getString("prodavac.lozinka");

            Prodavac prodavac = new Prodavac(idProdavac, ime, prezime, kontaktTelefon, plata, email, lozinka);

            int idSmena = rs.getInt("smena.idSmena");
            String nazivSmene = rs.getString("smena.nazivSmene");
            int brSati = rs.getInt("smena.brojSati");
            double satnica = rs.getDouble("smena.satnica");
            LocalTime vremeP = rs.getTime("smena.vremePocetka").toLocalTime();
            LocalTime vremeZ = rs.getTime("smena.vremeZavrsetka").toLocalTime();

            Smena smena = new Smena(idSmena, nazivSmene, brSati, satnica, vremeP, vremeZ);
            
            LocalDate datum=rs.getDate("prodavacsmena.datumPS").toLocalDate();
            ProdavacSmena ps = new ProdavacSmena(prodavac, smena, datumPS);
            lista.add(ps);

        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (prodavac, smena, datumPS) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return prodavac.getIdProdavac() + "," + smena.getIdSmena() + ", '" + datumPS.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "'";
    }

    @Override
    public String vrednostiZaUpdate() {
        return "prodavac=" + prodavac.getIdProdavac() + ", smena=" + smena.getIdSmena() + ", datumPS='" + datumPS.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "";
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
