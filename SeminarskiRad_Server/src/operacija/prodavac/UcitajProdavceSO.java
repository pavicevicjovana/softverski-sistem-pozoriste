/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.prodavac;

import domen.Prodavac;
import java.util.ArrayList;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Jovana
 */
public class UcitajProdavceSO extends ApstraktnaGenerickaOperacija{

    private List<Prodavac> lista = new ArrayList<>();
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if(objekat == null || !(objekat instanceof Prodavac))
            throw new Exception("Sistem ne moze da prikaze prodavce");
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        lista=broker.getAll(objekat, kljuc);
    }

    public List<Prodavac> getLista() {
        return lista;
    }
    
    
    
}
