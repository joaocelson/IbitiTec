package com.ibititec.campeonatold.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.ibititec.campeonatold.MainActivity;
import com.ibititec.campeonatold.R;
import com.ibititec.campeonatold.helpers.HttpHelper;
import com.ibititec.campeonatold.helpers.JsonHelper;
import com.ibititec.campeonatold.modelo.Usuario;

import java.io.IOException;

public class LoginUsuarioActivity extends AppCompatActivity {

    private Button btnLogin, btnCadastrarUsuario;
    private EditText txtEmail, txtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewByIdComponente();
        executarAcoes();
        validarUsuarioCadastrado();
    }

    private void validarUsuarioCadastrado() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginUsuarioActivity.this);
        final String usuarioJson = sharedPreferences.getString("usuario.json", "");

        Usuario usuarioLocal = (Usuario) JsonHelper.getObject(usuarioJson, Usuario.class);

        if (usuarioLocal != null) {
            startarActivity();
        }
    }


    private void executarAcoes() {
        btnCadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityCadastro();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtEmail.getWindowToken(), 0);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtEmail.getWindowToken(), 0);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                Usuario usuario = new Usuario();
                usuario.setLoginEmail(txtEmail.getText().toString());
                usuario.setSenha(txtSenha.getText().toString());
                fazerLogin(usuario);
            }
        });
    }

    private void startActivityCadastro() {
        Intent intent = new Intent(this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

    private void findViewByIdComponente() {
        btnLogin = (Button) findViewById(R.id.btnLogin_login);
        btnCadastrarUsuario = (Button) findViewById(R.id.btnCadastrar_login);
        txtEmail = (EditText) findViewById(R.id.txtEmailUsuario_login);
        txtSenha = (EditText) findViewById(R.id.txtSenhaUsuario_login);
    }

    private void fazerLogin(Usuario usuario) {

        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginUsuarioActivity.this);
            final String usuarioJson = JsonHelper.objectToJson(usuario);
            Log.i(MainActivity.TAG, "Json lido do banco local ao fazer o login: " + usuarioJson);
            Usuario usuarioLocal = (Usuario) JsonHelper.getObject(sharedPreferences.getString(MainActivity.USUARIO + ".json", ""), Usuario.class);

            if (usuarioLocal != null && usuario.getSenha() == usuarioLocal.getSenha() && usuario.getLoginEmail() == usuarioLocal.getLoginEmail()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            } else {
                (new AsyncTask<String, Void, String>() {
                    ProgressDialog progressDialog;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        progressDialog = ProgressDialog.show(LoginUsuarioActivity.this, "Aguarde", "Atualizando dados");
                    }

                    @Override
                    protected String doInBackground(String... params) {
                        String json = null;

                        try {
                            String url = params[0];

                            json = HttpHelper.POSTJson(url, usuarioJson);
                            Log.i(MainActivity.TAG, "Json retornado ao fazer o login: " + json);
                            if (json != null) {
                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginUsuarioActivity.this);
                                sharedPreferences.edit().putString(MainActivity.USUARIO + ".json", json).apply();
                            }

                            Log.i(MainActivity.TAG, json);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e(MainActivity.TAG, String.format(getString(R.string.msg_erro_json), e.getMessage()));
                        }
                        return json;
                    }

                    @Override
                    protected void onPostExecute(String json) {
                        super.onPostExecute(json);

                        progressDialog.dismiss();
                        if (json != null) {
                            startarActivity();
                        } else {
                            String mensagem = "Usuário ou senha incorretos.";
                            Snackbar.make(findViewById(R.id.btnCadastrar_cadastro), mensagem, Snackbar.LENGTH_SHORT).show();
                        }

                    }
                }).execute(getString(R.string.url_login_usuario));
            }
        } catch (Exception ex) {
            Log.i(MainActivity.TAG, "Erro ao salvar usuario." + ex.getMessage());
        }
    }

    private void startarActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
