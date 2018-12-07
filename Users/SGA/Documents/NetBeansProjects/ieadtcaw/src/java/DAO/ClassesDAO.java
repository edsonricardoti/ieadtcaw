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
import Modelo.Classes;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import org.hibernate.HibernateException;

/**
 *
 * @author Edson Ricardo
 */
public class ClassesDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Session session;

    public List<Classes> selectAll() {
      
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            List lista = session.createQuery("from Classes").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Classes> buscarPorData(Date data) throws ParseException {
        System.out.println("Data enviada=" + data);
     
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            List classes = session.createQuery("from Classes where classesData=:data")
                    .setDate("data", data)
                    .setMaxResults(100)
                    .list();
            t.commit();
            return classes;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Classes> buscarPorNome(String nome) throws ParseException {

      

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            List classes = session.createQuery("from Classes where classesNome like:nome")
                    .setString("nome", "%" + nome + "%")
                    .setMaxResults(100)
                    .list();
            t.commit();
            return classes;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public Classes buscarPorID(int id) {

       
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Classes cl = (Classes) session.createQuery("from Classes where idclasses=" + id)
                    .uniqueResult();
            t.commit();
            return cl;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public Classes buscarPegaClasse(String classe) {

      
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Classes cl = (Classes) session.createQuery("from Classes where classesNome='" + classe + "'")
                    .uniqueResult();
            t.commit();
            return cl;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }


    public boolean insert(Classes classe) {
      
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.save(classe);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Classes classe) {
      
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.delete(classe);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Classes classe) {
       
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.update(classe);
            t.commit();
            return true;
        } catch (HibernateException e) {
            System.out.println("Erro: " + e);
            if(t != null){ t.rollback();}
            return false;
        } finally {
            session.close();
        }
    }

}
