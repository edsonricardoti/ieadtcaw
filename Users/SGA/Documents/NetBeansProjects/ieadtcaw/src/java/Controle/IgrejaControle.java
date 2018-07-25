/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.inject.Named;

import Modelo.Igrejas;
import DAO.IgrejasDAO;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;

@SessionScoped

/**
 *
 * @author Edson Ricardo
 */
@Named
public class IgrejaControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Igrejas igreja;
    private IgrejasDAO dao;
    private Igrejas igrejaSelecionado;
    private List<Igrejas> listaIgrejas;
    private List<Igrejas> listaDaBusca;
    private Boolean isRederiza = false;

    public IgrejaControle() {
        igreja = new Igrejas();
        dao = new IgrejasDAO();
        igrejaSelecionado = new Igrejas();
    }

    @PostConstruct
    public void init() {
        igreja = new Igrejas();
        dao = new IgrejasDAO();
        igrejaSelecionado = new Igrejas();

    }

    @PreDestroy
    public void destroi() {
        igreja = null;
        igrejaSelecionado = null;
        listaIgrejas = null;
        listaDaBusca = null;
        dao = null;

    }

    public void limpaFormulario() {
        igreja = new Igrejas();
        igrejaSelecionado = new Igrejas();
        listaIgrejas = null;
        listaDaBusca = null;
        dao = null;

    }

    public void buscarLista(String nome) {
        List<Igrejas> lista;
        lista = dao.buscarPorNomeLista(nome);
        listaDaBusca = lista;
        igreja = new Igrejas();

    }

    public void buscar(int id) {

        igrejaSelecionado = dao.buscarPorId(id);

    }

    public void insert() {

        if (dao.insert(igreja)) {

            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void delete(Igrejas igrejas) {
        if (dao.delete(igrejas)) {
            addInfoMessage("Igreja excluida!");
        } else {
            addInfoMessage("Selecione uma Igreja!");
        }

    }

    public void update() {

        if (dao.update(igrejaSelecionado)) {
            addInfoMessage("Igreja alterada com sucesso!");
        } else {
            addInfoMessage("Igreja não alterada");
        }

    }

    public Igrejas getIgreja() {
        return igreja;
    }

    public void setIgreja(Igrejas igreja) {
        this.igreja = igreja;
    }

    public Igrejas getIgrejaSelecionado() {
        return igrejaSelecionado;
    }

    public void setIgrejaSelecionado(Igrejas igrejaSelecionado) {
        this.igrejaSelecionado = igrejaSelecionado;
    }

    public List<Igrejas> getListaIgrejas() {
        return listaIgrejas;
    }

    public void setListaIgrejas(List<Igrejas> listaIgrejas) {
        this.listaIgrejas = listaIgrejas;
    }

    public List<Igrejas> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Igrejas> listaDaBusca) {
        this.listaDaBusca = listaDaBusca;
    }

    public Boolean getIsRederiza() {
        return isRederiza;
    }

    public void setIsRederiza(Boolean isRederiza) {
        this.isRederiza = isRederiza;
    }


}
