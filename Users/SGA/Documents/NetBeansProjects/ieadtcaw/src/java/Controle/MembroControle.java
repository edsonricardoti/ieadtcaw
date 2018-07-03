/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.faces.bean.ManagedBean;

import Modelo.Membros;
import DAO.MembrosDAO;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped

/**
 *
 * @author Edson Ricardo
 */
public class MembroControle {

    private Membros membro;
    private MembrosDAO dao;
    private Membros membroSelecionado;
    private List<Membros> listaMembros;
    private List<Membros> listaDaBusca;
    private List<Membros> listaAniversariantes;
    private Boolean isRederiza = false;
    private String Nome;
    private String filtros;
    private List<String> selectedOptions;

    public MembroControle() {
        membro = new Membros();
        dao = new MembrosDAO();
        membroSelecionado = new Membros();
    }

    @PostConstruct
    public void init() {
        membro = new Membros();
        dao = new MembrosDAO();
        membroSelecionado = new Membros();

    }

    public List<Membros> lista() {
        listaMembros = dao.selectDirigentes();
        return listaMembros;
    }

    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public List<Membros> listaFiltrados(String filtro, String nome) {
        filtro = filtro.replace("[", "(");
        filtro = filtro.replace("]", ")");
        System.out.println("Filtro = " + selectedOptions);
        listaMembros = dao.selectAllFiltrado(filtro, nome);
        return listaMembros;
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
        listaMembros = dao.selectProfessor();
        return listaMembros;
    }

    public void limpaFormulario() {
        membro = new Membros();
        membroSelecionado = new Membros();
        listaDaBusca = null;
        listaAniversariantes = null;
        listaMembros = null;
    }


    public void buscarID(int id) {
        System.out.println("Entrou na busca por ID=" + id);
        membroSelecionado = dao.buscarPorId(id);
        // membro = membroSelecionado;

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

            addInfoMessage("Dados salvo com sucesso!");
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

}
