/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.Assinantes;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import Modelo.Assinatura;
import Modelo.Membros;
import Modelo.Missgeral;
import static Util.HibernateUtil.getSessionFactory;
import java.text.ParseException;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Edson Ricardo
 */
public class AssinaturaDAO {

    private Session session;

    public List<Assinatura> selectAll() {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Assinatura").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Assinatura> buscarPorData(Date data) throws ParseException {
        System.out.println("Data enviada=" + data);
        try {
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List criancas = session.createQuery("from Assinatura where dataassinatura=:data")
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

    public List<Assinatura> buscarPorDtiniDtfim(Date dtini, Date dtfim) throws ParseException {

        try {
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List criancas = session.createQuery("from Assinatura where dataassinatura>=:dtini and dataassinatura<=:dtfim")
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

    public List<Membros> buscarPorNome(String nome) throws ParseException {

        try {

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List criancas = session.createQuery("from Membros where membrosNome like:nome")
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

    public List<Assinatura> buscarPorAssinaMembro(int idmembro) throws ParseException {

        try {

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List criancas = session.createQuery("from Assinatura where idmembro=:idmembro")
                    .setInteger("idmembro", idmembro)
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

    public List<Assinantes> buscaAssinantes(String nome) throws ParseException {

        try {

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Query financeiro = session.createQuery("select m.membrosNome as membrosNome,m.idmembros as idmembros,a.idassinatura as idassinatura,a.valortotal as valortotal,a.idperiodico as idperiodico,a.modalidade as modalidade\n"
                    + " from Membros as m,Assinatura as a\n"
                    + " where m.idmembros=a.idmembro and m.membrosNome like '%" + nome + "%'")
                    .setMaxResults(10000)
                    .setResultTransformer(Transformers.aliasToBean(Assinantes.class));
            if (!t.wasCommitted()) {
                t.commit();
            }

            List<Assinantes> resultado = financeiro.list();

            return resultado;

        } catch (HibernateException e) {
            System.out.println("Erro encontrado: " + e);
//            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Missgeral> buscaFinanceiroGeral(int id) throws ParseException {

        try {

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Query financeiro = session.createQuery("select a.datacadastro as datacadastro, m.membrosNome as nome,m.membrosFone as fone1,m.membrosCelular as fone2,a.modalidade as modalidade,\n"
                    + "(select count(1) from Parcelamentos as pp where pp.idmembro = a.idmembro and pp.idperiodico = a.idperiodico and pp.datapagamento is not null) as parcelaspg,\n"
                    + "a.valortotal as valortotal,(select pp.datapagamento from Parcelamentos as pp where pp.idmembro=a.idmembro and pp.idperiodico=a.idperiodico and pp.numparcela=1) as parcela1,\n"
                    + "(select pp.datapagamento from Parcelamentos as pp where pp.idmembro=a.idmembro and pp.idperiodico=a.idperiodico and pp.numparcela=2) as parcela2,\n"
                    + "(select pp.datapagamento from Parcelamentos as pp where pp.idmembro=a.idmembro and pp.idperiodico=a.idperiodico and pp.numparcela=3) as parcela3,\n"
                    + "a.ed1 as ed1,a.ed2 as ed2,a.ed3 as ed3,a.ed4 as ed4,a.ed5 as ed5,a.ed6 as ed6,a.ed7 as ed7,a.ed8 as ed8,a.ed9 as ed9,a.ed10 as ed10,a.ed11 as ed11,a.ed12 as ed12 \n"
                    + " from Membros as m, Assinatura as a,Parcelamentos as p\n"
                    + " where m.idmembros = a.idmembro and a.idmembro = p.idmembro and a.idperiodico = p.idperiodico and a.idperiodico =:id \n"
                    + " group by a.idmembro,a.idperiodico")
                    .setInteger("id", id)
                    .setMaxResults(10000)
                    .setResultTransformer(Transformers.aliasToBean(Missgeral.class));
            if (!t.wasCommitted()) {
                t.commit();
            }

            List<Missgeral> resultado = financeiro.list();

            return resultado;

        } catch (HibernateException e) {
            System.out.println("Erro encontrado: " + e);
//            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public Assinatura buscarPorID(int id) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Assinatura criancas = (Assinatura) session.createQuery("from Assinatura where idassinatura=" + id)
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

    public Assinatura buscarPorAssinatura(int idassina, int idmembro, int idperiodico) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Assinatura criancas = (Assinatura) session.createQuery("from Assinatura where idassinatura=" + idassina + " and idperiodico=" + idperiodico + " and idmembro=" + idmembro)
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

    public boolean insert(Assinatura crianca) {
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

    public boolean delete(Assinatura crianca) {
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

    public boolean update(Assinatura crianca) {
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
