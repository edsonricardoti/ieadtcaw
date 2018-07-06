/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.faces.bean.ManagedBean;

import Modelo.Assinatura;
import DAO.AssinaturaDAO;
import DAO.FinanceiroDAO;
import DAO.MembrosDAO;
import DAO.ParcelamentoDAO;
import DAO.PeriodicoDAO;
import Modelo.Assinantes;
import Modelo.Financeiro;
import Modelo.Membros;
import Modelo.Missgeral;
import Modelo.Parcelamentos;
import Modelo.Periodico;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.io.Serializable;
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
import org.primefaces.event.RowEditEvent;

@ManagedBean
@SessionScoped

/**
 *
 * @author Edson Ricardo
 */
public class AssinaturaControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Assinatura assinatura;
    private AssinaturaDAO dao;
    private ParcelamentoDAO pdao;
    private Periodico periodico;
    private Membros membro;
    private MembrosDAO mdao;
    private Financeiro financeiro;
    private FinanceiroDAO fdao;
    private AssinaturaDAO adao;
    private PeriodicoDAO revista;
    private Assinatura assinaturaSelecionado;
    private Parcelamentos parcelas;
    private List<Assinatura> listaAssinatura;
    private List<Assinatura> listaDaBusca;
    private List<Parcelamentos> listaParcelas;
    private List<Periodico> periodicos;
    private Boolean isRederiza = false;
    private List<Membros> listaMembros;
    private List<Assinantes> listaAssinantes;
    private String nome;
    private Date dataini;
    private Date datafim;
    private Integer qtdparcela;
    private Boolean ed1, ed2, ed3, ed4, ed5, ed6, ed7, ed8, ed9, ed10, ed11, ed12;
    private Integer idmembros;
    private Integer idperiodico;
    private List<Missgeral> missgeral;

    public AssinaturaControle() {
        assinatura = new Assinatura();
        dao = new AssinaturaDAO();
        assinaturaSelecionado = new Assinatura();
        parcelas = new Parcelamentos();
        idperiodico = 0;

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
        parcelas = new Parcelamentos();
        idperiodico = 0;

        assinaturaSelecionado = new Assinatura();
        //listaMembros = mdao.selectAll();

    }

    public void onCellEdit(RowEditEvent event) {

        Parcelamentos parc = new Parcelamentos();
        ParcelamentoDAO pDao = new ParcelamentoDAO();
        parc = (Parcelamentos) event.getObject();
        if (pDao.update(parc)) {
            fdao = new FinanceiroDAO();
            financeiro = new Financeiro();
            financeiro.setFinanceiroData(parc.getDatapagamento());
            financeiro.setFinanceiroIdmembro(parc.getIdmembro());
            financeiro.setFinanceiroTipo("carne");
            financeiro.setFinanceiroValor(parc.getValorparcela());
            if (fdao.insert(financeiro)) {
                System.out.println("Lancou no financeiro");
            }

            addInfoMessage("Pagamento confirmado!");
        };

    }

    public void limpaFormulario() {
        assinatura = new Assinatura();
        assinaturaSelecionado = new Assinatura();
        listaDaBusca = null;
        membro = new Membros();
        //listaMembros = null;        
        isRederiza = false;
        nome = "";

        ed1 = false;
        ed2 = false;
        ed3 = false;
        ed4 = false;
        ed5 = false;
        ed6 = false;
        ed7 = false;
        ed8 = false;
        ed9 = false;
        ed10 = false;
        ed11 = false;
        ed12 = false;

    }

    public String PegaPeriodico(int idperiodico) {
        revista = new PeriodicoDAO();
        Periodico pediodico = new Periodico();
        periodico = revista.buscarPorID(idperiodico);
        if (periodico != null) {
            return periodico.getTitulo();
        } else {
            return "";
        }

    }

    public void PreparaInclusao(Membros membro) {
        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);
        nome = membro.getMembrosNome();
        dataini = calendar.getTime();
        idmembros = membro.getIdmembros();

    }

    public void BuscaAssinatura(String nome) throws ParseException {

        listaAssinantes = dao.buscaAssinantes(nome);
    }

    public String pegaMembro(int idmembro) {
        String onome = "";
        if (idmembro != 0) {
            onome = mdao.buscarPorId(idmembro).getMembrosNome();
        }
        return onome;
    }

    public void GerarParcelas(int idmembro, int idperiodico, int numparcela) {
        System.out.println("Parcelas dados idmembro:" + idmembro + " periodico:" + idperiodico + " numparcela:" + numparcela);
        assinaturaSelecionado = assinatura;
        Parcelamentos par;
        periodico = new Periodico();
        PeriodicoDAO per = new PeriodicoDAO();
        pdao = new ParcelamentoDAO();
        periodico = per.buscarPorID(idperiodico);

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
        periodico = new Periodico();
        PeriodicoDAO per = new PeriodicoDAO();
        pdao = new ParcelamentoDAO();
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

    public void atualizaParcelas(int idmembro, int idperiodico) {
        listaParcelas = pdao.selectAll(idmembro, idperiodico);
        isRederiza = true;

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
            //isRederiza = true;
            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void salva() {
        isRederiza = true;
        periodico = revista.buscarPorID(assinatura.getIdperiodico());
        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);
        assinatura.setDatacadastro(calendar.getTime());
        assinatura.setIdmembro(idmembros);
        assinatura.setValortotal(periodico.getValor());
        // EdicoesDAO edao = new EdicoesDAO();
        // edicoes = new Edicoes();
        // edicoes.setIdmembro(assinatura.getIdmembro());
        // edicoes.setIdperiodico(assinatura.getIdperiodico());

        if (dao.insert(assinatura)) {
            assinaturaSelecionado = new Assinatura();
            assinaturaSelecionado = assinatura;
            if (assinaturaSelecionado.getQtdparcelas() != null) {
                GerarParcelas(assinatura.getIdmembro(), assinatura.getIdperiodico(), assinatura.getQtdparcelas());
            } else {
                GerarParcelas(assinatura.getIdmembro(), assinatura.getIdperiodico(), 0);
            }
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
//            if (assinaturaSelecionado.getQtdparcelas() != null) {
//                GerarParcelas(assinaturaSelecionado.getIdmembro(), assinaturaSelecionado.getIdperiodico(), assinaturaSelecionado.getQtdparcelas());
//            } else {
//                GerarParcelas(assinaturaSelecionado.getIdmembro(), assinaturaSelecionado.getIdperiodico(), 0);
//            }

            addInfoMessage("Assinatura registrada com sucesso!");
        } else {
            addInfoMessage("Assinatura não aterada");
        }

    }

    public void update2() {
        isRederiza = true;
        if (dao.update(assinaturaSelecionado)) {
            addInfoMessage("Assinatura alterada com sucesso!");
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

    public Boolean getEd1() {
        return ed1;
    }

    public void setEd1(Boolean ed1) {
        this.ed1 = ed1;
    }

    public Boolean getEd2() {
        return ed2;
    }

    public void setEd2(Boolean ed2) {
        this.ed2 = ed2;
    }

    public Boolean getEd3() {
        return ed3;
    }

    public void setEd3(Boolean ed3) {
        this.ed3 = ed3;
    }

    public Boolean getEd4() {
        return ed4;
    }

    public void setEd4(Boolean ed4) {
        this.ed4 = ed4;
    }

    public Boolean getEd5() {
        return ed5;
    }

    public void setEd5(Boolean ed5) {
        this.ed5 = ed5;
    }

    public Boolean getEd6() {
        return ed6;
    }

    public void setEd6(Boolean ed6) {
        this.ed6 = ed6;
    }

    public Boolean getEd7() {
        return ed7;
    }

    public void setEd7(Boolean ed7) {
        this.ed7 = ed7;
    }

    public Boolean getEd8() {
        return ed8;
    }

    public void setEd8(Boolean ed8) {
        this.ed8 = ed8;
    }

    public Boolean getEd9() {
        return ed9;
    }

    public void setEd9(Boolean ed9) {
        this.ed9 = ed9;
    }

    public Boolean getEd10() {
        return ed10;
    }

    public void setEd10(Boolean ed10) {
        this.ed10 = ed10;
    }

    public Boolean getEd11() {
        return ed11;
    }

    public void setEd11(Boolean ed11) {
        this.ed11 = ed11;
    }

    public Boolean getEd12() {
        return ed12;
    }

    public void setEd12(Boolean ed12) {
        this.ed12 = ed12;
    }

    public Integer getIdmembros() {
        return idmembros;
    }

    public void setIdmembros(Integer idmembros) {
        this.idmembros = idmembros;
    }

    public Parcelamentos getParcelas() {
        return parcelas;
    }

    public void setParcelas(Parcelamentos parcelas) {
        this.parcelas = parcelas;
    }

    public Integer getIdperiodico() {
        return idperiodico;
    }

    public void setIdperiodico(Integer idperiodico) {
        this.idperiodico = idperiodico;
    }

    public List<Missgeral> getMissgeral() {
        return missgeral;
    }

    public void setMissgeral(List<Missgeral> missgeral) {
        this.missgeral = missgeral;
    }

}
