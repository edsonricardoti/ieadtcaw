/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.inject.Named;

import Modelo.Criancas;
import DAO.CriancaDAO;
import DAO.MembrosDAO;
import Modelo.Membros;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@SessionScoped

/**
 *
 * @author Edson Ricardo
 */
@Named
public class CriancaControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Criancas crianca;
    private CriancaDAO dao;
    private Membros membro;
    private MembrosDAO mdao;
    private Criancas criancaSelecionado;
    private List<Criancas> listaCriancas;
    private List<Criancas> listaDaBusca;
    private Boolean isRederiza = false;
    private UploadedFile uploadedFile;
    private String nome;
    private String texto;
    private String nascido;
    private String pastor;
    private String dtapresentacao;

    public CriancaControle() {
        crianca = new Criancas();
        dao = new CriancaDAO();
        criancaSelecionado = new Criancas();
    }

    @PostConstruct
    public void init() {
        crianca = new Criancas();
        dao = new CriancaDAO();
        criancaSelecionado = new Criancas();

    }
    @PreDestroy
    public void limpaFormulario() {
        crianca = new Criancas();
        criancaSelecionado = new Criancas();
    listaDaBusca = null;
    dao = null;
    mdao = null;
    listaCriancas = null;
    }

    public void geraCertificado(Criancas crianca) {
        membro = new Membros();
        mdao = new MembrosDAO();
        membro = mdao.buscarPastor();
        if (membro != null) {
            pastor = "PB " + membro.getMembrosNome().toUpperCase();
        } else {
            addErrorMessage("Cadastre um Pastor!");
        }

        Date data = crianca.getCriancasDataNasc();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        nascido = sdf.format(data);
        Date data2 = crianca.getCriancasDataNasc();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        dtapresentacao = sdf2.format(data2);
        if (crianca.getCriancasSexo().equals("Masculino")) {
            texto = "Certificamos que " + crianca.getCriancasNome().toUpperCase() + ", Nascido no dia " + nascido + " filho amado e esperado de " + crianca.getCriancasPai().toUpperCase() + " e " + crianca.getCriancasMae().toUpperCase() + " foi apresentado ao Senhor Jesus Cristo conforme a Palavra de Deus em Lucas 2:22.";
        }
        if (crianca.getCriancasSexo().equals("Feminino")) {
            texto = "Certificamos que " + crianca.getCriancasNome().toUpperCase() + ", Nascida no dia " + nascido + " filha amada e esperada de " + crianca.getCriancasPai().toUpperCase() + " e " + crianca.getCriancasMae().toUpperCase() + " foi apresentada ao Senhor Jesus Cristo conforme a Palavra de Deus em Lucas 2:22.";
        }
    }

    public void buscarLista(String nome) {

        List<Criancas> lista = null;
        try {
            lista = (List<Criancas>) dao.buscarPorNome(nome);
        } catch (ParseException ex) {
            Logger.getLogger(CriancaControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaDaBusca = lista;
    }

    public void buscarListaDtiniDtfim(Date dtini, Date dtfim) {

        List<Criancas> lista = null;
        try {
            lista = (List<Criancas>) dao.buscarPorDtiniDtfim(dtini, dtfim);
        } catch (ParseException ex) {
            Logger.getLogger(CriancaControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaDaBusca = lista;
    }

    public void buscarPorId(int id) {
        criancaSelecionado = dao.buscarPorID(id);

    }

    public void insert() {

        if (dao.insert(crianca)) {

            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void delete(Criancas atas) {
        if (dao.delete(atas)) {
            addInfoMessage("Apresentação de Criança excluida!");
        } else {
            addInfoMessage("Selecione uma Apresentação de Criança!");
        }

    }

    public void update() {

        if (dao.update(criancaSelecionado)) {
            addInfoMessage("Apresentação de Criança alterada com sucesso!");
        } else {
            addInfoMessage("Apresentação de Criança não alterada");
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
            String caminho = "C:\\apps"; // getRealPath();
            Random gerador = new Random();
            String arquivo = "APRESENTACAO" + gerador.nextInt((10000 - 1) + 1) + 1;
            String extensao = arq.getFileName();
            String t = extensao.substring(extensao.length() - 3);
            arquivo = arquivo + "." + t;
            File file = new File(caminho + "/arquivos/" + arquivo);
            crianca.setCriancasTelefone(arquivo);
            if (criancaSelecionado != null) {
                criancaSelecionado.setCriancasTelefone(arquivo);
            }

            try (FileOutputStream fout = new FileOutputStream(file)) {
                while (in.available() != 0) {
                    fout.write(in.read());
                }
            }
            FacesMessage msg = new FacesMessage("O Telefone ", file.getName()
                    + " foi salvo.");
            getCurrentInstance().addMessage("msg", msg);
        } catch (IOException ex) {
        }
    }

    public Criancas getCrianca() {
        return crianca;
    }

    public void setCrianca(Criancas crianca) {
        this.crianca = crianca;
    }

    public Criancas getCriancaSelecionado() {
        return criancaSelecionado;
    }

    public void setCriancaSelecionado(Criancas criancaSelecionado) {
        this.criancaSelecionado = criancaSelecionado;
    }

    public List<Criancas> getListaCriancas() {
        return listaCriancas;
    }

    public void setListaCriancas(List<Criancas> listaCriancas) {
        this.listaCriancas = listaCriancas;
    }

    public List<Criancas> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Criancas> listaDaBusca) {
        this.listaDaBusca = listaDaBusca;
    }

    public Boolean getIsRederiza() {
        return isRederiza;
    }

    public void setIsRederiza(Boolean isRederiza) {
        this.isRederiza = isRederiza;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getNascido() {
        return nascido;
    }

    public void setNascido(String nascido) {
        this.nascido = nascido;
    }

    public String getPastor() {
        return pastor;
    }

    public void setPastor(String pastor) {
        this.pastor = pastor;
    }

    public String getDtapresentacao() {
        return dtapresentacao;
    }

    public void setDtapresentacao(String dtapresentacao) {
        this.dtapresentacao = dtapresentacao;
    }

}
