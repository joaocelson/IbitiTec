package com.ibititec.ldapp.models;

/**
 * Created by JOAOCELSON on 01/03/2016.
 */
public class Telefone {

    private String numero ;
    private String descricao ;
    private String telefoneId ;
    private String comercianteID ;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTelefoneId() {
        return telefoneId;
    }

    public void setTelefoneId(String telefoneId) {
        this.telefoneId = telefoneId;
    }

    public String getComercianteID() {
        return comercianteID;
    }

    public void setComercianteID(String comercianteID) {
        this.comercianteID = comercianteID;
    }


}
