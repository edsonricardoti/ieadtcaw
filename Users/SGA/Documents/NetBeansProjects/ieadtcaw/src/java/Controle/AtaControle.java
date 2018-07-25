/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.inject.Named;

import Modelo.Ata;
import DAO.AtaDAO;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
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
public class AtaControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Ata ata;
    private AtaDAO dao;
    private Ata ataSelecionado;
    private List<Ata> listaAta;
    private List<Ata> listaDaBusca;
    private Boolean isRederiza = false;
    private UploadedFile uploadedFile;

    public AtaControle() {
        ata = new Ata();
        dao = new AtaDAO();
        ataSelecionado = new Ata();
    }

    public void limpaFormulario() {
        dao = new AtaDAO();
        ataSelecionado = new Ata();
        listaAta = null;
        listaDaBusca = null;
        isRederiza = null;
        uploadedFile = null;
    }

    @PreDestroy
    public void destroi() {
        ata = null;
        dao = null;
        ataSelecionado = null;
        listaAta = null;
        listaDaBusca = null;
        isRederiza = null;
        uploadedFile = null;
    }

    @PostConstruct
    public void init() {
        ata = new Ata();
        dao = new AtaDAO();
        ataSelecionado = new Ata();

    }

    public void buscarLista(Date data) {

        System.out.println("" + data);
        List<Ata> lista = null;
        try {
            lista = (List<Ata>) dao.buscarPorData(data);
        } catch (ParseException ex) {
            Logger.getLogger(AtaControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaDaBusca = lista;
    }

    public void buscarPorId(int id) {
        ataSelecionado = dao.buscarPorID(id);

    }

    public void insert() {

        if (dao.insert(ata)) {

            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void delete(Ata atas) {
        if (dao.delete(atas)) {
            addInfoMessage("Ata excluida!");
        } else {
            addInfoMessage("Selecione uma Ata!");
        }

    }

    public void update() {

        if (dao.update(ataSelecionado)) {
            addInfoMessage("Ata alterada com sucesso!");
        } else {
            addInfoMessage("Ata não alterada");
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
            String arquivo = "ATA" + gerador.nextInt((10000 - 1) + 1) + 1;;
            String extensao = arq.getFileName();
            String t = extensao.substring(extensao.length() - 3);
            arquivo = arquivo + "." + t;
            File file = new File(caminho + "/arquivos/" + arquivo);
            ata.setAtaArquivo(arquivo);
            if (ataSelecionado != null) {
                ataSelecionado.setAtaArquivo(arquivo);
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

    public Ata getAta() {
        return ata;
    }

    public void setAta(Ata ata) {
        this.ata = ata;
    }

    public Ata getAtaSelecionado() {
        return ataSelecionado;
    }

    public void setAtaSelecionado(Ata ataSelecionado) {
        this.ataSelecionado = ataSelecionado;
    }

    public List<Ata> getListaAta() {
        return listaAta;
    }

    public void setListaAta(List<Ata> listaAta) {
        this.listaAta = listaAta;
    }

    public List<Ata> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Ata> listaDaBusca) {
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

}
