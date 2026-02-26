/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.mesto;

import domen.Mesto;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Jovana
 */
public class UcitajMestaSO extends ApstraktnaGenerickaOperacija{
    private List<Mesto> mesta;
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
       mesta = broker.getAll(objekat, kljuc);
    }

    public List<Mesto> getMesta() {
        return mesta;
    }
    
    
    
}
