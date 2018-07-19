package Controle;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import Modelo.Membros;
import static Util.HibernateUtil.getSessionFactory;
import org.hibernate.HibernateException;

public class Membross implements Serializable {

    private static final long serialVersionUID = 1L;
    private Session session;


    public Membross() {

    }

    @SuppressWarnings("unchecked")
    public List<Membros> filtrados(FiltroMembros filtro) {
        Criteria criteria = criarCriteriaParaFiltro(filtro);

        criteria.setFirstResult(filtro.getPrimeiroRegistro());
        criteria.setMaxResults(filtro.getQuantidadeRegistros());

        if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
            criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
        } else if (filtro.getPropriedadeOrdenacao() != null) {
            criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
        }
        List lista = criteria.list();
        return lista;

    }

    public int quantidadeFiltrados(FiltroMembros filtro) {
        Criteria criteria = criarCriteriaParaFiltro(filtro);

        criteria.setProjection(Projections.rowCount());

        return ((Number) criteria.uniqueResult()).intValue();
    }

    private Criteria criarCriteriaParaFiltro(FiltroMembros filtro) {
        try {
            session = getSessionFactory().openSession();
        } catch (HibernateException hibernateException) {
            session.close();
        }
        Criteria criteria = session.createCriteria(Membros.class);

        if (StringUtils.isNotEmpty(filtro.getNome())) {
            criteria.add(Restrictions.ilike("membrosNome", filtro.getNome(), MatchMode.ANYWHERE));
        }
//        else if (StringUtils.isNotEmpty(filtro.getTipos())) {
//            String meutipo = filtro.getTipos();
//            meutipo = meutipo.replace("[", "(");
//            meutipo = meutipo.replace("]", ")");
//            criteria.add(Restrictions.sqlRestriction("membrosTipo in " + meutipo));
//        }
        return criteria;
    }

}
