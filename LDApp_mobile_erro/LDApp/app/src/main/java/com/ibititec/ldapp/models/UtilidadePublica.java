package com.ibititec.ldapp.models;

/**
 * Created by JOAOCELSON on 08/03/2016.
 */
public class UtilidadePublica {

    private String NomeUtilidade;
    private String TelefoneUtilidade;

    public UtilidadePublica() {

    }

    public UtilidadePublica(String nomeUtilidade, String telefoneUtilidade) {
        NomeUtilidade = nomeUtilidade;
        TelefoneUtilidade = telefoneUtilidade;
    }

    public String getNomeUtilidade() {
        return NomeUtilidade;
    }

    public void setNomeUtilidade(String nomeUtilidade) {
        NomeUtilidade = nomeUtilidade;
    }

    public String getTelefoneUtilidade() {
        return TelefoneUtilidade;
    }

    public void setTelefoneUtilidade(String telefoneUtilidade) {
        TelefoneUtilidade = telefoneUtilidade;
    }

}
