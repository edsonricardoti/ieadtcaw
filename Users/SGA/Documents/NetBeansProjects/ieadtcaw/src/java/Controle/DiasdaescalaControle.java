/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.faces.bean.ManagedBean;

import Modelo.Diasdaescala;
import DAO.DiasdaescalaDAO;
import DAO.EscalaDAO;
import Modelo.Escala;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@ManagedBean
@SessionScoped

/**
 *
 * @author Edson Ricardo
 */
public class DiasdaescalaControle {

    private Diasdaescala diasescala;
    private DiasdaescalaDAO dao;
    private Diasdaescala escalaSelecionado;
    private List<Diasdaescala> listaDiasdaescala;
    private List<Diasdaescala> listaDaBusca;
    private Escala escala;
    private EscalaDAO edao;
    private Date dataini;
    private Date datafim;
    private Boolean isRenderiza;
    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();

    public DiasdaescalaControle() {
        diasescala = new Diasdaescala();
        dao = new DiasdaescalaDAO();
        escalaSelecionado = new Diasdaescala();

    }

    @PostConstruct
    public void init() {
        diasescala = new Diasdaescala();
        dao = new DiasdaescalaDAO();
        escalaSelecionado = new Diasdaescala();

    }

    public void limpaFormulario() {
        diasescala = new Diasdaescala();
        escalaSelecionado = new Diasdaescala();
        listaDaBusca = null;
    }

    public String traduzSemana(Date data) {
        DateFormat dd = new SimpleDateFormat("EEEE");
        return dd.format(data);
    }

    public String periodoRel(Date dataini, Date datafim) {
        DateFormat dd = new SimpleDateFormat("dd/mm/YYYY");
        String texto;
        texto = dd.format(dataini) + " à " + dd.format(datafim);
        return texto;
    }

    public void buscarLista(int mes) {
        List<Diasdaescala> lista;
        lista = dao.buscarPorMesSemanaLista(mes);
        listaDaBusca = lista;
        diasescala = new Diasdaescala();

    }

    public void relatorio(Date dataini, Date datafim) {
        eventModel = new DefaultScheduleModel();
        edao = new EscalaDAO();
        escala = new Escala();
        Calendar inicio = Calendar.getInstance();
        inicio.setTime(dataini);
        Calendar dtfinal = Calendar.getInstance();
        dtfinal.setTime(datafim);
        List<Diasdaescala> lista;
        lista = dao.buscarPorPeriodo(inicio.getTime(), dtfinal.getTime());
        listaDaBusca = lista;
        for (Diasdaescala dia : listaDaBusca) {
            eventModel.addEvent(new DefaultScheduleEvent(dia.getEvento(), datafim, dataini));
            escala = edao.buscarPorID(dia.getIdescalapai());
        }
        isRenderiza = true;
    }

    public void buscar(Date data) {
        escalaSelecionado = dao.buscarPorData(data);

    }

    public void insert(Escala escala) {
        diasescala.setIdescalapai(escala.getIdescala());
        if (dao.insert(diasescala)) {
            diasescala = new Diasdaescala();
            //addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void delete(Diasdaescala escalas) {
        if (dao.delete(escalas)) {
            addInfoMessage("Trabalho excluido!");
        } else {
            addInfoMessage("Selecione uma Trabalho!");
        }

    }

    public void update() {

        if (dao.update(escalaSelecionado)) {
            addInfoMessage("Diasdaescala alterada com sucesso!");
        } else {
            addInfoMessage("Diasdaescala não alterada");
        }

    }

    public Diasdaescala getDiasescala() {
        return diasescala;
    }

    public void setDiasescala(Diasdaescala diasescala) {
        this.diasescala = diasescala;
    }

    public Diasdaescala getEscalaSelecionado() {
        return escalaSelecionado;
    }

    public void setEscalaSelecionado(Diasdaescala escalaSelecionado) {
        this.escalaSelecionado = escalaSelecionado;
    }

    public Escala getEscala() {
        return escala;
    }

    public void setEscala(Escala escala) {
        this.escala = escala;
    }

    public Date getDataini() {
        return dataini;
    }

    public void setDataini(Date dataini) {
        this.dataini = dataini;
    }

    public Date getDatafim() {
        return datafim;
    }

    public void setDatafim(Date datafim) {
        this.datafim = datafim;
    }

    public List<Diasdaescala> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Diasdaescala> listaDaBusca) {
        this.listaDaBusca = listaDaBusca;
    }

    public Boolean getIsRenderiza() {
        return isRenderiza;
    }

    public void setIsRenderiza(Boolean isRenderiza) {
        this.isRenderiza = isRenderiza;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

}
