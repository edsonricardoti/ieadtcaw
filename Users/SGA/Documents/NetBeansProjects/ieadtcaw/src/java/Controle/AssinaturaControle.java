/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.faces.bean.ManagedBean;

import Modelo.Assinatura;
import DAO.AssinaturaDAO;
import DAO.MembrosDAO;
import DAO.EdicoesDAO;
import DAO.ParcelamentoDAO;
import DAO.PeriodicoDAO;
import Modelo.Assinantes;
import Modelo.Edicoes;
import Modelo.Membros;
import Modelo.Parcelamentos;
import Modelo.Periodico;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped

/**
 *
 * @author Edson Ricardo
 */
public class AssinaturaControle {

    private Assinatura assinatura;
    private AssinaturaDAO dao;
    private ParcelamentoDAO pdao;
    private Periodico periodico;
    private Membros membro;
    private MembrosDAO mdao;
    private Edicoes edicoes;
    private EdicoesDAO edao;
    private AssinaturaDAO adao;
    private PeriodicoDAO revista;
    private Assinatura assinaturaSelecionado;
    private List<Assinatura> listaAssinatura;
    private List<Assinatura> listaDaBusca;
    private List<Parcelamentos> listaParcelas;
    private List<Periodico> periodicos;
    private Boolean isRederiza = false;
    private List<Edicoes> listaEdicoes;
    private List<Membros> listaMembros;
    private List<Assinantes> listaAssinantes;
    private String nome;
    private Date dataini;
    private Date datafim;
    private Integer qtdparcela;

    public AssinaturaControle() {
        assinatura = new Assinatura();
        dao = new AssinaturaDAO();
        assinaturaSelecionado = new Assinatura();
    }

    @PostConstruct
    public void init() {
        assinatura = new Assinatura();
        dao = new AssinaturaDAO();
        revista = new PeriodicoDAO();
        mdao = new MembrosDAO();
        pdao = new ParcelamentoDAO();
        listaParcelas = null;
        periodicos = null;
        isRederiza = false;
        periodicos = revista.selectAll();
        if (assinaturaSelecionado.equals(null)) {
            listaParcelas = pdao.selectAll(assinaturaSelecionado.getIdmembro(), assinaturaSelecionado.getIdperiodico());
        }
        assinaturaSelecionado = new Assinatura();
        listaMembros = mdao.selectAll();

    }

    public void limpaFormulario() {
        assinatura = new Assinatura();
        assinaturaSelecionado = new Assinatura();
        listaDaBusca = null;
        membro = new Membros();
        //listaMembros = null;
        listaEdicoes = null;

    }

    public String PegaPeriodico(int idperiodico) {
        Periodico pediodico = new Periodico();
        periodico = revista.buscarPorID(idperiodico);
        return periodico.getTitulo();

    }

    public void BuscaAssinatura(String nome) throws ParseException {

        listaAssinantes = dao.buscaAssinantes(nome);
    }

    public void AdicionaEdicao() {
        edao.insert(edicoes);
    }

    public String pegaMembro(int idmembro) {
        String onome;
        onome = mdao.buscarPorId(idmembro).getMembrosNome();
        return onome;
    }

    public void GerarParcelas(int idmembro, int idperiodico, int numparcela) {
        System.out.println("Parcelas dados idmembro:" + idmembro + " periodico:" + idperiodico + " numparcela:" + numparcela);
        assinaturaSelecionado = assinatura;
        Parcelamentos par;
        Periodico periodico = new Periodico();
        PeriodicoDAO per = new PeriodicoDAO();
        ParcelamentoDAO pdao = new ParcelamentoDAO();
        periodico = per.buscarPorID(idperiodico);
        Edicoes edicoes = new Edicoes();
        edicoes.setIdmembro(idmembro);
        edicoes.setIdperiodico(idperiodico);
        EdicoesDAO edao = new EdicoesDAO();
        edao.insert(edicoes);

        if (numparcela != 0) {
            for (int i = 1; i <= numparcela; i++) {
                par = new Parcelamentos();
                par.setIdmembro(idmembro);
                par.setIdperiodico(idperiodico);
                par.setValorparcela(periodico.getValor().divide(new BigDecimal(numparcela)));
                par.setNumparcela(i);
                pdao.insert(par);
            }
        } else {
            par = new Parcelamentos();
            par.setIdmembro(idmembro);
            par.setIdperiodico(idperiodico);
            par.setValorparcela(periodico.getValor());
            par.setNumparcela(numparcela);
            pdao.insert(par);
        }
        listaParcelas = pdao.selectAll(idmembro, idperiodico);
    }

    public void Reparcelar(int idmembro, int idperiodico, int numparcela) {

        Parcelamentos par;
        Periodico periodico = new Periodico();
        PeriodicoDAO per = new PeriodicoDAO();
        ParcelamentoDAO pdao = new ParcelamentoDAO();
        periodico = per.buscarPorID(idperiodico);
        par = pdao.buscarPorParcela(idmembro, idperiodico, 0);
        boolean delete = pdao.delete(par);
        if (numparcela != 0) {
            for (int i = 1; i <= numparcela; i++) {
                par = new Parcelamentos();
                par.setIdmembro(idmembro);
                par.setIdperiodico(idperiodico);
                par.setValorparcela(periodico.getValor().divide(new BigDecimal(numparcela)));
                par.setNumparcela(i);
                pdao.insert(par);
            }
        } else {
            par = new Parcelamentos();
            par.setIdmembro(idmembro);
            par.setIdperiodico(idperiodico);
            par.setValorparcela(periodico.getValor());
            par.setNumparcela(numparcela);
            pdao.insert(par);
        }
        listaParcelas = pdao.selectAll(idmembro, idperiodico);
    }

    public void atualizaParcelas() {
        listaParcelas = pdao.selectAll(assinaturaSelecionado.getIdmembro(), assinaturaSelecionado.getIdperiodico());
        //EdicoesDAO edao = new EdicoesDAO();
        edicoes = new Edicoes();
        edicoes = edao.buscarIdPerioEmembro(assinaturaSelecionado.getIdperiodico(), assinaturaSelecionado.getIdmembro());
    }

    public void buscarLista(String nome) throws ParseException {

        List<Membros> lista = null;
        lista = dao.buscarPorNome(nome);
        listaMembros = lista;
    }

    public void buscarListaDtiniDtfim(Date dtini, Date dtfim) {

        List<Assinatura> lista = null;
        try {
            lista = (List<Assinatura>) dao.buscarPorDtiniDtfim(dtini, dtfim);
        } catch (ParseException ex) {
            Logger.getLogger(AssinaturaControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaDaBusca = lista;
    }

    public void buscarPorId(int id) {
        assinaturaSelecionado = dao.buscarPorID(id);

    }

    public void ManterMembro(Membros membros) {
        assinatura.setIdmembro(membros.getIdmembros());

    }

    public void insert() {

        if (dao.insert(assinatura)) {
            isRederiza = true;
            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void Salva(Membros membro) {

        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);
        assinatura.setDatadatacadastro(calendar.getTime());
        assinatura.setIdmembro(membro.getIdmembros());

        if (dao.insert(assinatura)) {

            // addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void delete(Assinatura atas) {
        if (dao.delete(atas)) {
            addInfoMessage("Assinatura excluída com sucesso!");
        } else {
            addInfoMessage("Assinatura não excuída!");
        }

    }

    public void update() {
        isRederiza = true;
        if (dao.update(assinaturaSelecionado)) {
            // addInfoMessage("Assinatura alterada com sucesso!");
        } else {
            addInfoMessage("Assinatura não aterada");
        }

    }

    public Assinatura getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(Assinatura assinatura) {
        this.assinatura = assinatura;
    }

    public Assinatura getAssinaturaSelecionado() {
        return assinaturaSelecionado;
    }

    public void setAssinaturaSelecionado(Assinatura assinaturaSelecionado) {
        this.assinaturaSelecionado = assinaturaSelecionado;
    }

    public List<Assinatura> getListaAssinatura() {
        return listaAssinatura;
    }

    public void setListaAssinatura(List<Assinatura> listaAssinatura) {
        this.listaAssinatura = listaAssinatura;
    }

    public List<Assinatura> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Assinatura> listaDaBusca) {
        this.listaDaBusca = listaDaBusca;
    }

    public Boolean getIsRederiza() {
        return isRederiza;
    }

    public void setIsRederiza(Boolean isRederiza) {
        this.isRederiza = isRederiza;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Membros getMembro() {
        return membro;
    }

    public void setMembro(Membros membro) {
        this.membro = membro;
    }

    public Edicoes getEdicoes() {
        return edicoes;
    }

    public void setEdicoes(Edicoes edicoes) {
        this.edicoes = edicoes;
    }

    public List<Edicoes> getListaEdicoes() {
        return listaEdicoes;
    }

    public void setListaEdicoes(List<Edicoes> listaEdicoes) {
        this.listaEdicoes = listaEdicoes;
    }

    public List<Membros> getListaMembros() {
        return listaMembros;
    }

    public void setListaMembros(List<Membros> listaMembros) {
        this.listaMembros = listaMembros;
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

    public Integer getQtdparcela() {
        return qtdparcela;
    }

    public void setQtdparcela(Integer qtdparcela) {
        this.qtdparcela = qtdparcela;
    }

    public List<Parcelamentos> getListaParcelas() {
        return listaParcelas;
    }

    public void setListaParcelas(List<Parcelamentos> listaParcelas) {
        this.listaParcelas = listaParcelas;
    }

    public List<Periodico> getPeriodicos() {
        return periodicos;
    }

    public void setPeriodicos(List<Periodico> periodicos) {
        this.periodicos = periodicos;
    }

    public List<Assinantes> getListaAssinantes() {
        return listaAssinantes;
    }

    public void setListaAssinantes(List<Assinantes> listaAssinantes) {
        this.listaAssinantes = listaAssinantes;
    }

}
