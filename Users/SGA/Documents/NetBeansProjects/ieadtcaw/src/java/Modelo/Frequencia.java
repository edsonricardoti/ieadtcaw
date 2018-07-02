package Modelo;
// Generated 07/06/2018 16:04:37 by Hibernate Tools 4.3.1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Frequencia generated by hbm2java
 */
@Entity
@Table(name = "frequencia",
        catalog = "dadosk"
)
public class Frequencia implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idfrequencia;
    private Integer trimestre;
    private Integer idmembro;
    private Integer licao;
    private Integer presente;
    private Integer faltou;
    private Integer ano;
    private Integer idclasse;
    private String onde;
    private String agendou;

    public Frequencia() {
    }

    public Frequencia(Integer idclasse, Integer trimestre, Integer idmembro, Integer licao,
            Integer presente, Integer faltou, Integer ano, String onde, String agendou) {
        this.trimestre = trimestre;
        this.idmembro = idmembro;
        this.licao = licao;
        this.presente = presente;
        this.faltou = faltou;
        this.ano = ano;
        this.idclasse = idclasse;
        this.onde = onde;
        this.agendou = agendou;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "idfrequencia", unique = true, nullable = false)
    public Integer getIdfrequencia() {
        return this.idfrequencia;
    }

    public void setIdfrequencia(Integer idfrequencia) {
        this.idfrequencia = idfrequencia;
    }

    @Column(name = "trimestre")
    public Integer getTrimestre() {
        return this.trimestre;
    }

    public void setTrimestre(Integer trimestre) {
        this.trimestre = trimestre;
    }

    @Column(name = "idmembro")
    public Integer getIdmembro() {
        return this.idmembro;
    }

    public void setIdmembro(Integer idmembro) {
        this.idmembro = idmembro;
    }

    @Column(name = "licao")
    public Integer getLicao() {
        return this.licao;
    }

    public void setLicao(Integer licao) {
        this.licao = licao;
    }

    @Column(name = "presente")
    public Integer getPresente() {
        return this.presente;
    }

    public void setPresente(Integer presente) {
        this.presente = presente;
    }

    @Column(name = "faltou")
    public Integer getFaltou() {
        return this.faltou;
    }

    public void setFaltou(Integer faltou) {
        this.faltou = faltou;
    }

    @Column(name = "ano")
    public Integer getAno() {
        return this.ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    @Column(name = "idclasse")
    public Integer getIdclasse() {
        return idclasse;
    }

    public void setIdclasse(Integer idclasse) {
        this.idclasse = idclasse;
    }

    @Column(name = "onde")
    public String getOnde() {
        return onde;
    }

    public void setOnde(String onde) {
        this.onde = onde;
    }

    @Column(name = "agendou")
    public String getAgendou() {
        return agendou;
    }

    public void setAgendou(String agendou) {
        this.agendou = agendou;
    }

}
