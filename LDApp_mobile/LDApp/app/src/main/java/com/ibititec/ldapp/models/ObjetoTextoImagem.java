package com.ibititec.ldapp.models;

/**
 * Created by JOAOCELSON on 15/03/2016.
 */
public class ObjetoTextoImagem {
    private String texto;
    private int foto;


    public ObjetoTextoImagem() {
    }

    public ObjetoTextoImagem(String texto, int foto) {
        this.setTexto(texto);
        this.foto = foto;
    }


    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
