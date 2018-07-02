/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.Igrejas;
import static Util.HibernateUtil.getSessionFactory;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author SGA
 */
public class IgrejasDAO {

   

        private Session session;

    public List<Igrejas> selectAll() {
            try {
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                List lista = session.createQuery("from Igrejas").list();
                t.commit();
                return lista;

            } catch (HibernateException e) {
                session.getTransaction().rollback();
                return null;
            } finally {
                session.close();
            }
        }


    public Igrejas buscarPorId(Integer id) {
            try {
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                Igrejas igreja = (Igrejas) session.createQuery("from Igrejas where idIgreja=:id")
                        .setInteger("id", id)
                        .uniqueResult();
                t.commit();
                return igreja;

            } catch (HibernateException e) {
                session.getTransaction().rollback();
                return null;
            } finally {
                session.close();
            }
    }
        
        
    public List<Igrejas> buscarPorNomeLista(String nome) {

            try {
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                List lista = session.createQuery("from Igrejas where nome like '%" + nome + "%'").list();
                t.commit();
                return lista;

            } catch (HibernateException e) {
                session.getTransaction().rollback();
                return null;
            } finally {
                session.close();
            }
        }
        
    public boolean insert(Igrejas igreja) {
            
            try {
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                session.save(igreja);
                t.commit();
                return true;
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                return false;
            } finally {
            session.close();
            }
        }

    public boolean delete(Igrejas igreja) {
            try {
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                session.delete(igreja);
                t.commit();
                return true;
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                return false;
            } finally {
                session.close();
            }
        }

    public boolean update(Igrejas igreja) {
            try {
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                session.update(igreja);
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
