package com.ibititec.ldapp.models;

import java.io.Serializable;

/**
 * Created by JOAOCELSON on 14/03/2016.
 */
public class TipoComerciante implements Serializable {
    private String TipoComercioId;
    private String Descricao;

    public String getTipoComercianteId() {
        return TipoComercioId;
    }

    public void setTipoComercianteId(String tipoComercianteId) {
        TipoComercioId = tipoComercianteId;
    }

    public String getDescricacao() {
        return Descricao;
    }

    public void setDescricacao(String descricacao) {
        Descricao = descricacao;
    }
}
