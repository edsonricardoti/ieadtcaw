/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import DAO.AssinaturaDAO;
import javax.inject.Named;

import Modelo.Financeiro;
import DAO.FinanceiroDAO;
import DAO.MembrosDAO;
import Modelo.Membros;
import Modelo.Missgeral;
import Modelo.Relatorios;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ConversationScoped

/**
 *
 * @author Edson Ricardo
 */
@Named
public class FinanceiroControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Financeiro financeiro;
    private FinanceiroDAO dao;
    private final MembrosDAO mdao;
    private AssinaturaDAO adao;
    private List<Membros> listaMembros;
    private Financeiro financeiroSelecionado;
    private List<Financeiro> listaFinanceiro;
    private List<Financeiro> listaDaBusca;
    private List<Financeiro> listaDeAlunos;
    private Boolean isRederiza = false;
    private List<Relatorios> dizimoPormes;
    private List<Relatorios> dizimoPorano;
    private Relatorios dizimos;
    private List<Relatorios> dizimoLista;
    private BigDecimal subtotal;
    private String SubTotal;
    private Date dataini;
    private Date datafim;
    private Integer mes;
    private BigDecimal subDizimos;
    private BigDecimal subOfertas;
    private BigDecimal subAlcadas;
    private BigDecimal subVotos;
    private String NomeContribuinte;
    private Membros contribuintes;
    private Integer iddomembro;
    private Integer ano;
    private List<Missgeral> missgeral;

    @Inject
    private Conversation conversation;

    public FinanceiroControle() {

        financeiro = new Financeiro();
        dao = new FinanceiroDAO();
        mdao = new MembrosDAO();
        financeiroSelecionado = new Financeiro();
        listaFinanceiro = null;
        dizimoPormes = null;
        iddomembro = 0;
        Calendar cal = GregorianCalendar.getInstance();
        ano = cal.get(Calendar.YEAR);
    }

    @PostConstruct
    public void init() {
        financeiro = new Financeiro();
        contribuintes = new Membros();
        dao = new FinanceiroDAO();
        listaFinanceiro = null;
        financeiroSelecionado = new Financeiro();
        //listaFinanceiro = dao.selectAll();
        dizimos = new Relatorios();
        dizimoPormes = null;
        dizimoPorano = null;
        subtotal = BigDecimal.ZERO;
        subtotal = BigDecimal.ZERO;
        SubTotal = "";
        dataini = new Date();
        datafim = new Date();
        mes = 0;
        subDizimos = BigDecimal.ZERO;
        subOfertas = BigDecimal.ZERO;
        subAlcadas = BigDecimal.ZERO;
        subVotos = BigDecimal.ZERO;
        Calendar cal = GregorianCalendar.getInstance();
        ano = cal.get(Calendar.YEAR);

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
    public void limpaFormulario() {
        financeiro = new Financeiro();
        financeiroSelecionado = new Financeiro();
        listaDaBusca = null;
        listaFinanceiro = null;
        NomeContribuinte = "";
        iddomembro = 0;
        dizimoPormes = null;
        dizimoPorano = null;
        subtotal = BigDecimal.ZERO;
        subtotal = BigDecimal.ZERO;
        SubTotal = "";
        dataini = new Date();
        datafim = new Date();
        mes = 0;
        subDizimos = BigDecimal.ZERO;
        subOfertas = BigDecimal.ZERO;
        subAlcadas = BigDecimal.ZERO;
        subVotos = BigDecimal.ZERO;
    }

    public void buscarPorId(int id) {
        financeiroSelecionado = dao.buscarPorID(id);
        System.out.println("ID passado=" + id);
        contribuintes = new Membros();
        contribuintes = dao.buscarPorId(financeiroSelecionado.getFinanceiroIdmembro());
        NomeContribuinte = contribuintes.getMembrosNome();
        iddomembro = contribuintes.getIdmembros();
        System.out.println("ID Membro passado=" + iddomembro);

    }

    public Membros buscaMembro(int id) {
        contribuintes = mdao.buscarPorId(id);
        NomeContribuinte = contribuintes.getMembrosNome();
        iddomembro = contribuintes.getIdmembros();
        return contribuintes;

    }

    private void buscaNomeContribuite(int id) {
        System.out.println("ID passado=" + id);
        contribuintes = new Membros();
        contribuintes = mdao.buscarPorId(id);
        iddomembro = contribuintes.getIdmembros();

    }

    public void selecionaMembro(int id) {
        contribuintes = mdao.buscarPorId(id);
    }

    //funcao dashboard
    public void dashboardFin() {

    }

    public void buscarContribuintes(String nome) {
        List<Membros> lista = null;
        lista = (List<Membros>) mdao.buscarPorNomeLista(nome);
        listaMembros = lista;
    }

    public List<Financeiro> buscarPorPeriodo(Date dataini, Date dadafim) {
        //busca financeiro dentro de um periodo de datas
        try {
            List lista = dao.buscarPorPeriodo(dataini, dadafim);
            return lista;

        } catch (ParseException ex) {
            Logger.getLogger(FinanceiroControle.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void buscaPorMes(int mes) throws ParseException {
        //busca pelo mes digitado e o ano atual do sistema
        dizimoPormes = dao.buscarDizimoPorMes(mes);
        BigDecimal t = new BigDecimal(BigInteger.ZERO);
        for (Relatorios r : dizimoPormes) {

            t = t.add(r.getTotal());
            NumberFormat nf = new DecimalFormat("###,##0.00");
            subtotal = t; //total geral no fim do relatorio
            SubTotal = nf.format(this.subtotal);
        }
    }

    public void buscaPorPeriodo(Date dataini, Date datafim, String tipo) throws ParseException {

        dizimoPormes = dao.buscarDizimoPorPeriodo(dataini, datafim, tipo);
        BigDecimal t = new BigDecimal(BigInteger.ZERO);
        for (Relatorios r : dizimoPormes) {

            t = t.add(r.getTotal());
            NumberFormat nf = new DecimalFormat("###,##0.00");
            subtotal = t; //total geral no fim do relatorio
            SubTotal = nf.format(this.subtotal);
        }
    }

    public void buscaGeralPorPeriodo(Date dataini, Date datafim) throws ParseException {
        subtotal = BigDecimal.ZERO;
        SubTotal = "";
        mes = 0;
        subDizimos = BigDecimal.ZERO;
        subOfertas = BigDecimal.ZERO;
        subAlcadas = BigDecimal.ZERO;
        subVotos = BigDecimal.ZERO;
        dizimoPormes = dao.buscarGeralPorPeriodo(dataini, datafim);
        BigDecimal t = new BigDecimal(BigInteger.ZERO);
        BigDecimal dizimo = new BigDecimal(BigInteger.ZERO);
        BigDecimal oferta = new BigDecimal(BigInteger.ZERO);
        BigDecimal alcada = new BigDecimal(BigInteger.ZERO);
        BigDecimal votos = new BigDecimal(BigInteger.ZERO);
        for (Relatorios r : dizimoPormes) {

            t = t.add(r.getDizimos());
            t = t.add(r.getOfertas());
            t = t.add(r.getAlcadas());
            t = t.add(r.getVotos());
            dizimo = dizimo.add(r.getDizimos());
            oferta = oferta.add(r.getOfertas());
            alcada = alcada.add(r.getAlcadas());
            votos = votos.add(r.getVotos());
            subDizimos = dizimo;
            subOfertas = oferta;
            subAlcadas = alcada;
            subVotos = votos;
            NumberFormat nf = new DecimalFormat("###,##0.00");
            subtotal = t; //total geral no fim do relatorio
            SubTotal = nf.format(this.subtotal);
            //SubTotal = subtotal.toString().replace(".", ",");
        }
    }

    public void buscaMissGeralPorPeriodo(Date dataini, Date datafim) throws ParseException {
        subtotal = BigDecimal.ZERO;
        SubTotal = "";
        mes = 0;
        subDizimos = BigDecimal.ZERO;
        subOfertas = BigDecimal.ZERO;
        subAlcadas = BigDecimal.ZERO;
        subVotos = BigDecimal.ZERO;
        dizimoPormes = dao.buscarMissGeralPorPeriodo(dataini, datafim);
        BigDecimal t = new BigDecimal(BigInteger.ZERO);
        BigDecimal dizimo = new BigDecimal(BigInteger.ZERO);
        BigDecimal oferta = new BigDecimal(BigInteger.ZERO);
        BigDecimal alcada = new BigDecimal(BigInteger.ZERO);
        BigDecimal votos = new BigDecimal(BigInteger.ZERO);
        for (Relatorios r : dizimoPormes) {

            t = t.add(r.getOfertas());
            t = t.add(r.getAlcadas());
            t = t.add(r.getVotos());
            t = t.add(r.getDizimos());
            dizimo = dizimo.add(r.getDizimos());
            oferta = oferta.add(r.getOfertas());
            alcada = alcada.add(r.getAlcadas());
            votos = votos.add(r.getVotos());

            subOfertas = oferta;
            subAlcadas = alcada;
            subVotos = votos;
            subDizimos = dizimo;
            NumberFormat nf = new DecimalFormat("###,##0.00");
            subtotal = t; //total geral no fim do relatorio
            SubTotal = nf.format(this.subtotal);
            //SubTotal = subtotal.toString().replace(".", ",");
        }
    }

    public void buscaGeralMissoes(int idperiodico) throws ParseException {
        //gera planilha de missoes com a situacao dos assinantes
        adao = new AssinaturaDAO();
        missgeral = adao.buscaFinanceiroGeral(idperiodico);
    }

    public void buscaPorAno(int ano) throws ParseException {
        //busca pelo mes digitado e o ano atual do sistema
        dizimoPorano = dao.buscarDizimoPorAno(ano);
        BigDecimal t = new BigDecimal(BigInteger.ZERO);
        for (Relatorios r : dizimoPorano) {

            t = t.add(r.getTotal());
            NumberFormat nf = new DecimalFormat("###,##0.00");
            subtotal = t; //total geral no fim do relatorio
            SubTotal = nf.format(this.subtotal);
        }

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

    public String NomeDoMesTable(int i) {
        String mes[] = {"Janeiro", "Fevereiro", "Março", "Abril",
            "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro",
            "Novembro", "Dezembro"};
        if (i == 0) {
            return "";
        } else {
            return (mes[i - 1]);
        }

    }

    public void DizimosNoAno(int ano) throws ParseException {
        System.out.println("Entrou Dizimo no ano = " + ano);
        dizimoPorano = dao.buscarDizimoNosMese(ano);
        BigDecimal t = new BigDecimal(BigInteger.ZERO);
        for (Relatorios r : dizimoPorano) {

            t = t.add(r.getJaneiro());
            t = t.add(r.getFevereiro());
            t = t.add(r.getMarco());
            t = t.add(r.getAbril());
            t = t.add(r.getMaio());
            t = t.add(r.getJunho());
            t = t.add(r.getJulho());
            t = t.add(r.getAgosto());
            t = t.add(r.getSetembro());
            t = t.add(r.getOutubro());
            t = t.add(r.getNovembro());
            t = t.add(r.getDezembro());
            NumberFormat nf = new DecimalFormat("###,##0.00");
            subtotal = t; //total geral no fim do relatorio
            SubTotal = nf.format(this.subtotal);

        }
    }

    public void insert(String oque, int id) {
        financeiro.setFinanceiroTipo(oque);
        financeiro.setFinanceiroIdmembro(id);

        if (dao.insert(financeiro)) {

            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void delete(Financeiro financeiro) {
        if (dao.delete(financeiro)) {
            addInfoMessage("Dados excluidos!");
        } else {
            addInfoMessage("Selecione um registro!");
        }

    }

    public void update() {

        if (dao.update(financeiroSelecionado)) {
            addInfoMessage("Dados alterados com sucesso!");
        } else {
            addInfoMessage("Dados não alterados");
        }

    }

    public Financeiro getFinanceiro() {
        return financeiro;
    }

    public void setFinanceiro(Financeiro financeiro) {
        this.financeiro = financeiro;
    }

    public Financeiro getFinanceiroSelecionado() {
        return financeiroSelecionado;
    }

    public void setFinanceiroSelecionado(Financeiro financeiroSelecionado) {
        this.financeiroSelecionado = financeiroSelecionado;
    }

    public List<Financeiro> getListaFinanceiro() {
        listaFinanceiro = dao.selectAll();
        return listaFinanceiro;
    }

    public void setListaFinanceiro(List<Financeiro> listaFinanceiro) {
        this.listaFinanceiro = listaFinanceiro;
    }

    public List<Financeiro> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Financeiro> listaDaBusca) {
        this.listaDaBusca = listaDaBusca;
    }

    public Boolean getIsRederiza() {
        return isRederiza;
    }

    public void setIsRederiza(Boolean isRederiza) {
        this.isRederiza = isRederiza;
    }

    public Financeiro getClasse() {
        return financeiro;
    }

    public void setClasse(Financeiro financeiro) {
        this.financeiro = financeiro;
    }

    public Financeiro getClasseSelecionado() {
        return financeiroSelecionado;
    }

    public void setClasseSelecionado(Financeiro financeiroSelecionado) {
        this.financeiroSelecionado = financeiroSelecionado;
    }

    public List<Financeiro> getListaDeAlunos() {
        return listaDeAlunos;
    }

    public void setListaDeAlunos(List<Financeiro> listaDeAlunos) {
        this.listaDeAlunos = listaDeAlunos;
    }

    public String dataFormatada(int id) {
        financeiro = dao.buscarPorID(id);
        Date data = financeiro.getFinanceiroData();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }

    public List<Relatorios> getDizimoLista() {
        return dizimoLista;
    }

    public void setDizimoLista(List<Relatorios> dizimoLista) {
        this.dizimoLista = dizimoLista;
    }

    public List<Relatorios> getDizimoPormes() {
        return dizimoPormes;
    }

    public void setDizimoPormes(List<Relatorios> dizimoPormes) {
        this.dizimoPormes = dizimoPormes;
    }

    public List<Relatorios> getDizimoPorano() {
        return dizimoPorano;
    }

    public void setDizimoPorano(List<Relatorios> dizimoPorano) {
        this.dizimoPorano = dizimoPorano;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(String SubTotal) {
        this.SubTotal = SubTotal;
    }

    public Date getDataini() {
        Date data = dataini;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = formato.format(data);
        Date dataF = null;
        try {
            dataF = formato.parse(dataFormatada);

        } catch (ParseException ex) {
            Logger.getLogger(FinanceiroControle.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return dataF;
    }

    public void setDataini(Date dataini) {
        this.dataini = dataini;
    }

    public Date getDatafim() {
        Date data = datafim;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = formato.format(data);
        Date dataF = null;
        try {
            dataF = formato.parse(dataFormatada);

        } catch (ParseException ex) {
            Logger.getLogger(FinanceiroControle.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return dataF;
    }

    public void setDatafim(Date datafim) {
        this.datafim = datafim;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public BigDecimal getSubDizimos() {
        return subDizimos;
    }

    public void setSubDizimos(BigDecimal subDizimos) {
        this.subDizimos = subDizimos;
    }

    public BigDecimal getSubOfertas() {
        return subOfertas;
    }

    public void setSubOfertas(BigDecimal subOfertas) {
        this.subOfertas = subOfertas;
    }

    public BigDecimal getSubAlcadas() {
        return subAlcadas;
    }

    public void setSubAlcadas(BigDecimal subAlcadas) {
        this.subAlcadas = subAlcadas;
    }

    public BigDecimal getSubVotos() {
        return subVotos;
    }

    public void setSubVotos(BigDecimal subVotos) {
        this.subVotos = subVotos;
    }

    public List<Membros> getListaMembros() {
        return listaMembros;
    }

    public void setListaMembros(List<Membros> listaMembros) {
        this.listaMembros = listaMembros;
    }

    public String getNomeContribuinte() {
        return NomeContribuinte;
    }

    public void setNomeContribuinte(String NomeContribuinte) {
        this.NomeContribuinte = NomeContribuinte;
    }

    public Membros getContribuintes() {
        return contribuintes;
    }

    public void setContribuintes(Membros contribuintes) {
        this.contribuintes = contribuintes;
    }

    public Integer getIddomembro() {
        return iddomembro;
    }

    public void setIddomembro(Integer iddomembro) {
        this.iddomembro = iddomembro;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public List<Missgeral> getMissgeral() {
        return missgeral;
    }

    public void setMissgeral(List<Missgeral> missgeral) {
        this.missgeral = missgeral;
    }

}
