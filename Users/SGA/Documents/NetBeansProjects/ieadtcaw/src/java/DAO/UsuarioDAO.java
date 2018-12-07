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
import java.io.Serializable;
import org.hibernate.HibernateException;

/**
 *
 * @author Edson Ricardo
 */
public class UsuarioDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Session session;
    

    public List<Usuarios> selectAll() {
       
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try {
            List lista = session.createQuery("from Usuarios").list();
            t.commit();
            return lista;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }
    public Usuarios buscarPorNome(String nome) {
            System.out.println("Entrou na DAO busca nome ="+nome);
            
           
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                try{
                Usuarios usuario = (Usuarios) session.createQuery("from Usuarios where nomeUsuarios like '%"+nome+"%'")
                        .uniqueResult();
                t.commit();
                return usuario;

            } catch (HibernateException e) {
                if(t != null){ t.rollback();}
                return null;
            } finally {
                session.close();
            }
    }

    public Usuarios buscarPorID(int id) {

        
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Usuarios usuario = (Usuarios) session.createQuery("from Usuarios where idUsuarios=:id")
                    .setInteger("id", id)
                    .uniqueResult();
            t.commit();
            return usuario;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
            session.close();
        }
    }
    
    public List<Usuarios> buscarPorNomeLista(String nome) {
            System.out.println("Entrou na DAO busca nome ="+nome);
            
            
                session = getSessionFactory().openSession();
                Transaction t = session.beginTransaction();
                try{
                List lista = session.createQuery("from Usuarios where nomeUsuarios like '%"+nome+"%'").list();
                t.commit();
                return lista;

            } catch (HibernateException e) {
                if(t != null){ t.rollback();}
                return null;
            } finally {
                session.close();
            }
        }

    public Usuarios buscarPorUsuarioSenha(String usuario, String senha) {
       
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Usuarios usuariologado = (Usuarios) session.createQuery("from Usuarios where nome_usuarios=:usuario and senha_usuarios=:senha")
                    .setString("usuario", usuario)
                    .setString("senha", senha)
                    .uniqueResult();
            t.commit();
            return usuariologado;

        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return null;
        } finally {
//            session.close();
        }
    }

    public boolean insert(Usuarios usuario) {
        System.out.println("Entrou no inserte com Usuarios ");
       
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.save(usuario);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    public boolean delete(Usuarios usuario) {
        
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.delete(usuario);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return false;
        } finally {
            session.close();
        }
    }

    public boolean update(Usuarios usuario) {
       
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            session.update(usuario);
            t.commit();
            return true;
        } catch (HibernateException e) {
            if(t != null){ t.rollback();}
            return false;
        } finally {
            session.close();
        }
    }

   public Usuarios verificar(Usuarios usuario) {
       String nome = usuario.getNomeUsuarios();
       String senha = usuario.getSenhaUsuarios();
        
            session = getSessionFactory().openSession();
            Transaction t = session.beginTransaction();
            try{
            Usuarios logado = (Usuarios) session.createQuery("from Usuarios where nomeUsuarios=:nome and senhaUsuarios=:senha)")
                    .setString("nome", nome)
                    .setString("senha", senha)
                    .uniqueResult();
            t.commit();
            return logado;

        } catch (Exception e) {
            if(t != null){ t.rollback();}
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
        
    }
}
