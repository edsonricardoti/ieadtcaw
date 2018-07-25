/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import Modelo.Escala;
import Modelo.Diasdaescala;
import DAO.EscalaDAO;
import DAO.DiasdaescalaDAO;
import static Util.FacesUtil.addInfoMessage;
import static Util.HibernateUtil.getSessionFactory;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author SGA
 */
@Named
@SessionScoped

public class Gerador implements Serializable {

    private static final long serialVersionUID = 24940884301779837L;

    private Escala escala1;
    private Diasdaescala filho;
    private List<Diasdaescala> listaFilhos;
    private EscalaDAO edao;
    private List<Escala> listaEscala;
    private DiasdaescalaDAO ddao;
    private int qtdsemanas;
    private int iddaescala;

    private Session session;

//    public static void main(String[] args) throws ParseException {
//        System.out.println("Eu sou o seu primeiro programa.");
//        teste pp = new teste();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        GregorianCalendar calendar = new GregorianCalendar();
//        calendar.setTime(calendar.getTime());
//        pp.geraEscala(1, 6, 7);
//
//    }
    public Gerador() {
        System.out.println("Entrou na classe...");
        edao = new EscalaDAO();
        listaEscala = edao.selectAll();
    }

    public String NomeDoMes(int i) {
        String mes[] = {"Janeiro", "Fevereiro", "Março", "Abril",
            "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro",
            "Novembro", "Dezembro"};
        if (i == 0) {
            return "";
        } else {
            return (mes[i - 1]);
        }

    }

    public void geraEscala(int qtd, int idescala) {
        //lanca pai uma vez ou varias passada por parametro qtd
        escala1 = new Escala();
        edao = new EscalaDAO();
        ddao = new DiasdaescalaDAO();

        try {

            //contagem das semanas
            for (int x = 1; x <= qtd; x++) {
                escala1 = edao.buscarPorID(idescala);
                Date d1 = escala1.getEscalaData();
                GregorianCalendar calendar = new GregorianCalendar();
                escala1.setEscalaData(addDate(Calendar.WEEK_OF_MONTH, x, d1));
                //escala1.setEscalaData(replicarData(escala1.getEscalaData(), x)); //replicado
                calendar.setTime(escala1.getEscalaData());     //data agora é a replicada
                int semana = calendar.get(Calendar.WEEK_OF_MONTH); //pega a semana
                int mes = calendar.get(Calendar.MONTH) + 1; //pega o mes
                escala1.setEscalaSemana(semana);
                escala1.setEscalaMes(mes);
                escala1.setMatriz(Boolean.FALSE);

                if (edao.insert(escala1)) {
                    System.out.println("Feito Pai...");
                }
                //lanca filho na quantidade de semana passada                
                listaFilhos = ddao.buscarPorIdEscala(idescala);
                for (Diasdaescala filhos : listaFilhos) {
                    Date d2 = filhos.getData();
                    filhos.setData(addDate(Calendar.WEEK_OF_MONTH, x, d2));
                    //filhos.setData(replicarData(filhos.getData(), x));
                    filhos.setIdescalapai(escala1.getIdescala());
                    if (ddao.insert(filhos)) {
                        System.out.println("Feito Filhos...");
                    }
                }

            }
            //terminado. apresenta busca
            addInfoMessage("Escalas geradas com sucesso!");
            escala1 = edao.buscarPorID(idescala);
            escala1.setMatriz(Boolean.TRUE); // grava escala como matriz para nao ser deletada
            if (edao.update(escala1)) {
                System.out.println("Gravou matriz....");
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect("consultar.xhtml?faces-redirect=true");

        } catch (Exception e) {
            System.out.println("Erro apresentado: " + e);

        }

    }

    public Date addDate(int field, int amount, Date origDate) {
        GregorianCalendar gcal = new GregorianCalendar();
        try {
            gcal.setTime(origDate);
            gcal.add(field, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (new Date(gcal.getTime().getTime()));
    }

    public void deleteAll(int idesca) {
        edao = new EscalaDAO();
        ddao = new DiasdaescalaDAO();
        listaEscala = edao.selectAll();
        System.out.println("Id Escala passado..." + idesca);
        try {
            for (Escala escdel : listaEscala) {
                listaFilhos = ddao.selectAll(escdel.getIdescala());
                for (Diasdaescala dias : listaFilhos) {
                    if (!dias.getIdescalapai().equals(idesca)) {
                        if (ddao.delete(dias)) {
                            System.out.println("Filhos excliondo..");
                        }
                    }
                }
                if (!escdel.getMatriz()) {
                    if (edao.delete(escdel)) {
                        System.out.println("Apagando...");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro nao exclui = " + e);
        }

    }

    public int getQtdsemanas() {
        return qtdsemanas;
    }

    public void setQtdsemanas(int qtdsemanas) {
        this.qtdsemanas = qtdsemanas;
    }

    public int getIddaescala() {
        return iddaescala;
    }

    public void setIddaescala(int iddaescala) {
        this.iddaescala = iddaescala;
    }

    public List<Escala> getListaEscala() {
        return listaEscala;
    }

    public void setListaEscala(List<Escala> listaEscala) {
        this.listaEscala = listaEscala;
    }

}
