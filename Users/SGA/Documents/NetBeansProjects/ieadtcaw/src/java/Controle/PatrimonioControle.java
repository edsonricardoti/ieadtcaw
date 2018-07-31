/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import DAO.ClassecontabilDAO;
import javax.inject.Named;

import DAO.PatrimonioDAO;
import Modelo.Classecontabil;
import Modelo.Patrimonio;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;

import javax.faces.context.FacesContext;
import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.Conversation;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Edson Ricardo
 */
@ViewAccessScoped
@Named
public class PatrimonioControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Patrimonio patrimonio;
    private PatrimonioDAO dao;
    private Classecontabil classeContabil;
    private ClassecontabilDAO cdao;
    private Patrimonio patrimonioSelecionado;
    private List<Patrimonio> listaPatrimonio;
    private List<Patrimonio> listaDaBusca;
    private Boolean isRederiza = false;
    private String Nome;
    private Integer mes;
    private Integer ano;

    @Inject
    private Conversation conversation;

    public PatrimonioControle() {
        patrimonio = new Patrimonio();
        dao = new PatrimonioDAO();
        patrimonioSelecionado = new Patrimonio();

    }

    @PostConstruct
    public void init() {
        patrimonio = new Patrimonio();
        dao = new PatrimonioDAO();
        patrimonioSelecionado = new Patrimonio();
        listaPatrimonio = dao.selectAll();

    }

    public String fechar() throws IOException {
        this.conversation.close();
        FacesContext.getCurrentInstance().getExternalContext().redirect("../../inicio.xhtml?faces-redirect=true");
        return "";
    }


    public List<Patrimonio> listaTodos() {
        listaPatrimonio = dao.selectAll();
        return listaPatrimonio;
    }

    public String PegaDia(Patrimonio patrimonio) {
        Date data = patrimonio.getDataaquisicao();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        //int mes = calendario.get(Calendar.MONTH);
        String meudia = Integer.toString(dia);

        return meudia;
    }

    public String pegaTipo(int id) {
        classeContabil = new Classecontabil();
        cdao = new ClassecontabilDAO();
        classeContabil = cdao.buscarPorId(id);
        return classeContabil.getTipo();
    }


    public void limpaFormulario() {

        patrimonio = new Patrimonio();
        patrimonioSelecionado = new Patrimonio();
        listaDaBusca = null;
        listaPatrimonio = null;
        dao = null;
        listaDaBusca = null;

    }

    public void geraInvetario(int ano) {
        if (ano > 0) {
            listaPatrimonio = dao.geraInventario(ano);
        } else {
            listaPatrimonio = dao.selectAll();
        }
    }

    @PreDestroy
    public void destroi() {
        patrimonio = new Patrimonio();
        patrimonioSelecionado = new Patrimonio();
        listaDaBusca = null;
        listaPatrimonio = null;
        dao = null;
        listaDaBusca = null;
    }

    public void buscarID(int id) {
        System.out.println("Entrou na busca por ID=" + id);
        patrimonioSelecionado = dao.buscarPorId(id);
    }


    public void buscarPorDescricao(String descricao) {
        List<Patrimonio> lista;
        lista = dao.buscarPorNomeLista(descricao);
        listaDaBusca = lista;
        patrimonio = new Patrimonio();

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

    public String getRealPath() {
        ExternalContext externalContext
                = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response
                = (HttpServletResponse) externalContext.getResponse();

        FacesContext aFacesContext = FacesContext.getCurrentInstance();
        ServletContext context
                = (ServletContext) aFacesContext.getExternalContext().getContext();

        return context.getRealPath("/");
    }

    public void processFileUploadDigital(FileUploadEvent event) throws IOException {
        try {
            UploadedFile arq = event.getFile();
            InputStream in = new BufferedInputStream(arq.getInputstream());
            String caminho = "C:\\apps"; //getRealPath();
            Random gerador = new Random();
            String arquivo = "BEM_" + gerador.nextInt((10000 - 1) + 1) + 1;
            String extensao = arq.getFileName();
            String t = extensao.substring(extensao.length() - 3);
            arquivo = arquivo + "." + t;
            File file = new File(caminho + "/arquivos/" + arquivo);
            patrimonio.setFoto(arquivo);
            if (patrimonioSelecionado != null) {
                patrimonioSelecionado.setFoto(arquivo);
            }

            try (FileOutputStream fout = new FileOutputStream(file)) {
                while (in.available() != 0) {
                    fout.write(in.read());
                }
            }
            FacesMessage msg = new FacesMessage("O Arquivo ", file.getName()
                    + " foi salvo.");
            getCurrentInstance().addMessage("msg", msg);
        } catch (IOException ex) {
        }
    }

    public void insert() {
        Date data = new Date();
        if (dao.insert(patrimonio)) {
            limpaFormulario();
            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void maisum() throws IOException {

        if (dao.insert(patrimonio)) {
            patrimonio = new Patrimonio();
            addInfoMessage("Dados salvo com sucesso!");
            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastro.xhtml");

        } else {
            addErrorMessage("Erro ao salvar os dados! ");
        }
    }

    public void delete(Patrimonio patrimonios) {
        if (dao.delete(patrimonios)) {
            addInfoMessage("Patrimonio excluido!");
        } else {
            addInfoMessage("Selecione um Patrimonio!");
        }

    }

    public void update() {

        if (dao.update(patrimonioSelecionado)) {
            addInfoMessage("Patrimonio alterado com sucesso!");
        } else {
            addInfoMessage("Patrimonio não alterado");
        }

    }

    public List<Patrimonio> getListaPatrimonio() {
        return listaPatrimonio;
    }

    public void setListaPatrimonio(List<Patrimonio> listaPatrimonio) {
        this.listaPatrimonio = listaPatrimonio;
    }


    public Patrimonio getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(Patrimonio patrimonio) {
        this.patrimonio = patrimonio;
    }

    public Patrimonio getPatrimonioSelecionado() {
        return patrimonioSelecionado;
    }

    public void setPatrimonioSelecionado(Patrimonio patrimonioSelecionado) {
        this.patrimonioSelecionado = patrimonioSelecionado;
    }

    public List<Patrimonio> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Patrimonio> listaDaBusca) {
        this.listaDaBusca = listaDaBusca;
    }

    public Boolean getIsRederiza() {
        return isRederiza;
    }

    public void setIsRederiza(Boolean isRederiza) {
        this.isRederiza = isRederiza;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

}
