/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import controller.ServerController;
import domen.KartaPredstave;
import domen.Korisnik;
import domen.Mesto;
import domen.Prodavac;
import domen.Racun;
import domen.Smena;
import domen.StavkaRacuna;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import operacije.Operacije;
import transfer.KlijentskiZahtev;
import transfer.Posiljalac;
import transfer.Primalac;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Jovana
 */
public class KlijentNit extends Thread {

    private Socket socket;
    private Posiljalac posiljalac;
    private Primalac primalac;

    public KlijentNit(Socket socket) {
        this.socket = socket;
        this.posiljalac = new Posiljalac(socket);
        this.primalac = new Primalac(socket);
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {

                KlijentskiZahtev kz = (KlijentskiZahtev) primalac.primi();
                ServerskiOdgovor odgovor = upravljajZahtevom(kz);
                posiljalac.posalji(odgovor);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ServerskiOdgovor upravljajZahtevom(KlijentskiZahtev kz) {
        ServerskiOdgovor odgovor = new ServerskiOdgovor(null);
        try {

            switch (kz.getOperacija()) {
                //login
                case Operacije.LOGIN:
                    HashMap<Integer, String> mapa = (HashMap<Integer, String>) kz.getParametar();
                    String email = mapa.get(1);
                    String lozinka = mapa.get(2);

                    Prodavac prodavac = ServerController.getInstance().login(email, lozinka);
                    odgovor.setOdgovor(prodavac);

                    break;
                case Operacije.PRETRAZI_KORISNIKE:
                try {
                    HashMap<String, Object> k = (HashMap<String, Object>) kz.getParametar();
                    List<Korisnik> korisnici = ServerController.getInstance().pretraziKorisnike(k);
                    odgovor.setOdgovor(korisnici);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }
                break;
                case Operacije.PRETRAZI_RACUN:
                try {
                    HashMap<String, Object> ka = (java.util.HashMap<String, Object>) kz.getParametar();
                    List<Racun> racuni = ServerController.getInstance().pretraziRacune(ka);
                    odgovor.setOdgovor(racuni);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }
                break;
                //ucitaj
                case Operacije.UCITAJ_RACUNE:
                    List<Racun> racuni = ServerController.getInstance().ucitajListuRacuna();

                    odgovor.setOdgovor(racuni);

                    break;

                case Operacije.UCITAJ_SMENU:
                    List<Smena> smene = ServerController.getInstance().ucitajSmene();
                    odgovor.setOdgovor(smene);
                    break;
                case Operacije.UCITAJ_KORISNIKE:
                    List<Korisnik> korisnici = ServerController.getInstance().ucitajListuKorisnika();
                    odgovor.setOdgovor(korisnici);
                    break;
                case Operacije.UCITAJ_MESTA:
                    List<Mesto> mesta = ServerController.getInstance().ucitajMesta();
                    odgovor.setOdgovor(mesta);
                    break;
                case Operacije.PRIKAZI_KARTE:
                    List<KartaPredstave> karte = ServerController.getInstance().ucitajKartePredstave();
                    odgovor.setOdgovor(karte);
                    break;
                case Operacije.UCITAJ_PRODAVCE:
                    List<Prodavac> prodavci = ServerController.getInstance().ucitajListuProdavaca();
                    odgovor.setOdgovor(prodavci);
                    break;

                //kreiraj
                case Operacije.KREIRAJ_PRODAVCE:
                    try {
                    Prodavac prodavacZaKreiranje = (Prodavac) kz.getParametar();
                    ServerController.getInstance().kreirajProdavca(prodavacZaKreiranje);
                    odgovor.setOdgovor(true);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }
                break;
                case Operacije.KREIRAJ_RACUN:
                   try {
                    Racun racun = (Racun) kz.getParametar();
                    ServerController.getInstance().kreirajRacun(racun);
                    odgovor.setOdgovor(true);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }

                break;
                case Operacije.KREIRAJ_KARTU:
                    try {
                    KartaPredstave kartaZaKreiranje = (KartaPredstave) kz.getParametar();
                    ServerController.getInstance().kreirajKartu(kartaZaKreiranje);
                    odgovor.setOdgovor(true);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }
                break;
                case Operacije.UBACI_SMENU:
                    try {
                    Smena smena = (Smena) kz.getParametar();
                    ServerController.getInstance().ubaciSmenu(smena);
                    odgovor.setOdgovor(true);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }

                break;

                case Operacije.KREIRAJ_MESTO:
                    try {
                    Mesto mesto = (Mesto) kz.getParametar();
                    ServerController.getInstance().kreirajMesto(mesto);
                    odgovor.setOdgovor(true);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }
                break;

                case Operacije.KREIRAJ_KORISNIKA:
                    try {
                    Korisnik korisnik = (Korisnik) kz.getParametar();
                    ServerController.getInstance().kreirajKorisnika(korisnik);
                    odgovor.setOdgovor(true);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }
                break;

                //obrisi
                case Operacije.OBRISI_PRODAVCE:
                    try {
                    Prodavac prodavacBrisanje = (Prodavac) kz.getParametar();
                    ServerController.getInstance().obrisiProdavca(prodavacBrisanje);
                    odgovor.setOdgovor(true);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }
                break;
                case Operacije.OBRISI_KORISNIKA:
                    try {
                    Korisnik korisnik = (Korisnik) kz.getParametar();
                    ServerController.getInstance().obrisiKorisnika(korisnik);
                    odgovor.setOdgovor(true);

                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }
                break;

                case Operacije.OBRISI_MESTO:
                    try {
                    Mesto mestoZaBrisanje = (Mesto) kz.getParametar();
                    ServerController.getInstance().obrisiMesto(mestoZaBrisanje);
                    odgovor.setOdgovor(true);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }

                break;
                case Operacije.OBRISI_SMENU:
                    try {
                    Smena smenaBrisanje = (Smena) kz.getParametar();
                    ServerController.getInstance().obrisiSmenu(smenaBrisanje);
                    odgovor.setOdgovor(true);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }
                break;
                case Operacije.OBRISI_KARTU:
                    try {
                    KartaPredstave karta = (KartaPredstave) kz.getParametar();
                    ServerController.getInstance().obrisiKartu(karta);
                    odgovor.setOdgovor(true);

                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }

                break;
                //promeni
                case Operacije.PROMENI_SMENU:
                    try {
                    Smena smenaIzmena = (Smena) kz.getParametar();
                    ServerController.getInstance().azurirajSmenu(smenaIzmena);
                    odgovor.setOdgovor(true);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }
                break;
                case Operacije.PROMENI_PRODAVCE:
                    try {
                    Prodavac prodavacIzmena = (Prodavac) kz.getParametar();
                    ServerController.getInstance().azurirajProdavca(prodavacIzmena);
                    odgovor.setOdgovor(true);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }
                case Operacije.PROMENI_RACUN:
                    try {
                    Racun racunIzmena = (Racun) kz.getParametar();
                    ServerController.getInstance().azurirajRacun(racunIzmena);
                    odgovor.setOdgovor(true);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }

                break;
                case Operacije.PROMENI_KORISNIKA:
                    try {
                    Korisnik korisnikIzmena = (Korisnik) kz.getParametar();
                    ServerController.getInstance().azurirajKorisnika(korisnikIzmena);
                    odgovor.setOdgovor(true);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }
                break;

                case Operacije.PROMENI_KARTU:
                    try {
                    KartaPredstave kartaZaIzmenu = (KartaPredstave) kz.getParametar();
                    ServerController.getInstance().azurirajKartu(kartaZaIzmenu);
                    odgovor.setOdgovor(true);
                } catch (Exception e) {
                    odgovor.setOdgovor(e);
                }
                break;
                default:
                    return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return odgovor;
    }

}
