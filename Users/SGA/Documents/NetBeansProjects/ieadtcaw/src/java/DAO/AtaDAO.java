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
import Modelo.Ata;
import java.text.ParseException;
import java.util.Date;
import org.hibernate.HibernateException;

/**
 *
 * @author Edson Ricardo
 */
public class AtaDAO {

    private Session session;

    public List<Ata> selectAll() {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Ata").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Ata> buscarPorData(Date data) throws ParseException {
        System.out.println("Data enviada=" + data);
        try {
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List atas = session.createQuery("from Ata where ataData=:data")
                    .setDate("data", data)
                    .setMaxResults(100)
                    .list();
            t.commit();
            return atas;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            //   session.close();
        }
    }

    public Ata buscarPorID(int id) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Ata ata = (Ata) session.createQuery("from Ata where idata=" + id + "")
                    .uniqueResult();
            t.commit();
            return ata;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public boolean insert(Ata ata) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.save(ata);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Ata ata) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.delete(ata);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Ata ata) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.update(ata);
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
