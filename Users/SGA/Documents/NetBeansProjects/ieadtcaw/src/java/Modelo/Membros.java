package Modelo;
// Generated 11/05/2018 15:09:44 by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Membros generated by hbm2java
 */
@Entity
@Table(name = "membros",
        catalog = "dadosk",
        uniqueConstraints = @UniqueConstraint(columnNames = "membrosCPF")
)
public class Membros implements java.io.Serializable {

    private Integer idmembros;
    private String membrosNome;
    private Date membrosDataNasc;
    private String membrosEndereco;
    private String membrosNumero;
    private String membrosBairro;
    private String membrosCep;
    private String membrosFone;
    private String membrosCelular;
    private String membrosEstadoCivil;
    private String membrosFilhos;
    private String membrosProfissao;
    private String membrosEmpresa;
    private String membrosFoneCom;
    private String membrosCarteiraMemb;
    private Date membrosDtEmissao;
    private Date membrosDtConversao;
    private Date membrosDtBatismo;
    private String membrosCargoIgreja;
    private String membrosObs;
    private String membrosComplemento;
    private String membrosNaturalidade;
    private String membrosRg;
    private String membrosCpf;
    private Date membrosDataCasamento;
    private Date membrosCasamento;
    private String membrosTipo;
    private String membrosSexo;
    private String membrosCidade;
    private String membrosUf;
    private Date membrosUltimaVisita;
    private Boolean membrosEprof;

    public Membros() {
    }

    public Membros(String membrosNome) {
        this.membrosNome = membrosNome;
    }

    public Membros(String membrosNome, Date membrosDataNasc, String membrosEndereco, String membrosNumero, String membrosBairro, String membrosCep, String membrosFone, String membrosCelular, String membrosEstadoCivil, String membrosFilhos, String membrosProfissao, String membrosEmpresa, String membrosFoneCom, String membrosCarteiraMemb, Date membrosDtEmissao, Date membrosDtConversao, Date membrosDtBatismo, String membrosCargoIgreja, String membrosObs, String membrosComplemento, String membrosNaturalidade, String membrosRg, String membrosCpf, Date membrosDataCasamento, Date membrosCasamento, String membrosTipo, String membrosSexo, String membrosCidade, String membrosUf, Date membrosUltimaVisita, Boolean membrosEprof) {
        this.membrosNome = membrosNome;
        this.membrosDataNasc = membrosDataNasc;
        this.membrosEndereco = membrosEndereco;
        this.membrosNumero = membrosNumero;
        this.membrosBairro = membrosBairro;
        this.membrosCep = membrosCep;
        this.membrosFone = membrosFone;
        this.membrosCelular = membrosCelular;
        this.membrosEstadoCivil = membrosEstadoCivil;
        this.membrosFilhos = membrosFilhos;
        this.membrosProfissao = membrosProfissao;
        this.membrosEmpresa = membrosEmpresa;
        this.membrosFoneCom = membrosFoneCom;
        this.membrosCarteiraMemb = membrosCarteiraMemb;
        this.membrosDtEmissao = membrosDtEmissao;
        this.membrosDtConversao = membrosDtConversao;
        this.membrosDtBatismo = membrosDtBatismo;
        this.membrosCargoIgreja = membrosCargoIgreja;
        this.membrosObs = membrosObs;
        this.membrosComplemento = membrosComplemento;
        this.membrosNaturalidade = membrosNaturalidade;
        this.membrosRg = membrosRg;
        this.membrosCpf = membrosCpf;
        this.membrosDataCasamento = membrosDataCasamento;
        this.membrosCasamento = membrosCasamento;
        this.membrosTipo = membrosTipo;
        this.membrosSexo = membrosSexo;
        this.membrosCidade = membrosCidade;
        this.membrosUf = membrosUf;
        this.membrosUltimaVisita = membrosUltimaVisita;
        this.membrosEprof = membrosEprof;

    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "idmembros", unique = true, nullable = false)
    public Integer getIdmembros() {
        return this.idmembros;
    }

    public void setIdmembros(Integer idmembros) {
        this.idmembros = idmembros;
    }

    @Column(name = "membrosNome", nullable = false, length = 200)
    public String getMembrosNome() {
        return this.membrosNome;
    }

    public void setMembrosNome(String membrosNome) {
        this.membrosNome = membrosNome;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "membrosDataNasc", length = 10)
    public Date getMembrosDataNasc() {
        return this.membrosDataNasc;
    }

    public void setMembrosDataNasc(Date membrosDataNasc) {
        this.membrosDataNasc = membrosDataNasc;
    }

    @Column(name = "membrosEndereco")
    public String getMembrosEndereco() {
        return this.membrosEndereco;
    }

    public void setMembrosEndereco(String membrosEndereco) {
        this.membrosEndereco = membrosEndereco;
    }

    @Column(name = "membrosNumero", length = 45)
    public String getMembrosNumero() {
        return this.membrosNumero;
    }

    public void setMembrosNumero(String membrosNumero) {
        this.membrosNumero = membrosNumero;
    }

    @Column(name = "membrosBairro", length = 80)
    public String getMembrosBairro() {
        return this.membrosBairro;
    }

    public void setMembrosBairro(String membrosBairro) {
        this.membrosBairro = membrosBairro;
    }

    @Column(name = "membrosCep", length = 45)
    public String getMembrosCep() {
        return this.membrosCep;
    }

    public void setMembrosCep(String membrosCep) {
        this.membrosCep = membrosCep;
    }

    @Column(name = "membrosFone", length = 45)
    public String getMembrosFone() {
        return this.membrosFone;
    }

    public void setMembrosFone(String membrosFone) {
        this.membrosFone = membrosFone;
    }

    @Column(name = "membrosCelular", length = 45)
    public String getMembrosCelular() {
        return this.membrosCelular;
    }

    public void setMembrosCelular(String membrosCelular) {
        this.membrosCelular = membrosCelular;
    }

    @Column(name = "membrosEstadoCivil", length = 45)
    public String getMembrosEstadoCivil() {
        return this.membrosEstadoCivil;
    }

    public void setMembrosEstadoCivil(String membrosEstadoCivil) {
        this.membrosEstadoCivil = membrosEstadoCivil;
    }

    @Column(name = "membrosFilhos", length = 45)
    public String getMembrosFilhos() {
        return this.membrosFilhos;
    }

    public void setMembrosFilhos(String membrosFilhos) {
        this.membrosFilhos = membrosFilhos;
    }

    @Column(name = "membrosProfissao", length = 200)
    public String getMembrosProfissao() {
        return this.membrosProfissao;
    }

    public void setMembrosProfissao(String membrosProfissao) {
        this.membrosProfissao = membrosProfissao;
    }

    @Column(name = "membrosEmpresa")
    public String getMembrosEmpresa() {
        return this.membrosEmpresa;
    }

    public void setMembrosEmpresa(String membrosEmpresa) {
        this.membrosEmpresa = membrosEmpresa;
    }

    @Column(name = "membrosFoneCom", length = 45)
    public String getMembrosFoneCom() {
        return this.membrosFoneCom;
    }

    public void setMembrosFoneCom(String membrosFoneCom) {
        this.membrosFoneCom = membrosFoneCom;
    }

    @Column(name = "membrosCarteiraMemb", length = 45)
    public String getMembrosCarteiraMemb() {
        return this.membrosCarteiraMemb;
    }

    public void setMembrosCarteiraMemb(String membrosCarteiraMemb) {
        this.membrosCarteiraMemb = membrosCarteiraMemb;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "membrosDtEmissao", length = 10)
    public Date getMembrosDtEmissao() {
        return this.membrosDtEmissao;
    }

    public void setMembrosDtEmissao(Date membrosDtEmissao) {
        this.membrosDtEmissao = membrosDtEmissao;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "membrosDtConversao", length = 10)
    public Date getMembrosDtConversao() {
        return this.membrosDtConversao;
    }

    public void setMembrosDtConversao(Date membrosDtConversao) {
        this.membrosDtConversao = membrosDtConversao;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "membrosDtBatismo", length = 10)
    public Date getMembrosDtBatismo() {
        return this.membrosDtBatismo;
    }

    public void setMembrosDtBatismo(Date membrosDtBatismo) {
        this.membrosDtBatismo = membrosDtBatismo;
    }

    @Column(name = "membrosCargoIgreja", length = 45)
    public String getMembrosCargoIgreja() {
        return this.membrosCargoIgreja;
    }

    public void setMembrosCargoIgreja(String membrosCargoIgreja) {
        this.membrosCargoIgreja = membrosCargoIgreja;
    }

    @Column(name = "membrosObs", length = 200)
    public String getMembrosObs() {
        return this.membrosObs;
    }

    public void setMembrosObs(String membrosObs) {
        this.membrosObs = membrosObs;
    }

    @Column(name = "membrosComplemento", length = 100)
    public String getMembrosComplemento() {
        return this.membrosComplemento;
    }

    public void setMembrosComplemento(String membrosComplemento) {
        this.membrosComplemento = membrosComplemento;
    }

    @Column(name = "membrosNaturalidade", length = 200)
    public String getMembrosNaturalidade() {
        return this.membrosNaturalidade;
    }

    public void setMembrosNaturalidade(String membrosNaturalidade) {
        this.membrosNaturalidade = membrosNaturalidade;
    }

    @Column(name = "membrosRG", length = 45)
    public String getMembrosRg() {
        return this.membrosRg;
    }

    public void setMembrosRg(String membrosRg) {
        this.membrosRg = membrosRg;
    }

    @Column(name = "membrosCPF", length = 45)
    public String getMembrosCpf() {
        return this.membrosCpf;
    }

    public void setMembrosCpf(String membrosCpf) {
        this.membrosCpf = membrosCpf;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "membrosDataCasamento", length = 10)
    public Date getMembrosDataCasamento() {
        return this.membrosDataCasamento;
    }

    public void setMembrosDataCasamento(Date membrosDataCasamento) {
        this.membrosDataCasamento = membrosDataCasamento;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "membrosCasamento", length = 10)
    public Date getMembrosCasamento() {
        return this.membrosCasamento;
    }

    public void setMembrosCasamento(Date membrosCasamento) {
        this.membrosCasamento = membrosCasamento;
    }

    @Column(name = "membrosTipo", length = 45)
    public String getMembrosTipo() {
        return this.membrosTipo;
    }

    public void setMembrosTipo(String membrosTipo) {
        this.membrosTipo = membrosTipo;
    }

    @Column(name = "membrosSexo", length = 45)
    public String getMembrosSexo() {
        return this.membrosSexo;
    }

    public void setMembrosSexo(String membrosSexo) {
        this.membrosSexo = membrosSexo;
    }

    @Column(name = "membrosCidade", length = 100)
    public String getMembrosCidade() {
        return this.membrosCidade;
    }

    public void setMembrosCidade(String membrosCidade) {
        this.membrosCidade = membrosCidade;
    }

    @Column(name = "membrosUF", length = 45)
    public String getMembrosUf() {
        return this.membrosUf;
    }

    public void setMembrosUf(String membrosUf) {
        this.membrosUf = membrosUf;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "membrosUltimaVisita", length = 10)
    public Date getMembrosUltimaVisita() {
        return this.membrosUltimaVisita;
    }

    public void setMembrosUltimaVisita(Date membrosUltimaVisita) {
        this.membrosUltimaVisita = membrosUltimaVisita;
    }

    @Column(name = "membrosEprof")
    public Boolean getMembrosEprof() {
        return this.membrosEprof;
    }

    public void setMembrosEprof(Boolean membrosEprof) {
        this.membrosEprof = membrosEprof;
    }

}
