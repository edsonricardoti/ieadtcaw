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
import Modelo.Caixa;
import Modelo.LivroCaixa;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Edson Ricardo
 */
public class CaixaDAO {

    private Session session;

    public List<Caixa> selectAll() {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Caixa").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Caixa> buscarPorData(Date data) throws ParseException {
        System.out.println("Data enviada=" + data);
        try {
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List caixas = session.createQuery("from Caixa where dtlancamento=:data")
                    .setDate("data", data)
                    .setMaxResults(100)
                    .list();
            t.commit();
            return caixas;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<LivroCaixa> buscarPorPeriodo(Date dataini, Date datafim) throws ParseException {

        try {
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Query caixas = session.createQuery("select descricao as descricao,sum(valordareceita) as receitas,sum(valordadespesa) as despesas\n"
                    + " FROM Caixa where  dtlancamento >=:dataini and dtlancamento <=:datafim\n"
                    + " group by descricao order by valordadespesa")
                    .setDate("dataini", dataini)
                    .setDate("datafim", datafim)
                    .setMaxResults(10000)
                    .setResultTransformer(Transformers.aliasToBean(LivroCaixa.class));
            if (!t.wasCommitted()) {
                t.commit();
            }
            List<LivroCaixa> resultado = caixas.list();

            return resultado;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Caixa> buscarPorDescricao(String nome) throws ParseException {

        try {

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List caixas = session.createQuery("from Caixa where descricao like:nome")
                    .setString("nome", "%" + nome + "%")
                    .setMaxResults(100)
                    .list();
            t.commit();
            return caixas;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public Caixa buscarPorID(int id) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Caixa ata = (Caixa) session.createQuery("from Caixa where idcaixa=" + id)
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

    public boolean NaoExisteNoCaixa(Date data) {
        String minhadata;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        minhadata = sdf.format(data);

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List<Caixa> ata = session.createQuery("from Caixa where dtlancamento='" + minhadata + "'").list();
            t.commit();
            if (ata.isEmpty()) {
                return true;
            } else {
                return false;
            }

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public void insert(Caixa caixa) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.save(caixa);
            t.commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public boolean delete(Caixa caixa) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.delete(caixa);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Caixa caixa) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.update(caixa);
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
