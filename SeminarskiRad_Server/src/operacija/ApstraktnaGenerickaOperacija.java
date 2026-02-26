/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija;

import repository.Repository;
import repository.db.DbRepository;
import repository.db.impl.DbRepositoryGerenic;

/**
 *
 * @author Jovana
 */
public abstract class ApstraktnaGenerickaOperacija {

    protected final Repository broker;

    public ApstraktnaGenerickaOperacija() {
        this.broker = new DbRepositoryGerenic();
    }

    public final void izvrsi(Object objekat, String kljuc) throws Exception {

        try {
            preduslovi(objekat);
            zapocniTransakciju();
            izvrsiOperaciju(objekat, kljuc);
            potvrdiTransakciju();

        } catch (Exception e) {
            ponistiTransakciju();
            throw e;
        }
    }

    protected abstract void preduslovi(Object objekat) throws Exception;

    private void zapocniTransakciju() throws Exception {
        ((DbRepository) broker).connect();
    }

    protected abstract void izvrsiOperaciju(Object objekat, String kljuc) throws Exception;

    protected void potvrdiTransakciju() throws Exception {
        ((DbRepository) broker).commit();
    }

    private void ponistiTransakciju() throws Exception {
        ((DbRepository) broker).rollback();
    }

    protected void ugasiKonekciju() throws Exception {
        ((DbRepository) broker).disconnect();
    }

}
