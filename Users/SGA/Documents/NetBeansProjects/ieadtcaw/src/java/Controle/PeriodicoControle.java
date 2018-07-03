/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.faces.bean.ManagedBean;

import Modelo.Periodico;
import DAO.PeriodicoDAO;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped

/**
 *
 * @author Edson Ricardo
 */
public class PeriodicoControle {

    private Periodico periodico;
    private PeriodicoDAO dao;
    private Periodico periodicoSelecionado;
    private List<Periodico> listaPeriodico;
    private List<Periodico> listaDaBusca;
    private Boolean isRederiza = false;
    private String nome;

    public PeriodicoControle() {
        periodico = new Periodico();
        dao = new PeriodicoDAO();
        //periodicoSelecionado = new Periodico();
    }

    @PostConstruct
    public void init() {
        periodico = new Periodico();
        dao = new PeriodicoDAO();
        listaPeriodico = dao.selectAll();
        //periodicoSelecionado = new Periodico();

    }

    public void limpaFormulario() {
        periodico = new Periodico();
        periodicoSelecionado = new Periodico();
        listaDaBusca = null;

    }

    public void buscarLista(String nome) {

        List<Periodico> lista = null;
        try {
            lista = (List<Periodico>) dao.buscarPorNome(nome);
        } catch (ParseException ex) {
            Logger.getLogger(PeriodicoControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaDaBusca = lista;
    }

    public void buscarListaDtiniDtfim(Date dtini, Date dtfim) {

        List<Periodico> lista = null;
        try {
            lista = (List<Periodico>) dao.buscarPorDtiniDtfim(dtini, dtfim);
        } catch (ParseException ex) {
            Logger.getLogger(PeriodicoControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaDaBusca = lista;
    }

    public void buscarPorId(int id) {
        periodicoSelecionado = dao.buscarPorID(id);

    }

    public void insert() {

        if (dao.insert(periodico)) {

            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void delete(Periodico atas) {
        if (dao.delete(atas)) {
            addInfoMessage("Apresentação de Criança excluida!");
        } else {
            addInfoMessage("Selecione uma Apresentação de Criança!");
        }

    }

    public void update() {

        if (dao.update(periodicoSelecionado)) {
            addInfoMessage("Apresentação de Criança alterada com sucesso!");
        } else {
            addInfoMessage("Apresentação de Criança não alterada");
        }

    }

    public Periodico getPeriodico() {
        return periodico;
    }

    public void setPeriodico(Periodico periodico) {
        this.periodico = periodico;
    }

    public Periodico getPeriodicoSelecionado() {
        return periodicoSelecionado;
    }

    public void setPeriodicoSelecionado(Periodico periodicoSelecionado) {
        this.periodicoSelecionado = periodicoSelecionado;
    }

    public List<Periodico> getListaPeriodico() {
        return listaPeriodico;
    }

    public void setListaPeriodico(List<Periodico> listaPeriodico) {
        this.listaPeriodico = listaPeriodico;
    }

    public List<Periodico> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Periodico> listaDaBusca) {
        this.listaDaBusca = listaDaBusca;
    }

    public Boolean getIsRederiza() {
        return isRederiza;
    }

    public void setIsRederiza(Boolean isRederiza) {
        this.isRederiza = isRederiza;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}
