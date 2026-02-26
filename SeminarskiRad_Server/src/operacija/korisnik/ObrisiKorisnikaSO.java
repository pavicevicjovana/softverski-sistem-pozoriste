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
public class ObrisiKorisnikaSO extends ApstraktnaGenerickaOperacija {
    
    
    
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if(objekat == null || !(objekat instanceof Korisnik)){
            throw new Exception("Sistem ne moze da nadje korisnika");
        }
        
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        broker.delete((Korisnik)objekat);
    }
    
}
