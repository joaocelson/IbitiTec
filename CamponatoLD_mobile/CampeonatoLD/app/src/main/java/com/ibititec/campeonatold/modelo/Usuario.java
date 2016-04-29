package com.ibititec.campeonatold.modelo;

/**
 * Created by JOAOCELSON on 28/04/2016.
 */
public class Usuario {

   private String LoginEmail ;

    private String Senha ;

    private String ConfirmaSenha ;

    private String NomeUsuario;

    private String Token;

    public String getLoginEmail() {
        return LoginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        LoginEmail = loginEmail;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getConfirmaSenha() {
        return ConfirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        ConfirmaSenha = confirmaSenha;
    }

    public String getNomeUsuario() {
        return NomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        NomeUsuario = nomeUsuario;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
