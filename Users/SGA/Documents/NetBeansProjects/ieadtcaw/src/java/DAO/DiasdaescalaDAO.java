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
import Modelo.Diasdaescala;
import java.io.Serializable;
import java.util.Date;
import org.hibernate.HibernateException;

/**
 *
 * @author Edson Ricardo
 */
public class DiasdaescalaDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Session session;

    public List<Diasdaescala> selectAll(int id) {
        System.out.println("ID passado p/ deletar = " + id);
        try {

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Diasdaescala where idescalapai =" + id + "").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public Diasdaescala buscarPorData(Date data) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Diasdaescala escala = (Diasdaescala) session.createQuery("from Diasdaescala where data=" + data + "'")
                    .uniqueResult();
            t.commit();
            return escala;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public Diasdaescala buscarPorID(int id) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Diasdaescala escala = (Diasdaescala) session.createQuery("from Diasdaescala where id=" + id)
                    .uniqueResult();
            t.commit();
            return escala;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Diasdaescala> buscarPorPeriodo(Date dataini, Date datafim) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List escala = session.createQuery("from Diasdaescala where data >=:dataini and data <=:datafim order by data,hora")
                    .setDate("dataini", dataini)
                    .setDate("datafim", datafim)
                    .list();
            t.commit();
            return escala;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Diasdaescala> buscarPorMesSemanaLista(int mes) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Diasdaescala where month(data) =" + mes + " and year(data)=year(Now())").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Diasdaescala> buscarPorIdEscala(int id) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Diasdaescala where idescalapai=" + id).list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public boolean insert(Diasdaescala escala) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.save(escala);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Diasdaescala escala) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.delete(escala);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Diasdaescala escala) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.update(escala);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

}
