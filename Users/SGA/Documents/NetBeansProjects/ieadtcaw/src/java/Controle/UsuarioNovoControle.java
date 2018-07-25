/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import Modelo.Usuarios;
import DAO.UsuarioDAO;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SessionScoped

/**
 *
 * @author Edson Ricardo
 */
@Named
public class UsuarioNovoControle implements Serializable {

    private static final long serialVersionUID = 1L;

    Usuarios usuario = new Usuarios();
    UsuarioDAO dao = new UsuarioDAO();
    Usuarios usuarioSelecionado = new Usuarios();
    private List<Usuarios> listaDaBusca;

    @PostConstruct
    public void init() {
        usuarioSelecionado = new Usuarios();
        listaDaBusca = dao.selectAll();
    }

    public UsuarioNovoControle() {
        usuario = new Usuarios();
    }

    public void logOFF() {
        usuario = new Usuarios();
        usuarioSelecionado = new Usuarios();
        usuario = null;
    }

    @PreDestroy
    public void destroi() {
        usuario = null;
        usuarioSelecionado = null;
        listaDaBusca = null;
        dao = null;
    }

    public void limpaFormulario() {
        usuario = new Usuarios();
        usuarioSelecionado = new Usuarios();
        listaDaBusca = null;
        dao = null;
    }

    public void buscarLista(String nome) {
        List<Usuarios> lista;
        lista = dao.buscarPorNomeLista(nome);
        listaDaBusca = lista;
        usuario = new Usuarios();

    }

    public void buscar(String nome) {
        System.out.println("Entrou na busca nome=" + nome);
        usuarioSelecionado = dao.buscarPorNome(nome);
        // membro = membroSelecionado;

    }

    public void buscarID(int id) {
        usuarioSelecionado = dao.buscarPorID(id);
        // membro = membroSelecionado;

    }

    public String logando() {

        Usuarios u = new Usuarios();
        u = dao.verificar(usuario);

        if (u != null) {
            usuario = new Usuarios();
            usuario = u;
            usuarioSelecionado = u;
            System.out.println("Entrou no usuario = " + usuario.getNomeUsuarios());

            return "inicio.xhtml";
        } else {
            addErrorMessage("Usuário ou Senha incorreto(s)");
            return "index.xhtml";
        }

    }

    public void insert() {

        if (dao.insert(usuarioSelecionado)) {

            addInfoMessage("Usuário salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar o Usuário!");
        }
    }

    public void delete(Usuarios usuario) {
        if (dao.delete(usuario)) {
            addInfoMessage("Usuário excluido!");
        } else {
            addInfoMessage("Selecione um usuário!");
        }

    }

    public void update() {

        if (dao.update(usuarioSelecionado)) {
            addInfoMessage("Usuário alterado com sucesso!");
        } else {
            addInfoMessage("Usuário não alterado");
        }

    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Usuarios getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuarios usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public List<Usuarios> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Usuarios> listaDaBusca) {
        this.listaDaBusca = listaDaBusca;
    }

}
