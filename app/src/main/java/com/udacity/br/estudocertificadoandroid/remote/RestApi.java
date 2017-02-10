package com.udacity.br.estudocertificadoandroid.remote;

import com.udacity.br.estudocertificadoandroid.model.Aluno;
import com.udacity.br.estudocertificadoandroid.model.Turma;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BPardini on 10/02/2017.
 */

public class RestApi {

    public static final int SUCCESS = 200;
    public static final int ERROR = 500;

    public static final String URL_TURMAS = "https://demo7351248.mockable.io";

    public static Turma[] downloadData() throws Exception{
        InputStream inputStream;
        HttpURLConnection urlConnection;

        /* forming th java.net.URL object */
        URL url = new URL(URL_TURMAS);
        urlConnection = (HttpURLConnection) url.openConnection();
            /* optional request header */
        urlConnection.setRequestProperty("Content-Type", "application/json");

            /* optional request header */
        urlConnection.setRequestProperty("Accept", "application/json");

            /* for Get request */
        urlConnection.setRequestMethod("GET");

        int statusCode = urlConnection.getResponseCode();

            /* 200 represents HTTP OK */
        if (statusCode == SUCCESS) {
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            String response = convertInputStreamToString(inputStream);
            return parseResult(response);
        } else {
            throw new Exception("Failed to fetch data!!");
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

            /* Close Stream */
        if (null != inputStream) {
            inputStream.close();
        }

        return result;
    }

    private static Turma[] parseResult(String result){
        Turma[] turmas = null;

        try{
            JSONObject response = new JSONObject(result);
            JSONArray arrayTurmas = response.getJSONArray("Turmas");

            turmas = new Turma[arrayTurmas.length()];

            for(int i = 0; i < arrayTurmas.length(); i++){
                Turma turma = new Turma();
                List<Aluno> listAlunos = new ArrayList<>();

                JSONObject objTurma = arrayTurmas.getJSONObject(i);
                turma.setCodigo(objTurma.getLong("CodigoTurma"));
                turma.setCodigoEscola(objTurma.getInt("CodigoEscola"));
                turma.setNomeEscola(objTurma.getString("NomeEscola"));
                turma.setNome(objTurma.getString("NomeTurma"));

                JSONArray alunos = objTurma.getJSONArray("Alunos");
                for(int j = 0; j < alunos.length(); j++){
                    Aluno aluno = new Aluno();

                    JSONObject objAluno = alunos.getJSONObject(j);

                    aluno.setCodigoMatricula(objAluno.getLong("CodigoMatricula"));
                    aluno.setCodigoTurma(turma.getCodigo());
                    aluno.setNome(objAluno.getString("NomeAluno"));
                    aluno.setNumeroChamada(objAluno.getInt("NumeroChamada"));

                    listAlunos.add(aluno);
                }

                turma.setAlunos(listAlunos);

                turmas[i] = turma;
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        return turmas;
    }

}
