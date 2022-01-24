package com.nelioalves.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.nelioalves.cursomc.domain.enums.EstadoPagamento;

import javax.persistence.Entity;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {
    private static final long serialVersionUID = 1L;
    //Atributos básicos
    private Integer numeroDeParcelas;

    //2º Construtores = não incluir listas
    public PagamentoComCartao() {
    }

    public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
        super(id, estado, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }
//3º Getter e Setter = todos

    public Integer getNumeroDeParcelas() {
        return numeroDeParcelas;
    }

    public void setNumeroDeParcelas(Integer numeroDeParcelas) {
        this.numeroDeParcelas = numeroDeParcelas;
    }
    //5º HastCode e Equals = somente o Id = Neste Caso esta subclasse pega da classe
}
