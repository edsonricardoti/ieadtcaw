/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import DAO.DiasdaescalaDAO;
import javax.inject.Named;

import Modelo.Escala;
import DAO.EscalaDAO;
import Modelo.Diasdaescala;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ConversationScoped

/**
 *
 * @author Edson Ricardo
 */
@Named
public class EscalaControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Escala escala;
    private EscalaDAO dao;
    private Escala escalaSelecionado;
    private List<Escala> listaEscala;
    private List<Escala> listaDaBusca;
    private List<Diasdaescala> listaFilhos;
    private DiasdaescalaDAO fdao;
    private Boolean isRederiza = false;
    private Integer mes;
    private Date dataini;
    private Date datafim;
    private String periodo;
    private Boolean emtodos;
    private Integer tipo;
    private Integer idAuxiliar;

    @Inject
    private Conversation conversation;

    public EscalaControle() {
        escala = new Escala();
        dao = new EscalaDAO();
        isRederiza = false;
        //escalaSelecionado = new Escala();
    }

    @PostConstruct
    public void init() {
        escala = new Escala();
        dao = new EscalaDAO();
        isRederiza = false;
        //escalaSelecionado = new Escala();

    }

    public void beginConversation() {
        if (conversation.isTransient()) {
            conversation.setTimeout(1800000L);
            conversation.begin();
        }
    }


    public void endConversation() {
        System.out.println("Finalizou conversacao...");
        if (!conversation.isTransient()) {
            conversation.end();
        }

    }

    public String fechar() throws IOException {
        endConversation();
        FacesContext.getCurrentInstance().getExternalContext().redirect("../../inicio.xhtml?faces-redirect=true");
        return "";
    }

    @PreDestroy
    public void destroi() {
        escala = null;
        escalaSelecionado = null;
        listaDaBusca = null;
        dao = null;
        listaEscala = null;
        listaFilhos = null;
        dao = null;
    }

    public void limpaFormulario() {
        escala = new Escala();
        escalaSelecionado = new Escala();
        listaDaBusca = null;
        dao = null;
        listaEscala = null;
        listaFilhos = null;
        dao = null;
    }

    public void buscarLista(int mes) {
        List<Escala> lista;
        lista = dao.buscarPorMesSemanaLista(mes);
        listaDaBusca = lista;
        escala = new Escala();
        escalaSelecionado = new Escala();

    }

    public String periodoRel(Date dataini, Date datafim) {
        DateFormat dd = new SimpleDateFormat("dd/MM/yyyy");
        String texto = "";
        if (dataini != null) {
            texto = dd.format(dataini) + " à " + dd.format(datafim);
        }
        return texto;
    }
//rotinas para alteracao do auxiliar em todas as escalas

    public void marcaTipo(int tipos, int idauxiliares) {
        tipo = tipos;
        idAuxiliar = idauxiliares;

    }

    public void alterarEmTodas() {
        listaEscala = dao.selectAll();
        for (Escala esca : listaEscala) {
            if (tipo == 1) {
                esca.setEscalaPortao(idAuxiliar);
            }
            if (tipo == 2) {
                esca.setEscalaPorta(idAuxiliar);
            }
            if (tipo == 3) {
                esca.setEscalaGaleria(idAuxiliar);
            }
            if (tipo == 4) {
                esca.setEscalaDentro(idAuxiliar);
            }
            if (tipo == 5) {
                esca.setEscalaCorredor(idAuxiliar);
            }
            if (tipo == 6) {
                esca.setEscalaOficial1(idAuxiliar);
            }
            if (tipo == 7) {
                esca.setEscalaOficial2(idAuxiliar);
            }
            if (tipo == 8) {
                esca.setEscalaOficial3(idAuxiliar);
            }
            if (tipo == 9) {
                esca.setEscalaOficial4(idAuxiliar);
            }
            if (tipo == 10) {
                esca.setSecretario1(idAuxiliar);
            }
            if (tipo == 11) {
                esca.setSecretario2(idAuxiliar);
            }

            if (dao.update(esca)) {
                System.out.println("Feito a troca...");
            }

        }
    }
    //fim das rotinas de alteracao do auxiliar

    public void PegaFilho(Escala escala) {
        fdao = new DiasdaescalaDAO();
        listaFilhos = fdao.selectAll(escala.getIdescala());
    }

    public void buscar(Date data) {
        escalaSelecionado = dao.buscarPorData(data);

    }

    public void buscarPorID(int idescala) {
        escalaSelecionado = dao.buscarPorID(idescala);
    }

    public void insert() {
        escalaSelecionado = new Escala();
        escalaSelecionado = escala;
        if (dao.insert(escala)) {
            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void delete(Escala escalas) {
        if (dao.delete(escalas)) {
            addInfoMessage("Escala excluida!");
        } else {
            addInfoMessage("Selecione uma Escala!");
        }

    }

    public void update() {

        if (dao.update(escalaSelecionado)) {
            addInfoMessage("Escala alterada com sucesso!");
        } else {
            addInfoMessage("Escala não alterada");
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
            String arquivo = "ESCALA" + gerador.nextInt((10000 - 1) + 1) + 1;
            String extensao = arq.getFileName();
            String t = extensao.substring(extensao.length() - 3);
            arquivo = arquivo + "." + t;
            File file = new File(caminho + "/arquivos/" + arquivo);
            escala.setEscalaArquivo(arquivo);
            if (escalaSelecionado != null) {
                escalaSelecionado.setEscalaArquivo(arq.getFileName());
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

    public Escala getEscala() {
        return escala;
    }

    public void setEscala(Escala escala) {
        this.escala = escala;
    }

    public Escala getEscalaSelecionado() {
        return escalaSelecionado;
    }

    public void setEscalaSelecionado(Escala escalaSelecionado) {
        this.escalaSelecionado = escalaSelecionado;
    }

    public List<Escala> getListaEscala() {
        return listaEscala;
    }

    public void setListaEscala(List<Escala> listaEscala) {
        this.listaEscala = listaEscala;
    }

    public List<Escala> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Escala> listaDaBusca) {
        this.listaDaBusca = listaDaBusca;
    }

    public Boolean getIsRederiza() {
        return isRederiza;
    }

    public void setIsRederiza(Boolean isRederiza) {
        this.isRederiza = isRederiza;
    }

    public List<Diasdaescala> getListaFilhos() {
        return listaFilhos;
    }

    public void setListaFilhos(List<Diasdaescala> listaFilhos) {
        this.listaFilhos = listaFilhos;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
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

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public Boolean getEmtodos() {
        return emtodos;
    }

    public void setEmtodos(Boolean emtodos) {
        this.emtodos = emtodos;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getIdAuxiliar() {
        return idAuxiliar;
    }

    public void setIdAuxiliar(Integer idAuxiliar) {
        this.idAuxiliar = idAuxiliar;
    }

}
