/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.smena;

import domen.Smena;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Jovana
 */
public class UcitajSmenuSO extends ApstraktnaGenerickaOperacija {
    private List<Smena> lista;
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if(objekat==null || !(objekat instanceof Smena))
            throw new Exception("Sistem ne moze da prikaze smene!");
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        lista= broker.getAll(new Smena(), "");
        
        
    }

    public List<Smena> getLista() {
        return lista;
    }
    
    
}
