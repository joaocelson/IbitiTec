package com.ibititec.ldapp.models;

import java.io.Serializable;

/**
 * Created by JOAOCELSON on 01/03/2016.
 */
public class Telefone implements Serializable {

    private String Numero ;
    private String Descricao ;
    private String TelefoneId ;
    private String ComercianteID ;

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getTelefoneId() {
        return TelefoneId;
    }

    public void setTelefoneId(String telefoneId) {
        TelefoneId = telefoneId;
    }

    public String getComercianteID() {
        return ComercianteID;
    }

    public void setComercianteID(String comercianteID) {
        ComercianteID = comercianteID;
    }
}
