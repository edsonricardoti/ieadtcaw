/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import DAO.CaixaDAO;
import DAO.ContasapagarDAO;
import DAO.FinanceiroDAO;
import Modelo.Caixa;
import Modelo.Contasapagar;
import Modelo.Financeiro;
import Modelo.LivroCaixa;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
/**
 *
 * @author SGA
 */
public class CaixaControle {

    private Financeiro financeiro;
    private Caixa caixa;
    private CaixaDAO dao;
    private BigDecimal totreceita = BigDecimal.ZERO;
    private BigDecimal totdespes = BigDecimal.ZERO;
    private BigDecimal saldo = BigDecimal.ZERO;
    private List<Financeiro> listaReceitas;
    private List<LivroCaixa> listaCaixa;
    private FinanceiroDAO fdao;
    private Contasapagar contasapagar;
    private ContasapagarDAO cdao;
    private List<Contasapagar> listaDespesas;
    private Date dataini;
    private Date datafim;

    public CaixaControle() {
        totreceita = new BigDecimal(BigInteger.ZERO);
        totdespes = new BigDecimal(BigInteger.ZERO);
        saldo = new BigDecimal(BigInteger.ZERO);
    }

    public void GeraCaixa(Date dataini, Date datafim) throws ParseException {
        //gera dados do financeiro e lana no caixa
        totreceita = new BigDecimal(BigInteger.ZERO);
        totdespes = new BigDecimal(BigInteger.ZERO);
        saldo = new BigDecimal(BigInteger.ZERO);
        BigDecimal tr = new BigDecimal(BigInteger.ZERO);
        BigDecimal td = new BigDecimal(BigInteger.ZERO);
        dao = new CaixaDAO();
        fdao = new FinanceiroDAO();
        listaReceitas = fdao.buscarPorPeriodo(dataini, datafim);
        caixa = new Caixa();

        for (Financeiro f : listaReceitas) {
            if (f.getFinanceiroTipo().equals("dizimo")) {
                caixa.setDescricao("Dizimos e Ofertas");
            }
            if (f.getFinanceiroTipo().equals("oferta")) {
                caixa.setDescricao("Dizimos e Ofertas");
            }
            if (f.getFinanceiroTipo().equals("alcada")) {
                caixa.setDescricao("Dizimos e Ofertas");
            }
            if (f.getFinanceiroTipo().equals("voto")) {
                caixa.setDescricao("Dizimos e Ofertas");
            }
            if (f.getFinanceiroTipo().equals("missoferta")) {
                caixa.setDescricao("Missções - Dizimos e Ofertas");
            }
            if (f.getFinanceiroTipo().equals("missalcada")) {
                caixa.setDescricao("Missções - Dizimos e Ofertas");
            }
            if (f.getFinanceiroTipo().equals("missvoto")) {
                caixa.setDescricao("Missções - Dizimos e Ofertas");
            }
            if (f.getFinanceiroTipo().equals("carne")) {
                caixa.setDescricao("Missções - Carnês");
            }
            caixa.setDtlancamento(f.getFinanceiroData());
            caixa.setValordareceita(f.getFinanceiroValor());

            if (dao.NaoExisteNoCaixa(f.getFinanceiroData())) {
                //lancado
                dao.insert(caixa);
            }
        }

        //gera dados de despesas(contas a pagar) para o caixa
        cdao = new ContasapagarDAO();
        listaDespesas = cdao.buscarDespesas(dataini, datafim);
        caixa = new Caixa();
        for (Contasapagar c : listaDespesas) {
            if (c.getObs() == null) {
                caixa.setDescricao(c.getDescricao());
            } else {
                caixa.setDescricao(c.getObs());
            }
            caixa.setDtlancamento(c.getDatalancamento());
            caixa.setValordadespesa(c.getValordespesa());

            if (dao.NaoExisteNoCaixa(c.getDatalancamento())) {
                //lancado
                dao.insert(caixa);
            }
        }

        listaCaixa = dao.buscarPorPeriodo(dataini, datafim);
        for (LivroCaixa cx : listaCaixa) {
            if (cx.getReceitas() != null) {
                tr = tr.add(cx.getReceitas());
            }
            if (cx.getDespesas() != null) {
                td = td.add(cx.getDespesas());
            }
            totreceita = tr;
            totdespes = td;
        }
        saldo = saldo.add(totreceita.subtract(totdespes));

    }

    public Financeiro getFinanceiro() {
        return financeiro;
    }

    public void setFinanceiro(Financeiro financeiro) {
        this.financeiro = financeiro;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public BigDecimal getTotreceita() {
        return totreceita;
    }

    public void setTotreceita(BigDecimal totreceita) {
        this.totreceita = totreceita;
    }

    public BigDecimal getTotdespes() {
        return totdespes;
    }

    public void setTotdespes(BigDecimal totdespes) {
        this.totdespes = totdespes;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public List<Financeiro> getListaReceitas() {
        return listaReceitas;
    }

    public void setListaReceitas(List<Financeiro> listaReceitas) {
        this.listaReceitas = listaReceitas;
    }

    public List<Contasapagar> getListaDespesas() {
        return listaDespesas;
    }

    public void setListaDespesas(List<Contasapagar> listaDespesas) {
        this.listaDespesas = listaDespesas;
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

    public List<LivroCaixa> getListaCaixa() {
        return listaCaixa;
    }

    public void setListaCaixa(List<LivroCaixa> listaCaixa) {
        this.listaCaixa = listaCaixa;
    }

}
