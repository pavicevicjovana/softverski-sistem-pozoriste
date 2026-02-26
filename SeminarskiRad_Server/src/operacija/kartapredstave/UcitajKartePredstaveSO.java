/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.kartapredstave;
import domen.KartaPredstave;
import java.util.ArrayList;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija; 
/**
 *
 * @author Jovana
 */
public class UcitajKartePredstaveSO extends ApstraktnaGenerickaOperacija {
    
    private List<KartaPredstave> karte;
        
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        karte = broker.getAll(objekat, kljuc);
    }

    public List<KartaPredstave> getKarte() {
        return karte;
    }
    
    
    
}
