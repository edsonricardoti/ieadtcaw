package Modelo;
// Generated 30/06/2018 15:56:14 by Hibernate Tools 4.3.1


import java.math.BigDecimal;

/**
 * Relatorios generated by hbm2java
 */
public class Assinantes implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String membrosNome;
    private Integer idmembros;
    private Integer idassinatura;
    private BigDecimal valortotal;
    private Integer idperiodico;
    private String modalidade;

    public Assinantes() {
    }

    public Assinantes(String membrosNome, Integer idmembros, Integer idassinatura, BigDecimal valortotal,
            Integer idperiodico, String modalidade) {

    }

    public String getMembrosNome() {
        return membrosNome;
    }

    public void setMembrosNome(String membrosNome) {
        this.membrosNome = membrosNome;
    }

    public Integer getIdmembros() {
        return idmembros;
    }

    public void setIdmembros(Integer idmembros) {
        this.idmembros = idmembros;
    }

    public Integer getIdassinatura() {
        return idassinatura;
    }

    public void setIdassinatura(Integer idassinatura) {
        this.idassinatura = idassinatura;
    }

    public BigDecimal getValortotal() {
        return valortotal;
    }

    public void setValortotal(BigDecimal valortotal) {
        this.valortotal = valortotal;
    }

    public Integer getIdperiodico() {
        return idperiodico;
    }

    public void setIdperiodico(Integer idperiodico) {
        this.idperiodico = idperiodico;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }
   

}


