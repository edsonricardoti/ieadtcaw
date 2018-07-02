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
import Modelo.Escala;
import java.util.Date;
import org.hibernate.HibernateException;

/**
 *
 * @author Edson Ricardo
 */
public class EscalaDAO {

    private Session session;

    public List<Escala> selectAll() {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Escala").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public Escala buscarPorData(Date data) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Escala escala = (Escala) session.createQuery("from Escala where escalaData like '%" + data + "%'")
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

    public Escala buscarPorPeriodo(Date dataini, Date datafim) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Escala escala = (Escala) session.createQuery("from Escala where escalaData >=:dataini and escalaData <=:datafim")
                    .setDate("dataini", dataini)
                    .setDate("datafim", datafim)
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

    public Escala buscarPorID(int id) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Escala escala = (Escala) session.createQuery("from Escala where idescala=:id")
                    .setInteger("id", id)
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

    public List<Escala> buscarPorMesSemanaLista(int mes) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Escala e where e.escalaMes =" + mes + " and year(e.escalaData)=year(Now())").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public boolean insert(Escala escala) {
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

    public boolean delete(Escala escala) {
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

    public boolean update(Escala escala) {
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
