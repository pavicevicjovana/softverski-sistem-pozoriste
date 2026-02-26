/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.Korisnik;
import domen.Mesto;
import domen.TipKorisnika;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jovana
 */
public class ModelTabeleKorisnici extends AbstractTableModel {

    private List<Korisnik> lista= new ArrayList<>();
    String[] kolone = {"id", "ime", "prezime", "email", "tip korisnika", "mesto"};

    public ModelTabeleKorisnici(List<Korisnik> lista) {
        this.lista = lista;
    }
    public ModelTabeleKorisnici(){
        
    }
    @Override
    public int getRowCount() {
        
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
        Korisnik korisnik = lista.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return korisnik.getIdKorisnik();
            case 1:
                return korisnik.getIme();
            case 2:
                return korisnik.getPrezime();

            case 3:
                return korisnik.getEmail();
            case 4:
                return korisnik.getTipKorisnika().toString();
            case 5:
                return korisnik.getMesto().toString();
            default:
                return "n/a";
        }
    }

    public List<Korisnik> getLista() {
        return lista;
    }

    public void pretrazi(String ime, String prezime, Mesto mesto, TipKorisnika tip) {

        List<Korisnik> filterKorisnici = lista.stream()
                .filter(p -> (ime == null || ime.isEmpty() || p.getIme().toLowerCase().contains(ime.toLowerCase())))
                .filter(p -> (prezime == null || prezime.isEmpty() || p.getPrezime().toLowerCase().contains(prezime.toLowerCase())))
                .filter(p -> (mesto == null || p.getMesto().equals(mesto)))
                .filter(p -> (tip == null || p.getTipKorisnika().equals(tip)))
                .collect(Collectors.toList());
        this.lista = filterKorisnici;
        if (filterKorisnici.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sitem ne moze da nadje korisnika po zadatim kriterijumima", "Greska", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "Sitem je nasao korisnike po zadatim kriterijumima", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        fireTableDataChanged();
    }

}
