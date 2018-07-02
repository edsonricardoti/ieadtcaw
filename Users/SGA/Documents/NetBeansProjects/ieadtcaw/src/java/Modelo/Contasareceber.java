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
 * Contasareceber generated by hbm2java
 */
@Entity
@Table(name="contasareceber"
    ,catalog="dadosk"
)
public class Contasareceber  implements java.io.Serializable {


     private Integer idcontasareceber;
     private Date datalancamento;
     private Date datapagamento;
     private String descricao;
     private BigDecimal valorpago;

    public Contasareceber() {
    }

    public Contasareceber(Date datalancamento, Date datapagamento, String descricao, BigDecimal valorpago) {
       this.datalancamento = datalancamento;
       this.datapagamento = datapagamento;
       this.descricao = descricao;
       this.valorpago = valorpago;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idcontasareceber", unique=true, nullable=false)
    public Integer getIdcontasareceber() {
        return this.idcontasareceber;
    }
    
    public void setIdcontasareceber(Integer idcontasareceber) {
        this.idcontasareceber = idcontasareceber;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="datalancamento", length=10)
    public Date getDatalancamento() {
        return this.datalancamento;
    }
    
    public void setDatalancamento(Date datalancamento) {
        this.datalancamento = datalancamento;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="datapagamento", length=10)
    public Date getDatapagamento() {
        return this.datapagamento;
    }
    
    public void setDatapagamento(Date datapagamento) {
        this.datapagamento = datapagamento;
    }

    
    @Column(name="descricao")
    public String getDescricao() {
        return this.descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
    @Column(name="valorpago", precision=10)
    public BigDecimal getValorpago() {
        return this.valorpago;
    }
    
    public void setValorpago(BigDecimal valorpago) {
        this.valorpago = valorpago;
    }




}


