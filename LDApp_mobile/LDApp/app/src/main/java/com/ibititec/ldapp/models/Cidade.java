package com.ibititec.ldapp.models;

/**
 * Created by JOAOCELSON on 15/03/2016.
 */
public class Cidade {
    private  int texto;
    private int foto;


    public Cidade() {
    }

    public Cidade(int texto, int foto) {
        this.texto = texto;
        this.foto = foto;
    }

    public int getTexto() {
        return texto;
    }

    public void setTexto(int texto) {
        this.texto = texto;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
