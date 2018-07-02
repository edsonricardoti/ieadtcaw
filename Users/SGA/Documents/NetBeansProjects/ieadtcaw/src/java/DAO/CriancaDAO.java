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
import Modelo.Criancas;
import java.text.ParseException;
import java.util.Date;
import org.hibernate.HibernateException;

/**
 *
 * @author Edson Ricardo
 */
public class CriancaDAO {

    private Session session;

    public List<Criancas> selectAll() {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Criancas").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Criancas> buscarPorData(Date data) throws ParseException {
        System.out.println("Data enviada=" + data);
        try {
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List criancas = session.createQuery("from Criancas where criancasData=:data")
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

    public List<Criancas> buscarPorDtiniDtfim(Date dtini, Date dtfim) throws ParseException {

        try {
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List criancas = session.createQuery("from Criancas where criancasData>=:dtini and criancasData<=:dtfim")
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

    public List<Criancas> buscarPorNome(String nome) throws ParseException {

        try {

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List criancas = session.createQuery("from Criancas where criancasNome like:nome")
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

    public Criancas buscarPorID(int id) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Criancas criancas = (Criancas) session.createQuery("from Criancas where idcriancas=" + id)
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

    public boolean insert(Criancas crianca) {
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

    public boolean delete(Criancas crianca) {
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

    public boolean update(Criancas crianca) {
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
