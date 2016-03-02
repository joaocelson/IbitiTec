package com.ibititec.ldapp.models;

import java.util.List;

/**
 * Created by JOAOCELSON on 01/03/2016.
 */
public class Comerciante {

    private String comercianteId;

    public String getComercianteId() {
        return comercianteId;
    }

    public void setComercianteId(String comercianteId) {
        this.comercianteId = comercianteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public String getNomeFoto() {
        return nomeFoto;
    }

    public void setNomeFoto(String nomeFoto) {
        this.nomeFoto = nomeFoto;
    }

    private String nome;
    private List<Telefone> telefones ;
    private List<Endereco> enderecos;
    private String nomeFoto ;

}
