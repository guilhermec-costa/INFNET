package com.academia.poliglota.model;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "avaliacoes_fisicas")
public class AvaliacaoFisica {

    @Id
    private String id;

    private Long alunoId;

    private BigDecimal peso;

    private BigDecimal altura;

    private BigDecimal percentualGordura;

    private String anotacoesMedicas;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getAltura() {
        return altura;
    }

    public void setAltura(BigDecimal altura) {
        this.altura = altura;
    }

    public BigDecimal getPercentualGordura() {
        return percentualGordura;
    }

    public void setPercentualGordura(BigDecimal percentualGordura) {
        this.percentualGordura = percentualGordura;
    }

    public String getAnotacoesMedicas() {
        return anotacoesMedicas;
    }

    public void setAnotacoesMedicas(String anotacoesMedicas) {
        this.anotacoesMedicas = anotacoesMedicas;
    }
}
