/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

/**
 *
 * @author Jovana
 */
public abstract class ApstraktniDomenskiObjekat implements Serializable {
    public abstract String nazivTabele();
    public abstract String alijas();
    public abstract String join();
    public abstract List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception;
    public abstract String koloneZaInsert();
    public abstract String vrednostiZaInsert();
    public abstract String vrednostiZaUpdate();
    public abstract String vratiPrimarniKljuc();
    public abstract String uslovZaSelect();
    public abstract ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception;
}
