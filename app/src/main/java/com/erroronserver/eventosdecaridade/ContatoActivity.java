package com.erroronserver.eventosdecaridade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContatoActivity extends AppCompatActivity {


    @BindView(R.id.btn_voltar)
    Button voltar;
    @BindView(R.id.btn_enviar)
    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
        ButterKnife.bind(this);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acaoBotoes(false);
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acaoBotoes(true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void acaoBotoes(boolean exibeMsg){
        startActivity(new Intent(this, MainActivity.class));
        if(exibeMsg){
            Toast.makeText(this, "Mensagem enviada com sucesso", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
