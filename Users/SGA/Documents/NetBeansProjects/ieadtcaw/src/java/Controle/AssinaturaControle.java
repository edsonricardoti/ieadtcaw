/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.faces.bean.ManagedBean;

import Modelo.Assinatura;
import DAO.AssinaturaDAO;
import DAO.MembrosDAO;
import DAO.EdicoesDAO;
import Modelo.Edicoes;
import Modelo.Membros;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped

/**
 *
 * @author Edson Ricardo
 */
public class AssinaturaControle {

    private Assinatura periodico;
    private AssinaturaDAO dao;
    private Membros membro;
    private MembrosDAO mdao;
    private Edicoes edicoes;
    private EdicoesDAO edao;
    private Assinatura periodicoSelecionado;
    private List<Assinatura> listaAssinatura;
    private List<Assinatura> listaDaBusca;
    private Boolean isRederiza = false;
    private List<Edicoes> listaEdicoes;
    private List<Membros> listaMembros;
    private String nome;
    private Date dataini;
    private Date datafim;

    public AssinaturaControle() {
        periodico = new Assinatura();
        dao = new AssinaturaDAO();
        periodicoSelecionado = new Assinatura();
    }

    @PostConstruct
    public void init() {
        periodico = new Assinatura();
        dao = new AssinaturaDAO();
        periodicoSelecionado = new Assinatura();

    }

    public void limpaFormulario() {
        periodico = new Assinatura();
        periodicoSelecionado = new Assinatura();
        listaDaBusca = null;
        membro = new Membros();
        listaMembros = null;
        listaEdicoes = null;

    }

    public void buscarLista(String nome) {

        List<Assinatura> lista = null;
        try {
            lista = (List<Assinatura>) dao.buscarPorNome(nome);
        } catch (ParseException ex) {
            Logger.getLogger(AssinaturaControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaDaBusca = lista;
    }

    public void buscarListaDtiniDtfim(Date dtini, Date dtfim) {

        List<Assinatura> lista = null;
        try {
            lista = (List<Assinatura>) dao.buscarPorDtiniDtfim(dtini, dtfim);
        } catch (ParseException ex) {
            Logger.getLogger(AssinaturaControle.class.getName()).log(Level.SEVERE, null, ex);
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

    public void delete(Assinatura atas) {
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

    public Assinatura getAssinatura() {
        return periodico;
    }

    public void setAssinatura(Assinatura periodico) {
        this.periodico = periodico;
    }

    public Assinatura getAssinaturaSelecionado() {
        return periodicoSelecionado;
    }

    public void setAssinaturaSelecionado(Assinatura periodicoSelecionado) {
        this.periodicoSelecionado = periodicoSelecionado;
    }

    public List<Assinatura> getListaAssinatura() {
        return listaAssinatura;
    }

    public void setListaAssinatura(List<Assinatura> listaAssinatura) {
        this.listaAssinatura = listaAssinatura;
    }

    public List<Assinatura> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Assinatura> listaDaBusca) {
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

    public Membros getMembro() {
        return membro;
    }

    public void setMembro(Membros membro) {
        this.membro = membro;
    }

    public Edicoes getEdicoes() {
        return edicoes;
    }

    public void setEdicoes(Edicoes edicoes) {
        this.edicoes = edicoes;
    }

    public List<Edicoes> getListaEdicoes() {
        return listaEdicoes;
    }

    public void setListaEdicoes(List<Edicoes> listaEdicoes) {
        this.listaEdicoes = listaEdicoes;
    }

    public List<Membros> getListaMembros() {
        return listaMembros;
    }

    public void setListaMembros(List<Membros> listaMembros) {
        this.listaMembros = listaMembros;
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


}
