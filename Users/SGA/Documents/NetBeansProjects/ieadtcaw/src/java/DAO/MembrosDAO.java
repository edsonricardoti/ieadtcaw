/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Modelo.Membros;
import static Util.HibernateUtil.getSessionFactory;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Edson Ricardo
 */
public class MembrosDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Session session;

    public List<Membros> selectAll() {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Membros order by membrosNome").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> selectAllFiltrado(String filtro, String nome) {
        String sql = "";
        System.out.println("Filtro =" + filtro);
        if (filtro.isEmpty() || filtro.equals("()")) {
            sql = "from Membros where membrosNome like '%" + nome + "%' order by membrosNome";
        } else {
            sql = "from Membros where membrosTipo in" + filtro + " order by membrosNome";

        }

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery(sql).list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> selectAllFiltradoCad(String nome) {
        String sql = "from Membros";

//        if (filtro.isEmpty() || filtro.equals("()")) {
        sql = "from Membros where membrosNome like '%" + nome + "%' order by membrosNome";
//        } else {
//            sql = "from Membros where membrosTipo in" + filtro + " order by membrosNome";
//
//        }

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery(sql)
                    .setMaxResults(10)
                    .list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> selectDirigentes() {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Membros").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> selectProfessor() {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Membros where membrosEprof=1").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> selectAllVisitantes() {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Membros where membrosTipo = 'Visitante'").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> aniversariantes(Date data) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Membros where membrosDataNasc= DATE_FORMAT(data, '%Y-%m-%d') and membrosTipo = 'Membro'").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public Membros buscarPorId(int id) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            Membros membro = (Membros) session.createQuery("from Membros where idmembros=:id ")
                    .setInteger("id", id)
                    .uniqueResult();
            t.commit();
            return membro;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public Membros buscarPastor() {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            Membros membro = (Membros) session.createQuery("from Membros where membrosCargoIgreja ='Pastor' and membrosTipo = 'Membro'")
                    .uniqueResult();
            t.commit();
            return membro;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public Membros buscarVisitantePorId(Integer id) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            Membros membro = (Membros) session.createQuery("from Membros where idmembros=:id and membrosTipo = 'Visitante'")
                    .setInteger("id", id)
                    .uniqueResult();
            t.commit();
            return membro;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> buscarPorNome(String nome) {
        System.out.println("Entrou na DAO busca nome =" + nome);

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List membro = session.createQuery("from Membros where membrosNome like '%" + nome + "%'")
                    .list();
            t.commit();
            return membro;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public Membros buscarVisitantePorNome(String nome) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            Membros membro = (Membros) session.createQuery("from Membros where membrosNome like '%" + nome + "%' and membrosTipo = 'Visitante'")
                    .uniqueResult();
            t.commit();
            return membro;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> buscarPorNomeLista(String nome) {
        System.out.println("Entrou na DAO busca nome =" + nome);

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Membros where membrosNome like '%" + nome + "%'").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> buscarNaoAlunoNomeLista(String nome) {
        System.out.println("Entrou na DAO busca nome =" + nome);

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Membros where membrosNome like '%" + nome + "%' and membrosTipo = 'Membro' and membrosNome not in(select alunosMembroNome from Alunos)").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> buscarAniversariantes(String mes) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Membros where Month(membrosDataNasc)=" + mes + " and membrosTipo = 'Membro' order by Day(membrosDataNasc)").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> buscarCasados(String mes) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Membros where membrosEstadoCivil='Casado(a)' and Month(membrosDataCasamento)=" + mes + " and membrosTipo = 'Membro' order by Day(membrosDataCasamento) ASC").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> buscarPorEstCivil(String estacivil) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Membros where membrosEstadoCivil='" + estacivil + "' and membrosTipo = 'Membro' order by membrosNome").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public List<Membros> buscarVisitantePorNomeLista(String nome) {
        System.out.println("Entrou na DAO busca nome =" + nome);

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List lista = session.createQuery("from Membros where membrosNome like '%" + nome + "%' and membrosTipo = 'Visitante'").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return null;
        } finally {
            session.close();
        }
    }

    public boolean insert(Membros membro) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(membro);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Membros membro) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            session.delete(membro);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Membros membro) {

        session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            session.update(membro);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            return false;
        } finally {
            session.close();
        }
    }

}
