/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.Igrejas;
import static Util.HibernateUtil.getSessionFactory;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author SGA
 */
public class IgrejasDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Session session;

    public List<Igrejas> selectAll() {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Igrejas").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public Igrejas buscarPorId(Integer id) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            Igrejas igreja = (Igrejas) session.createQuery("from Igrejas where idIgreja=:id")
                    .setInteger("id", id)
                    .uniqueResult();
            t.commit();
            return igreja;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Igrejas> buscarPorNomeLista(String nome) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Igrejas where nome like '%" + nome + "%'").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public boolean insert(Igrejas igreja) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(igreja);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Igrejas igreja) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            session.delete(igreja);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Igrejas igreja) {
        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            session.update(igreja);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

}
