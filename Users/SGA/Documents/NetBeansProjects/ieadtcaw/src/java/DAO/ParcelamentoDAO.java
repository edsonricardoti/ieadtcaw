/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static Util.HibernateUtil.getSessionFactory;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import Modelo.Parcelamentos;
import java.text.ParseException;
import java.util.Date;
import org.hibernate.HibernateException;

/**
 *
 * @author Edson Ricardo
 */
public class ParcelamentoDAO {

    private Session session;

    public List<Parcelamentos> selectAll(int idmembro, int idperiodico) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Parcelamentos where idmembro=" + idmembro + " and idperiodico=" + idperiodico).list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Parcelamentos> buscarPorData(Date data) throws ParseException {
        System.out.println("Data enviada=" + data);
        try {
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List parcelamentos = session.createQuery("from Parcelamentos where datapagamento=:data")
                    .setDate("data", data)
                    .setMaxResults(100)
                    .list();
            t.commit();
            return parcelamentos;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }


    public List<Parcelamentos> buscarPorDtiniDtfim(Date dtini, Date dtfim) throws ParseException {

        try {
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List parcelamentos = session.createQuery("from Parcelamentos where datapagamento>=:dtini and datapagamento<=:dtfim")
                    .setDate("dtini", dtini)
                    .setDate("dtfim", dtfim)
                    .setMaxResults(100)
                    .list();
            t.commit();
            return parcelamentos;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public Parcelamentos buscarPorID(int id) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Parcelamentos parcelamentos = (Parcelamentos) session.createQuery("from Parcelamentos where idperiodico=" + id)
                    .uniqueResult();
            t.commit();
            return parcelamentos;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public Parcelamentos buscarPorParcela(int idmembro, int idperiodico, int parcela) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Parcelamentos parcelamentos = (Parcelamentos) session.createQuery("from Parcelamentos where idperiodico=:idperiodico and idmembro=:idmembro and numparcela=:parcela")
                    .setInteger("idmembro", idmembro)
                    .setInteger("idperiodico", idperiodico)
                    .setInteger("parcela", parcela)
                    .uniqueResult();
            t.commit();
            return parcelamentos;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public boolean insert(Parcelamentos parcelamento) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.save(parcelamento);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Parcelamentos parcelamento) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.delete(parcelamento);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Parcelamentos parcelamento) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.update(parcelamento);
            t.commit();
            return true;
        } catch (HibernateException e) {
            System.out.println("Erro: " + e);
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

}
