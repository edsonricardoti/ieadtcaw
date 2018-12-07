/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.inject.Named;

import Modelo.Membros;
import DAO.MembrosDAO;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.Conversation;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;

/**
 *
 * @author Edson Ricardo
 */
@ViewAccessScoped
@Named
public class MembroControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Membros membro;
    private Membross membross;
    private MembrosDAO dao;
    private Membros membroSelecionado;
    private List<Membros> listaMembros;
    private List<Membros> listaDaBusca;
    private List<Membros> listaProfessores;
    private List<Membros> listaAniversariantes;
    private Boolean isRederiza = false;
    private String Nome;
    private String filtros;
    private List<String> selectedOptions;
    private Integer mes;

    @Inject
    private Conversation conversation;

    public MembroControle() {
        membro = new Membros();
        dao = new MembrosDAO();
        membroSelecionado = new Membros();

//        model = new LazyDataModel<Membros>() {
//
//            private static final long serialVersionUID = 1L;
//            Membross membross = new Membross();
//
//            @Override
//            public List<Membros> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
//
//                filtro.setPrimeiroRegistro(first);
//                filtro.setQuantidadeRegistros(pageSize);
//                filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
//                filtro.setPropriedadeOrdenacao(sortField);
//
//                setRowCount(membross.quantidadeFiltrados(filtro));
//
//                return membross.filtrados(filtro);
//
//            }
//
//        };
    }

    @PostConstruct
    public void init() {
        membro = new Membros();
        dao = new MembrosDAO();
        membroSelecionado = new Membros();
        //listaMembros = dao.selectAll();

        // filtro = new FiltroMembros();
    }

    public String fechar() throws IOException {
        this.conversation.close();
        FacesContext.getCurrentInstance().getExternalContext().redirect("../../inicio.xhtml?faces-redirect=true");
        return "";
    }

    public List<Membros> lista() {
        listaMembros = dao.selectDirigentes();
        listaProfessor();
        return listaMembros;
    }

    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public void listaFiltrados(String filtro, String nome) {
        System.out.println("Passado filtro=" + filtro + " e nome =" + nome);
        filtro = filtro.replace("[", "(");
        filtro = filtro.replace("]", ")");
        System.out.println("Filtro = " + selectedOptions);
        if (filtro != "") {
            listaMembros = dao.selectAllFiltrado(filtro, nome);
        }

    }

    //picklist
    public void listaFiltradosCad(String nome) {

        //filtro.setTipos(meufiltro);
        // filtro = filtro.replace("[", "(");
        // filtro = filtro.replace("]", ")");
        listaMembros = dao.selectAllFiltradoCad(nome);

    }

     public void printPDF() throws JRException, IOException{
    List sources =new  ArrayList<>();
    //listaMembros = dao.selectAll();
    sources =listaMembros;
    String filename = "membros.pdf";
    String jasperPath="/resources/membrosgeral.jasper";
    this.PDF(null,jasperPath,sources,filename);
    
        
    }
     
    public void PDF(Map<String,Object> params, String jasperPath,List<?> dataSource, String fileName) throws JRException,IOException{
    String welativeWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
    File file = new File(welativeWebPath);
    
    JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataSource,false);
    JasperPrint print = JasperFillManager.fillReport(file.getPath(),params,source);
    HttpServletResponse response =(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
    response.addHeader("Content-disposition","attachment;filename="+fileName);
    ServletOutputStream stream = response.getOutputStream();
    
    JasperExportManager.exportReportToPdfStream(print,stream);
    FacesContext.getCurrentInstance().responseComplete();
    
    }

    public List<Membros> listaTodos() {
        listaMembros = dao.selectAll();
        return listaMembros;
    }

    public String PegaDia(Membros membro) {
        Date data = membro.getMembrosDataNasc();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        //int mes = calendario.get(Calendar.MONTH);
        String meudia = Integer.toString(dia);

        return meudia;
    }

    public String PegaDiaCasado(Membros membro) {
        Date data = membro.getMembrosDataCasamento();
        Calendar calendario = Calendar.getInstance();
        if (data == null) {
            data = new Date();
        } else {
            calendario.setTime(data);
        }
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        //int mes = calendario.get(Calendar.MONTH);
        String meudia = Integer.toString(dia);

        return meudia;
    }

    public String NomeDaBoda(Date datacasou) {
        int i;
        i = pegaIdade(datacasou);

        String bodas[] = {"Bodas de Papel", "Bodas de Algodão", "Bodas de Trigo", "Bodas de Flores e Frutas ou Cera", "Bodas de Madeira ou Ferro",
            "Bodas de Perfume ou Açúcar", "Bodas de Latão ou Lã", "Bodas de Papoula ou Barro", "Bodas de Cerâmica ou Vime", "Bodas de Estanho",
            "Bodas de Aço", "Bodas de Seda ou Ônix", "Bodas de Linho ou Renda", "Bodas de Marfim", "Bodas de Cristal", "Bodas de Turmalina",
            "Bodas de Rosa", "Bodas de Turquesa", "Bodas de Cretone ou Água marinha", "Bodas de Porcelana", "Bodas de Zircão", "Bodas de Louça",
            "Bodas de Palha", "Bodas de Opala", "Bodas de Prata", "Bodas de Alexandrita", "Bodas de Crisopázio", "Bodas de Hematita", "Bodas de Erva",
            "Bodas de Pérola", "Bodas de Nácar", "Bodas de Pinho", "Bodas de Crizo", "Bodas de Oliveira", "Bodas de Coral", "Bodas de Cedro",
            "Bodas de Aventurina", "Bodas de Carvalho", "Bodas de Mármore", "Bodas de Rubi ou Esmeralda", "Bodas de Seda", "Bodas de Prata Dourada",
            "Bodas de Azeviche", "Bodas de Carbonato", "Bodas de Platina ou Safira", "Bodas de Alabastro", "Bodas de Jaspe", "Bodas de Granito",
            "Bodas de Heliotrópio", "Bodas de Ouro", "Bodas de Bronze", "Bodas de Argila", "Bodas de Antimônio", "Bodas de Níquel", "Bodas de Ametista",
            "Bodas de Malaquita", "Bodas de Lápis Lazuli", "Bodas de Vidro", "Bodas de Cereja", "Bodas de Diamante ou Jade", "Bodas de Cobre",
            "Bodas de Telurita", "Bodas de Sândalo ou Lilás", "Bodas de Fabulita", "Bodas de Ferro", "Bodas de Ébano", "Bodas de Neve", "Bodas de Chumbo",
            "Bodas de Mercúrio", "Bodas de Vinho", "Bodas de Zinco", "Bodas de Aveia", "Bodas de Manjerona", "Bodas de Macieira",
            "Bodas de Brilhante ou Alabastro", "Bodas de Cipreste", "Bodas de Alfazema", "Bodas de Benjoim", "Bodas de Café", "Bodas de Carvalho",
            "Bodas de Cacau", "Bodas de Cravo", "Bodas de Begônia", "Bodas de Crisântemo", "Bodas de Girassol", "Bodas de Hortensia", "Bodas de Nogueira",
            "Bodas de Pera", "Bodas de Figueira", "Bodas de Álamo", "Bodas de Pinheiro"};
        if (i == 0) {
            return "Casou a menos de um ano.";
        } else {
            return (i + " Ano(s) de casados, " + bodas[i - 1]);
        }

    }

    private Integer pegaIdade(Date casouem) {

        GregorianCalendar hj = new GregorianCalendar();
        GregorianCalendar nascimento = new GregorianCalendar();
        if (casouem != null) {
            nascimento.setTime(casouem);
        }
        int anohj = hj.get(Calendar.YEAR);
        int anoNascimento = nascimento.get(Calendar.YEAR);

        return anohj - anoNascimento;
    }

    public List<Membros> listaProfessor() {
        listaProfessores = dao.selectProfessor();
        return listaProfessores;
    }

    public void limpaFormulario() {

        membro = new Membros();
        membroSelecionado = new Membros();
        listaDaBusca = null;
        listaAniversariantes = null;
        listaMembros = null;
        membross = null;
        dao = null;
        listaDaBusca = null;
        listaProfessores = null;

    }

    @PreDestroy
    public void destroi() {
        membro = new Membros();
        membroSelecionado = new Membros();
        listaDaBusca = null;
        listaAniversariantes = null;
        listaMembros = null;
        membross = null;
        dao = null;
        listaDaBusca = null;
        listaProfessores = null;
    }

    public void buscarID(int id) {
        System.out.println("Entrou na busca por ID=" + id);
        membroSelecionado = dao.buscarPorId(id);

        // membro = membroSelecionado;
    }

    public String buscarIDView(int id) {
        System.out.println("Entrou na busca por ID=" + id);
        membroSelecionado = dao.buscarPorId(id);
        String ec = membroSelecionado.getMembrosEstadoCivil();
        if ("Casado(a)".equals(ec)) {
            isRederiza = true;
        } else {
            membro.setMembrosDataCasamento(null);
            membroSelecionado.setMembrosDataCasamento(null);
            isRederiza = false;
        }
        return "consulta.xhtml";
        // membro = membroSelecionado;

    }

    public String fecharPg() {

        return "consultar.xhtml";
    }

    public void HabilitaProf(Membros habilitado) {
        System.out.println("Entror para habilitar membro = " + habilitado.getMembrosNome());
        String status;
        membroSelecionado = dao.buscarPorId(habilitado.getIdmembros());
        if (habilitado.getMembrosEprof()) {
            membroSelecionado.setMembrosEprof(true);
            status = "Habilitado";
        } else {
            membroSelecionado.setMembrosEprof(false);
            status = "Dessabilitado";
        }

        if (dao.update(membroSelecionado)) {
            addInfoMessage("Membro " + status + " como professor da EBD!");
        } else {
            addInfoMessage("Falha ao habilitar/desabilitar!");
        }
    }

    public void buscarVisitante(String nome) {
        System.out.println("Entrou na busca nome=" + nome);
        membroSelecionado = dao.buscarVisitantePorNome(nome);
        // membro = membroSelecionado;

    }

    public void buscarLista(String nome) {
        List<Membros> lista;
        lista = dao.buscarPorNomeLista(nome);
        listaDaBusca = lista;
        membro = new Membros();

    }

    public void buscarAniversariantes(String mes) {
        List<Membros> lista;
        lista = dao.buscarAniversariantes(mes);
        listaAniversariantes = lista;
        membro = new Membros();

    }

    public void buscarCasados(String mes) {
        List<Membros> lista;
        lista = dao.buscarCasados(mes);
        listaAniversariantes = lista;
        membro = new Membros();

    }

    public void buscarPorEstCivil(String estacivil) {
        List<Membros> lista;
        lista = dao.buscarPorEstCivil(estacivil);
        listaMembros = lista;
        membro = new Membros();

    }

    public void buscarListaVisitante(String nome) {
        List<Membros> lista;
        lista = dao.buscarVisitantePorNomeLista(nome);
        listaDaBusca = lista;
        membro = new Membros();
    }

    public void renderizar(String ec) {
        if ("Casado(a)".equals(ec)) {
            isRederiza = true;
        } else {
            membro.setMembrosDataCasamento(null);
            membroSelecionado.setMembrosDataCasamento(null);
            isRederiza = false;
        }
    }

    public void preProcessPDFPaisagem(Object document) {
        Document pdf = (Document) document;
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.open();
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

    public void insert() {
        Date data = new Date();
        membro.setMembrosCasamento(data);
        if (dao.insert(membro)) {
            limpaFormulario();
            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados! Se estiver cadastrando um Membro verifique se o CPF está correto e se a data de nascimento foi preenchida. Refaça o cadastro.");
        }
    }

    public void maisum() throws IOException {
        Date data = new Date();
        membro.setMembrosCasamento(data);
        if (dao.insert(membro)) {
            membro = new Membros();
            addInfoMessage("Dados salvo com sucesso!");
            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastro.xhtml");

        } else {
            addErrorMessage("Erro ao salvar os dados! Se estiver cadastrando um Membro verifique se o CPF está correto e se a data de nascimento foi preenchida. Refaça o cadastro.");
        }
    }

    public void delete(Membros membros) {
        if (dao.delete(membros)) {
            addInfoMessage("Membro excluido!");
        } else {
            addInfoMessage("Selecione um Membro!");
        }

    }

    public void update() {

        if (dao.update(membroSelecionado)) {
            addInfoMessage("Membro alterado com sucesso!");
        } else {
            addInfoMessage("Membro não alterado");
        }

    }

    public List<Membros> getListaMembros() {
        return listaMembros;
    }

    public void setListaMembros(List<Membros> listaMembros) {
        this.listaMembros = listaMembros;
    }

    public List<Membros> getListaAniversariantes() {
        return listaAniversariantes;
    }

    public void setListaAniversariantes(List<Membros> listaAniversariantes) {
        this.listaAniversariantes = listaAniversariantes;
    }

    public Membros getMembro() {
        return membro;
    }

    public void setMembro(Membros membro) {
        this.membro = membro;
    }

    public Membros getMembroSelecionado() {
        return membroSelecionado;
    }

    public void setMembroSelecionado(Membros membroSelecionado) {
        this.membroSelecionado = membroSelecionado;
    }

    public List<Membros> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Membros> listaDaBusca) {
        this.listaDaBusca = listaDaBusca;
    }

    public Boolean getIsRederiza() {
        return isRederiza;
    }

    public void setIsRederiza(Boolean isRederiza) {
        this.isRederiza = isRederiza;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getFiltros() {
        return filtros;
    }

    public void setFiltros(String filtros) {
        this.filtros = filtros;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public List<Membros> getListaProfessores() {
        return listaProfessores;
    }

    public void setListaProfessores(List<Membros> listaProfessores) {
        this.listaProfessores = listaProfessores;
    }

    public Conversation getConversation() {
        return conversation;
    }

}
