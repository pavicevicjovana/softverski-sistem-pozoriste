/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.kartapredstave;

import domen.KartaPredstave;
import java.time.LocalDate;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author Jovana
 */
public class PromeniKartuSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof KartaPredstave)) {
            throw new Exception("Sistem ne moze da izmeni kartu!");
        }
        KartaPredstave k = (KartaPredstave) objekat;

        if (k.getNazivPredstave() == null || k.getNazivPredstave().isEmpty()) {
            throw new Exception("Polje Naziv predstave je obavezno!");
        }
        if (k.getZanr()==null || k.getZanr().isEmpty() ) {
            throw new Exception("Polje Zanr je obavezno!");
        }
        if (k.getReditelj()==null || k.getReditelj().isEmpty()) {
            throw new Exception("Polje Reditelj je obavezno!");
        }
       
        if (k.getCena() <= 0) {
            throw new Exception("Cena mora biti veca od 0!");
        }
        if(k.getDatumOdrzavanja()==null){
            throw new Exception("Polje datum predstave je obavezno!");
        }
        if (k.getDatumOdrzavanja().isBefore(LocalDate.now())) {
            throw new Exception("Datum predstave nije validan!");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        broker.edit((KartaPredstave)objekat);
    }
    
}
