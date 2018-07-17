/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.faces.bean.ManagedBean;
import DAO.AlunoDAO;
import DAO.ClassesDAO;
import DAO.FrequenciaDAO;
import DAO.MembrosDAO;
import DAO.FrequenciaDAO;
import Modelo.Frequencia;
import Modelo.Membros;
import Modelo.Relatorios;
import Modelo.Alunos;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean
@SessionScoped

/**
 *
 * @author Edson Ricardo
 */
public class FrequenciaControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Frequencia frequencia;
    private FrequenciaDAO dao;
    private MembrosDAO mdao;
    private ClassesDAO cdao;
    private AlunoDAO adao;
    private Alunos aluno;
    private Membros membro;
    private List<Membros> listaMembros;
    private List<Alunos> listaDeAlunos;
    private Frequencia frequenciaSelecionado;
    private List<Frequencia> listaFrequencia;
    private Boolean isRederiza = false;
    private Date dataini;
    private Date datafim;
    private Integer presenca;
    private Integer faltas;
    private Integer iddomembro;
    private String nomeMembro;
    private List<Relatorios> relatorioLista;
    private Integer classe;
    private Integer trimestres;
    private String nomeDaClasse;
    private Double ps;
    private Double ft;
    private Double tl;
    private BarChartModel barModel;
    private Integer ano;

    public FrequenciaControle() {

        frequencia = new Frequencia();
        dao = new FrequenciaDAO();
        mdao = new MembrosDAO();
        adao = new AlunoDAO();
        cdao = new ClassesDAO();
        // frequenciaSelecionado = new Frequencia();
        listaFrequencia = null;
        iddomembro = 0;
        Calendar cal = GregorianCalendar.getInstance();
        ano = cal.get(Calendar.YEAR);
    }

    @PostConstruct
    public void init() {
        createBarModels();
        frequencia = new Frequencia();
        dao = new FrequenciaDAO();
        adao = new AlunoDAO();
        mdao = new MembrosDAO();
        listaFrequencia = null;
        frequenciaSelecionado = new Frequencia();
        dataini = new Date();
        datafim = new Date();
        Calendar cal = GregorianCalendar.getInstance();
        ano = cal.get(Calendar.YEAR);

    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    private BarChartModel initBarModel() throws ParseException {
        BarChartModel model = new BarChartModel();
        ChartSeries classes;
        relatorioLista = dao.graficoPorclassePeriodo();

        for (Relatorios rel : relatorioLista) {
            classes = new ChartSeries();
            classes.setLabel(rel.getNome());
            classes.set("1º Trimestre", rel.getTrimestre1());
            classes.set("2º Trimestre", rel.getTrimestre2());
            classes.set("3º Trimestre", rel.getTrimestre3());
            classes.set("4º Trimestre", rel.getTrimestre4());
            model.addSeries(classes);
        }

        return model;
    }

    private void createBarModels() {
        try {
            createBarModel();
        } catch (ParseException ex) {
            Logger.getLogger(FrequenciaControle.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void createBarModel() throws ParseException {
        barModel = initBarModel();
        barModel.setTitle("Comparativo das presenças por período");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Períodos");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Presenças");

        yAxis.setMin(0);
        yAxis.setMax(65);
    }

    public void limpaFormulario() {
        createBarModels();
        frequencia = new Frequencia();
        frequenciaSelecionado = new Frequencia();
        listaFrequencia = null;
        nomeMembro = "";
        iddomembro = 0;
        relatorioLista = null;
        dataini = new Date();
        datafim = new Date();
    }

    public void buscarPorId(int id) {
        // frequenciaSelecionado = dao.buscarPorID(id);
        System.out.println("ID passado=" + id);
        membro = new Membros();
        // membro = dao.buscarPorId(frequenciaSelecionado.getIdfrequencia());
        nomeMembro = membro.getMembrosNome();
        iddomembro = membro.getIdmembros();

    }

    public Membros buscaMembro(int id) {
        membro = mdao.buscarPorId(id);
        nomeMembro = membro.getMembrosNome();
        iddomembro = membro.getIdmembros();
        return membro;

    }

    public String PegaNomeAluno(int idaluno) {
        membro = mdao.buscarPorId(idaluno);
        nomeMembro = membro.getMembrosNome();
        return nomeMembro;
    }

    public void PegaAluno(int idAluno, int idClasse, int trimestre) {
        frequenciaSelecionado.setIdclasse(idClasse);
        frequenciaSelecionado.setIdmembro(idAluno);
        frequenciaSelecionado.setTrimestre(trimestre);

    }

    public void VeioFaltou(Boolean veio, int idAluno, int idClasse, int trimestre) {
        frequenciaSelecionado.setIdclasse(idClasse);
        frequenciaSelecionado.setIdmembro(idAluno);
        frequenciaSelecionado.setTrimestre(trimestre);

        if (veio == true) {
            frequenciaSelecionado.setPresente(1);
            frequenciaSelecionado.setFaltou(0);
        } else {
            frequenciaSelecionado.setFaltou(1);
            frequenciaSelecionado.setPresente(0);
        }
    }

    public void selecionaMembro(int id) {
        membro = mdao.buscarPorId(id);
    }

    public void buscarContribuintes(String nome) {
        List<Membros> lista = null;
        lista = (List<Membros>) mdao.buscarPorNomeLista(nome);
        listaMembros = lista;
    }

    public void presencaPorAluno(int idclasse, int trimestre, int ano) {
        listaFrequencia = dao.buscarPresencaLicoes(idclasse, trimestre, ano);
    }

    public void presencaPorClasse(int idclasse, int trimestre, int ano) {
        nomeDaClasse = cdao.buscarPorID(idclasse).getClassesNome();
        relatorioLista = null;
        double p = 0;
        double f = 0;
        double t = 0;

        try {
            relatorioLista = dao.frequenciaPorClasse(idclasse, trimestre, ano);
            for (Relatorios rel : relatorioLista) {
                p = p + rel.getPresente();
                f = f + rel.getFaltou();
            }
            t = p + f;
            p = 100 * p / t;
            f = 100 * f / t;
            ps = p;
            ft = f;
            tl = t;

            System.out.println(p + "% de Presencas e " + f + "% defaltas");

        } catch (ParseException ex) {
            System.out.println("Erro=" + ex);
            Logger.getLogger(FrequenciaControle.class.getName()).log(Level.SEVERE, null, ex);
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

    public void insert() {
        Calendar cal = GregorianCalendar.getInstance();
        frequencia.setAno(cal.get(Calendar.YEAR));
        frequencia.setLicao(frequenciaSelecionado.getLicao());
        frequencia.setPresente(frequenciaSelecionado.getPresente());
        frequencia.setFaltou(frequenciaSelecionado.getFaltou());
        frequencia.setIdclasse(frequenciaSelecionado.getIdclasse());
        frequencia.setIdmembro(frequenciaSelecionado.getIdmembro());
        frequencia.setTrimestre(frequenciaSelecionado.getTrimestre());
        if (frequenciaSelecionado.getLicao() != 0) {

            if (dao.insert(frequencia)) {

                addInfoMessage("Frequencia marcada com sucesso!");
            } else {
                addErrorMessage("Erro ao marcar frequencia!");
            }
        } else {
            addErrorMessage("Selecione a lição!");
        }
    }

    public void delete(Frequencia frequencia) {
        if (dao.delete(frequencia)) {
            addInfoMessage("Dados excluidos!");
        } else {
            addInfoMessage("Selecione um registro!");
        }

    }

    public void update() {

        if (dao.update(frequenciaSelecionado)) {
            addInfoMessage("Dados alterados com sucesso!");
        } else {
            addInfoMessage("Dados não alterados");
        }

    }

    public Frequencia getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Frequencia frequencia) {
        this.frequencia = frequencia;
    }

    public Frequencia getFrequenciaSelecionado() {
        return frequenciaSelecionado;
    }

    public void setFrequenciaSelecionado(Frequencia frequenciaSelecionado) {
        this.frequenciaSelecionado = frequenciaSelecionado;
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

    public List<Frequencia> getListaFrequencia() {
        return listaFrequencia;
    }

    public void setListaFrequencia(List<Frequencia> listaFrequencia) {
        this.listaFrequencia = listaFrequencia;
    }

    public Boolean getIsRederiza() {
        return isRederiza;
    }

    public void setIsRederiza(Boolean isRederiza) {
        this.isRederiza = isRederiza;
    }

    public Date getDatafim() {
        return datafim;
    }

    public void setDatafim(Date datafim) {
        this.datafim = datafim;
    }

    public Integer getPresenca() {
        return presenca;
    }

    public void setPresenca(Integer presenca) {
        this.presenca = presenca;
    }

    public Integer getFaltas() {
        return faltas;
    }

    public void setFaltas(Integer faltas) {
        this.faltas = faltas;
    }

    public Integer getIddomembro() {
        return iddomembro;
    }

    public void setIddomembro(Integer iddomembro) {
        this.iddomembro = iddomembro;
    }

    public String getNomeMembro() {
        return nomeMembro;
    }

    public void setNomeMembro(String nomeMembro) {
        this.nomeMembro = nomeMembro;
    }

    public List<Relatorios> getRelatorioLista() {
        return relatorioLista;
    }

    public void setRelatorioLista(List<Relatorios> relatorioLista) {
        this.relatorioLista = relatorioLista;
    }

    public Integer getClasse() {
        return classe;
    }

    public void setClasse(Integer classe) {
        this.classe = classe;
    }

    public Integer getTrimestres() {
        return trimestres;
    }

    public void setTrimestres(Integer trimestres) {
        this.trimestres = trimestres;
    }

    public String getNomeDaClasse() {
        return nomeDaClasse;
    }

    public void setNomeDaClasse(String nomeDaClasse) {
        this.nomeDaClasse = nomeDaClasse;
    }

    public Double getPs() {
        return ps;
    }

    public void setPs(Double ps) {
        this.ps = ps;
    }

    public Double getTl() {
        return tl;
    }

    public void setTl(Double tl) {
        this.tl = tl;
    }

    public Double getFt() {
        return ft;
    }

    public void setFt(Double ft) {
        this.ft = ft;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

}
