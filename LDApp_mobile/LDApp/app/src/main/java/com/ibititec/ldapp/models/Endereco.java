package com.ibititec.ldapp.models;

import java.io.Serializable;

/**
 * Created by JOAOCELSON on 01/03/2016.
 */
public class Endereco  implements Serializable {

    private String EnderecoId;
    private String Logradouro ;
    private String Numero ;
    private String Complemento;
    private String Cidade ;
    private String Estado ;
    private String Bairro ;
    private String Latitude;
    private String Longitude;
    private String ComercianteId;

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }
    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
    public String getLatitude() {
       return  Latitude ;
    }
    public String getLongitude() {
        return  Longitude ;
    }

    public String getEnderecoId() {
        return EnderecoId;
    }

    public void setEnderecoId(String enderecoId) {
        EnderecoId = enderecoId;
    }

    public String getLogradouro() {
        return Logradouro;
    }

    public void setLogradouro(String logradouro) {
        Logradouro = logradouro;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        Complemento = complemento;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        Cidade = cidade;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }

    public String getComercianteId() {
        return ComercianteId;
    }

    public void setComercianteId(String comercianteId) {
        ComercianteId = comercianteId;
    }
}


