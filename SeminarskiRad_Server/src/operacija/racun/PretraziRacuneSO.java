/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.racun;

import java.sql.*;
import domen.Racun;
import domen.StavkaRacuna;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbConnectionFactory;

/**
 *
 * @author Jovana
 */
public class PretraziRacuneSO extends ApstraktnaGenerickaOperacija {

    private List<Racun> lista = new ArrayList<>();

    public List<Racun> getLista() {
        return lista;
    }

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (!(objekat instanceof HashMap)) {
            throw new Exception("Neispravni kriterijumi pretrage");
        }
        HashMap<String, Object> k = (HashMap<String, Object>) objekat;

        String korisnik = (String) k.get("korisnik");
        String prodavac = (String) k.get("prodavac");
        LocalDate datum = (LocalDate) k.get("datum");
        String karta = (String) k.get("karta");
        if (korisnik == null && prodavac == null && datum == null && karta == null) {
            throw new Exception("Unesti barem jedan paramter da bi pretrazivanje bilo omoguceno!");
        }

    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        HashMap<String, Object> k = (HashMap<String, Object>) objekat;

        String korisnik = (String) k.get("korisnik");
        String prodavac = (String) k.get("prodavac");
        LocalDate datum = (LocalDate) k.get("datum");
        String karta = (String) k.get("karta");

        StringBuilder uslov = new StringBuilder();

        uslov.append(" JOIN prodavac ON prodavac.idProdavac = racun.prodavac ");
        uslov.append(" JOIN korisnik ON korisnik.idKorisnik = racun.korisnik ");
        uslov.append(" JOIN mesto ON mesto.idMesto = korisnik.mesto ");

        uslov.append(" LEFT JOIN stavkaracuna sr ON sr.racun = racun.idRacun ");
        uslov.append(" LEFT JOIN kartapredstave kp ON kp.idKarta = sr.kartaPredstave ");

        uslov.append(" WHERE 1=1 ");

        if (korisnik != null && !korisnik.trim().isEmpty()) {
            String q = korisnik.trim().toLowerCase();
            uslov.append(" AND (LOWER(korisnik.ime) LIKE '%").append(q).append("%' ")
                    .append(" OR LOWER(korisnik.prezime) LIKE '%").append(q).append("%') ");
        }

        if (prodavac != null && !prodavac.trim().isEmpty()) {
            String q = prodavac.trim().toLowerCase();
            uslov.append(" AND (LOWER(prodavac.ime) LIKE '%").append(q).append("%' ")
                    .append(" OR LOWER(prodavac.prezime) LIKE '%").append(q).append("%') ");
        }

        if (datum != null) {
            uslov.append(" AND racun.datumTransakcije = '")
                    .append(java.sql.Date.valueOf(datum))
                    .append("' ");
        }

        if (karta != null && !karta.trim().isEmpty()) {
            String q = karta.trim().toLowerCase();
            uslov.append(" AND LOWER(kp.nazivPredstave) LIKE '%").append(q).append("%' ");
        }

        uslov.append(" GROUP BY racun.idRacun ");
        uslov.append(" ORDER BY racun.idRacun ");

        lista = (List<Racun>) (List<?>) broker.getAll(new Racun(), uslov.toString());

        for (Racun r : lista) {
            r.setStavke(ucitajStavkeZaRacun(r.getIdRacun()));
        }

        if (lista == null || lista.isEmpty()) {
            
            throw new Exception("Sistem ne moze da nadje racune po zadatim kriterijumima");
        }
    }

    private List<StavkaRacuna> ucitajStavkeZaRacun(int idRacun) throws Exception {
        String upit
                = "SELECT sr.rb, sr.iznos, sr.cenaKarte, sr.brojKarata, "
                + "kp.idKarta, kp.nazivPredstave, kp.zanr, kp.reditelj, kp.datumOdrzavanja, kp.cena "
                + "FROM stavkaracuna sr "
                + "JOIN kartapredstave kp ON kp.idKarta = sr.kartaPredstave "
                + "WHERE sr.racun = " + idRacun + " "
                + "ORDER BY sr.rb";

        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(upit);

        List<StavkaRacuna> lista = new ArrayList<>();
        while (rs.next()) {
            StavkaRacuna s = new StavkaRacuna();
            s.setRb(rs.getInt("rb"));
            s.setIznos(rs.getDouble("iznos"));
            s.setCenaKarte(rs.getDouble("cenaKarte"));
            s.setBrojKarata(rs.getInt("brojKarata"));

            domen.KartaPredstave kp = new domen.KartaPredstave();
            kp.setIdKarta(rs.getInt("idKarta"));
            kp.setNazivPredstave(rs.getString("nazivPredstave"));
            kp.setZanr(rs.getString("zanr"));
            kp.setReditelj(rs.getString("reditelj"));
            kp.setDatumOdrzavanja(rs.getDate("datumOdrzavanja").toLocalDate());
            kp.setCena(rs.getDouble("cena"));

            s.setKartaPredstave(kp);

            Racun r = new Racun();
            r.setIdRacun(idRacun);
            s.setRacun(r);

            lista.add(s);
        }

        rs.close();
        st.close();
        return lista;
    }

}
