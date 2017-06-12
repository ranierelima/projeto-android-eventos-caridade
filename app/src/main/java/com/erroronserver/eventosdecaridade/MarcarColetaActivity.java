package com.erroronserver.eventosdecaridade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.erroronserver.eventosdecaridade.model.Evento;
import com.erroronserver.eventosdecaridade.model.enumerations.EnumTipoEvento;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MarcarColetaActivity extends AppCompatActivity {

    @BindView(R.id.spinner_tipo_evento)
    Spinner tiposEventos;

    @BindView(R.id.et_descricao_evento)
    EditText descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_coleta);
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, EnumTipoEvento.getAll());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tiposEventos.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.btn_voltar_evento)
    public void btnVoltar(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.btn_avanca_evento)
    public void btnAvancar(){
        Evento evento = new Evento();
        evento.setDescricao( descricao.getText().toString() );
        evento.set
        finish();
    }
}
