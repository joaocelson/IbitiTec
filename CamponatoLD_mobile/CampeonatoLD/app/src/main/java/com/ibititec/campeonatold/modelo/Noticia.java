package com.ibititec.campeonatold.modelo;

/**
 * Created by JOAOCELSON on 04/05/2016.
 */
public class Noticia {
    private String Titulo;
    private String Corpo;
    private String DataNoticia;
    private Time Time;

    public String getDataNoticia() {
        return DataNoticia;
    }

    public void setDataNoticia(String dataNoticia) {
        DataNoticia = dataNoticia;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getCorpo() {
        return Corpo;
    }

    public void setCorpo(String corpo) {
        Corpo = corpo;
    }

    public Time getTime() {
        return Time;
    }

    public void setTime(Time time) {
        Time = time;
    }
}
