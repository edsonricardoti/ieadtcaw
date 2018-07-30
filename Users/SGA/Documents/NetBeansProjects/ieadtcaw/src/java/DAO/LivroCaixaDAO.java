/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
import org.hibernate.Session;
import Modelo.LivroCaixa;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Edson Ricardo
 */
public class LivroCaixaDAO implements Serializable {

    private Session session;

    public List<LivroCaixa> GeraCaixa(Date dataini, Date datafim) throws ParseException {


//        try {
//
//            session = getSessionFactory().openSession();
//            Transaction t = session.beginTransaction();
//            Query financeiro = session.createQuery("")
//                    .setDate("dataini", dataini)
//                    .setDate("datafim", datafim)
//                    .setMaxResults(10000)
//                    .setResultTransformer(Transformers.aliasToBean(LivroCaixa.class));
//            if (!t.wasCommitted()) {
//                t.commit();
//            }
//
//            List<LivroCaixa> resultado = financeiro.list();
//
//            return resultado;
//
//        } catch (HibernateException e) {
//            session.getTransaction().rollback();
//            return null;
//        } finally {
//            session.close();
//        }
        return null;
    }


}
