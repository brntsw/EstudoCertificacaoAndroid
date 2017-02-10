package com.udacity.br.estudocertificadoandroid.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by BPardini on 10/02/2017.
 */

public class ContentContract {

    public static final String CONTENT_AUTHORITY = "com.udacity.br.estudocertificadoandroid";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USER = "user";
    public static final String PATH_TURMA = "turma";
    public static final String PATH_ALUNO = "aluno";

    //The columns
    public static final String[] TURMA_COLUMNS = {
            TurmaEntry.COLUMN_CODIGO,
            TurmaEntry.COLUMN_CODIGO_ESCOLA,
            TurmaEntry.COLUMN_NOME,
            TurmaEntry.COLUMN_NOME_ESCOLA
    };

    public static final String[] ALUNO_COLUMNS = {
            AlunoEntry.COLUMN_CODIGO_TURMA,
            AlunoEntry.COLUMN_CODIGO_MATRICULA,
            AlunoEntry.COLUMN_NOME,
            AlunoEntry.COLUMN_NUMERO_CHAMADA
    };

    public static final class UserEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        //Table name
        public static final String TABLE_NAME = "user";

        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_ACTIVE = "active";

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class TurmaEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TURMA).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TURMA;

        //Table name
        public static final String TABLE_NAME = "turma";

        public static final String COLUMN_CODIGO = "codigo";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_CODIGO_ESCOLA = "codigo_escola";
        public static final String COLUMN_NOME_ESCOLA = "nome_escola";

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class AlunoEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ALUNO).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ALUNO;

        //Table name
        public static final String TABLE_NAME = "aluno";

        public static final String COLUMN_CODIGO_TURMA = "codigo_turma";
        public static final String COLUMN_CODIGO_MATRICULA = "codigo_matricula";
        public static final String COLUMN_NUMERO_CHAMADA = "numero_chamada";
        public static final String COLUMN_NOME = "nome";

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
