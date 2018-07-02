/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.Relatorios;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import Modelo.Contasapagar;
import Modelo.Membros;
import static Util.HibernateUtil.getSessionFactory;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;


/**
 *
 * @author Edson Ricardo
 */
public class ContasapagarDAO {

    private Session session;

    public List<Relatorios> gasolinaXmanutencao(Date dataini, Date datafim) {

        try {

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Query financeiro = session.createQuery("select month(c.datalancamento) as mes,sum(case when c.descricao = 'gasolina' then c.valordespesa end) as gasolina,\n"
                    + "sum(case when c.descricao <> 'gasolina' then c.valordespesa end) as manutencao\n"
                    + " from Contasapagar as c where c.datalancamento >=:dataini and c.datalancamento <=:datafim and c.tipo = 'veiculo' group by month(c.datalancamento))")
                    .setDate("dataini", dataini)
                    .setDate("datafim", datafim)
                    .setMaxResults(10000)
                    .setResultTransformer(Transformers.aliasToBean(Relatorios.class));
            if (!t.wasCommitted()) {
                t.commit();
            }

            List<Relatorios> resultado = financeiro.list();

            return resultado;

        } catch (HibernateException e) {
            System.out.println("Erro encontrado:" + e);
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Contasapagar> selectAll() {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Contasapagar where tipo='nada'").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Contasapagar> buscarUmaLista(String descricao, String tipo, Date dataini, Date datafim) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Contasapagar where descricao like '%" + descricao + "%' and tipo='" + tipo + "' and datalancamento >=:dataini and datalancamento <=:datafim ")
                    .setDate("dataini", dataini)
                    .setDate("datafim", datafim)
                    .list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Contasapagar> buscarDespesas(String tipo) {
        Calendar cal = GregorianCalendar.getInstance();
        Date hoje;
        hoje = cal.getTime();
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Contasapagar where tipo=:tipo and datalancamento=:hoje")
                    .setString("tipo", tipo)
                    .setDate("hoje", hoje)
                    .list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public Contasapagar buscarPorID(int id) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Contasapagar cl = (Contasapagar) session.createQuery("from Contasapagar where idcontasapagar=" + id)
                    .uniqueResult();
            t.commit();
            return cl;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public boolean insert(Contasapagar contasapagar) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.save(contasapagar);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean insertDizimo(Relatorios dizimo) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.save(dizimo);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Contasapagar contasapagar) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.delete(contasapagar);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Contasapagar contasapagar) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.update(contasapagar);
            t.commit();
            return true;
        } catch (HibernateException e) {
            System.out.println("Erro: " + e);
            System.out.println("Erro encontrado :" + e);
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

}
