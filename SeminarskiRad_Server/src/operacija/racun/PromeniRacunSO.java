/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.racun;

import domen.Racun;
import domen.StavkaRacuna;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import repository.db.DbConnectionFactory;
/**
 *
 * @author Jovana
 */
public class PromeniRacunSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Racun)) {
            throw new Exception("Sistem ne moze da zapamti racun.");
        }

        Racun r = (Racun) objekat;

        if (r.getIdRacun() <= 0 || r.getDatumTransakcije() == null || r.getUkupanIznos() < 0
                || r.getProdavac() == null || r.getKorisnik() == null
                || r.getStavke() == null || r.getStavke().isEmpty()) {
            throw new Exception("Sistem ne moze da zapamti racun.");
        }

        for (StavkaRacuna s : r.getStavke()) {
            if (s.getRb() <= 0 || s.getBrojKarata() <= 0 || s.getCenaKarte() < 0 || s.getIznos() < 0
                    || s.getKartaPredstave() == null) {
                throw new Exception("Sistem ne moze da zapamti racun.");
            }
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        Racun racunNovi = (Racun) objekat;

        //update racun
        broker.edit(racunNovi);

        
        List<StavkaRacuna> stareStavke = ucitajStareStavke(racunNovi.getIdRacun());

        HashMap<Integer, StavkaRacuna> starePoRb = new HashMap<>();
        for (StavkaRacuna s : stareStavke) {
            starePoRb.put(s.getRb(), s);
        }

        HashSet<Integer> rbNove = new HashSet<>();

       
        for (StavkaRacuna nova : racunNovi.getStavke()) {
            nova.setRacun(racunNovi);
            rbNove.add(nova.getRb());

            StavkaRacuna stara = starePoRb.get(nova.getRb());

            if (stara == null) {
                broker.add(nova); // nova stavka
            } else {
                boolean izmenjena =
                        stara.getBrojKarata() != nova.getBrojKarata()
                        || Double.compare(stara.getCenaKarte(), nova.getCenaKarte()) != 0
                        || Double.compare(stara.getIznos(), nova.getIznos()) != 0
                        || stara.getKartaPredstave().getIdKarta() != nova.getKartaPredstave().getIdKarta();

                if (izmenjena) {
                    broker.edit(nova);
                }
            }
        }

        // DELETE samo obrisanih stavki (one koje su bile u bazi a nisu u novim)
        for (StavkaRacuna stara : stareStavke) {
            if (!rbNove.contains(stara.getRb())) {
                broker.delete(stara);
            }
        }
    }

    private List<StavkaRacuna> ucitajStareStavke(int idRacun) throws Exception {
        String upit = "SELECT rb, racun, iznos, cenaKarte, brojKarata, kartaPredstave "
                    + "FROM stavkaracuna "
                    + "WHERE racun = " + idRacun;

        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(upit);

        List<StavkaRacuna> lista = new ArrayList<>();
        while (rs.next()) {
            int rb = rs.getInt("rb");
            double iznos = rs.getDouble("iznos");
            double cenaKarte = rs.getDouble("cenaKarte");
            int brojKarata = rs.getInt("brojKarata");
            int kartaId = rs.getInt("kartaPredstave");

          
            Racun r = new Racun();
            r.setIdRacun(idRacun);

            domen.KartaPredstave kp = new domen.KartaPredstave();
            kp.setIdKarta(kartaId);

            StavkaRacuna sr = new StavkaRacuna();
            sr.setRacun(r);
            sr.setRb(rb);
            sr.setIznos(iznos);
            sr.setCenaKarte(cenaKarte);
            sr.setBrojKarata(brojKarata);
            sr.setKartaPredstave(kp);

            lista.add(sr);
        }

        rs.close();
        st.close();
        return lista;
    }
    
    
}
