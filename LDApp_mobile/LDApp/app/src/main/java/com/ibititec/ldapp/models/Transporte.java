package com.ibititec.ldapp.models;

/**
 * Created by JOAOCELSON on 14/03/2016.
 */
public class Transporte {

    private String TransporteId;
    private String NomeTranporte;
    private String TextoSemanal;
    private String TextFds;

    public Transporte() {
    }

    public Transporte(String nomeTranporte, String textoSemanal, String textFds) {
        NomeTranporte = nomeTranporte;
        TextoSemanal = textoSemanal;
        TextFds = textFds;
    }

    public String getTransporteId() {
        return TransporteId;
    }

    public void setTransporteId(String transporteId) {
        TransporteId = transporteId;
    }

    public String getNomeTranporte() {
        return NomeTranporte;
    }

    public void setNomeTranporte(String nomeTranporte) {
        NomeTranporte = nomeTranporte;
    }

    public String getTextoSemanal() {
        return TextoSemanal;
    }

    public void setTextoSemanal(String textoSemanal) {
        TextoSemanal = textoSemanal;
    }

    public String getTextFds() {
        return TextFds;
    }

    public void setTextFds(String textFds) {
        TextFds = textFds;
    }
}
