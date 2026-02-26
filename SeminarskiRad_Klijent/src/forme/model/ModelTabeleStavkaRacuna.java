/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.StavkaRacuna;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jovana
 */
public class ModelTabeleStavkaRacuna extends AbstractTableModel {

    String[] kolone = {"rb", "Karta Predstave", "Broj Karata", "Cena Karte", "Iznos"};
    private List<StavkaRacuna> lista = new ArrayList<>();

    public ModelTabeleStavkaRacuna() {
    }

    public ModelTabeleStavkaRacuna(List<StavkaRacuna> lista) {
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
        StavkaRacuna stavka = lista.get(rowIndex);

        switch (columnIndex) {
            case 0:

                return rowIndex + 1;
            case 1:
                return stavka.getKartaPredstave().getNazivPredstave();
            case 2:
                return stavka.getBrojKarata();
            case 3:
                return stavka.getCenaKarte();
            case 4:
                return stavka.getIznos();
            default:
                return "na";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<StavkaRacuna> getLista() {
        return lista;
    }

    public void dodajStavku(StavkaRacuna stavka) {
        int trenutniRB = lista.stream()
                .mapToInt(StavkaRacuna::getRb)
                .max()
                .orElse(0) + 1;

        stavka.setRb(trenutniRB);
        lista.add(stavka);
        fireTableDataChanged();
    }

    public void obrisiStavku(StavkaRacuna stavka) {
        lista.remove(stavka);

        fireTableDataChanged();
    }

}
