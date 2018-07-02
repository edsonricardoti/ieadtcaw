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
import Modelo.Periodico;
import java.text.ParseException;
import java.util.Date;
import org.hibernate.HibernateException;

/**
 *
 * @author Edson Ricardo
 */
public class PeriodicoDAO {

    private Session session;

    public List<Periodico> selectAll() {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Periodico").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Periodico> buscarPorData(Date data) throws ParseException {
        System.out.println("Data enviada=" + data);
        try {
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List criancas = session.createQuery("from Periodico where dataassinatura=:data")
                    .setDate("data", data)
                    .setMaxResults(100)
                    .list();
            t.commit();
            return criancas;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Periodico> buscarPorDtiniDtfim(Date dtini, Date dtfim) throws ParseException {

        try {
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List criancas = session.createQuery("from Periodico where dataassinatura>=:dtini and dataassinatura<=:dtfim")
                    .setDate("dtini", dtini)
                    .setDate("dtfim", dtfim)
                    .setMaxResults(100)
                    .list();
            t.commit();
            return criancas;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Periodico> buscarPorNome(String nome) throws ParseException {

        try {

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List criancas = session.createQuery("from Periodico where tiyulo like:nome")
                    .setString("nome", "%" + nome + "%")
                    .setMaxResults(100)
                    .list();
            t.commit();
            return criancas;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public Periodico buscarPorID(int id) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Periodico criancas = (Periodico) session.createQuery("from Periodico where idassinatura=" + id)
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

    public boolean insert(Periodico crianca) {
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

    public boolean delete(Periodico crianca) {
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

    public boolean update(Periodico crianca) {
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
