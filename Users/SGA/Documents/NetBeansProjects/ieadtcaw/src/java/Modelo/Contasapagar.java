package Modelo;
// Generated 15/06/2018 12:08:11 by Hibernate Tools 4.3.1

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
 * Contasapagar generated by hbm2java
 */
@Entity
@Table(name = "contasapagar",
        catalog = "dadosk"
)
public class Contasapagar implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idcontasapagar;
    private Date datalancamento;
    private Date datavencimento;
    private Date datapagamento;
    private String descricao;
    private BigDecimal valordespesa;
    private String tipo;
    private String obs;

    public Contasapagar() {
    }

    public Contasapagar(Date datalancamento, Date datavencimento, String descricao, BigDecimal valordespesa,
            String tipo, String obs, Date datapagamento) {
        this.datalancamento = datalancamento;
        this.datavencimento = datavencimento;
        this.descricao = descricao;
        this.valordespesa = valordespesa;
        this.tipo = tipo;
        this.obs = obs;
        this.datapagamento = datapagamento;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "idcontasapagar", unique = true, nullable = false)
    public Integer getIdcontasapagar() {
        return this.idcontasapagar;
    }

    public void setIdcontasapagar(Integer idcontasapagar) {
        this.idcontasapagar = idcontasapagar;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "datalancamento", length = 10)
    public Date getDatalancamento() {
        return this.datalancamento;
    }

    public void setDatalancamento(Date datalancamento) {
        this.datalancamento = datalancamento;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "datavencimento", length = 10)
    public Date getDatavencimento() {
        return this.datavencimento;
    }

    public void setDatavencimento(Date datavencimento) {
        this.datavencimento = datavencimento;
    }

    @Column(name = "descricao")
    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Column(name = "valordespesa", precision = 10)
    public BigDecimal getValordespesa() {
        return this.valordespesa;
    }

    public void setValordespesa(BigDecimal valordespesa) {
        this.valordespesa = valordespesa;
    }

    @Column(name = "tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Column(name = "obs", length = 255)
    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "datapagamento", length = 10)
    public Date getDatapagamento() {
        return datapagamento;
    }

    public void setDatapagamento(Date datapagamento) {
        this.datapagamento = datapagamento;
    }

}
