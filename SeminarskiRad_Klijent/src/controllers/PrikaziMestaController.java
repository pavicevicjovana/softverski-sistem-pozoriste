/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import domen.Mesto;
import forme.PrikaziMestaForma;
import forme.model.ModelTabeleMesto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jovana
 */
public class PrikaziMestaController {
    private final PrikaziMestaForma pmf;

    public PrikaziMestaController(PrikaziMestaForma pmf) {
        this.pmf = pmf;
        addActionListeners();
        
    }

    private void addActionListeners() {
        pmf.addBtnObrisiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pmf.getTblMesta().getSelectedRow();
                if(red==-1){
                    JOptionPane.showMessageDialog(pmf, "Sistem ne moze da obrise mesto", "Greska", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                ModelTabeleMesto mtm = (ModelTabeleMesto) pmf.getTblMesta().getModel();
                Mesto mesto = mtm.getLista().get(red);
                
                try {
                    komunikacija.Komunikacija.getInstance().obrisiMesto(mesto);
                    JOptionPane.showMessageDialog(pmf, "Sistem je obrisao mesto!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    osveziFormu();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pmf, "Sistem ne moze da obrise mesto!\n"+ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });
    }
    
    
    public void otvoriFormu(){
        pripremiFormu();
        pmf.setVisible(true);
    }

    private void pripremiFormu() {
        List<Mesto> mesta = komunikacija.Komunikacija.getInstance().ucitajMesta();
        ModelTabeleMesto mtm = new ModelTabeleMesto(mesta);
        pmf.getTblMesta().setModel(mtm);
    }
    public void osveziFormu(){
        pripremiFormu();
    }
}
