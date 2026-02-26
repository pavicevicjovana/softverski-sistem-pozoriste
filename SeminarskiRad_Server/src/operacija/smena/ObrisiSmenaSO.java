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
public class ObrisiSmenaSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if(objekat == null || !(objekat instanceof Smena)){
            throw new Exception("Sistem ne moze da obrise smenu");
        }
        
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        broker.delete((Smena)objekat);
    }
    
}
