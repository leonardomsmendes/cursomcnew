package com.nelioalves.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nelioalves.cursomc.domain.enums.EstadoPagamento;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
//public abstract = Garante que nunca consiga instanciar sem ser por New
@Entity
@Inheritance(strategy = InheritanceType.JOINED)//Mapear Herança / Escolhe estratégia (JOINED uma tabela para cada ou uma Tabela Geral
public abstract class  Pagamento implements Serializable {
    private static final long serialVersionUID = 1L;

    //Atributos básicos
    @Id
    private Integer id;
    private Integer estado;
    //Associações (inicie as coleções)

    @JsonIgnore//Proteger contra serialização Json cíclica
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @MapsId // Para garantir que seja o mesmo ID do Pedido
    private Pedido pedido;

    //2º Construtores = não incluir listas
    public Pagamento() {
    }

    public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
        this.id = id;
        this.estado = (estado ==null) ? null : estado.getCod();
        this.pedido = pedido;
    }

    //3º Getter e Setter = todos
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EstadoPagamento getEstado() {
        return EstadoPagamento.toEnum(estado);
    }

    public void setEstado(EstadoPagamento estado) {
        this    .estado = estado.getCod();
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    //5º HastCode e Equals = somente o Id


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return Objects.equals(id, pagamento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
