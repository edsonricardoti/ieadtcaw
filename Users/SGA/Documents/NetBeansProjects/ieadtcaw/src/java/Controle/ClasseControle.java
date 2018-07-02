/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import DAO.AlunoDAO;
import javax.faces.bean.ManagedBean;

import Modelo.Classes;
import DAO.ClassesDAO;
import DAO.FrequenciaDAO;
import DAO.MembrosDAO;
import Modelo.Alunos;
import Modelo.Frequencia;
import Modelo.Membros;
import static Util.FacesUtil.addErrorMessage;
import static Util.FacesUtil.addInfoMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped

/**
 *
 * @author Edson Ricardo
 */
public class ClasseControle {

    private Classes classe;
    private ClassesDAO dao;
    private AlunoDAO adao;
    private MembrosDAO mdao;
    private List<Frequencia> frequenciaLista;
    private FrequenciaDAO fdao;
    private Classes classeSelecionado;
    private List<Classes> listaClasses;
    private List<Classes> listaDaBusca;
    private List<Classes> listaDeAlunos;
    private Boolean isRederiza = false;
    private UploadedFile uploadedFile;
    private List<Membros> alunos;
    private Alunos aluno;
    private Membros membro;
    private Integer trimestre;
    private Boolean veio;
    private Integer idAlunos;
    private String nomeAluno;

    public ClasseControle() {
        classe = new Classes();
        aluno = new Alunos();
        membro = new Membros();
        dao = new ClassesDAO();
        mdao = new MembrosDAO();
        classeSelecionado = new Classes();
        listaClasses = null;
    }

    @PostConstruct
    public void init() {
        classe = new Classes();
        dao = new ClassesDAO();
        listaClasses = null;
        classeSelecionado = new Classes();
        listaClasses = dao.selectAll();
        qualTrimestre();
        frequenciaLista = null;

    }

    public void limpaFormulario() {
        classe = new Classes();
        classeSelecionado = new Classes();
        listaDaBusca = null;
        listaClasses = null;
    }

    public void Alunos(int classe) throws ParseException {

        adao = new AlunoDAO();
        alunos = adao.buscarAlunosPorClasse(classe);
    }

    public void PegaOaluno(int idBuscado) {
        System.out.println("Id do aluno:" + idBuscado);
        aluno = adao.buscarAlunoPorID(idBuscado, classeSelecionado.getIdclasses());
        membro = mdao.buscarPorId(idBuscado);
        nomeAluno = membro.getMembrosNome();
        idAlunos = idBuscado;
        fdao = new FrequenciaDAO();
        frequenciaLista = fdao.buscarLicoesAluno(idBuscado, classeSelecionado.getIdclasses());


    }

    public void buscarLista(String nome) {

        List<Classes> lista = null;
        try {
            lista = (List<Classes>) dao.buscarPorNome(nome);
        } catch (ParseException ex) {
            Logger.getLogger(ClasseControle.class.getName()).log(Level.SEVERE, null, ex);
        }
        listaDaBusca = lista;
    }

    public void buscarPorId(int id) {
        classeSelecionado = dao.buscarPorID(id);

    }

    public void qualTrimestre() {

        Calendar cal = GregorianCalendar.getInstance();
        if (cal.get(Calendar.MONTH) <= 2) {
            trimestre = 1;
        } else if (cal.get(Calendar.MONTH) <= 5) {
            trimestre = 2;
        } else if (cal.get(Calendar.MONTH) <= 8) {
            trimestre = 3;
        } else if (cal.get(Calendar.MONTH) <= 11) {
            trimestre = 4;
        }

    }

    public String nomeTrimestre(Date data) {
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

        if (dao.insert(classe)) {

            addInfoMessage("Dados salvo com sucesso!");
        } else {
            addErrorMessage("Erro ao salvar os dados!");
        }
    }

    public void delete(Classes atas) {
        if (dao.delete(atas)) {
            addInfoMessage("Classe excluida!");
        } else {
            addInfoMessage("Selecione uma Classe!");
        }

    }

    public void update() {

        if (dao.update(classeSelecionado)) {
            addInfoMessage("Classe alterada com sucesso!");
        } else {
            addInfoMessage("Classe não alterada");
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

    public Classes getClasses() {
        return classe;
    }

    public void setClasses(Classes classe) {
        this.classe = classe;
    }

    public Classes getClassesSelecionado() {
        return classeSelecionado;
    }

    public void setClassesSelecionado(Classes classeSelecionado) {
        this.classeSelecionado = classeSelecionado;
    }

    public List<Classes> getListaClasses() {
        listaClasses = dao.selectAll();
        return listaClasses;
    }

    public void setListaClasses(List<Classes> listaClasses) {
        this.listaClasses = listaClasses;
    }

    public List<Classes> getListaDaBusca() {
        return listaDaBusca;
    }

    public void setListaDaBusca(List<Classes> listaDaBusca) {
        this.listaDaBusca = listaDaBusca;
    }

    public Boolean getIsRederiza() {
        return isRederiza;
    }

    public void setIsRederiza(Boolean isRederiza) {
        this.isRederiza = isRederiza;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Classes getClasse() {
        return classe;
    }

    public void setClasse(Classes classe) {
        this.classe = classe;
    }

    public Classes getClasseSelecionado() {
        return classeSelecionado;
    }

    public void setClasseSelecionado(Classes classeSelecionado) {
        this.classeSelecionado = classeSelecionado;
    }

    public List<Classes> getListaDeAlunos() {
        return listaDeAlunos;
    }

    public void setListaDeAlunos(List<Classes> listaDeAlunos) {
        this.listaDeAlunos = listaDeAlunos;
    }

    public List<Membros> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Membros> alunos) {
        this.alunos = alunos;
    }

    public String dataFormatada(int id) {
        classe = dao.buscarPorID(id);
        Date data = classe.getClassesData();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }

    public Integer getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(Integer trimestre) {
        this.trimestre = trimestre;
    }

    public Boolean getVeio() {
        return veio;
    }

    public void setVeio(Boolean veio) {
        this.veio = veio;
    }

    public Integer getIdAlunos() {
        return idAlunos;
    }

    public void setIdAlunos(Integer idAlunos) {
        this.idAlunos = idAlunos;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public Alunos getAluno() {
        return aluno;
    }

    public void setAluno(Alunos aluno) {
        this.aluno = aluno;
    }

    public List<Frequencia> getFrequenciaLista() {
        return frequenciaLista;
    }

    public void setFrequenciaLista(List<Frequencia> frequenciaLista) {
        this.frequenciaLista = frequenciaLista;
    }

}
