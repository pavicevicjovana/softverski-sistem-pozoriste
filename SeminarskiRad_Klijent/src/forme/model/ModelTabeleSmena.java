/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.Smena;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jovana
 */
public class ModelTabeleSmena extends AbstractTableModel {

    private List<Smena> lista;
    private String[] kolone = {"ID", "Naziv smene", "Satnica", "Vreme pocetka", "Vreme zavrsetka", "Broj sati"};

    public ModelTabeleSmena(List<Smena> lista) {
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        Smena smena = lista.get(rowIndex);

        switch (columnIndex) {
            case 0:

                return smena.getIdSmena();
            case 1:
                return smena.getNazivSmena();
            case 2:
                return smena.getSatnica();
            case 3:
                return smena.getVremePocetka().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            case 4:
                return smena.getVremeZavrsetka().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            case 5:
                return smena.getBrojSati();
            default:
                return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<Smena> getLista() {
        return lista;
    }

    public void pretrazi(String naziv, double satnica, LocalTime vremePocetka, LocalTime vremeZavrsetka) {
        List<Smena> filterSmene = lista.stream()
                .filter(s -> naziv == null || naziv.isEmpty()
                || s.getNazivSmena().toLowerCase().contains(naziv.toLowerCase()))
                .filter(s -> satnica <=0 || s.getSatnica() == satnica)
                .filter(s -> vremePocetka == null || s.getVremePocetka().equals(vremePocetka))
                .filter(s -> vremeZavrsetka == null || s.getVremeZavrsetka().equals(vremeZavrsetka))
                .collect(Collectors.toList());

        this.lista = filterSmene;

        if (filterSmene.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sistem ne može da nađe smene po zadatim kriterijumima","Greška", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null,"Sistem je našao smene po zadatim kriterijumima", "Uspeh", JOptionPane.INFORMATION_MESSAGE);

        fireTableDataChanged();
    }
}
