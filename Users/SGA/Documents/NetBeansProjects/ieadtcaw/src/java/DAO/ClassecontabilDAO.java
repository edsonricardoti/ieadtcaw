/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.Classecontabil;
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
public class ClassecontabilDAO implements Serializable {

    private static final long serialVersionUID = 1L;


        private Session session;

    public List<Classecontabil> selectAll() {
           
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                try{
                List lista = session.createQuery("from Classecontabil").list();
                t.commit();
                return lista;

            } catch (HibernateException e) {
                if(t != null){ t.rollback();}
                return null;
            } finally {
                session.close();
            }
        }


    public Classecontabil buscarPorId(Integer id) {
         
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                try{
                Classecontabil classecontabil = (Classecontabil) session.createQuery("from Classecontabil where id=:id")
                        .setInteger("id", id)
                        .uniqueResult();
                t.commit();
                return classecontabil;

            } catch (HibernateException e) {
                if(t != null){ t.rollback();}
                return null;
            } finally {
                session.close();
            }
    }
        
        
        
    public boolean insert(Classecontabil classecontabil) {
            
        
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                try{
                session.save(classecontabil);
                t.commit();
                return true;
            } catch (HibernateException e) {
                if(t != null){ t.rollback();}
                return false;
            } finally {
            session.close();
            }
        }

    public boolean delete(Classecontabil classecontabil) {
          
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                try{
                session.delete(classecontabil);
                t.commit();
                return true;
            } catch (HibernateException e) {
                if(t != null){ t.rollback();}
                return false;
            } finally {
                session.close();
            }
        }

    public boolean update(Classecontabil classecontabil) {
          
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                try{
                session.update(classecontabil);
                t.commit();
                return true;
            } catch (HibernateException e) {
                if(t != null){ t.rollback();}
                return false;
            } finally {
                session.close();
            }
        }

        
 
}
