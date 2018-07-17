/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.faces.bean.ManagedBean;

import DAO.ContasapagarDAO;
import Modelo.Contasapagar;
import Modelo.Relatorios;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

@ManagedBean
@SessionScoped

/**
 *
 * @author Edson Ricardo
 */
public class ContasapagarControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Contasapagar contasapagar;
    private ContasapagarDAO dao;
    private Contasapagar contasapagarSelecionado;
    private List<Contasapagar> listaContasapagar;
    private Boolean isRederiza = false;
    private String descricao;
    private Date dataini;
    private Date datafim;
    private LineChartModel lineModel2;
    private List<Relatorios> relatoriolista;
    private BigDecimal maximo;

    public ContasapagarControle() {

        contasapagar = new Contasapagar();
        dao = new ContasapagarDAO();
        contasapagarSelecionado = new Contasapagar();
        listaContasapagar = dao.buscarDespesas("nada");
        maximo = BigDecimal.ZERO;
    }

    @PostConstruct
    public void init() {

        contasapagar = new Contasapagar();
        dao = new ContasapagarDAO();
        listaContasapagar = dao.buscarDespesas("nada");
        contasapagarSelecionado = new Contasapagar();
        Date data1 = new Date();
        Date data2 = new Date();
        createLineModels(data1, data2);

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

    public void limpaFormulario() {
        listaContasapagar = dao.buscarDespesas("nada");
        contasapagar = new Contasapagar();
        contasapagarSelecionado = new Contasapagar();
    }

    public LineChartModel getLineModel2() {
        return lineModel2;
    }

    private void createLineModels(Date inicio, Date fim) {

        lineModel2 = initCategoryModel(inicio, fim);
        lineModel2.setTitle("Comparativo Gasolina X Manutenção");
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.setShowDatatip(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Mês"));
        Axis yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Valores");
        yAxis.setMin(0);
        yAxis.setMax(maximo);
        yAxis.setTickCount(10);
    }

    public boolean temNotificacao() {
        dao = new ContasapagarDAO();
        listaContasapagar = dao.buscarVencidos();
        if (listaContasapagar.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void geraGrafico(Date datainicio, Date datafinal) {
        lineModel2.clear();
        createLineModels(datainicio, datafinal);
    }

    private LineChartModel initCategoryModel(Date datainicial, Date datafinal) {
        LineChartModel model = new LineChartModel();
        ChartSeries boys = new ChartSeries();
        ChartSeries girls = new ChartSeries();
        boys.setLabel("Gasolina");
        girls.setLabel("Manutenção");
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal valora = BigDecimal.ZERO;
        int x = 0;

        relatoriolista = dao.gasolinaXmanutencao(datainicial, datafinal);
        for (Relatorios rel : relatoriolista) {

            //boys.set(NomeDoMes(rel.getMes()), rel.getGasolina());
            //girls.set(NomeDoMes(rel.getMes()), rel.getManutencao());
            valora = relatoriolista.get(x).getGasolina();
            if (valora == null) {
                valora = BigDecimal.ZERO;
            }
            boys.set(NomeDoMes(rel.getMes()), valora);
            total = total.add(valora);
            valora = relatoriolista.get(x).getManutencao();
            if (valora == null) {
                valora = BigDecimal.ZERO;
            }
            girls.set(NomeDoMes(rel.getMes()), valora);
            total = total.add(valora);
            x = x + 1;

        }
        System.out.println("Valor=" + total.toString());
        maximo = total;
        System.out.println("maximo=" + maximo);
        model.addSeries(boys);
        model.addSeries(girls);
        return model;
    }

    public BigDecimal somar(BigDecimal param1, BigDecimal param2) {
        return param1.add(param2);
    }

    public void buscarPorId(int id) {
        contasapagarSelecionado = dao.buscarPorID(id);

    }

    public void buscarLista(String descricao, String tipo, Date dataini, Date datafim) {
        listaContasapagar = dao.buscarUmaLista(descricao, tipo, dataini, datafim);
    }

    public void atualizaDespesas(String tipo) {
        listaContasapagar = dao.buscarDespesas(tipo);
        contasapagar = new Contasapagar();
    }

    public void insert(String tipo) {
        Calendar cal = GregorianCalendar.getInstance();
        contasapagar.setDatalancamento(cal.getTime());
        contasapagar.setTipo(tipo);
        if (dao.insert(contasapagar)) {

            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void delete(Contasapagar contasapagar) {
        if (dao.delete(contasapagar)) {
            addInfoMessage("Dados excluidos!");
        } else {
            addInfoMessage("Selecione um registro!");
        }

    }

    public void update() {

        if (dao.update(contasapagarSelecionado)) {
            contasapagarSelecionado = new Contasapagar();
            addInfoMessage("Dados alterados com sucesso!");
        } else {
            addInfoMessage("Dados não alterados");
        }

    }

    public Contasapagar getContasapagar() {
        return contasapagar;
    }

    public void setContasapagar(Contasapagar contasapagar) {
        this.contasapagar = contasapagar;
    }

    public Contasapagar getContasapagarSelecionado() {
        return contasapagarSelecionado;
    }

    public void setContasapagarSelecionado(Contasapagar contasapagarSelecionado) {
        this.contasapagarSelecionado = contasapagarSelecionado;
    }

    public List<Contasapagar> getListaContasapagar() {
        return listaContasapagar;
    }

    public void setListaContasapagar(List<Contasapagar> listaContasapagar) {
        this.listaContasapagar = listaContasapagar;
    }

    public Boolean getIsRederiza() {
        return isRederiza;
    }

    public void setIsRederiza(Boolean isRederiza) {
        this.isRederiza = isRederiza;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public List<Relatorios> getRelatoriolista() {
        return relatoriolista;
    }

    public void setRelatoriolista(List<Relatorios> relatoriolista) {
        this.relatoriolista = relatoriolista;
    }

    public BigDecimal getMaximo() {
        return maximo;
    }

    public void setMaximo(BigDecimal maximo) {
        this.maximo = maximo;
    }

}
