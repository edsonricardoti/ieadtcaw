/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.inject.Named;

import Modelo.Carta;
import DAO.CartaDAO;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
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
public class CartaControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Carta carta;
    private CartaDAO dao;
    private Carta cartaSelecionado;
    private List<Carta> listaCarta;
    private List<Carta> listaDaBusca;
    private Boolean isRederiza = false;
    private UploadedFile uploadedFile;

    public CartaControle() {
        carta = new Carta();
        dao = new CartaDAO();
        cartaSelecionado = new Carta();
    }

    @PostConstruct
    public void init() {
        carta = new Carta();
        dao = new CartaDAO();
        cartaSelecionado = new Carta();

    }

    @PreDestroy
    public void limpaFormulario() {
        carta = new Carta();
        cartaSelecionado = new Carta();
        listaDaBusca = null;
        dao = null;
        listaCarta = null;
    }

    public void buscarLista(String nome) {

        List<Carta> lista = null;
        try {
            lista = (List<Carta>) dao.buscarPorNome(nome);
        } catch (ParseException ex) {
            Logger.getLogger(CartaControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaDaBusca = lista;
    }

    public void buscarPorId(int id) {
        cartaSelecionado = dao.buscarPorID(id);

    }

    public void insert() {

        if (dao.insert(carta)) {

            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void delete(Carta atas) {
        if (dao.delete(atas)) {
            addInfoMessage("Apresentação de Criança excluida!");
        } else {
            addInfoMessage("Selecione uma Apresentação de Criança!");
        }

    }

    public void update() {

        if (dao.update(cartaSelecionado)) {
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
            String caminho = "C:\\apps"; //getRealPath();
            Random gerador = new Random();
            String arquivo = "CARTA_RECOM" + gerador.nextInt((10000 - 1) + 1) + 1;
            String extensao = arq.getFileName();
            String t = extensao.substring(extensao.length() - 3);
            arquivo = arquivo + "." + t;
            File file = new File(caminho + "/arquivos/" + arquivo);
            carta.setCartaArquivo(arquivo);
            if (cartaSelecionado != null) {
                cartaSelecionado.setCartaArquivo(arquivo);
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

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }

    public Carta getCartaSelecionado() {
        return cartaSelecionado;
    }

    public void setCartaSelecionado(Carta cartaSelecionado) {
        this.cartaSelecionado = cartaSelecionado;
    }

    public List<Carta> getListaCarta() {
        return listaCarta;
    }

    public void setListaCarta(List<Carta> listaCarta) {
        this.listaCarta = listaCarta;
    }

    public List<Carta> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Carta> listaDaBusca) {
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
