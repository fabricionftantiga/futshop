package com.futshop.futshop.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuarios")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigo;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String senha;

    @Column(length = 17, nullable = false)
    private String numero;

    @ElementCollection
    private List<CarrinhoModel> itens = null;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public List<CarrinhoModel> getItens() {
        return itens;
    }

    public void setItens(CarrinhoModel itens) {
        this.itens.add(itens);
    }
}
