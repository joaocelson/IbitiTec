package com.ibititec.ldapp.models;

/**
 * Created by JOAOCELSON on 01/03/2016.
 */
public class Endereco {

    private String enderecoId;
    private String logradouro ;
    private String numero ;
    private String complemento;
    private String cidade ;
    private String estado ;
    private String bairro ;
    private String comercianteId ;

    public String getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(String enderecoId) {
        this.enderecoId = enderecoId;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComercianteId() {
        return comercianteId;
    }

    public void setComercianteId(String comercianteId) {
        this.comercianteId = comercianteId;
    }
}


