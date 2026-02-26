/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.racun;
import domen.KartaPredstave;
import java.sql.*;
import domen.Racun;
import domen.StavkaRacuna;
import java.util.ArrayList;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import repository.db.DbConnectionFactory;

/**
 *
 * @author Jovana
 */
public class UcitajRacunSO extends ApstraktnaGenerickaOperacija {

    List<Racun> racuni;

    @Override
    protected void preduslovi(Object objekat) throws Exception {

    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        racuni = broker.getAll(objekat, " JOIN prodavac on prodavac.idProdavac=racun.prodavac "
                + "join korisnik on korisnik.idKorisnik=racun.korisnik "
                + "join mesto on mesto.idmesto=korisnik.mesto ");

        for (Racun r : racuni) {
            r.setStavke(ucitajStavkeZaRacun(r.getIdRacun()));
        }
    }

    public List<Racun> getRacuni() {
        return racuni;
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

            KartaPredstave kp = new KartaPredstave();
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
