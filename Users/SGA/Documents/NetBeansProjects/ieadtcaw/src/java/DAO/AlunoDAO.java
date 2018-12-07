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
import Modelo.Alunos;
import java.text.ParseException;
import java.util.Date;
import org.hibernate.HibernateException;
import Modelo.Membros;
import java.io.Serializable;

/**
 *
 * @author Edson Ricardo
 */
public class AlunoDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Session session;

    public List<Membros> selectAll() {
      
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            List alunos = session.createQuery("from Membros as m where m.idmembros in(select a.alunosIdmembro from Alunos as a)")
                    .setMaxResults(10000)
                    .list();
            t.commit();

            return alunos;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> buscarPorData(Date data) throws ParseException {
        System.out.println("Data enviada=" + data);
       
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            List alunos = session.createQuery("from Membros as m where m.idmembros in(select a.alunosIdmembro from Alunos as a where a.alunosData=:data)")
                    .setDate("data", data)
                    .setMaxResults(10000)
                    .list();
            t.commit();

            return alunos;

        } catch (HibernateException e) {
           if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> buscarPorDtiniDtfim(Date dtini, Date dtfim) throws ParseException {

       
            // String newDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(d);
            // System.out.println("Entrou na busca por data=" + newDateFormat);
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            List alunos = session.createQuery("from Membros as m where m.idmembros in(select a.alunosIdmembro from Alunos as a where a.alunosData>=:dtini and a.alunosData<=:dtfim)")
                    .setDate("dtini", dtini)
                    .setDate("dtfim", dtfim)
                    .setMaxResults(10000)
                    .list();
            t.commit();

            return alunos;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> buscarPorNome(String nome) throws ParseException {

       

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            List alunos = session.createQuery("from Membros as m where m.membrosNome=:nome and m.idmembros in(select a.alunosIdmembro from Alunos as a)")
                    .setString("nome", nome)
                    .setMaxResults(10000)
                    .list();

            t.commit();

            return alunos;

        } catch (HibernateException e) {
           if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> buscarAlunosPorClasse(int id) {

       

            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            List alunos = session.createQuery("from Membros as m where m.idmembros in(select a.alunosIdmembro from Alunos as a where a.idclasse=:id)")
                    .setInteger("id", id)
                    .setMaxResults(10000)
                    .list();
            if (!t.wasCommitted()) {
                t.commit();
            }

            //List<Membros> resultado = alunos.list();
            return alunos;

        } catch (HibernateException e) {
          if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public Membros buscarPorID(int id, int idclasse) {

     
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Membros alunos = (Membros) session.createQuery("from Membros as m where m.idmembros=:id and m.idmembros in(select a.alunosIdmembro from Alunos as a where a.alunosIdmembro=:id and a.idclasse=:idclasse)")
                    .setInteger("id", id)
                    .setInteger("idclasse", idclasse)
                    .uniqueResult();
            t.commit();
            return alunos;

        } catch (HibernateException e) {
           if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public Alunos buscarAlunoPorID(int id, int idclasse) {

     
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Alunos alunos = (Alunos) session.createQuery("from Alunos where alunosIdmembro=:id and idclasse=:idclasse)")
                    .setInteger("id", id)
                    .setInteger("idclasse", idclasse)
                    .uniqueResult();
            t.commit();
            return alunos;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }

    public boolean insert(Alunos aluno) {
      
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.save(aluno);
            t.commit();
            return true;
        } catch (HibernateException e) {
           if(t != null){ t.rollback();}
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Alunos aluno) {
      
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.delete(aluno);
            t.commit();
            return true;
        } catch (HibernateException e) {
           if(t != null){ t.rollback();}
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Alunos aluno) {
      
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.update(aluno);
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
