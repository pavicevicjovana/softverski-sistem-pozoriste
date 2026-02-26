/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.korisnik;

import domen.Korisnik;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Jovana
 */
public class UcitajKorisnikeSO extends ApstraktnaGenerickaOperacija {
    private List<Korisnik> korisnici;
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        korisnici=broker.getAll(objekat,kljuc);
    }

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }
    
    
    
}
