package com.ibititec.ldapp.models;

/**
 * Created by JOAOCELSON on 08/03/2016.
 */
public class UtilidadePublicaFarmacia {

    private String DiaUtilidade;
    private String NomeUtilidade;
    private String TelefoneUtilidade;

    public UtilidadePublicaFarmacia() {

    }

    public UtilidadePublicaFarmacia(String diaUtilidade, String nomeUtilidade, String telefoneUtilidade) {
        DiaUtilidade = diaUtilidade;
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

    public String getDiaUtilidade() {
        return DiaUtilidade;
    }

    public void setDiaUtilidade(String diaUtilidade) {
        DiaUtilidade = diaUtilidade;
    }
}
