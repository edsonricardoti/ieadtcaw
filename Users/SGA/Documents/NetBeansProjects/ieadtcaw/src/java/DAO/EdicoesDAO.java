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
import Modelo.Edicoes;
import java.text.ParseException;
import java.util.Date;
import org.hibernate.HibernateException;

/**
 *
 * @author Edson Ricardo
 */
public class EdicoesDAO {

    private Session session;

    public List<Edicoes> selectAll() {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Edicoes").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }


    public Edicoes buscarPorID(int id) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Edicoes criancas = (Edicoes) session.createQuery("from Edicoes where idedicoes=" + id)
                    .uniqueResult();
            t.commit();
            return criancas;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public Edicoes buscarIdPerioEmembro(int idperiodico, int idmembro) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Edicoes criancas = (Edicoes) session.createQuery("from Edicoes where idperiodico=" + idperiodico + " and idmembro=" + idmembro)
                    .uniqueResult();
            t.commit();
            return criancas;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public boolean insert(Edicoes crianca) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.save(crianca);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Edicoes crianca) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.delete(crianca);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Edicoes crianca) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.update(crianca);
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
