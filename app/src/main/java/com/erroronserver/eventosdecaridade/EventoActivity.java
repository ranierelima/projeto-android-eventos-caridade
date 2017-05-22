package com.erroronserver.eventosdecaridade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.erroronserver.eventosdecaridade.controller.EventosController;
import com.erroronserver.eventosdecaridade.model.Evento;
import com.erroronserver.eventosdecaridade.util.Constantes;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventoActivity extends AppCompatActivity {

    @BindView(R.id.tv_evento_data)
    TextView dataEvento;
    @BindView(R.id.tv_evento_endereco)
    TextView endereco;
    private Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        evento = (Evento) intent.getSerializableExtra(Constantes.INTENT_EVENTO);
        dataEvento.setText(evento.getDataEvento().toString());
    }
}
