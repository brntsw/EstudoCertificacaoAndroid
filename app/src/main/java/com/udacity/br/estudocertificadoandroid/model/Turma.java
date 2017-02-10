package com.udacity.br.estudocertificadoandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by BPardini on 10/02/2017.
 */

public class Turma implements Parcelable {

    private long codigo;
    private String nome;
    private int codigoEscola;
    private String nomeEscola;
    private List<Aluno> alunos;

    public Turma(){}

    protected Turma(Parcel in) {
        codigo = in.readLong();
        nome = in.readString();
        codigoEscola = in.readInt();
        nomeEscola = in.readString();
    }

    public static final Creator<Turma> CREATOR = new Creator<Turma>() {
        @Override
        public Turma createFromParcel(Parcel in) {
            return new Turma(in);
        }

        @Override
        public Turma[] newArray(int size) {
            return new Turma[size];
        }
    };

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeEscola() {
        return nomeEscola;
    }

    public void setNomeEscola(String nomeEscola) {
        this.nomeEscola = nomeEscola;
    }

    public int getCodigoEscola() {
        return codigoEscola;
    }

    public void setCodigoEscola(int codigoEscola) {
        this.codigoEscola = codigoEscola;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(codigo);
        parcel.writeString(nome);
        parcel.writeInt(codigoEscola);
        parcel.writeString(nomeEscola);
    }
}
