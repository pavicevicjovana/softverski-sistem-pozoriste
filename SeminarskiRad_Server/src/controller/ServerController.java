/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domen.KartaPredstave;
import domen.Korisnik;
import domen.Mesto;
import domen.Prodavac;
import domen.Racun;
import domen.Smena;
import domen.StavkaRacuna;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import operacija.kartapredstave.KreirajKartuSO;
import operacija.kartapredstave.ObrisiKartuSO;
import operacija.kartapredstave.PromeniKartuSO;
import operacija.kartapredstave.UcitajKartePredstaveSO;
import operacija.korisnik.KreirajKorisnikaSO;
import operacija.korisnik.ObrisiKorisnikaSO;
import operacija.korisnik.PretraziKorisnikaSO;
import operacija.korisnik.PromeniKorisnikSO;
import operacija.korisnik.UcitajKorisnikeSO;
import operacija.login.PrijaviProdavacSO;
import operacija.mesto.KreirajMestoSO;
import operacija.mesto.ObrisiMestoSO;
import operacija.mesto.UcitajMestaSO;
import operacija.prodavac.KreirajProdavcaSO;
import operacija.prodavac.ObrisiProdavcaSO;
import operacija.prodavac.PromeniProdavcaSO;
import operacija.prodavac.UcitajProdavceSO;
import operacija.racun.KreirajRacunSO;
import operacija.racun.PretraziRacuneSO;
import operacija.racun.PromeniRacunSO;
import operacija.racun.UcitajRacunSO;
import operacija.smena.ObrisiSmenaSO;
import operacija.smena.PromeniSmenaSO;
import operacija.smena.UbaciSmenaSO;
import operacija.smena.UcitajSmenuSO;

/**
 *
 * @author Jovana
 */
public class ServerController {

    private static ServerController instance;

    private ServerController() {

    }

    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    public Prodavac login(String email, String lozinka) throws Exception {
        PrijaviProdavacSO operacija = new PrijaviProdavacSO();
        Prodavac p = new Prodavac();
        p.setEmail(email);
        p.setLozinka(lozinka);

        operacija.izvrsi(p, null);
        System.out.println("Klasa ServerController: " + operacija.getProdavac());
        return operacija.getProdavac();

    }

    public List<Korisnik> ucitajListuKorisnika() throws Exception {
        UcitajKorisnikeSO operacija = new UcitajKorisnikeSO();
        operacija.izvrsi(new Korisnik(), " JOIN MESTO ON (MESTO.idMesto = KORISNIK.mesto) ");
        System.out.println("Klasa ServerController: " + operacija.getKorisnici());
        return operacija.getKorisnici();
    }

    public List<Mesto> ucitajMesta() throws Exception {
        UcitajMestaSO operacija = new UcitajMestaSO();
        operacija.izvrsi(new Mesto(), "");
        return operacija.getMesta();
    }

    public List<KartaPredstave> ucitajKartePredstave() throws Exception {
        UcitajKartePredstaveSO operacija = new UcitajKartePredstaveSO();
        operacija.izvrsi(new KartaPredstave(), "");
        return operacija.getKarte();
    }

    public List<Smena> ucitajSmene() throws Exception {
        UcitajSmenuSO operacija = new UcitajSmenuSO();
        operacija.izvrsi(new Smena(), "");
        return operacija.getLista();
    }

    public List<Racun> ucitajListuRacuna() throws Exception {
        UcitajRacunSO operacija = new UcitajRacunSO();
        operacija.izvrsi(new Racun(), "");
        return operacija.getRacuni();
    }

    public List<Prodavac> ucitajListuProdavaca() throws Exception {
        UcitajProdavceSO operacija = new UcitajProdavceSO();
        operacija.izvrsi(new Prodavac(), "");
        return operacija.getLista();
    }

    public void kreirajKorisnika(Korisnik korisnik) throws Exception {
        KreirajKorisnikaSO operacija = new KreirajKorisnikaSO();
        operacija.izvrsi(korisnik, "");
    }

    public void ubaciSmenu(Smena smena) throws Exception {
        UbaciSmenaSO operacija = new UbaciSmenaSO();
        operacija.izvrsi(smena, "");
    }

    public void kreirajRacun(Racun racun) throws Exception {
        KreirajRacunSO operacija = new KreirajRacunSO();
        operacija.izvrsi(racun, "");
    }

    public void kreirajMesto(Mesto mesto) throws Exception {
        KreirajMestoSO operacija = new KreirajMestoSO();
        operacija.izvrsi(mesto, "");
    }

    public void kreirajKartu(KartaPredstave kartaZaKreiranje) throws Exception {
        KreirajKartuSO operacija = new KreirajKartuSO();
        operacija.izvrsi(kartaZaKreiranje, "");
    }

    public void kreirajProdavca(Prodavac prodavacZaKreiranje) throws Exception {
        KreirajProdavcaSO operacija = new KreirajProdavcaSO();
        operacija.izvrsi(prodavacZaKreiranje, "");
    }

    public void azurirajKorisnika(Korisnik korisnikIzmena) throws Exception {
        PromeniKorisnikSO operacija = new PromeniKorisnikSO();
        operacija.izvrsi(korisnikIzmena, "");
    }

    public void azurirajKartu(KartaPredstave kartaZaIzmenu) throws Exception {
        PromeniKartuSO operacija = new PromeniKartuSO();
        operacija.izvrsi(kartaZaIzmenu, "");
    }

    public void azurirajRacun(Racun racunIzmena) throws Exception {
        PromeniRacunSO operacija = new PromeniRacunSO();
        operacija.izvrsi(racunIzmena, "");
    }

    public void azurirajProdavca(Prodavac prodavacIzmena) throws Exception {
        PromeniProdavcaSO operacija = new PromeniProdavcaSO();
        operacija.izvrsi(prodavacIzmena, "");
    }

    public void azurirajSmenu(Smena smenaIzmena) throws Exception {
        PromeniSmenaSO operacija = new PromeniSmenaSO();
        operacija.izvrsi(smenaIzmena, "");
    }

    public void obrisiKorisnika(Korisnik korisnik) throws Exception {
        ObrisiKorisnikaSO operacija = new ObrisiKorisnikaSO();
        operacija.izvrsi(korisnik, "");
    }

    public void obrisiMesto(Mesto mestoZaBrisanje) throws Exception {
        ObrisiMestoSO operacija = new ObrisiMestoSO();
        operacija.izvrsi(mestoZaBrisanje, "");
    }

    public void obrisiKartu(KartaPredstave karta) throws Exception {
        ObrisiKartuSO operacija = new ObrisiKartuSO();
        operacija.izvrsi(karta, "");
    }

    public void obrisiProdavca(Prodavac prodavacBrisanje) throws Exception {
        ObrisiProdavcaSO operacija = new ObrisiProdavcaSO();
        operacija.izvrsi(prodavacBrisanje, "");
    }

    public void obrisiSmenu(Smena smenaBrisanje) throws Exception {
        ObrisiSmenaSO operacija = new ObrisiSmenaSO();
        operacija.izvrsi(smenaBrisanje, "");
    }

    public List<Korisnik> pretraziKorisnike(HashMap<String, Object> k) throws Exception {
        PretraziKorisnikaSO operacija = new PretraziKorisnikaSO();
        operacija.izvrsi(k, "");
        return operacija.getLista();
    }

    public List<Racun> pretraziRacune(HashMap<String, Object> ka) throws Exception {
        PretraziRacuneSO operacija = new PretraziRacuneSO();
        operacija.izvrsi(ka, "");
        return operacija.getLista();
    }

}
