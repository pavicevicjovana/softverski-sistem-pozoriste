/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.racun;

import domen.ApstraktniDomenskiObjekat;
import domen.Racun;
import domen.StavkaRacuna;
import java.sql.Date;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Jovana
 */
public class KreirajRacunSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if(objekat==null || !(objekat instanceof Racun)){
            throw new Exception("Sistem ne moze da zapamti racun.");
        }
        
        Racun racun = (Racun) objekat;
        if(racun.getDatumTransakcije() == null || racun.getUkupanIznos()<0 || racun.getKorisnik()==null
                || racun.getProdavac() == null){
            throw new Exception("Sistem ne moze da zapamti racun.");
        }
        java.sql.Date sqlDatum=Date.valueOf(racun.getDatumTransakcije());
        String uslov = " WHERE datumTransakcije = '"+ sqlDatum +"'"
                + " AND prodavac = "+racun.getProdavac().getIdProdavac() 
                + " AND korisnik = "+racun.getKorisnik().getIdKorisnik()
                + " AND ukupanIznos = "+ racun.getUkupanIznos();
        
        List<ApstraktniDomenskiObjekat> lista = broker.getAll(new Racun(), uslov);
        if(!lista.isEmpty()){
            throw new Exception("Racun vec postoji.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        Racun racun = (Racun) objekat;
        int idRacun = broker.addReturnKey(racun);
        racun.setIdRacun(idRacun);
        List<StavkaRacuna> stavke=racun.getStavke();
        for (StavkaRacuna stavkaRacuna : stavke) {
            stavkaRacuna.setRacun(racun);
            broker.add(stavkaRacuna);
        }
    }
    
}
