/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.inject.Named;

import Modelo.Veiculos;
import DAO.VeiculoDAO;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.Conversation;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;


@ViewAccessScoped

/**
 *
 * @author Edson Ricardo
 */
@Named
public class VeiculoControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Veiculos veiculo;
    private VeiculoDAO dao;
    private Veiculos veiculoSelecionado;
    private List<Veiculos> listaVeiculos;
    private List<Veiculos> listaDaBusca;
    private Boolean isRederiza = false;

    @Inject
    private Conversation conversation;

    public VeiculoControle() {
        veiculo = new Veiculos();
        dao = new VeiculoDAO();
        veiculoSelecionado = new Veiculos();
    }

    @PostConstruct
    public void init() {
        veiculo = new Veiculos();
        dao = new VeiculoDAO();
        veiculoSelecionado = new Veiculos();

    }

    @PreDestroy
    public void destroi() {
        veiculo = null;
        veiculoSelecionado = null;
        listaDaBusca = null;
        listaVeiculos = null;
        dao = null;
        this.conversation.close();
    }

    public void limpaFormulario() {
        veiculo = new Veiculos();
        veiculoSelecionado = new Veiculos();
        listaDaBusca = null;
        listaVeiculos = null;
        dao = null;
    }

    public void buscarLista(String placa) {
        List<Veiculos> lista;
        lista = dao.buscarPorPlacaLista(placa);
        listaDaBusca = lista;
        veiculo = new Veiculos();

    }

    public void buscar(String placa) {
        veiculoSelecionado = dao.buscarPorPlaca(placa);

    }

    public void insert() {

        if (dao.insert(veiculo)) {

            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void delete(Veiculos veiculos) {
        if (dao.delete(veiculos)) {
            addInfoMessage("Veiculo excluido!");
        } else {
            addInfoMessage("Selecione um Veiculo!");
        }

    }

    public void update() {

        if (dao.update(veiculoSelecionado)) {
            addInfoMessage("Veiculo alterado com sucesso!");
        } else {
            addInfoMessage("Veiculo não alterado");
        }

    }

    public Veiculos getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculos veiculo) {
        this.veiculo = veiculo;
    }

    public Veiculos getVeiculoSelecionado() {
        return veiculoSelecionado;
    }

    public void setVeiculoSelecionado(Veiculos veiculoSelecionado) {
        this.veiculoSelecionado = veiculoSelecionado;
    }

    public List<Veiculos> getListaVeiculos() {
        return listaVeiculos;
    }

    public void setListaVeiculos(List<Veiculos> listaVeiculos) {
        this.listaVeiculos = listaVeiculos;
    }

    public List<Veiculos> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Veiculos> listaDaBusca) {
        this.listaDaBusca = listaDaBusca;
    }

    public Boolean getIsRederiza() {
        return isRederiza;
    }

    public void setIsRederiza(Boolean isRederiza) {
        this.isRederiza = isRederiza;
    }


}
