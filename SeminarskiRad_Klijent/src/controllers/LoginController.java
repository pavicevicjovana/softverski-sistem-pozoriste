/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import domen.Prodavac;
import forme.LoginForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;

/**
 *
 * @author Jovana
 */
public class LoginController {
    
    private final LoginForma lf;

    public LoginController(LoginForma lf) {
        this.lf = lf;
        addActionListeners();
    }

    private void addActionListeners() {
       lf.loginAddActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               login(e);
           }

           private void login(ActionEvent e) {
               String email = lf.getTxtEmail().getText().trim();
               String lozinka=String.valueOf(lf.getTxtPassword().getPassword());
               
               Komunikacija.getInstance().konekcija();
               Prodavac prodavac = Komunikacija.getInstance().login(email,lozinka);
               
               if(prodavac==null){
                   JOptionPane.showMessageDialog(lf, "Korisnicko ime i sifra nisu ispravni.", "Greska", JOptionPane.ERROR_MESSAGE);
                   JOptionPane.showMessageDialog(lf, "Ne moze da se otvori glavna forma i meni.", "Greska", JOptionPane.ERROR_MESSAGE);
                   
               }else{
                   cordinator.Cordinator.getInstance().setUlogovani(prodavac);
                   JOptionPane.showMessageDialog(lf, "Korisnicko ime i sifra su ispravni.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                   cordinator.Cordinator.getInstance().otvoriGlavnuFormu();
                   lf.dispose();
               }
           }
       });
    }

    public void otvoriFormu() {
        lf.setVisible(true);
    }
    
    
}
