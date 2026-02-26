/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.mesto;

import domen.Mesto;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Jovana
 */
public class KreirajMestoSO extends ApstraktnaGenerickaOperacija {
    
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if(objekat == null || !(objekat instanceof Mesto)){
            throw new Exception("Sistem ne moze da kreira mesto");
        }
        Mesto mesto = (Mesto)objekat;
        if(mesto.getNaziv().isEmpty()){
            throw new Exception("Polje naziv je obavezno!");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        broker.add((Mesto)objekat);
    }
    
}
