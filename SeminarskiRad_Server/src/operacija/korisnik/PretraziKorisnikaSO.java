/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.korisnik;

import domen.Korisnik;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Jovana
 */
public class PretraziKorisnikaSO extends ApstraktnaGenerickaOperacija {
    private List<Korisnik> lista = new ArrayList<>();

    public List<Korisnik> getLista() {
        return lista;
    }
    
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if(!(objekat instanceof HashMap)){
            throw new Exception("Neispravni kriterijumi pretrage");
        }
        HashMap<String, Object> k = (HashMap<String, Object>) objekat;
        
        String ime = (String) k.get("ime");
        String prezime= (String) k.get("prezime");
        String tip = (String) k.get("tip");
        String mesto = (String) k.get("mesto");
        if(ime.isEmpty() && prezime.isEmpty() && tip == null && mesto == null){
            throw new Exception("Da bi ste pretrazivali unesite barem jedan paramter");
        }
        
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        HashMap<String, Object> k = (HashMap<String, Object>) objekat;
        
        String ime = (String) k.get("ime");
        String prezime= (String) k.get("prezime");
        String tip = (String) k.get("tip");
        String mesto = (String) k.get("mesto");
        
        StringBuilder uslov = new StringBuilder();
        uslov.append(" JOIN mesto ON mesto.idMesto = korisnik.mesto ");
        uslov.append(" WHERE 1=1 ");

        if (ime != null && !ime.trim().isEmpty()) {
            uslov.append(" AND LOWER(korisnik.ime) LIKE '%")
                 .append(escapeLike(ime.trim().toLowerCase()))
                 .append("%' ");
        }

        if (prezime != null && !prezime.trim().isEmpty()) {
            uslov.append(" AND LOWER(korisnik.prezime) LIKE '%")
                 .append(escapeLike(prezime.trim().toLowerCase()))
                 .append("%' ");
        }

        
        if (tip != null && !tip.trim().isEmpty()) {
            uslov.append(" AND korisnik.tipKorisnika = '")
                 .append(tip.trim())
                 .append("' ");
        }

        if (mesto != null && !mesto.trim().isEmpty()) {
            uslov.append(" AND LOWER(mesto.naziv) LIKE '%")
                 .append(escapeLike(mesto.trim().toLowerCase()))
                 .append("%' ");
        }

        uslov.append(" ORDER BY korisnik.idKorisnik ");

        lista = (List<Korisnik>)(List<?>) broker.getAll(new Korisnik(), uslov.toString());
    }
    private String escapeLike(String s) {
        return s.replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
    }
    
}
