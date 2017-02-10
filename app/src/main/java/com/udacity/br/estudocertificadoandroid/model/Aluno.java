package com.udacity.br.estudocertificadoandroid.model;

/**
 * Created by BPardini on 10/02/2017.
 */

public class Aluno {

    private long codigoTurma;
    private long codigoMatricula;
    private int numeroChamada;
    private String nome;

    public long getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(long codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public long getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(long codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }

    public int getNumeroChamada() {
        return numeroChamada;
    }

    public void setNumeroChamada(int numeroChamada) {
        this.numeroChamada = numeroChamada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
