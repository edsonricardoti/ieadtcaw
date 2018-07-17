/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import Modelo.Usuarios;
import DAO.UsuarioDAO;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@SessionScoped

/**
 *
 * @author Edson Ricardo
 */
@Named
public class UsuarioControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Usuarios usuario = new Usuarios();
    private final UsuarioDAO dao = new UsuarioDAO();
    private Usuarios usuarioSelecionado = new Usuarios();
    private Boolean usuarioLogado;

    @PostConstruct
    public void init() {
        usuarioSelecionado = new Usuarios();
        usuario = new Usuarios();
    }

    public UsuarioControle() {
        usuario = new Usuarios();
    }

    public void limpaFormulario() {
        usuarioSelecionado = new Usuarios();
        usuario.setSenhaUsuarios("");

    }

    public void buscarID(int id) {
        usuarioSelecionado = dao.buscarPorID(id);
        // membro = membroSelecionado;

    }

    public String logOFF() {
        usuario = null;
        usuarioSelecionado = null;
        usuarioLogado = false;
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("usuario");
        session.invalidate();

        return "index.xhtml?faces-redirect=true";

    }

    public String logando() {

        Usuarios u = new Usuarios();
        u = dao.verificar(usuario);

        if (u != null) {
            usuario = new Usuarios();
            usuario = u;
            usuarioLogado = true;
            HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            sessao.setAttribute("usuario", u);

            //usuarioSelecionado = u;
            System.out.println("Entrou no usuario = " + usuario.getNomeUsuarios());

            return "inicio.xhtml?faces-redirect=true";
        } else {
            usuarioLogado = false;
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            addErrorMessage("Usuário ou Senha incorreto(s)");
            return "";
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

        if (dao.update(usuario)) {
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

    public Boolean getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Boolean usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

}
