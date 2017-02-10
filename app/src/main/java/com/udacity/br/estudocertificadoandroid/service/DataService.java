package com.udacity.br.estudocertificadoandroid.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.udacity.br.estudocertificadoandroid.model.Aluno;
import com.udacity.br.estudocertificadoandroid.model.Turma;
import com.udacity.br.estudocertificadoandroid.provider.ContentContract;
import com.udacity.br.estudocertificadoandroid.remote.RestApi;

/**
 * Created by BPardini on 10/02/2017.
 */

public class DataService extends IntentService {
    public DataService() {
        super(DataService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();

        try {
            Turma[] turmas = RestApi.downloadData();

            for(Turma turma : turmas){
                ContentValues turmasValues = new ContentValues();
                turmasValues.put(ContentContract.TurmaEntry.COLUMN_CODIGO, turma.getCodigo());
                turmasValues.put(ContentContract.TurmaEntry.COLUMN_CODIGO_ESCOLA, turma.getCodigoEscola());
                turmasValues.put(ContentContract.TurmaEntry.COLUMN_NOME, turma.getNome());
                turmasValues.put(ContentContract.TurmaEntry.COLUMN_NOME_ESCOLA, turma.getNomeEscola());

                this.getContentResolver().insert(ContentContract.TurmaEntry.CONTENT_URI, turmasValues);

                for(Aluno aluno : turma.getAlunos()){
                    ContentValues alunosValues = new ContentValues();
                    alunosValues.put(ContentContract.AlunoEntry.COLUMN_CODIGO_TURMA, aluno.getCodigoTurma());
                    alunosValues.put(ContentContract.AlunoEntry.COLUMN_CODIGO_MATRICULA, aluno.getCodigoMatricula());
                    alunosValues.put(ContentContract.AlunoEntry.COLUMN_NOME, aluno.getNome());
                    alunosValues.put(ContentContract.AlunoEntry.COLUMN_NUMERO_CHAMADA, aluno.getNumeroChamada());

                    this.getContentResolver().insert(ContentContract.AlunoEntry.CONTENT_URI, alunosValues);
                }
            }

            bundle.putParcelableArray("turmas", turmas);
            receiver.send(RestApi.SUCCESS, bundle);
        }
        catch (Exception e){
            receiver.send(RestApi.ERROR, Bundle.EMPTY);
        }
    }
}
