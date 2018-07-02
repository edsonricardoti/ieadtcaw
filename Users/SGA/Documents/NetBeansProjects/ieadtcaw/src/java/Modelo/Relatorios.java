package Modelo;
// Generated 30/06/2018 15:56:14 by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Relatorios generated by hbm2java
 */
@Entity
@Table(name="relatorios"
    ,catalog="dadosk"
)
public class Relatorios  implements java.io.Serializable {


     private Integer id;
     private BigDecimal abril;
     private BigDecimal agosto;
     private BigDecimal alcadas;
     private Integer ano;
     private Date data;
     private BigDecimal dezembro;
     private BigDecimal dizimos;
     private Long faltou;
     private BigDecimal fevereiro;
     private BigDecimal gasolina;
     private Integer idfinanceiro;
     private BigDecimal janeiro;
     private BigDecimal julho;
     private BigDecimal junho;
     private Integer licao;
     private BigDecimal maio;
     private BigDecimal manutencao;
     private BigDecimal marco;
     private Integer mes;
     private String nome;
     private BigDecimal novembro;
     private BigDecimal ofertas;
     private BigDecimal outubro;
     private Long presente;
     private BigDecimal setembro;
     private BigDecimal total;
     private Long trimestre1;
     private Long trimestre2;
     private Long trimestre3;
     private Long trimestre4;
     private BigDecimal votos;

    public Relatorios() {
    }

    public Relatorios(BigDecimal abril, BigDecimal agosto, BigDecimal alcadas, Integer ano, Date data, BigDecimal dezembro, BigDecimal dizimos, Long faltou, BigDecimal fevereiro, BigDecimal gasolina, Integer idfinanceiro, BigDecimal janeiro, BigDecimal julho, BigDecimal junho, Integer licao, BigDecimal maio, BigDecimal manutencao, BigDecimal marco, Integer mes, String nome, BigDecimal novembro, BigDecimal ofertas, BigDecimal outubro, Long presente, BigDecimal setembro, BigDecimal total, Long trimestre1, Long trimestre2, Long trimestre3, Long trimestre4, BigDecimal votos) {
       this.abril = abril;
       this.agosto = agosto;
       this.alcadas = alcadas;
       this.ano = ano;
       this.data = data;
       this.dezembro = dezembro;
       this.dizimos = dizimos;
       this.faltou = faltou;
       this.fevereiro = fevereiro;
       this.gasolina = gasolina;
       this.idfinanceiro = idfinanceiro;
       this.janeiro = janeiro;
       this.julho = julho;
       this.junho = junho;
       this.licao = licao;
       this.maio = maio;
       this.manutencao = manutencao;
       this.marco = marco;
       this.mes = mes;
       this.nome = nome;
       this.novembro = novembro;
       this.ofertas = ofertas;
       this.outubro = outubro;
       this.presente = presente;
       this.setembro = setembro;
       this.total = total;
       this.trimestre1 = trimestre1;
       this.trimestre2 = trimestre2;
       this.trimestre3 = trimestre3;
       this.trimestre4 = trimestre4;
       this.votos = votos;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    
    @Column(name="Abril")
    public BigDecimal getAbril() {
        return this.abril;
    }
    
    public void setAbril(BigDecimal abril) {
        this.abril = abril;
    }

    
    @Column(name="Agosto")
    public BigDecimal getAgosto() {
        return this.agosto;
    }
    
    public void setAgosto(BigDecimal agosto) {
        this.agosto = agosto;
    }

    
    @Column(name="alcadas")
    public BigDecimal getAlcadas() {
        return this.alcadas;
    }
    
    public void setAlcadas(BigDecimal alcadas) {
        this.alcadas = alcadas;
    }

    
    @Column(name="ano")
    public Integer getAno() {
        return this.ano;
    }
    
    public void setAno(Integer ano) {
        this.ano = ano;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="data", length=10)
    public Date getData() {
        return this.data;
    }
    
    public void setData(Date data) {
        this.data = data;
    }

    
    @Column(name="Dezembro")
    public BigDecimal getDezembro() {
        return this.dezembro;
    }
    
    public void setDezembro(BigDecimal dezembro) {
        this.dezembro = dezembro;
    }

    
    @Column(name="dizimos")
    public BigDecimal getDizimos() {
        return this.dizimos;
    }
    
    public void setDizimos(BigDecimal dizimos) {
        this.dizimos = dizimos;
    }

    
    @Column(name="faltou")
    public Long getFaltou() {
        return this.faltou;
    }
    
    public void setFaltou(Long faltou) {
        this.faltou = faltou;
    }

    
    @Column(name="Fevereiro")
    public BigDecimal getFevereiro() {
        return this.fevereiro;
    }
    
    public void setFevereiro(BigDecimal fevereiro) {
        this.fevereiro = fevereiro;
    }

    
    @Column(name="gasolina")
    public BigDecimal getGasolina() {
        return this.gasolina;
    }
    
    public void setGasolina(BigDecimal gasolina) {
        this.gasolina = gasolina;
    }

    
    @Column(name="idfinanceiro")
    public Integer getIdfinanceiro() {
        return this.idfinanceiro;
    }
    
    public void setIdfinanceiro(Integer idfinanceiro) {
        this.idfinanceiro = idfinanceiro;
    }

    
    @Column(name="Janeiro")
    public BigDecimal getJaneiro() {
        return this.janeiro;
    }
    
    public void setJaneiro(BigDecimal janeiro) {
        this.janeiro = janeiro;
    }

    
    @Column(name="Julho")
    public BigDecimal getJulho() {
        return this.julho;
    }
    
    public void setJulho(BigDecimal julho) {
        this.julho = julho;
    }

    
    @Column(name="Junho")
    public BigDecimal getJunho() {
        return this.junho;
    }
    
    public void setJunho(BigDecimal junho) {
        this.junho = junho;
    }

    
    @Column(name="licao")
    public Integer getLicao() {
        return this.licao;
    }
    
    public void setLicao(Integer licao) {
        this.licao = licao;
    }

    
    @Column(name="Maio")
    public BigDecimal getMaio() {
        return this.maio;
    }
    
    public void setMaio(BigDecimal maio) {
        this.maio = maio;
    }

    
    @Column(name="manutencao")
    public BigDecimal getManutencao() {
        return this.manutencao;
    }
    
    public void setManutencao(BigDecimal manutencao) {
        this.manutencao = manutencao;
    }

    
    @Column(name="Marco")
    public BigDecimal getMarco() {
        return this.marco;
    }
    
    public void setMarco(BigDecimal marco) {
        this.marco = marco;
    }

    
    @Column(name="mes")
    public Integer getMes() {
        return this.mes;
    }
    
    public void setMes(Integer mes) {
        this.mes = mes;
    }

    
    @Column(name="nome", length=100)
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    
    @Column(name="Novembro")
    public BigDecimal getNovembro() {
        return this.novembro;
    }
    
    public void setNovembro(BigDecimal novembro) {
        this.novembro = novembro;
    }

    
    @Column(name="ofertas")
    public BigDecimal getOfertas() {
        return this.ofertas;
    }
    
    public void setOfertas(BigDecimal ofertas) {
        this.ofertas = ofertas;
    }

    
    @Column(name="Outubro")
    public BigDecimal getOutubro() {
        return this.outubro;
    }
    
    public void setOutubro(BigDecimal outubro) {
        this.outubro = outubro;
    }

    
    @Column(name="presente")
    public Long getPresente() {
        return this.presente;
    }
    
    public void setPresente(Long presente) {
        this.presente = presente;
    }

    
    @Column(name="Setembro")
    public BigDecimal getSetembro() {
        return this.setembro;
    }
    
    public void setSetembro(BigDecimal setembro) {
        this.setembro = setembro;
    }

    
    @Column(name="total")
    public BigDecimal getTotal() {
        return this.total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    
    @Column(name="1trimestre")
    public Long getTrimestre1() {
        return this.trimestre1;
    }
    
    public void setTrimestre1(Long trimestre1) {
        this.trimestre1 = trimestre1;
    }

    
    @Column(name="2trimestre")
    public Long getTrimestre2() {
        return this.trimestre2;
    }
    
    public void setTrimestre2(Long trimestre2) {
        this.trimestre2 = trimestre2;
    }

    
    @Column(name="3trimestre")
    public Long getTrimestre3() {
        return this.trimestre3;
    }
    
    public void setTrimestre3(Long trimestre3) {
        this.trimestre3 = trimestre3;
    }

    
    @Column(name="4trimestre")
    public Long getTrimestre4() {
        return this.trimestre4;
    }
    
    public void setTrimestre4(Long trimestre4) {
        this.trimestre4 = trimestre4;
    }

    
    @Column(name="votos")
    public BigDecimal getVotos() {
        return this.votos;
    }
    
    public void setVotos(BigDecimal votos) {
        this.votos = votos;
    }




}


