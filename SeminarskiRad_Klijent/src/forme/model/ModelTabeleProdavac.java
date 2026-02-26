/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.Prodavac;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jovana
 */
public class ModelTabeleProdavac extends AbstractTableModel {
     private List<Prodavac> lista;
    String[] kolone = {"ID", "Ime", "Prezime", "Kontakt telefon", "Email", "Plata"};

    public ModelTabeleProdavac(List<Prodavac> lista) {
        this.lista = lista;
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
        Prodavac prodavac = lista.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return prodavac.getIdProdavac();
            case 1:
                return prodavac.getIme();
            case 2:
                return prodavac.getPrezime();

            case 3:
                return prodavac.getKontaktTelefon();
            case 4:
                return prodavac.getEmail();
            case 5:
                return prodavac.getPlata();
            default:
                return "n/a";
        }
    }

    public List<Prodavac> getLista() {
        return lista;
    }

    public void pretrazi(String ime, String prezime, String kontakt, String email) {

        List<Prodavac> filterProdavci = lista.stream()
                .filter(p -> (ime == null || ime.isEmpty() || p.getIme().toLowerCase().contains(ime.toLowerCase())))
                .filter(p -> (prezime == null || prezime.isEmpty() || p.getPrezime().toLowerCase().contains(prezime.toLowerCase())))
                .filter(p -> (kontakt == null || kontakt.isEmpty() || p.getKontaktTelefon().toLowerCase().contains(kontakt.toLowerCase())))
                .filter(p -> (email == null || email.isEmpty() || p.getEmail().toLowerCase().contains(email.toLowerCase())))
                .collect(Collectors.toList());
        this.lista = filterProdavci;
        if (filterProdavci.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sitem ne moze da nadje prodavca po zadatim kriterijumima", "Greska", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "Sitem je nasao prodavca po zadatim kriterijumima", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        fireTableDataChanged();
    }
}
