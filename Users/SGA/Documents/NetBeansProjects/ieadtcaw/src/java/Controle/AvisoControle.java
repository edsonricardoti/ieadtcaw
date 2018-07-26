/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import DAO.AvisoDAO;
import Modelo.Avisos;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.Conversation;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.UploadedFile;

@ViewAccessScoped

/**
 *
 * @author Edson Ricardo
 */
@Named
public class AvisoControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private ScheduleModel eventModel;

    private ScheduleModel lazyEventModel;

    private ScheduleEvent event = new DefaultScheduleEvent();

    private Avisos aviso;
    private AvisoDAO dao;
    private Avisos avisoSelecionado;
    private List<Avisos> listaAviso;
    private List<Avisos> listaAvisoHoje;
    private UploadedFile uploadedFile;
    private Integer qtd;
    private Date dataini, datafim;
    private List<Avisos> listaSelecionada;

    @Inject
    private Conversation conversation;

    public AvisoControle() {
        dao = new AvisoDAO();
        aviso = new Avisos();
        avisoSelecionado = new Avisos();
        //listaAviso = dao.selectAll();
    }

    @PostConstruct
    public void Init() {
        event = new DefaultScheduleEvent();
        eventModel = new DefaultScheduleModel();
        eventModel.clear();
        listaAvisoHoje = null;
        avisoSelecionado = new Avisos();
        eventModel = new DefaultScheduleModel();
        dao = new AvisoDAO();
        aviso = new Avisos();
        listaAviso = dao.selectAll();
        for (int i = 0; i <= listaAviso.size() - 1; i++) {
            eventModel.addEvent(new DefaultScheduleEvent(listaAviso.get(i).getTitulo(), listaAviso.get(i).getDataAviso(), listaAviso.get(i).getDataFinal(), listaAviso.get(i).getIdAviso()));
        }

    }


    public String fechar() throws IOException {
        this.conversation.close();
        FacesContext.getCurrentInstance().getExternalContext().redirect("../../inicio.xhtml?faces-redirect=true");
        return "";
    }

    public void pegaSemana(Date dataini, Date datafim) {
        listaAviso = dao.selectSemana(dataini, datafim);
    }

    //acoes com a agenda
    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public void adicionaEvento(ActionEvent actionEvent) {
        if (event.getId() == null) {
            eventModel.addEvent(event);

            // aviso.setDataAviso(event.getStartDate());
            // aviso.setDataFinal(event.getEndDate());
            // aviso.setTitulo(event.getTitle());
            // aviso.setDescriAviso(event.getDescription());
            // dao.insert(aviso);
        } else {
            eventModel.updateEvent(event);
            // aviso.setDataAviso(event.getStartDate());
            // aviso.setDataFinal(event.getEndDate());
            // aviso.setTitulo(event.getTitle());
            // aviso.setDescriAviso(event.getDescription());
            // dao.update(aviso);

        }

        event = new DefaultScheduleEvent();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        avisoSelecionado = dao.buscarPorId((Integer) event.getData());
        System.out.println("ID=" + (Integer) event.getData());

    }

    public void onDateSelect(SelectEvent selectEvent) {
        avisoSelecionado = new Avisos();
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());

    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento movido", "Qtd. Dias:" + event.getDayDelta() + ", Minuto:" + event.getMinuteDelta());
        avisoSelecionado = new Avisos();
        avisoSelecionado = dao.buscarPorId((Integer) event.getScheduleEvent().getData());
        Calendar c = Calendar.getInstance();
        c.setTime(avisoSelecionado.getDataAviso());
        c.add(Calendar.DATE, event.getDayDelta());
        avisoSelecionado.setDataAviso(c.getTime());
        c.setTime(avisoSelecionado.getDataFinal());
        c.add(Calendar.DATE, event.getDayDelta());
        avisoSelecionado.setDataFinal(c.getTime());
        update();

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void atualizaAgenda() {
        // event = new DefaultScheduleEvent();
        // eventModel = new DefaultScheduleModel();
        eventModel.clear();
        // listaAvisoHoje = null;
        // avisoSelecionado = new Avisos();
        // eventModel = new DefaultScheduleModel();
        // dao = new AvisoDAO();
        // aviso = new Avisos();
        listaAviso = dao.selectAll();
        for (int i = 0; i <= listaAviso.size() - 1; i++) {
            eventModel.addEvent(new DefaultScheduleEvent(listaAviso.get(i).getTitulo(), listaAviso.get(i).getDataAviso(), listaAviso.get(i).getDataFinal(), listaAviso.get(i).getIdAviso()));
        }
    }

    //demais metodos
    public void atualizaTab(int a) {
        listaAviso = null;
        listaAvisoHoje = null;
        dao = new AvisoDAO();
        listaAviso = null; //dao.selectAll();

    }

    public void leuAviso(int l) {
        avisoSelecionado = dao.buscarPorId(l);
        avisoSelecionado.setLido(1);
        dao.update(avisoSelecionado);
    }

    public void pegaPraAlterar(int l) {
        avisoSelecionado = dao.buscarPorId(l);
    }

    public void limpaCampo() {
        event = new DefaultScheduleEvent();
        eventModel = new DefaultScheduleModel();
        eventModel.clear();
        avisoSelecionado = new Avisos();
        eventModel = new DefaultScheduleModel();
        dao = new AvisoDAO();
        aviso = new Avisos();
        listaAviso = dao.selectAll();
        for (int i = 0; i <= listaAviso.size() - 1; i++) {
            eventModel.addEvent(new DefaultScheduleEvent(listaAviso.get(i).getTitulo(), listaAviso.get(i).getDataAviso(), listaAviso.get(i).getDataFinal(), listaAviso.get(i).getIdAviso()));
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
            String arquivo = "AVISO" + gerador.nextInt((10000 - 1) + 1) + 1;
            String extensao = arq.getFileName();
            String t = extensao.substring(extensao.length() - 3);
            arquivo = arquivo + "." + t;
            File file = new File(caminho + "/arquivos/" + arquivo);
            aviso.setArquivoAviso(arquivo);
            if (avisoSelecionado != null) {
                avisoSelecionado.setArquivoAviso(arquivo);
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

    public Date replicarData(Date data, int qtd) {
        int vai = 7 * qtd;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        calendar.add(GregorianCalendar.DAY_OF_WEEK, vai);
        return calendar.getTime();

    }

    public void repetirAgenda(int qtdsemanas) {

        System.out.println("Passado qtd=" + qtdsemanas);
        Avisos av = new Avisos();

        for (Avisos av1 : listaSelecionada) {

            av = dao.buscarPorId(av1.getIdAviso());

            System.out.println("Aviso =" + av.getTitulo());
            //Date d1 = replicarData(av.getDataAviso());
            //Date d2 = replicarData(av.getDataFinal());
            //av.setDataAviso(d1);
            //av.setDataFinal(d2);
            av.setLido(2);
            if (dao.update(av)) {
                // addInfoMessage("Aviso/Evento salvo com sucesso!");
            } else {
                // addErrorMessage("Erro ao salvar o aviso/evento!");
            }

        }
        repetePadrao();
        addInfoMessage("Aviso/Evento repetidos com sucesso!");
    }

    public void repetePadrao() {

        List<Avisos> lista = dao.marcadosPreplicar();
        Avisos avis = new Avisos();
        int qtds = qtd + 1;
        for (int x = 1; x < qtds; x++) {
            for (Avisos av : lista) {
                //av = dao.buscarPorId(avis.getIdAviso());
                Date d1 = replicarData(av.getDataAviso(), x);
                Date d2 = replicarData(av.getDataFinal(), x);
                try {
                    av.setDataAviso(d1);
                    av.setDataFinal(d2);
                    av.setLido(0);
                } catch (Exception e) {
                    System.out.println("Erro no passo:" + e);
                }
                if (dao.insert(av)) {
                    // System.out.println("Feito...." + x + " Vez!");
                }

            }
            lista = dao.marcadosPreplicar();

        }
        lista = dao.marcadosPreplicar();
        for (Avisos volta : lista) {
            volta.setLido(0);
            if (dao.update(volta)) {
                //nada
            }
        }
    }

    @PreDestroy
    public void destroy() {

     event = new DefaultScheduleEvent();
     eventModel = new DefaultScheduleModel();
     eventModel.clear();
     avisoSelecionado = new Avisos();
     eventModel = new DefaultScheduleModel();
     dao = new AvisoDAO();
     aviso = new Avisos();
     listaAviso = null;

    }

    public void insert() {
        if (event.getId() == null) {
            eventModel.addEvent(event);
            aviso = new Avisos();
            System.out.println("Teste=" + event.getStartDate());
            aviso.setDataAviso(event.getStartDate());
            aviso.setDataFinal(event.getEndDate());
            aviso.setTitulo(event.getTitle());
            aviso.setDescriAviso(avisoSelecionado.getDescriAviso());
            aviso.setArquivoAviso(avisoSelecionado.getArquivoAviso());

            if (dao.insert(aviso)) {
                Init();
                addInfoMessage("Aviso/Evento salvo com sucesso!");
            } else {
                addErrorMessage("Erro ao salvar o aviso/evento!");
            }
        } else {

            aviso = new Avisos();
            System.out.println("Teste=" + event.getData());
            aviso = dao.buscarPorId((Integer) event.getData());
            aviso.setDataAviso(event.getStartDate());
            aviso.setDataFinal(event.getEndDate());
            aviso.setTitulo(event.getTitle());
            aviso.setDescriAviso(avisoSelecionado.getDescriAviso());
            aviso.setArquivoAviso(avisoSelecionado.getArquivoAviso());
            eventModel.updateEvent(event);

            if (dao.update(aviso)) {
                Init();
                addInfoMessage("Aviso/Evento alterado!");
            } else {
                //addErrorMessage("Foto não enviada");
            }

        }

    }

    public void delete(Avisos aviso) {
        if (dao.delete(aviso)) {

            addInfoMessage("Aviso excluido!");
        } else {
            addInfoMessage("Selecione um aviso!");
        }

    }

    public void deleteMarcados() {
        for (Avisos apaga : listaSelecionada) {
            if (dao.delete(apaga)) {
            }
        }
        addInfoMessage("Aviso/Eventos excluidos!");
    }

    public void updateFoto() {
        if (dao.insertFoto(avisoSelecionado)) {
            //addInfoMessage("Foto enviada");
        } else {
            //addErrorMessage("Foto não enviada");
        }

    }

    public void update() {
        avisoSelecionado.setLido(null);
        if (dao.update(avisoSelecionado)) {
            addInfoMessage("Aviso alterado com sucesso!");
        } else {
            addInfoMessage("Aviso não alterado");
        }

    }

    public List<Avisos> selectAll() {
        return dao.selectAll();

    }

    public Avisos getAviso() {
        return aviso;
    }

    public void setAviso(Avisos aviso) {
        this.aviso = aviso;
    }

    public Avisos getAvisoSelecionado() {
        return avisoSelecionado;
    }

    public void setAvisoSelecionado(Avisos avisoSelecionado) {
        this.avisoSelecionado = avisoSelecionado;
    }

    public List<Avisos> getListaAviso() {
        return listaAviso;
    }

    public void setListaAviso(List<Avisos> listaAviso) {
        this.listaAviso = listaAviso;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<Avisos> getListaAvisoHoje() {
        return listaAvisoHoje;
    }

    public void setListaAvisoHoje(List<Avisos> listaAvisoHoje) {
        this.listaAvisoHoje = listaAvisoHoje;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
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

    public List<Avisos> getListaSelecionada() {
        return listaSelecionada;
    }

    public void setListaSelecionada(List<Avisos> listaSelecionada) {
        this.listaSelecionada = listaSelecionada;
    }

}
