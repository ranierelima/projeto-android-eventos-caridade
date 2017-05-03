package com.erroronserver.eventosdecaridade;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.erroronserver.eventosdecaridade.adapter.ListaEventosAdapter;
import com.erroronserver.eventosdecaridade.controller.EventosController;
import com.erroronserver.eventosdecaridade.model.Evento;
import com.erroronserver.eventosdecaridade.model.ResponseJSON;
import com.erroronserver.eventosdecaridade.service.LoginService;
import com.erroronserver.eventosdecaridade.util.Constantes;
import com.erroronserver.eventosdecaridade.util.SharedPreferencesFactory;
import com.erroronserver.eventosdecaridade.util.VerificaConexao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String responseJSON;

    @BindView(R.id.lv_eventos)
    ListView listaEventos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(R.string.app_name);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        new GetEventosHTTP().execute();
       /* boolean temConexao = new VerificaConexao(MainActivity.this).verificaConexao();
        if(temConexao){
            excluirEventos();
        }else{
            List<Evento> eventos = Evento.listAll(Evento.class);
            EventosController.getInstance().setListaEventos(eventos);
            listaEventos.setAdapter(new ListaEventosAdapter(eventos, this));
        }*/
    }


    private void tratarEventos() {

        Gson gson = new Gson();
        ArrayList<Evento> eventos = (ArrayList<Evento>) gson.fromJson(this.responseJSON, new TypeToken<List<Evento>>() {
        }.getType());

//        salvaListaEmBanco(eventos);
        EventosController.getInstance().setListaEventos(eventos);
        listaEventos.setAdapter(new ListaEventosAdapter(eventos, this));
    }

    private void salvaListaEmBanco(ArrayList<Evento> eventos) {
        for (Evento e : eventos){
            e.save();
        }
    }


    private void excluirEventos() {
        List<Evento> eventos = Evento.listAll(Evento.class);
        for (Evento e : eventos){
            e.delete();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_marcar_coleta) {
            startActivity(new Intent(MainActivity.this, MarcarColetaActivity.class));
            finish();
        } else if (id == R.id.nav_contato) {
            startActivity(new Intent(MainActivity.this, ContatoActivity.class));
            finish();
        } else if (id == R.id.nav_logout) {
            SharedPreferencesFactory.remove(this, Constantes.TOKEN);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class GetEventosHTTP extends AsyncTask<Void, Void, Response> {

        OkHttpClient client = null;
        public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        private Request request;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, "");
            request = new Request.Builder().url(Constantes.URL_EVENTOS).post(body).build();
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
            tratarEventos();
        }
    }

}
