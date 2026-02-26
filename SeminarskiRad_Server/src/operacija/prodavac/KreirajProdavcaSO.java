/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.prodavac;

import domen.Prodavac;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Jovana
 */
public class KreirajProdavcaSO extends ApstraktnaGenerickaOperacija {
    
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Prodavac)) {
            throw new Exception("Sistem ne moze da kreira prodavca");
        }
        Prodavac p = (Prodavac) objekat;
        if (p.getIme() == null || p.getIme().isEmpty()) {
            throw new Exception("Polje ime ne sme biti prazno!");
        }
        if (p.getPrezime() == null || p.getPrezime().isEmpty()) {
            throw new Exception("Polje prezime ne sme biti prazno!");
        }
        if (p.getEmail() == null || p.getEmail().isEmpty()) {
            throw new Exception("Polje email ne sme biti prazno!");
        }
        if (p.getKontaktTelefon() == null || p.getKontaktTelefon().isEmpty()) {
            throw new Exception("Polje kontakt ne sme biti prazno!");
        }
        if (p.getLozinka() == null || p.getLozinka().isEmpty()) {
            throw new Exception("Polje lozinka ne sme biti prazno!");
        }
    }
    
    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        broker.add((Prodavac) objekat);
    }

  
    
}
