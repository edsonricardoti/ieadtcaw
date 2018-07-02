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
import Modelo.Usuarios;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 *
 * @author Edson Ricardo
 */
public class UsuarioDAO {

    private Session session;
    

    public List<Usuarios> selectAll() {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            List lista = session.createQuery("from Usuarios").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }
    public Usuarios buscarPorNome(String nome) {
            System.out.println("Entrou na DAO busca nome ="+nome);
            
            try {
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                Usuarios usuario = (Usuarios) session.createQuery("from Usuarios where nomeUsuarios like '%"+nome+"%'")
                        .uniqueResult();
                t.commit();
                return usuario;

            } catch (HibernateException e) {
                session.getTransaction().rollback();
                return null;
            } finally {
                session.close();
            }
    }

    public Usuarios buscarPorID(int id) {

        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Usuarios usuario = (Usuarios) session.createQuery("from Usuarios where idUsuarios=:id")
                    .setInteger("id", id)
                    .uniqueResult();
            t.commit();
            return usuario;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }
    
    public List<Usuarios> buscarPorNomeLista(String nome) {
            System.out.println("Entrou na DAO busca nome ="+nome);
            
            try {
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                List lista = session.createQuery("from Usuarios where nomeUsuarios like '%"+nome+"%'").list();
                t.commit();
                return lista;

            } catch (HibernateException e) {
                session.getTransaction().rollback();
                return null;
            } finally {
                session.close();
            }
        }

    public Usuarios buscarPorUsuarioSenha(String usuario, String senha) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Usuarios usuariologado = (Usuarios) session.createQuery("from Usuarios where nome_usuarios=:usuario and senha_usuarios=:senha")
                    .setString("usuario", usuario)
                    .setString("senha", senha)
                    .uniqueResult();
            t.commit();
            return usuariologado;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        } finally {
//            session.close();
        }
    }

    public boolean insert(Usuarios usuario) {
        System.out.println("Entrou no inserte com Usuarios ");
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.save(usuario);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Usuarios usuario) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.delete(usuario);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Usuarios usuario) {
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            session.update(usuario);
            t.commit();
            return true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

   public Usuarios verificar(Usuarios usuario) {
       String nome = usuario.getNomeUsuarios();
       String senha = usuario.getSenhaUsuarios();
        try {
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            Usuarios logado = (Usuarios) session.createQuery("from Usuarios where nomeUsuarios=:nome and senhaUsuarios=:senha)")
                    .setString("nome", nome)
                    .setString("senha", senha)
                    .uniqueResult();
            t.commit();
            return logado;

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
        
    }
}
