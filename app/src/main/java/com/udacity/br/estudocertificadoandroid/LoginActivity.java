package com.udacity.br.estudocertificadoandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.br.estudocertificadoandroid.adapter.TurmaAdapter;
import com.udacity.br.estudocertificadoandroid.model.Turma;
import com.udacity.br.estudocertificadoandroid.model.User;
import com.udacity.br.estudocertificadoandroid.provider.ContentContract;
import com.udacity.br.estudocertificadoandroid.receiver.TurmasResultReceiver;
import com.udacity.br.estudocertificadoandroid.remote.RestApi;
import com.udacity.br.estudocertificadoandroid.service.DataService;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements TurmasResultReceiver.Receiver {

    private EditText editUser, editPassword;
    private Button btLogin;
    private ProgressBar progress;
    private TurmasResultReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUser = (EditText) findViewById(R.id.edit_user);
        editPassword = (EditText) findViewById(R.id.edit_password);
        btLogin = (Button) findViewById(R.id.bt_login);
        progress = (ProgressBar) findViewById(R.id.progress);

        //Consulta se há algum usuário logado, então vai para a MainActivity
        Cursor c = getContentResolver().query(ContentContract.UserEntry.CONTENT_URI, null, null, null, null);
        if(c != null && c.getCount() > 0){
            if(c.moveToNext()){
                finish();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
            c.close();
        }
    }

    protected void onResume(){
        super.onResume();

        receiver = new TurmasResultReceiver(new Handler());

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txtUser = editUser.getText().toString();
                String txtPassword = editPassword.getText().toString();

                if(txtUser.equals("")){
                    Toast.makeText(LoginActivity.this, "Type the user", Toast.LENGTH_SHORT).show();
                    editUser.requestFocus();
                }
                else if(txtPassword.equals("")){
                    Toast.makeText(LoginActivity.this, "Type the password", Toast.LENGTH_SHORT).show();
                    editPassword.requestFocus();
                }
                else{
                    //Salva o usuário no ContentProvider
                    ContentValues userValues = new ContentValues();
                    userValues.put(ContentContract.UserEntry.COLUMN_USERNAME, txtUser);
                    userValues.put(ContentContract.UserEntry.COLUMN_ACTIVE, 1);

                    getContentResolver().insert(ContentContract.UserEntry.CONTENT_URI, userValues);

                    receiver.setReceiver(LoginActivity.this);
                    Intent intent = new Intent(Intent.ACTION_SYNC, null, LoginActivity.this, DataService.class);
                    intent.putExtra("receiver", receiver);

                    startService(intent);

                    progress.setVisibility(View.VISIBLE);
                    progress.setIndeterminate(true);
                }
            }
        });
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        progress.setVisibility(View.GONE);

        switch (resultCode){
            case RestApi.SUCCESS:
                ArrayList<Turma> turmas = new ArrayList<>();

                Parcelable[] parcelableTurmas = resultData.getParcelableArray("turmas");

                if(parcelableTurmas != null){
                    for(Parcelable parcelableTurma : parcelableTurmas){
                        Turma turma = (Turma) parcelableTurma;

                        turmas.add(turma);
                    }
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putParcelableArrayListExtra("turmas", turmas);
                startActivity(intent);

                break;
            case RestApi.ERROR:
                Log.d("ERROR", "An error has occurred");
                Toast.makeText(LoginActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
