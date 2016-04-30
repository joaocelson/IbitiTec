package com.ibititec.campeonatold.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by JOAOCELSON on 28/04/2016.
 */
public class Partida implements Serializable {

    private int Id ;

    private String IdTimeMandante ;

    private String IdCampeonato ;

    private String IdTimeVisitante ;


    private String GolMandante ;


    private String GolVisitante ;

    private String LocalPartida ;

    private Date  DataPartida ;

    private Date DataPartidaRemarcada ;

    private Boolean Remarcada ;

    private String TimeMandante ;

    private String TimeVisitante ;

    private String EscudoPequenoMandante ;

    private String EscudoPequenoVisitante ;

    private Boolean InverterMandante ;

    private Boolean PontosComputados ;


    private String NomeCampeonato ;

    private JogoOnline JogoOnline ;

    private Campeonato Campeonatos ;

    private Time TimeVisitanteObj ;

    private Time TimeMandanteObj ;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getIdTimeMandante() {
        return IdTimeMandante;
    }

    public void setIdTimeMandante(String idTimeMandante) {
        IdTimeMandante = idTimeMandante;
    }

    public String getIdCampeonato() {
        return IdCampeonato;
    }

    public void setIdCampeonato(String idCampeonato) {
        IdCampeonato = idCampeonato;
    }

    public String getIdTimeVisitante() {
        return IdTimeVisitante;
    }

    public void setIdTimeVisitante(String idTimeVisitante) {
        IdTimeVisitante = idTimeVisitante;
    }

    public String getGolMandante() {
        return GolMandante;
    }

    public void setGolMandante(String golMandante) {
        GolMandante = golMandante;
    }

    public String getGolVisitante() {
        return GolVisitante;
    }

    public void setGolVisitante(String golVisitante) {
        GolVisitante = golVisitante;
    }

    public String getLocalPartida() {
        return LocalPartida;
    }

    public void setLocalPartida(String localPartida) {
        LocalPartida = localPartida;
    }

    public Date getDataPartida() {
        return DataPartida;
    }

    public void setDataPartida(Date dataPartida) {
        DataPartida = dataPartida;
    }

    public Date getDataPartidaRemarcada() {
        return DataPartidaRemarcada;
    }

    public void setDataPartidaRemarcada(Date dataPartidaRemarcada) {
        DataPartidaRemarcada = dataPartidaRemarcada;
    }

    public Boolean getRemarcada() {
        return Remarcada;
    }

    public void setRemarcada(Boolean remarcada) {
        Remarcada = remarcada;
    }

    public String getTimeMandante() {
        return TimeMandante;
    }

    public void setTimeMandante(String timeMandante) {
        TimeMandante = timeMandante;
    }

    public String getTimeVisitante() {
        return TimeVisitante;
    }

    public void setTimeVisitante(String timeVisitante) {
        TimeVisitante = timeVisitante;
    }

    public String getEscudoPequenoMandante() {
        return EscudoPequenoMandante;
    }

    public void setEscudoPequenoMandante(String escudoPequenoMandante) {
        EscudoPequenoMandante = escudoPequenoMandante;
    }

    public String getEscudoPequenoVisitante() {
        return EscudoPequenoVisitante;
    }

    public void setEscudoPequenoVisitante(String escudoPequenoVisitante) {
        EscudoPequenoVisitante = escudoPequenoVisitante;
    }

    public Boolean getInverterMandante() {
        return InverterMandante;
    }

    public void setInverterMandante(Boolean inverterMandante) {
        InverterMandante = inverterMandante;
    }

    public Boolean getPontosComputados() {
        return PontosComputados;
    }

    public void setPontosComputados(Boolean pontosComputados) {
        PontosComputados = pontosComputados;
    }

    public String getNomeCampeonato() {
        return NomeCampeonato;
    }

    public void setNomeCampeonato(String nomeCampeonato) {
        NomeCampeonato = nomeCampeonato;
    }

    public com.ibititec.campeonatold.modelo.JogoOnline getJogoOnline() {
        return JogoOnline;
    }

    public void setJogoOnline(com.ibititec.campeonatold.modelo.JogoOnline jogoOnline) {
        JogoOnline = jogoOnline;
    }

    public Campeonato getCampeonatos() {
        return Campeonatos;
    }

    public void setCampeonatos(Campeonato campeonatos) {
        Campeonatos = campeonatos;
    }

    public Time getTimeVisitanteObj() {
        return TimeVisitanteObj;
    }

    public void setTimeVisitanteObj(Time timeVisitanteObj) {
        TimeVisitanteObj = timeVisitanteObj;
    }

    public Time getTimeMandanteObj() {
        return TimeMandanteObj;
    }

    public void setTimeMandanteObj(Time timeMandanteObj) {
        TimeMandanteObj = timeMandanteObj;
    }
}
