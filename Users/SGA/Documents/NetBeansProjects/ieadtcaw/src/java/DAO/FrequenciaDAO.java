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
import Modelo.Frequencia;
import java.text.ParseException;
import java.util.Date;
import org.hibernate.HibernateException;
import Modelo.Membros;
import Modelo.Relatorios;
import static Util.HibernateUtil.getSessionFactory;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;


/**
 *
 * @author Edson Ricardo
 */
public class FrequenciaDAO {

    private Session session;

    public List<Relatorios> frequenciaPorClasse(int idclasse, int trimestre, int ano) throws ParseException {
        String SQL = "";
        if (trimestre == 0) {
            SQL = "select m.membrosNome as nome,sum(f.presente) as presente, sum(f.faltou) as faltou from Frequencia as f\n"
                    + ", Membros as m where f.idmembro=m.idmembros and f.ano=:ano and f.trimestre>=:trimestre and f.idclasse=:idclasse\n"
                    + " group by f.idmembro";
        } else {
            SQL = "select m.membrosNome as nome,sum(f.presente) as presente, sum(f.faltou) as faltou from Frequencia as f\n"
                    + ", Membros as m where f.idmembro=m.idmembros and f.ano=:ano and f.trimestre=:trimestre and f.idclasse=:idclasse\n"
                    + " group by f.idmembro,f.trimestre";
        }
        try {

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Query financeiro = session.createQuery(SQL)
                    .setInteger("idclasse", idclasse)
                    .setInteger("trimestre", trimestre)
                    .setInteger("ano", ano)
                    .setMaxResults(10000)
                    .setResultTransformer(Transformers.aliasToBean(Relatorios.class));
            if (!t.wasCommitted()) {
                t.commit();
            }

            List<Relatorios> resultado = financeiro.list();

            return resultado;

        } catch (HibernateException e) {
            System.out.println("Erro apresentado=" + e);
//            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Relatorios> graficoPorclassePeriodo() throws ParseException {

        try {

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Query financeiro = session.createQuery("select c.classesNome as nome,\n"
                    + " sum(case f.trimestre when 1 then f.presente else 0 end) as trimestre1,\n"
                    + " sum(case f.trimestre when 2 then f.presente else 0 end) as trimestre2,\n"
                    + " sum(case f.trimestre when 3 then f.presente else 0 end) as trimestre3,\n"
                    + " sum(case f.trimestre when 4 then f.presente else 0 end) as trimestre4\n"
                    + " from Frequencia as f,Classes as c where f.idclasse=c.idclasses and f.ano=year(now()) group by f.idclasse")
                    .setMaxResults(10000)
                    .setResultTransformer(Transformers.aliasToBean(Relatorios.class));
            if (!t.wasCommitted()) {
                t.commit();
            }

            List<Relatorios> resultado = financeiro.list();

            return resultado;

        } catch (HibernateException e) {
            System.out.println("Erro apresentado: " + e);
//            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> buscarPorData(Date data) throws ParseException {
        System.out.println("Data enviada=" + data);
        try {
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List frequencias = session.createQuery("from Membros as m where m.idmembros in(select a.frequenciasIdmembro from Frequencia as a where a.frequenciasData=:data)")
                    .setDate("data", data)
                    .setMaxResults(10000)
                    .list();
            t.commit();

            return frequencias;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> buscarPorDtiniDtfim(Date dtini, Date dtfim) throws ParseException {

        try {
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List frequencias = session.createQuery("from Membros as m where m.idmembros in(select a.frequenciasIdmembro from Frequencia as a where a.frequenciasData>=:dtini and a.frequenciasData<=:dtfim)")
                    .setDate("dtini", dtini)
                    .setDate("dtfim", dtfim)
                    .setMaxResults(10000)
                    .list();
            t.commit();

            return frequencias;

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
            List frequencias = session.createQuery("from Membros as m where m.membrosNome=:nome and m.idmembros in(select a.frequenciasIdmembro from Frequencia as a)")
                    .setString("nome", nome)
                    .setMaxResults(10000)
                    .list();

            t.commit();

            return frequencias;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> buscarFrequenciaPorClasse(int id) {

        try {

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List frequencias = session.createQuery("from Membros as m where m.idmembros in(select a.frequenciasIdmembro from Frequencia as a where a.idclasse=:id)")
                    .setInteger("id", id)
                    .setMaxResults(10000)
                    .list();
            if (!t.wasCommitted()) {
                t.commit();
            }

            //List<Membros> resultado = frequencias.list();
            return frequencias;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public Membros buscarPorID(int id, int idclasse) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Membros frequencias = (Membros) session.createQuery("from Membros as m where m.idmembros=:id and m.idmembros in(select a.frequenciasIdmembro from Frequencia as a where a.frequenciasIdmembro=:id and a.idclasse=:idclasse)")
                    .setInteger("id", id)
                    .setInteger("idclasse", idclasse)
                    .uniqueResult();
            t.commit();
            return frequencias;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public Frequencia buscarAlunoPorID(int id, int idclasse) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Frequencia frequencias = (Frequencia) session.createQuery("from Frequencia where idmembro =:id and idclasse =:idclasse order by licao ASC")
                    .setInteger("id", id)
                    .setInteger("idclasse", idclasse)
                    .uniqueResult();
            t.commit();
            return frequencias;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Frequencia> buscarLicoesAluno(int id, int idclasse) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List frequencias = session.createQuery("from Frequencia where idmembro=:id and idclasse=:idclasse")
                    .setInteger("id", id)
                    .setInteger("idclasse", idclasse)
                    .list();

            t.commit();
            return frequencias;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Frequencia> buscarPresencaLicoes(int idclasse, int trimestre, int ano) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List frequencias = session.createQuery("from Frequencia where idclasse =:idclasse and trimestre =:trimestre and ano =:ano order by idmembro,licao")
                    .setInteger("idclasse", idclasse)
                    .setInteger("trimestre", trimestre)
                    .setInteger("ano", ano)
                    .list();

            t.commit();
            return frequencias;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public boolean insert(Frequencia frequencia) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.save(frequencia);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Frequencia frequencia) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.delete(frequencia);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Frequencia frequencia) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.update(frequencia);
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
