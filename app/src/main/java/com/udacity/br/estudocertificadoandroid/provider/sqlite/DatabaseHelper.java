package com.udacity.br.estudocertificadoandroid.provider.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udacity.br.estudocertificadoandroid.provider.ContentContract;

/**
 * Created by BPardini on 10/02/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "estudocertificadoandroid.db";
    static final int DATABASE_VERSION = 1;

    private final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + ContentContract.UserEntry.TABLE_NAME +
            " (" + ContentContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ContentContract.UserEntry.COLUMN_USERNAME + " TEXT NOT NULL," +
            ContentContract.UserEntry.COLUMN_ACTIVE + " TEXT NOT NULL);";

    private final String SQL_CREATE_TURMA_TABLE = "CREATE TABLE " + ContentContract.TurmaEntry.TABLE_NAME +
            " (" + ContentContract.TurmaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ContentContract.TurmaEntry.COLUMN_CODIGO + " INTEGER NOT NULL," +
            ContentContract.TurmaEntry.COLUMN_CODIGO_ESCOLA + " INTEGER NOT NULL," +
            ContentContract.TurmaEntry.COLUMN_NOME + " TEXT NOT NULL," +
            ContentContract.TurmaEntry.COLUMN_NOME_ESCOLA + " TEXT NOT NULL);";

    private final String SQL_CREATE_ALUNO_TABLE = "CREATE TABLE " + ContentContract.AlunoEntry.TABLE_NAME +
            " (" + ContentContract.AlunoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ContentContract.AlunoEntry.COLUMN_CODIGO_TURMA + " INTEGER NOT NULL," +
            ContentContract.AlunoEntry.COLUMN_CODIGO_MATRICULA + " INTEGER NOT NULL," +
            ContentContract.AlunoEntry.COLUMN_NOME + " TEXT NOT NULL," +
            ContentContract.AlunoEntry.COLUMN_NUMERO_CHAMADA + " INTEGER NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TURMA_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ALUNO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContentContract.UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContentContract.TurmaEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContentContract.AlunoEntry.TABLE_NAME);
        //Aqui deveria, ao invés de apagar as tabelas no upgrade do banco, criar tabelas auxiliares, transferir os dados para elas e então novamente
        //para as tabelas atualizadas
        onCreate(sqLiteDatabase);
    }
}
