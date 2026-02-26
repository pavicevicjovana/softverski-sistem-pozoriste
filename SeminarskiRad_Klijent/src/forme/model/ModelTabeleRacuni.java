/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.KartaPredstave;
import domen.Racun;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jovana
 */
public class ModelTabeleRacuni extends AbstractTableModel {

    private List<Racun> lista=new ArrayList<>();
    String[] kolone = {"idRacun", "datum transakcije", "ukupan iznos", "prodavac", "korisnik"};

    public ModelTabeleRacuni(List<Racun> lista) {
        this.lista = lista;
    }
    public ModelTabeleRacuni() {
        
    }

    @Override
    public int getRowCount() {
        if(lista==null)
            return 0;
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Racun r = lista.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return r.getIdRacun();
            case 1:
                return r.getDatumTransakcije().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
            case 2:
                return r.getUkupanIznos();

            case 3:
                return r.getProdavac().toString();
            case 4:
                return r.getKorisnik().toString();

            default:
                return "n/a";
        }
    }

    public List<Racun> getLista() {
        return lista;
    }

    public void pretrazi(String korisnik, String prodavac, LocalDate datum, String karta) {

        String qKarta = (karta == null) ? "" : karta.trim().toLowerCase();

        List<Racun> filteredRacuni = lista.stream()
                .filter(r -> korisnik == null || korisnik.isEmpty()
                || (r.getKorisnik() != null
                && ((r.getKorisnik().getIme() + " " + r.getKorisnik().getPrezime())
                        .toLowerCase().contains(korisnik.toLowerCase()))))
                .filter(r -> prodavac == null || prodavac.isEmpty()
                || (r.getProdavac() != null
                && r.getProdavac().getIme().toLowerCase().contains(prodavac.toLowerCase())))
                .filter(r -> datum == null
                || (r.getDatumTransakcije() != null && r.getDatumTransakcije().equals(datum)))
                .filter(r -> qKarta.isEmpty() || (r.getStavke() != null && !r.getStavke().isEmpty()
                && r.getStavke().stream().anyMatch(s
                        -> s != null
                && s.getKartaPredstave() != null
                && s.getKartaPredstave().getNazivPredstave() != null
                && s.getKartaPredstave().getNazivPredstave().trim().toLowerCase().contains(qKarta)
                )))
                .collect(Collectors.toList());

        this.lista = filteredRacuni;
        if (filteredRacuni.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sitem ne moze da nadje racune po zadatim kriterijumima", "Greska", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "Sitem je nasao racune po zadatim kriterijumima", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        fireTableDataChanged();
    }
}
