/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import javax.inject.Named;

import Modelo.Alunos;
import DAO.AlunoDAO;
import DAO.ClassesDAO;
import DAO.MembrosDAO;
import Modelo.Classes;
import Modelo.Membros;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
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
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ConversationScoped

/**
 *
 * @author Edson Ricardo
 */
@Named
public class AlunoControle implements Serializable {

    private static final long serialVersionUID = 1L;

    private Alunos aluno;
    private AlunoDAO dao;
    private Alunos alunoSelecionado;
    private Membros alunosMembros;
    private List<Membros> listaAlunos;
    private List<Membros> listaDaBusca;
    private Boolean isRederiza = false;
    private MembrosDAO mdao;
    private List<Membros> listaDeAlunos;
    private Membros membro;
    private List<Membros> alunosFiltrados;
    private String nomeAluno;
    private String titulo;
    private int idDaclasse;
    private List<String> licoesAfazer;
    private Integer idaluno;

    @Inject
    private Conversation conversation;

    public AlunoControle() {
        aluno = new Alunos();
        dao = new AlunoDAO();
        alunoSelecionado = new Alunos();
    }

    @PostConstruct
    public void init() {
        aluno = new Alunos();
        dao = new AlunoDAO();
        alunoSelecionado = new Alunos();
        alunosFiltrados = null;
        idDaclasse = 0;
        isRederiza = false;
        //lições a fazer
        List<String> licoestem = new ArrayList<String>();

        licoestem.add("1ª Lição");
        licoestem.add("2ª Lição");
        licoestem.add("3ª Lição");
        licoestem.add("4ª Lição");
        licoestem.add("5ª Lição");
        licoestem.add("6ª Lição");
        licoestem.add("7ª Lição");
        licoestem.add("8ª Lição");
        licoestem.add("9ª Lição");
        licoestem.add("10ª Lição");
        licoestem.add("11ª Lição");
        licoestem.add("12ª Lição");
        licoestem.add("13ª Lição");

        licoesAfazer = licoestem;

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
        mdao = new MembrosDAO();
        aluno = new Alunos();
        alunoSelecionado = new Alunos();
        listaDeAlunos = null;
        listaDaBusca = null;
        alunosFiltrados = null;
    }

    public void buscarLista(String nome) {
        if (nome == null || nome == "") {
            listaDeAlunos = null;
        } else {
            List<Membros> lista = null;
            lista = (List<Membros>) mdao.buscarPorNomeLista(nome);
            listaDeAlunos = lista;
        }
    }

    public void buscarListaDeAlunos(String nome) throws ParseException {

        List<Membros> lista = null;
        lista = dao.buscarPorNome(nome);
        listaDaBusca = lista;

    }

    public void buscarListaDeAlunosPorClasse(int idclasse) throws ParseException {
        System.out.println("ID passado=" + idclasse);
        Classes classe = new Classes();
        ClassesDAO cdao = new ClassesDAO();
        classe = cdao.buscarPorID(idclasse);
        titulo = classe.getClassesNome() + " | " + classe.getClassesTipo();
        listaDaBusca = dao.buscarAlunosPorClasse(idclasse);

    }

    public void buscarListaDtiniDtfim(Date dtini, Date dtfim) {

        List<Membros> lista = null;
        try {
            lista = (List<Membros>) dao.buscarPorDtiniDtfim(dtini, dtfim);
        } catch (ParseException ex) {
            Logger.getLogger(AlunoControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaDaBusca = lista;
    }

    public void buscarPorId(int id, int idclasse) {
        idaluno = id;
        alunosMembros = dao.buscarPorID(id, idclasse);
        alunoSelecionado = dao.buscarAlunoPorID(id, idclasse);
        nomeAluno = alunosMembros.getMembrosNome();
    }

    public String buscarPorMembroPorId(int id) {
        Date hoje = new Date();

        membro = new Membros();
        membro = mdao.buscarPorId(id);
        aluno.setAlunosIdmembro(membro.getIdmembros());
        aluno.setAlunosData(hoje);
        return membro.getMembrosNome();
    }

    public void renderizar(int ec) {
        Classes classe;
        classe = new Classes();
        ClassesDAO cdao = new ClassesDAO();
        classe = cdao.buscarPorID(ec);
        String tipo = classe.getClassesTipo();
        if ("DISCIPULADO".equals(tipo)) {
            isRederiza = true;
        } else {
            isRederiza = false;
        }
    }

    private Classes pegaClasse(Alunos aluno) {
        Classes classe = new Classes();
        ClassesDAO cdao = new ClassesDAO();
        classe = cdao.buscarPorID(aluno.getIdclasse());
        return classe;
    }

    public String qualTrimestre(Date data) {
        int trimestre = 0;
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(data);
        if (cal.get(Calendar.MONTH) <= 2) {
            trimestre = 1;
        } else if (cal.get(Calendar.MONTH) <= 5) {
            trimestre = 2;
        } else if (cal.get(Calendar.MONTH) <= 8) {
            trimestre = 3;
        } else if (cal.get(Calendar.MONTH) <= 11) {
            trimestre = 4;
        }
        return 4 + "º Trimestre";
    }

    public void insert() {
        Date hoje = new Date();
        //aluno.setAlunosIdmembro(membro.getIdmembros());
        aluno.setAlunosData(hoje);
        if (dao.insert(aluno)) {
            //atualiza qtde de aluno ana classe
            Classes cl = new Classes();
            ClassesDAO cdao = new ClassesDAO();
            cl = pegaClasse(aluno);
            int qtd = 0;
            if (cl.getClassesQtdeMemb() != null) {
                qtd = cl.getClassesQtdeMemb();
            }
            qtd = qtd + 1;
            cl.setClassesQtdeMemb(qtd);
            boolean update = cdao.update(cl);
            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void delete(Membros membro, int idclasse) {

        Classes cl = new Classes();
        ClassesDAO cdao = new ClassesDAO();
        aluno = dao.buscarAlunoPorID(membro.getIdmembros(), idclasse);

        if (dao.delete(aluno)) {

            cl = pegaClasse(aluno);
            int qtd = cl.getClassesQtdeMemb();
            qtd = qtd - 1;
            cl.setClassesQtdeMemb(qtd);
            boolean update = cdao.update(cl);

            addInfoMessage("Apresentação de Criança excluida!");
        } else {
            addInfoMessage("Selecione uma Apresentação de Criança!");
        }

    }

    public void update() {

        if (dao.update(alunoSelecionado)) {
            addInfoMessage("Aluno alterado com sucesso!");
        } else {
            addInfoMessage("aluno não alterado!");
        }

    }

    public void update(Alunos aluno) {

        if (dao.update(aluno)) {
            addInfoMessage("Aluno alterado com sucesso!");
        } else {
            addInfoMessage("aluno não alterado!");
        }

    }

    public Alunos getAluno() {
        return aluno;
    }

    public void setAluno(Alunos aluno) {
        this.aluno = aluno;
    }

    public Alunos getAlunoSelecionado() {
        return alunoSelecionado;
    }

    public void setAlunoSelecionado(Alunos alunoSelecionado) {
        this.alunoSelecionado = alunoSelecionado;
    }

    public Boolean getIsRederiza() {
        return isRederiza;
    }

    public void setIsRederiza(Boolean isRederiza) {
        this.isRederiza = isRederiza;
    }

    public List<Membros> getListaDeAlunos() {
        return listaDeAlunos;
    }

    public void setListaDeAlunos(List<Membros> listaDeAlunos) {
        this.listaDeAlunos = listaDeAlunos;
    }

    public Membros getAlunosMembros() {
        return alunosMembros;
    }

    public void setAlunosMembros(Membros alunosMembros) {
        this.alunosMembros = alunosMembros;
    }

    public List<Membros> getListaAlunos() {
        return listaAlunos;
    }

    public void setListaAlunos(List<Membros> listaAlunos) {
        this.listaAlunos = listaAlunos;
    }

    public List<Membros> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Membros> listaDaBusca) {
        this.listaDaBusca = listaDaBusca;
    }

    public List<Membros> getAlunosFiltrados() {
        return alunosFiltrados;
    }

    public void setAlunosFiltrados(List<Membros> alunosFiltrados) {
        this.alunosFiltrados = alunosFiltrados;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIdDaclasse() {
        return idDaclasse;
    }

    public void setIdDaclasse(int idDaclasse) {
        this.idDaclasse = idDaclasse;
    }

    public List<String> getLicoesAfazer() {
        return licoesAfazer;
    }

    public void setLicoesAfazer(List<String> licoesAfazer) {
        this.licoesAfazer = licoesAfazer;
    }

    public Integer getIdaluno() {
        return idaluno;
    }

    public void setIdaluno(Integer idaluno) {
        this.idaluno = idaluno;
    }

    public Conversation getConversation() {
        return conversation;
    }

}
