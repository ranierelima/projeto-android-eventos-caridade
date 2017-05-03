package com.erroronserver.eventosdecaridade;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        int anInt = extras.getInt(Constantes.INTENT_EVENTO);
        Evento evento = EventosController.getInstance().getById(anInt);
        dataEvento.setText(evento.getData());
        endereco.setText( "Cep:" + evento.getCep() + " Bairro:" + evento.getRua());
    }
}
