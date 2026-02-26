/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;
import domen.Prodavac;
import forme.GlavnaForma;
/**
 *
 * @author Jovana
 */
public class GlavnaFormaController {
    private final GlavnaForma gf;

    public GlavnaFormaController(GlavnaForma gf) {
        this.gf = gf;
        addActionListeners();
    }

    private void addActionListeners() {
        
    }

    public void otvoriFormu() {
        Prodavac ulogovani = cordinator.Cordinator.getInstance().getUlogovani();
        gf.setVisible(true);
        gf.getLblProdavac().setText(ulogovani.toString());
    }
    
 }
