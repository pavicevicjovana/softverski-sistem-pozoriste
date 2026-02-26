/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.smena;
import operacija.ApstraktnaGenerickaOperacija;
import domen.Smena;

/**
 *
 * @author Jovana
 */
public class UbaciSmenaSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if(objekat == null || !(objekat instanceof Smena)){
            throw new Exception("Sitem ne moze da zapamti smenu.");
        }
        Smena smena = (Smena)objekat;
        if(smena.getNazivSmena()==null || smena.getNazivSmena().isEmpty()){
            throw new Exception("Sitem ne moze da zapamti smenu.");
        }
        if(smena.getVremePocetka()==null){
            throw new Exception("Sitem ne moze da zapamti smenu.");
        }
        if(smena.getVremeZavrsetka()==null){
            throw new Exception("Sitem ne moze da zapamti smenu.");
        }
        
        
        if(smena.getSatnica() <= 0){
            throw new Exception("Sitem ne moze da zapamti smenu.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        broker.add((Smena)objekat);
    }

}
