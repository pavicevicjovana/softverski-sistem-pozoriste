/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.korisnik;

import domen.Korisnik;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Jovana
 */
public class PromeniKorisnikSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
         if(objekat == null || !(objekat instanceof Korisnik)){
            throw new Exception("Sistem ne moze da zapamti korisnika!");
        }
        Korisnik k =(Korisnik)objekat;
        if(k.getIme()==null || k.getIme().isEmpty() || k.getPrezime().isEmpty() || k.getEmail().isEmpty() || k.getPrezime()==null || k.getEmail()==null || !k.getEmail().contains("@")
                || k.getMesto()==null || k.getTipKorisnika()==null){
            throw new Exception("Sistem ne moze da zapamti korisnika");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        broker.edit((Korisnik)objekat);
    }
    
}
