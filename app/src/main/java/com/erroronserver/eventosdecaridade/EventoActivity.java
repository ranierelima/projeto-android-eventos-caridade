package com.erroronserver.eventosdecaridade;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.erroronserver.eventosdecaridade.controller.EventosController;
import com.erroronserver.eventosdecaridade.controller.MapsController;
import com.erroronserver.eventosdecaridade.model.Evento;
import com.erroronserver.eventosdecaridade.util.Constantes;
import com.erroronserver.eventosdecaridade.util.VerificaConexao;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class EventoActivity extends AppCompatActivity {

    @BindView(R.id.tv_evento_data)
    TextView dataEvento;
    @BindView(R.id.tv_evento_endereco)
    TextView endereco;
    @BindView(R.id.tv_evento_descricao)
    TextView descricao;

    private Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        evento = (Evento) intent.getSerializableExtra(Constantes.INTENT_EVENTO);

        String dataFormatada = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dataFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(evento.getDataEvento());
        }else{
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            dataFormatada =  dateFormat.format(evento.getDataEvento());
        }

        dataEvento.setText(dataFormatada);
        if(evento.getDescricao() != null){
            descricao.setText(evento.getDescricao());
        }
    }

    @OnClick(R.id.btn_evento_mapa)
    public void abrirMapa(){

        boolean conexao = new VerificaConexao(EventoActivity.this).verificaConexao();
        if(conexao){

            MapsController.getInstance().setEvento(evento);
            startActivity(new Intent(EventoActivity.this, MapsActivity.class));

        }else{

            new SweetAlertDialog(EventoActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Sem conexão")
                    .setContentText("Para visualizar o local no mapa, é necessário ter conexão com a internet").show();

        }
    }
}
