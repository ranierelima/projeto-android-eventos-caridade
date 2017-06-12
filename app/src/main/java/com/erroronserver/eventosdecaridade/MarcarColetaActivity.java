package com.erroronserver.eventosdecaridade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.erroronserver.eventosdecaridade.model.enumerations.EnumTipoEvento;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarcarColetaActivity extends AppCompatActivity {

    @BindView(R.id.spinner_tipo_evento)
    Spinner tiposEventos;

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
}
