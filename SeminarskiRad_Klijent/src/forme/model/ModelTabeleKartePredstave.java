/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.KartaPredstave;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jovana
 */
public class ModelTabeleKartePredstave extends AbstractTableModel {
    private List<KartaPredstave> lista;
    private String[] kolone={"ID","Naziv Predstave","Zanr","Reditelj","Datum Odrzavanja","Cena"};
    public ModelTabeleKartePredstave(List<KartaPredstave> lista) {
        this.lista = lista;
    }

    public List<KartaPredstave> getLista() {
        return lista;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
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
        KartaPredstave karta = lista.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                
                return karta.getIdKarta();
            case 1:
                return karta.getNazivPredstave();
            case 2:
                return karta.getZanr();
            case 3:
                return karta.getReditelj();
            case 4:
                return karta.getDatumOdrzavanja().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
            case 5:
                return karta.getCena();
            default:
                return "n/a";
        }
    }

    public void pretrazi(String nazivPredstave, String zanr, String reditelj, LocalDate datumOdrzavanja, double cenaOd) {
        List<KartaPredstave> filterKarte = lista.stream()
                .filter(k -> (nazivPredstave== null || nazivPredstave.isEmpty() || k.getNazivPredstave().toLowerCase().contains(nazivPredstave.toLowerCase())))
                .filter(k -> (zanr == null || zanr.isEmpty() || k.getZanr().toLowerCase().contains(zanr.toLowerCase())))
                .filter(k -> (reditelj == null || reditelj.isEmpty() || k.getReditelj().toLowerCase().contains(reditelj.toLowerCase())))
                .filter(k -> (datumOdrzavanja== null || k.getDatumOdrzavanja().equals(datumOdrzavanja)))
                .filter(k -> (k.getCena() >= cenaOd ))
                .collect(Collectors.toList());
        
        this.lista=filterKarte;
        if (filterKarte.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sitem ne moze da nadje kartu po zadatim kriterijumima", "Greska", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "Sitem je nasao karte po zadatim kriterijumima", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
        fireTableDataChanged();
    }
    
}
