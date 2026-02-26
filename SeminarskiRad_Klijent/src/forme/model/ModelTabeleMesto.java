/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import domen.Mesto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jovana
 */
public class ModelTabeleMesto extends AbstractTableModel {

    private List<Mesto> lista;
    private String[] kolone = {"ID", "Naziv"};

    public ModelTabeleMesto(List<Mesto> lista) {
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
        Mesto mesto = lista.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                
                return mesto.getIdMesto();
            case 1:
                return mesto.getNaziv();
            default:
                return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<Mesto> getLista() {
        return lista;
    }
    
    
    
    

}
