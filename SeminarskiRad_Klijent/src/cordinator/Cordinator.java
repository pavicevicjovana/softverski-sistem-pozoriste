/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cordinator;

import controllers.GlavnaFormaController;
import controllers.KreirajKartuController;
import controllers.KreirajKorisnikaController;
import controllers.KreirajMestoController;
import controllers.KreirajProdavcaController;
import controllers.KreirajRacunController;
import controllers.LoginController;
import controllers.PrikazKorisnikaController;
import controllers.PrikazProdavcaController;
import controllers.PrikazRacunaController;
import controllers.PrikaziKartePredstaveController;
import controllers.PrikaziMestaController;
import controllers.PrikaziSmenaController;
import controllers.UbaciSmenaController;
import domen.Prodavac;
import forme.GlavnaForma;
import forme.KreirajKorisnikaForma;
import forme.LoginForma;
import forme.PrikazKorisnikaForma;
import java.util.HashMap;
import forme.FormMode;
import forme.KreirajKartuForma;
import forme.KreirajMestoForma;
import forme.KreirajProdavcaForma;
import forme.KreirajRacunForma;
import forme.PrikazProdavcaForma;
import forme.PrikazRacunaForme;
import forme.PrikaziKartePredstaveForma;
import forme.PrikaziMestaForma;
import forme.PrikaziSmenuForma;
import forme.UbaciSmenaForma;

/**
 *
 * @author Jovana
 */
public class Cordinator {

    private static Cordinator instance;
    private Prodavac ulogovani;
    private HashMap<String, Object> parametri;
    private LoginController loginController;
    private GlavnaFormaController glavnaFormaController;
    private PrikazKorisnikaController prikazKorisnikaController;
    private KreirajKorisnikaController kreirajKorisnikaController;
    private KreirajMestoController kreirajMestoController;
    private PrikaziMestaController prikaziMestaController;
    private PrikaziKartePredstaveController prikaziKarteController;
    private KreirajKartuController kreirajKartuController;
    private UbaciSmenaController ubaciSmenaController;
    private PrikaziSmenaController prikaziSmenaController;
    private PrikazProdavcaController prikazProdavcaController;
    private KreirajProdavcaController kreirajProdavcaController;
    private PrikazRacunaController prikaziRacunController;
    private KreirajRacunController kreirajRacunController;
    public static Cordinator getInstance() {
        if (instance == null) {
            instance = new Cordinator();
        }
        return instance;
    }

    private Cordinator() {
        parametri = new HashMap<>();
    }

    public void otvoriLoginFormu() {
        loginController = new LoginController(new LoginForma());
        loginController.otvoriFormu();
    }

    public void otvoriPrikaziMestaFormu() {
        prikaziMestaController = new PrikaziMestaController(new PrikaziMestaForma());
        prikaziMestaController.otvoriFormu();
    }

    public void otvoriPrikaziKarteFormu() {
        prikaziKarteController = new PrikaziKartePredstaveController(new PrikaziKartePredstaveForma());
        prikaziKarteController.otvoriFormu();
    }

    public void otvoriGlavnuFormu() {
        glavnaFormaController = new GlavnaFormaController(new GlavnaForma());
        glavnaFormaController.otvoriFormu();
    }

    public void otvoriPrikazPacijenataFormu() {
        prikazKorisnikaController = new PrikazKorisnikaController(new PrikazKorisnikaForma());
        prikazKorisnikaController.otvoriFormu();
    }

    public void otvoriKreirajKorisnikaFormu() {
        kreirajKorisnikaController = new KreirajKorisnikaController(new KreirajKorisnikaForma());
        kreirajKorisnikaController.otvoriFormu(FormMode.kreiraj);
    }

    public void otvoriKreirajMestoFormu() {
        kreirajMestoController = new KreirajMestoController(new KreirajMestoForma());
        kreirajMestoController.otvoriFormu();
    }

    public void setUlogovani(Prodavac ulogovani) {
        this.ulogovani = ulogovani;
    }

    public Prodavac getUlogovani() {
        return ulogovani;
    }

    public void dodajParametar(String s, Object o) {
        parametri.put(s, o);
    }

    public Object vratiParametar(String s) {
        return parametri.get(s);
    }

    public void otvoriPromeniKorisnikaFormu() {
        kreirajKorisnikaController = new KreirajKorisnikaController(new KreirajKorisnikaForma());
        kreirajKorisnikaController.otvoriFormu(FormMode.promeni);
    }

    public void osveziPrikaziKorisnikFormu() {
        prikazKorisnikaController.osveziFormu();
    }

    public void osveziPrikaziKartuFormu() {
        prikaziKarteController.osveziFormu();
    }

    public void otvoriPromeniKartuFormu() {
        kreirajKartuController = new KreirajKartuController(new KreirajKartuForma());
        kreirajKartuController.otvoriFormu(FormMode.promeni);
    }

    public void otvoriKreirajKartuFormu() {
        kreirajKartuController = new KreirajKartuController(new KreirajKartuForma());
        kreirajKartuController.otvoriFormu(FormMode.kreiraj);
    }

    public void otvoriUbaciSmenaFormu() {
        ubaciSmenaController = new UbaciSmenaController(new UbaciSmenaForma());
        ubaciSmenaController.otvoriFormu(FormMode.kreiraj);
    }

    public void otvoriPrikaziSmenaFormu() {
        prikaziSmenaController = new PrikaziSmenaController(new PrikaziSmenuForma());
        prikaziSmenaController.otvoriFormu();
    }

    public void otvoriPrikazProdavcaFormu() {
        prikazProdavcaController = new PrikazProdavcaController(new PrikazProdavcaForma());
        prikazProdavcaController.otvoriFormu();
    }

    public void otvoriKreirajProdavcaFormu() {
        kreirajProdavcaController = new KreirajProdavcaController(new KreirajProdavcaForma());
        kreirajProdavcaController.otvoriFormu(FormMode.kreiraj);
    }

    public void otvoriPromeniProdavcaFormu() {
        kreirajProdavcaController = new KreirajProdavcaController(new KreirajProdavcaForma());
        kreirajProdavcaController.otvoriFormu(FormMode.promeni);
    }

    public void osveziPrikaziProdavacFormu() {
        prikazProdavcaController.osveziFormu();
    }

    public void otvoriPromeniSmenaFormu() {
        ubaciSmenaController = new UbaciSmenaController(new UbaciSmenaForma());
        ubaciSmenaController.otvoriFormu(FormMode.promeni);
    }

    public void osveziPrikaziSmenaFormu() {
        prikaziSmenaController.osveziFormu();
    }

    public void otvoriPrikaziRacunFormu() {
        prikaziRacunController=new PrikazRacunaController(new PrikazRacunaForme());
        prikaziRacunController.otvoriFormu();
    }

    public void otvoriKreirajRacunFromu() {
        kreirajRacunController=new KreirajRacunController(new KreirajRacunForma());
        kreirajRacunController.otvoriKreirajRacunFormu(FormMode.kreiraj);
    }
    public void otvoriPromeniRacunFromu() {
        kreirajRacunController=new KreirajRacunController(new KreirajRacunForma());
        kreirajRacunController.otvoriKreirajRacunFormu(FormMode.promeni);
    }

    public void osveziPrikaziRacunFormu() {
        prikaziRacunController.osveziFormu();
    }

}
