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
import Modelo.Veiculos;
import java.io.Serializable;
import org.hibernate.HibernateException;

/**
 *
 * @author Edson Ricardo
 */
public class VeiculoDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Session session;

    public List<Veiculos> selectAll() {
        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Veiculos").list();
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

    public Veiculos buscarPorPlaca(String placa) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            Veiculos veiculo = (Veiculos) session.createQuery("from Veiculos where veiculosPlaca like '%" + placa + "%'")
                    .uniqueResult();
            t.commit();
            return veiculo;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Veiculos> buscarPorPlacaLista(String placa) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Veiculos where veiculosPlaca like '%" + placa + "%'").list();
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

    public boolean insert(Veiculos veiculo) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(veiculo);
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

    public boolean delete(Veiculos veiculo) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            session.delete(veiculo);
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

    public boolean update(Veiculos veiculo) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            session.update(veiculo);
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
