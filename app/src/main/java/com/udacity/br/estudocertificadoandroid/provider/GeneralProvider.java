package com.udacity.br.estudocertificadoandroid.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.br.estudocertificadoandroid.provider.sqlite.DatabaseHelper;

/**
 * Created by BPardini on 10/02/2017.
 */

public class GeneralProvider extends ContentProvider {
    private SQLiteDatabase db;

    static final int USER = 1;
    static final int TURMA = 2;
    static final int ALUNO = 3;

    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ContentContract.CONTENT_AUTHORITY, ContentContract.PATH_USER, USER);
        uriMatcher.addURI(ContentContract.CONTENT_AUTHORITY, ContentContract.PATH_TURMA, TURMA);
        uriMatcher.addURI(ContentContract.CONTENT_AUTHORITY, ContentContract.PATH_ALUNO, ALUNO);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        db = dbHelper.getWritableDatabase();

        return db != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = uriMatcher.match(uri);

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (match){
            case USER:
                qb.setTables(ContentContract.UserEntry.TABLE_NAME);
                break;
            case TURMA:
                qb.setTables(ContentContract.TurmaEntry.TABLE_NAME);
                break;
            case ALUNO:
                qb.setTables(ContentContract.AlunoEntry.TABLE_NAME);
                break;
        }

        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int match = uriMatcher.match(uri);

        long rowId;

        switch (match){
            case USER:

                rowId = db.insert(ContentContract.UserEntry.TABLE_NAME, "", contentValues);

                if(rowId > 0){
                    Uri returnUri = ContentContract.UserEntry.buildUserUri(rowId);
                    getContext().getContentResolver().notifyChange(returnUri, null);
                    return returnUri;
                }
                break;
            case TURMA:

                rowId = db.insert(ContentContract.TurmaEntry.TABLE_NAME, "", contentValues);

                if(rowId > 0){
                    Uri returnUri = ContentContract.TurmaEntry.buildUserUri(rowId);
                    getContext().getContentResolver().notifyChange(returnUri, null);
                    return returnUri;
                }
                break;
            case ALUNO:

                rowId = db.insert(ContentContract.AlunoEntry.TABLE_NAME, "", contentValues);

                if(rowId > 0){
                    Uri returnUri = ContentContract.AlunoEntry.buildUserUri(rowId);
                    getContext().getContentResolver().notifyChange(returnUri, null);
                    return returnUri;
                }
                break;
        }

        throw new SQLException("Failed to add record into " + uri);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
