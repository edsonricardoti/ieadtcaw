/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.inject.Named;

import Modelo.Classecontabil;
import DAO.ClassecontabilDAO;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.Conversation;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;


@ViewAccessScoped

/**
 *
 * @author Edson Ricardo
 */
@Named
public class ClassecontabilControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Classecontabil classecontabil;
    private ClassecontabilDAO dao;
    private Classecontabil classecontabilSelecionado;
    private List<Classecontabil> listaClassecontabil;
    private List<Classecontabil> listaDaBusca;
    private Boolean isRederiza = false;

    @Inject
    private Conversation conversation;

    public ClassecontabilControle() {
        classecontabil = new Classecontabil();
        dao = new ClassecontabilDAO();
        classecontabilSelecionado = new Classecontabil();
    }

    @PostConstruct
    public void init() {
        classecontabil = new Classecontabil();
        dao = new ClassecontabilDAO();
        classecontabilSelecionado = new Classecontabil();
        listaDaBusca = dao.selectAll();
        listaClassecontabil = dao.selectAll();

    }

    @PreDestroy
    public void destroi() {
        classecontabil = null;
        classecontabilSelecionado = null;
        listaClassecontabil = null;
        listaDaBusca = null;
        dao = null;
        this.conversation.close();
    }

    public void limpaFormulario() {
        classecontabil = new Classecontabil();
        classecontabilSelecionado = new Classecontabil();
        listaClassecontabil = null;
        listaDaBusca = null;
        dao = null;

    }

    public void buscar(int id) {
        classecontabilSelecionado = dao.buscarPorId(id);
    }

    public void insert() {

        if (dao.insert(classecontabil)) {

            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void maisum() throws IOException {

        if (dao.insert(classecontabil)) {
            classecontabil = new Classecontabil();
            addInfoMessage("Dados salvo com sucesso!");
            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastro.xhtml");

        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void delete(Classecontabil classecontabils) {
        if (dao.delete(classecontabils)) {
            addInfoMessage("Igreja excluida!");
        } else {
            addInfoMessage("Selecione uma Igreja!");
        }

    }

    public void update() {

        if (dao.update(classecontabilSelecionado)) {
            addInfoMessage("Igreja alterada com sucesso!");
        } else {
            addInfoMessage("Igreja não alterada");
        }

    }

    public Boolean getIsRederiza() {
        return isRederiza;
    }

    public void setIsRederiza(Boolean isRederiza) {
        this.isRederiza = isRederiza;
    }

    public Classecontabil getClassecontabil() {
        return classecontabil;
    }

    public void setClassecontabil(Classecontabil classecontabil) {
        this.classecontabil = classecontabil;
    }

    public Classecontabil getClassecontabilSelecionado() {
        return classecontabilSelecionado;
    }

    public void setClassecontabilSelecionado(Classecontabil classecontabilSelecionado) {
        this.classecontabilSelecionado = classecontabilSelecionado;
    }

    public List<Classecontabil> getListaClassecontabil() {
        return listaClassecontabil;
    }

    public void setListaClassecontabil(List<Classecontabil> listaClassecontabil) {
        this.listaClassecontabil = listaClassecontabil;
    }

    public List<Classecontabil> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Classecontabil> listaDaBusca) {
        this.listaDaBusca = listaDaBusca;
    }

}
