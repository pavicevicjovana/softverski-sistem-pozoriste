/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.login;
import domen.Prodavac;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;
/**
 *
 * @author Jovana
 */
public class PrijaviProdavacSO extends ApstraktnaGenerickaOperacija {

    private Prodavac prodavac;
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if(objekat == null || !(objekat instanceof Prodavac)){
            throw new Exception("Sistem ne moze da nadje Prodavca");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        List<Prodavac> sviProdavci=broker.getAll(objekat, null);
        System.out.println("loginOpreacija : "+sviProdavci);
        
        for (Prodavac prodavac1 : sviProdavci) {
            if(prodavac1.equals((Prodavac)objekat)){
                prodavac=prodavac1;
                return;
            }
                
        }
        prodavac=null;
    }

    public Prodavac getProdavac() {
        return prodavac;
    }
    
    
    
}
