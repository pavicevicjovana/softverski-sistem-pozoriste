/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.smena;

import domen.Smena;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Jovana
 */
public class PromeniSmenaSO extends ApstraktnaGenerickaOperacija{

    @Override
    protected void preduslovi(Object objekat) throws Exception {
         if(objekat == null || !(objekat instanceof Smena)){
            throw new Exception("Sitem ne moze da zapamti smenu!");
        }
        Smena smena = (Smena)objekat;
        if(smena.getNazivSmena()==null || smena.getNazivSmena().isEmpty()){
            throw new Exception("Polje naziv je obavezno!");
        }
        if(smena.getVremePocetka()==null){
            throw new Exception("Polje vreme pocetka je obavezno!");
        }
        if(smena.getVremeZavrsetka()==null){
            throw new Exception("Polje vreme pocetka je obavezno!");
        }
        
        
        if(smena.getSatnica() <= 0){
            throw new Exception("Satnica mora biti veca od 0!");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        broker.edit((Smena)objekat);
    }
    
}
