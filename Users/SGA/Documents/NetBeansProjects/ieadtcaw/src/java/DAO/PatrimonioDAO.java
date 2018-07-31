/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.Patrimonio;
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
public class PatrimonioDAO implements Serializable {

    private static final long serialVersionUID = 1L;


        private Session session;

    public List<Patrimonio> selectAll() {
            try {
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                List lista = session.createQuery("from Patrimonio").list();
                t.commit();
                return lista;

            } catch (HibernateException e) {
                session.getTransaction().rollback();
                return null;
            } finally {
                session.close();
            }
    }

    public List<Patrimonio> geraInventario(int ano) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Patrimonio where Year(dataaquisicao)=" + ano).list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public Patrimonio buscarPorId(Integer id) {
            try {
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                Patrimonio patrimonio = (Patrimonio) session.createQuery("from Patrimonio where id=:id")
                        .setInteger("id", id)
                        .uniqueResult();
                t.commit();
                return patrimonio;

            } catch (HibernateException e) {
                session.getTransaction().rollback();
                return null;
            } finally {
                session.close();
            }
    }
        
        
    public List<Patrimonio> buscarPorNomeLista(String descricao) {

            try {
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                List lista = session.createQuery("from Patrimonio where descricao like '%" + descricao + "%'").list();
                t.commit();
                return lista;

            } catch (HibernateException e) {
                session.getTransaction().rollback();
                return null;
            } finally {
                session.close();
            }
        }
        
    public boolean insert(Patrimonio patrimonio) {
            
            try {
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                session.save(patrimonio);
                t.commit();
                return true;
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                return false;
            } finally {
            session.close();
            }
        }

    public boolean delete(Patrimonio patrimonio) {
            try {
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                session.delete(patrimonio);
                t.commit();
                return true;
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                return false;
            } finally {
                session.close();
            }
        }

    public boolean update(Patrimonio patrimonio) {
            try {
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                session.update(patrimonio);
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
