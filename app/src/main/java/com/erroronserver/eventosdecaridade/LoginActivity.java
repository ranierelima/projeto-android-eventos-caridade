package com.erroronserver.eventosdecaridade;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.erroronserver.eventosdecaridade.model.ResponseJSON;
import com.erroronserver.eventosdecaridade.model.Usuario;
import com.erroronserver.eventosdecaridade.service.LoginService;
import com.erroronserver.eventosdecaridade.util.Constantes;
import com.erroronserver.eventosdecaridade.util.SharedPreferencesFactory;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    // UI references.
    @BindView(R.id.et_login)
    AutoCompleteTextView mEmailView;

    @BindView(R.id.et_password)
    EditText mPasswordView;

    @BindView(R.id.cb_manterConectado)
    CheckBox manterConectado;

    @BindView(R.id.btn_login)
    Button btnLoogin;

    private String responseJSON;
    LoginActivity loginActivity;
    private SweetAlertDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loginActivity = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressBar = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);

        if( SharedPreferencesFactory.get(this, Constantes.TOKEN, null) != null ){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    return true;
                }
                return false;
            }
        });

    }

    @OnClick(R.id.btn_login)
    public void realizarLogin(){

        // hide the keyboard in order to avoid getTextBeforeCursor on inactive InputConnection
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);


        progressBar.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressBar.setTitleText("Aguarde");
        progressBar.setCancelable(false);
        progressBar.show();


        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder().url(Constantes.URL_LOGIN)
                .post(RequestBody.create(JSON, parseUserToJSON())).build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loginActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.hide();
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Erro")
                                .setContentText(Constantes.ERROR_API_LOGIN ).show();
                    }
                });
//                Toast.makeText(LoginActivity.this, , Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                loginActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            responseJSON  = response.body().string();
                        } catch (IOException e) {
                            Log.e("error", "Login Acitivty, conversão response.body.toString");
                        }
                        validacaoResposta();
                        progressBar.hide();
                    }
                });
            }
        });
    }



    private String parseUserToJSON() {

        String login = mEmailView.getText().toString();
        String senha = mPasswordView.getText().toString();

        Usuario usuario = new Usuario();
        usuario.setSenha(senha);
        usuario.setLogin(login);

        Gson gson = new Gson();
        String usuarioJSON = gson.toJson(usuario);

        return usuarioJSON;
    }

    private void validacaoResposta() {
        Gson gson = new Gson();
        ResponseJSON json = gson.fromJson(this.responseJSON, ResponseJSON.class);

        if(json.getStatus().equals("FAIL")){
            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Erro")
                    .setContentText("Login e/ou senha inválidos").show();
        }else{
            if( manterConectado.isChecked() ){
                SharedPreferencesFactory.set(this, Constantes.TOKEN, json.getToken());
            }
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

    }

    private class RealizanLoginRetrofit extends AsyncTask<Void, Void, Response>{
        @Override
        protected Response doInBackground(Void... params) {

            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, parseUserToJSON());
            Request request = new Request.Builder().url(Constantes.URL_LOGIN)
                    .post(body).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(LoginActivity.this, Constantes.ERROR_API_LOGIN , Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    responseJSON  = response.body().string();
                    validacaoResposta();
                }
            });

            return null;
        }


        /*OkHttpClient client = null;
        LoginService loginService;
        public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        private Request request;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, parseUserToJSON());
            request = new Request.Builder().url(Constantes.URL_LOGIN).post(body).build();
        }

        @Override
        protected Response doInBackground(Void... params) {
            try {
                Response response = client.newCall(request).execute();
                return response;
            }catch (IOException e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(Response response) {
            try {
                if (response.message().equals("OK")){
                    responseJSON = response.body().string();
                }
            }catch (IOException e){
                responseJSON = "OPS - Fail connection";
            }
            validacaoResposta();
        }*/
    }
}

