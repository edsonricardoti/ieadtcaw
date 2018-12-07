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
import Modelo.Financeiro;
import Modelo.Membros;
import static Util.HibernateUtil.getSessionFactory;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Edson Ricardo
 */
public class FinanceiroDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Session session;

    public List<Financeiro> selectAll() {
       
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            List lista = session.createQuery("from Financeiro").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Relatorios> selectAllDizimo() {
       
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            List lista = session.createQuery("from dizimopormes").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Financeiro> buscarPorPeriodo(Date dataini, Date datafim) throws ParseException {
        
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            List financeiro = session.createQuery("from Financeiro where financeiroData >=:dataini and financeiroData <=:datafim")
                    .setDate("dataini", dataini)
                    .setDate("datafim", datafim)
                    .setMaxResults(400)
                    .list();
            t.commit();
            return financeiro;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }


    public List<Relatorios> buscarDizimoPorMes(int mes) throws ParseException {

       

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Query financeiro = session.createQuery("select m.membrosNome as nome, sum(f.financeiroValor) as total from Financeiro as f, Membros as m\n"
                    + " where m.idmembros=f.financeiroIdmembro and month(f.financeiroData)=:mes and year(f.financeiroData) = year(now()) and f.financeiroTipo = 'dizimo'\n"
                    + " group by f.financeiroIdmembro")
                    .setInteger("mes", mes)
                    .setMaxResults(10000)
                    .setResultTransformer(Transformers.aliasToBean(Relatorios.class));
            if (!t.wasCommitted()) {
                t.commit();
            }

            List<Relatorios> resultado = financeiro.list();

            return resultado;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Relatorios> buscarDizimoPorPeriodo(Date dataini, Date datafim, String tipo) throws ParseException {

       

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Query financeiro = session.createQuery("select f.idfinanceiro as idfinanceiro, f.financeiroData as data, m.membrosNome as nome, sum(f.financeiroValor) as total from Financeiro as f, Membros as m\n"
                    + " where m.idmembros=f.financeiroIdmembro and f.financeiroData >=:dataini and f.financeiroData <=:datafim and f.financeiroTipo=:tipo\n"
                    + " group by f.financeiroIdmembro")
                    .setDate("dataini", dataini)
                    .setDate("datafim", datafim)
                    .setString("tipo", tipo)
                    .setMaxResults(10000)
                    .setResultTransformer(Transformers.aliasToBean(Relatorios.class));
            if (!t.wasCommitted()) {
                t.commit();
            }

            List<Relatorios> resultado = financeiro.list();

            return resultado;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public Membros buscarPorId(int id) {
      
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Membros membro = (Membros) session.createQuery("from Membros where idmembros=:id")
                    .setInteger("id", id)
                    .uniqueResult();
            t.commit();
            return membro;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Relatorios> buscarGeralPorPeriodo(Date dataini, Date datafim) throws ParseException {

      

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Query financeiro = session.createQuery("select month(f.financeiroData) as mes, sum(case financeiroTipo when 'dizimo' then f.financeiroValor else 0 end) as dizimos,\n"
                    + " sum(case financeiroTipo when 'alcada' then f.financeiroValor else 0 end) as alcadas,\n"
                    + " sum(case financeiroTipo when 'oferta' then f.financeiroValor else 0 end) as ofertas,\n"
                    + " sum(case financeiroTipo when 'voto' then f.financeiroValor else 0 end) as votos\n"
                    + " from Financeiro as f where f.financeiroData>=:dataini and f.financeiroData<=:datafim group by  month(f.financeiroData)")
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
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Relatorios> buscarMissGeralPorPeriodo(Date dataini, Date datafim) throws ParseException {

      

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Query financeiro = session.createQuery("select month(f.financeiroData) as mes,\n"
                    + " sum(case financeiroTipo when 'missalcada' then f.financeiroValor else 0 end) as alcadas,\n"
                    + " sum(case financeiroTipo when 'missoferta' then f.financeiroValor else 0 end) as ofertas,\n"
                    + " sum(case financeiroTipo when 'missvoto' then f.financeiroValor else 0 end) as votos,\n"
                    + " sum(case financeiroTipo when 'carne' then f.financeiroValor else 0 end) as dizimos\n"
                    + " from Financeiro as f where f.financeiroData>=:dataini and f.financeiroData<=:datafim group by  month(f.financeiroData)")
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
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Relatorios> buscarDizimoNosMese(int ano) throws ParseException {

      

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Query financeiro = session.createQuery("select m.membrosNome as nome,\n"
                    + " sum(case month(f.financeiroData) when 1 then f.financeiroValor else 0 end) as janeiro,\n"
                    + " sum(case month(f.financeiroData) when 2 then f.financeiroValor else 0 end) as fevereiro,\n"
                    + " sum(case month(f.financeiroData) when 3 then f.financeiroValor else 0 end) as marco,\n"
                    + " sum(case month(f.financeiroData) when 4 then f.financeiroValor else 0 end) as abril,\n"
                    + " sum(case month(f.financeiroData) when 5 then f.financeiroValor else 0 end) as maio,\n"
                    + " sum(case month(f.financeiroData) when 6 then f.financeiroValor else 0 end) as junho,\n"
                    + " sum(case month(f.financeiroData) when 7 then f.financeiroValor else 0 end) as julho,\n"
                    + " sum(case month(f.financeiroData) when 8 then f.financeiroValor else 0 end) as agosto,\n"
                    + " sum(case month(f.financeiroData) when 9 then f.financeiroValor else 0 end) as setembro,\n"
                    + " sum(case month(f.financeiroData) when 10 then f.financeiroValor else 0 end) as outubro,\n"
                    + " sum(case month(f.financeiroData) when 11 then f.financeiroValor else 0 end) as novembro,\n"
                    + " sum(case month(f.financeiroData) when 12 then f.financeiroValor else 0 end) as dezembro \n"
                    + "from\n"
                    + "  Financeiro as f,Membros as m\n"
                    + "where\n"
                    + "  year(f.financeiroData)=:ano and financeiroTipo='dizimo' and f.financeiroIdmembro=m.idmembros and year(f.financeiroData)='2018'\n"
                    + "group by f.financeiroIdmembro")
                    .setInteger("ano", ano)
                    .setMaxResults(10000)
                    .setResultTransformer(Transformers.aliasToBean(Relatorios.class));
            if (!t.wasCommitted()) {
                t.commit();
            }

            List<Relatorios> resultado = financeiro.list();

            return resultado;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Relatorios> buscarDizimoPorAno(int ano) throws ParseException {

      

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Query financeiro = session.createQuery("select month(f.financeiroData) as mes, sum(f.financeiroValor) as total from Financeiro as f, Membros as m\n"
                    + " where m.idmembros=f.financeiroIdmembro and year(f.financeiroData) =:ano and f.financeiroTipo = 'dizimo'\n"
                    + " group by month(f.financeiroData)")
                    .setInteger("ano", ano)
                    .setMaxResults(10000)
                    .setResultTransformer(Transformers.aliasToBean(Relatorios.class));
            if (!t.wasCommitted()) {
                t.commit();
            }

            List<Relatorios> resultado = financeiro.list();

            return resultado;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public Financeiro buscarPorID(int id) {

       
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Financeiro cl = (Financeiro) session.createQuery("from Financeiro where idfinanceiro=" + id)
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

    public boolean insert(Financeiro financeiro) {
       
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.save(financeiro);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return false;
        } finally {
            session.close();
        }
    }

    public boolean insertDizimo(Relatorios dizimo) {
       
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.save(dizimo);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Financeiro financeiro) {
     
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.delete(financeiro);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Financeiro financeiro) {
     
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.update(financeiro);
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
