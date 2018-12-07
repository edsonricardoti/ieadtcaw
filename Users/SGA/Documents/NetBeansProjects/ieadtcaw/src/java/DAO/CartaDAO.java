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
import Modelo.Carta;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import org.hibernate.HibernateException;

/**
 *
 * @author Edson Ricardo
 */
public class CartaDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Session session;

    public List<Carta> selectAll() {
   
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            List lista = session.createQuery("from Carta").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Carta> buscarPorData(Date data) throws ParseException {
        System.out.println("Data enviada=" + data);
     
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            List cartas = session.createQuery("from Carta where cartaData=:data")
                    .setDate("data", data)
                    .setMaxResults(100)
                    .list();
            t.commit();
            return cartas;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Carta> buscarPorNome(String nome) throws ParseException {

     

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            List cartas = session.createQuery("from Carta where cartaNome like:nome")
                    .setString("nome", "%" + nome + "%")
                    .setMaxResults(100)
                    .list();
            t.commit();
            return cartas;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public Carta buscarPorID(int id) {

    
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Carta ata = (Carta) session.createQuery("from Carta where idcarta=" + id)
                    .uniqueResult();
            t.commit();
            return ata;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public boolean insert(Carta carta) {
     
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.save(carta);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Carta carta) {
     
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.delete(carta);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Carta carta) {
     
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.update(carta);
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
