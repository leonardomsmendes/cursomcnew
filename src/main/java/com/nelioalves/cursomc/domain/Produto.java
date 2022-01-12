package com.nelioalves.cursomc.domain;

// Checklist para criar entidades:
//        o Atributos básicos
//        o Associações (inicie as coleções)
//        o Construtores (não inclua coleções no construtor com parâmetros)
//        o Getters e setters
//        o hashCode e equals (implementação padrão: somente id)
//        o Serializable (padrão: 1L)

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumns;

@Entity
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    //Atributos básicos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private Double preco;

    //Associações (inicie as coleções)
    @JsonIgnore //Agruado do outro lado com @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "PRODUTO_CATEGORIA",
            joinColumns = {@JoinColumn(name = "produto_id")},
            inverseJoinColumns = @JoinColumn(name="categoria_id")
    )
    private List<Categoria> categorias = new ArrayList<>();

    //set me garante que a linguagem java não vai ter item repedido no mesmo pedido
    @JsonIgnore//Proteger contra serialização Json cíclica
    @OneToMany(mappedBy = "id.produto")
    private Set<ItemPedido> itens = new HashSet<>();

    //Construtores (não inclua coleções no construtor com parâmetros)
    public Produto(){
    }

    public Produto(Integer id, String nome, Double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }
    @JsonIgnore //Proteger contra serialização Json cíclica
    public List<Pedido> getPedidos(){
        List<Pedido> lista = new ArrayList<>();

        for (ItemPedido x : itens) {
            lista.add(x.getPedido());
        }
        return lista;
    }

    //Getters e setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Produto(Set<ItemPedido> itens) {
        this.itens = itens;
    }
    public Set<ItemPedido> getItens() {
        return itens;
    }

//hashCode e equals (implementação padrão: somente id)


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id.equals(produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}