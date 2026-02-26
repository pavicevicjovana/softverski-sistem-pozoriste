/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.db.impl;

import java.sql.*;
import domen.ApstraktniDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;

/**
 *
 * @author Jovana
 */
public class DbRepositoryGerenic implements DbRepository<ApstraktniDomenskiObjekat> {

    @Override
    public List<ApstraktniDomenskiObjekat> getAll(ApstraktniDomenskiObjekat param, String uslov) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();

        String upit = "SELECT * FROM " + param.nazivTabele();
        if (uslov != null) {
            System.out.println("uslov nije null " + uslov);
            upit += uslov;
        }
        System.out.println(upit);

        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(upit);
        lista = param.vratiListu(rs);
        st.close();
        return lista;
    }

    @Override
    public void add(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "INSERT INTO " + param.nazivTabele() + param.koloneZaInsert() + " VALUES (" + param.vrednostiZaInsert() + ")";
        System.out.println(upit);
        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();
    }

    @Override
    public void edit(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "UPDATE " + param.nazivTabele() + " SET " + param.vrednostiZaUpdate();
        System.out.println(upit);
        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();

    }

    @Override
    public void delete(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "DELETE FROM " + param.nazivTabele() + " WHERE " + param.vratiPrimarniKljuc();
        System.out.println(upit);
        Statement st = DbConnectionFactory.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();
    }

    @Override
    public List<ApstraktniDomenskiObjekat> getAll() {
        return null;
    }

    @Override
    public int addReturnKey(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "INSERT INTO " + param.nazivTabele() + " " + param.koloneZaInsert() + " VALUES (" + param.vrednostiZaInsert() + ") ";
        PreparedStatement ps = DbConnectionFactory.getInstance().getConnection().prepareStatement(upit, Statement.RETURN_GENERATED_KEYS);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        int generatedId = -1;
        if (rs.next()) {
            generatedId = rs.getInt(1);

        }
        rs.close();
        ps.close();
        return generatedId;
    }

}
