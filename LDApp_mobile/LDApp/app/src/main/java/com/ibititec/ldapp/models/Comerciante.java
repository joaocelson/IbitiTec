package com.ibititec.ldapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOAOCELSON on 01/03/2016.
 */
public class Comerciante implements Serializable {

    private static final String PAHT_FOTO = "http://52.37.37.207:86/Comerciante/Image?nomeimagem=";
    private String ComercianteId;
    private String Nome;
    private List<Telefone> Telefones;
    private List<Endereco> Enderecos;
    private String NomeFoto;
    private TipoComerciante TipoComercio;

    public TipoComerciante getTipoComercio() {
        return TipoComercio;
    }

    public void setTipoComercio(com.ibititec.ldapp.models.TipoComerciante tipoComerciante) {
        TipoComercio = tipoComerciante;
    }

    public double getLatitude() {
        //return Latitude;
        //-21.847771, -43.814943
        return  -21.847771;
    }


    public double getLongitude() {
        //return Longitude;
        return -43.814943;
    }



    public String getComercianteId() {
        return ComercianteId;
    }

    public void setComercianteId(String comercianteId) {
        ComercianteId = comercianteId;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public List<Telefone> getTelefones() {
        return Telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        Telefones = telefones;
    }

    public List<Endereco> getEnderecos() {
        return Enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        Enderecos = enderecos;
    }

    public String getNomeFoto() {
        return PAHT_FOTO + NomeFoto;
    }

    public void setNomeFoto(String nomeFoto) {
        NomeFoto = nomeFoto;
    }

    public List<String> getNomeListaFotos(ArrayList<Comerciante> comerciantes) {
        List<String> nomeFotos = new ArrayList<>();
        for (Comerciante comerciante : comerciantes) {
            nomeFotos.add(comerciante.getNomeFoto());
        }
        return nomeFotos;
    }

    public int getComercianteImage(int position) {
        switch (position) {
            case 0:
                return 0;
            case 1:
                return 0;
            case 2:
                return 0;
            case 3:
                return 0;
            default:
                return 0;
        }
    }
}
