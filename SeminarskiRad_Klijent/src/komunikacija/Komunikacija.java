/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;

import domen.KartaPredstave;
import domen.Korisnik;
import domen.Mesto;
import domen.Prodavac;
import domen.Racun;
import domen.Smena;
import domen.StavkaRacuna;
import domen.TipKorisnika;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import operacije.Operacije;
import transfer.KlijentskiZahtev;
import transfer.Posiljalac;
import transfer.Primalac;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Jovana
 */
public class Komunikacija {

    private Socket socket;
    private Posiljalac posiljalac;
    private Primalac primalac;
    private static Komunikacija instance;

    public static Komunikacija getInstance() {
        if (instance == null) {
            instance = new Komunikacija();
        }
        return instance;
    }

    private Komunikacija() {

    }

    public void konekcija() {
        try {
            socket = new Socket("localhost", 9000);
            posiljalac = new Posiljalac(socket);
            primalac = new Primalac(socket);
        } catch (IOException ex) {
            System.out.println("Server nije povezan! " + ex.getMessage());
        }

    }

    public Prodavac login(String email, String lozinka) {
        HashMap<Integer, String> mapa = new HashMap<>();
        mapa.put(1, email);
        mapa.put(2, lozinka);
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.LOGIN, mapa);

        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();

        Prodavac prodavac = (Prodavac) odgovor.getOdgovor();
        return prodavac;

    }

    public List<Korisnik> ucitajKorisnike() {
        List<Korisnik> korisnici = new ArrayList<>();

        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.UCITAJ_KORISNIKE, null);
        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();

        korisnici = (List<Korisnik>) odgovor.getOdgovor();
        return korisnici;
    }

    public List<Mesto> ucitajMesta() {
        List<Mesto> mesta = new ArrayList<>();
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.UCITAJ_MESTA, null);

        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        mesta = (List<Mesto>) odgovor.getOdgovor();
        return mesta;
    }

    public List<KartaPredstave> ucitajKarte() {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.PRIKAZI_KARTE, null);

        List<KartaPredstave> karte = new ArrayList<>();

        posiljalac.posalji(zahtev);
        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        karte = (List<KartaPredstave>) odgovor.getOdgovor();
        return karte;
    }

    public void kreirajKorisnika(Korisnik korisnik) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.KREIRAJ_KORISNIKA, korisnik);

        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();

        Object o = odgovor.getOdgovor();
        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspesno kreiran korisnik");
            return;
        }

        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }

        throw new Exception("Greska pri kreiranju korisnika!");
    }

    public void kreirajMesto(Mesto mesto) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.KREIRAJ_MESTO, mesto);

        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();
        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspesno kreirano mesto");
            return;
        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }
        throw new Exception("Greska pri kreiranju mesta!");
    }

    public void kreirajKartu(KartaPredstave karta) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.KREIRAJ_KARTU, karta);
        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();
        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspeh, karta je kreirana!");
            return;
        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }
        throw new Exception("Greska pri kreiranju karte!");

    }

    public void kreirajRacun(Racun racun) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.KREIRAJ_RACUN, racun);
        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();
        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspeh, racun kreiran");
            return;

        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }
        throw new Exception("Greska pri kreiranju racuna!");
    }

    public void promeniKorisnika(Korisnik korisnik) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.PROMENI_KORISNIKA, korisnik);

        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();

        Object o = odgovor.getOdgovor();
        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspesno je promenjen korisnik");
            cordinator.Cordinator.getInstance().osveziPrikaziKorisnikFormu();
            return;
        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }
        throw new Exception("Greska pri izmeni korisnika!");
    }

    public void promeniRacun(Racun racun) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.PROMENI_RACUN, racun);
        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();

        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspesno je promenjen racun");
            cordinator.Cordinator.getInstance().osveziPrikaziRacunFormu();
            return;
        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) o).getMessage());
        }
        throw new Exception("Greska pri izmeni racuna!");
    }

    public void azuzirajKartu(KartaPredstave karta) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.PROMENI_KARTU, karta);

        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();
        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspesno je promenjena karta");
            cordinator.Cordinator.getInstance().osveziPrikaziKartuFormu();
            return;

        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }
        throw new Exception("Greska pri izmeni karte!");
    }

    public void obrisiKorisnika(Korisnik korisnik) throws Exception {

        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.OBRISI_KORISNIKA, korisnik);
        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();

        Object o = odgovor.getOdgovor();

        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspesno obrisan korisnik");
            return;
        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }
        throw new Exception("Greska pri brisanju korisnika!");
    }

    public void obrisiMesto(Mesto mesto) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.OBRISI_MESTO, mesto);

        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();
        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspesno obrisano mesto");
            return;
        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }
        throw new Exception("Greska pri brisanju mesta!");
    }

    public void obrisiKartu(KartaPredstave karta) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.OBRISI_KARTU, karta);

        posiljalac.posalji(zahtev);
        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();
        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspesno obrisana karta");
            return;
        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }
        throw new Exception("Greska pri brisanju karte!");
    }

    public void ubaciSmenu(Smena smena) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.UBACI_SMENU, smena);

        posiljalac.posalji(zahtev);
        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();
        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspesno ubacena smena");
            return;
        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }
        throw new Exception("Greska pri ubacivanju smene! Klasa Komunikacija");
    }

    public List<Smena> ucitajSmene() {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.UCITAJ_SMENU, null);

        List<Smena> lista = new ArrayList<>();
        posiljalac.posalji(zahtev);
        ServerskiOdgovor odg = (ServerskiOdgovor) primalac.primi();
        lista = (List<Smena>) odg.getOdgovor();
        return lista;
    }

    public List<Prodavac> ucitajProdavce() {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.UCITAJ_PRODAVCE, null);
        List<Prodavac> listaProdavca = new ArrayList<>();

        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        listaProdavca = (List<Prodavac>) odgovor.getOdgovor();

        return listaProdavca;
    }

    public void kreirajProdavca(Prodavac prodavac) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.KREIRAJ_PRODAVCE, prodavac);

        posiljalac.posalji(zahtev);
        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();
        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspesno kreiran prodavac");
            return;
        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }
        throw new Exception("Greska pri ubacivanju prodavca! Klasa Komunikacija");

    }

    public void promeniProdavca(Prodavac prodavacIzmena) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.PROMENI_PRODAVCE, prodavacIzmena);

        posiljalac.posalji(zahtev);
        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();
        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspesno izmenjen prodavac");
            cordinator.Cordinator.getInstance().osveziPrikaziProdavacFormu();
            return;
        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }
        throw new Exception("Greska pri ubacivanju prodavca! Klasa Komunikacija");

    }

    public void obrisiProdavca(Prodavac prodavac) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.OBRISI_PRODAVCE, prodavac);
        posiljalac.posalji(zahtev);
        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();
        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspesno obrisan prodavac");
            return;
        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }
        throw new Exception("Greska pri brisanju prodavca!");
    }

    public void azurirajSmenu(Smena smena) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.PROMENI_SMENU, smena);
        posiljalac.posalji(zahtev);
        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();
        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("uspesno izmenjena smena");
            cordinator.Cordinator.getInstance().osveziPrikaziSmenaFormu();
            return;
        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }
        throw new Exception("Greska pri izmeni smene Komunikacija");
    }

    public void obrisiSmenu(Smena smena) throws Exception {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.OBRISI_SMENU, smena);
        posiljalac.posalji(zahtev);
        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();
        if (o instanceof Boolean && (Boolean) o) {
            System.out.println("Uspesno obrisan smena");
            return;
        }
        if (o instanceof Exception) {
            throw new Exception(((Exception) odgovor.getOdgovor()).getMessage());
        }
        throw new Exception("Greska pri brisanju smene!");
    }

    public List<Racun> ucitajRacune() {
        List<Racun> listaRacuna = new ArrayList<>();
        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.UCITAJ_RACUNE, null);
        posiljalac.posalji(zahtev);
        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();

        listaRacuna = (List<Racun>) odgovor.getOdgovor();

        return listaRacuna;

    }

    public List<Korisnik> pretraziKorisnika(String ime, String prezime, String imeMesta, String tipKorisnikaNaZiv) throws Exception {
        HashMap<String, Object> parametri = new HashMap<>();
        parametri.put("ime", ime);
        parametri.put("prezime", prezime);
        parametri.put("mesto", imeMesta);
        parametri.put("tip", tipKorisnikaNaZiv);

        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.PRETRAZI_KORISNIKE, parametri);
        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();

        if (o instanceof Exception) {
            throw new Exception(((Exception) o).getMessage());
        }
        return (List<Korisnik>) o;
    }

    public List<Racun> pretraziRacune(String korisnik, String prodavac, LocalDate datum, String karta) throws Exception {
        HashMap<String, Object> k = new HashMap<>();
        k.put("korisnik", korisnik);
        k.put("prodavac", prodavac);
        k.put("datum", datum);
        k.put("karta", karta);

        KlijentskiZahtev zahtev = new KlijentskiZahtev(Operacije.PRETRAZI_RACUN, k);
        posiljalac.posalji(zahtev);

        ServerskiOdgovor odgovor = (ServerskiOdgovor) primalac.primi();
        Object o = odgovor.getOdgovor();

        if (o instanceof Exception) {
            throw new Exception(((Exception) o).getMessage());
        }
        return (List<Racun>) o;
    }

}
