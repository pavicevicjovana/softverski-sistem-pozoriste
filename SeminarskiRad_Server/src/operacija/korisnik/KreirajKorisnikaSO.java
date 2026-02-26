/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.korisnik;

import domen.Korisnik;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Jovana
 */
public class KreirajKorisnikaSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Korisnik)) {
            throw new Exception("Sistem ne moze da kreira korisnika!");
        }
        Korisnik k = (Korisnik) objekat;

        if (k.getIme() == null || k.getIme().isEmpty()) {
            throw new Exception("Sistem ne može da zapamti korisnika.");
        }
        if (k.getEmail().isEmpty() || k.getEmail() == null) {
            throw new Exception("Sistem ne može da zapamti korisnika.");
        }
        if (k.getPrezime().isEmpty() || k.getPrezime() == null) {
            throw new Exception("Sistem ne može da zapamti korisnika.");
        }
        if (!k.getEmail().contains("@")) {
            throw new Exception("Sistem ne može da zapamti korisnika.");
        }
        if (k.getMesto() == null) {
            throw new Exception("Sistem ne može da zapamti korisnika.");
        }
        if (k.getTipKorisnika() == null) {
            throw new Exception("Sistem ne može da zapamti korisnika.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        broker.add((Korisnik) objekat);
    }

}
