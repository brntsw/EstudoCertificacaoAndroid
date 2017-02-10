package com.udacity.br.estudocertificadoandroid;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.br.estudocertificadoandroid.adapter.TurmaAdapter;
import com.udacity.br.estudocertificadoandroid.model.Turma;
import com.udacity.br.estudocertificadoandroid.provider.ContentContract;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private TurmaAdapter adapter;
    private RecyclerView recyclerTurmas;

    private static final int TURMA_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerTurmas = (RecyclerView) findViewById(R.id.recycler_turmas);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerTurmas.setLayoutManager(manager);
        recyclerTurmas.setItemAnimator(new DefaultItemAnimator());
    }

    protected void onResume(){
        super.onResume();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            List<Turma> turmas = bundle.getParcelableArrayList("turmas");

            if(turmas != null){
                adapter = new TurmaAdapter(turmas);
                recyclerTurmas.setAdapter(adapter);
            }
            else{
                getLoaderManager().restartLoader(TURMA_LOADER, null, this);
            }
        }
        else{
            getLoaderManager().restartLoader(TURMA_LOADER, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                MainActivity.this,
                ContentContract.TurmaEntry.CONTENT_URI,
                ContentContract.TURMA_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<Turma> turmas = new ArrayList<>();

        //Consulta no ContentProvider
        if(cursor != null){
            while(cursor.moveToNext()){
                Turma turma = new Turma();
                turma.setCodigo(cursor.getLong(cursor.getColumnIndex(ContentContract.TurmaEntry.COLUMN_CODIGO)));
                turma.setCodigoEscola(cursor.getInt(cursor.getColumnIndex(ContentContract.TurmaEntry.COLUMN_CODIGO_ESCOLA)));
                turma.setNome(cursor.getString(cursor.getColumnIndex(ContentContract.TurmaEntry.COLUMN_NOME)));
                turma.setNomeEscola(cursor.getString(cursor.getColumnIndex(ContentContract.TurmaEntry.COLUMN_NOME_ESCOLA)));

                turmas.add(turma);
            }

            adapter = new TurmaAdapter(turmas);
            recyclerTurmas.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
