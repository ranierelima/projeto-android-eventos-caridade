package com.erroronserver.eventosdecaridade;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.erroronserver.eventosdecaridade.controller.MapsController;
import com.erroronserver.eventosdecaridade.model.Evento;
import com.erroronserver.eventosdecaridade.model.enumerations.EnumTipoEvento;
import com.erroronserver.eventosdecaridade.util.VerificaConexao;
import com.google.common.collect.Maps;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MarcarColetaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.spinner_tipo_evento)
    Spinner tiposEventos;

    @BindView(R.id.et_descricao_evento)
    EditText descricao;

    @BindView(R.id.tv_evento_data)
    TextView dataEvento;

    @BindView(R.id.tv_evento_hora)
    TextView horaEvento;

    Evento evento = new Evento();
    private Calendar timeInMostrar;

    private Calendar calendarEvento = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_coleta);
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, EnumTipoEvento.getAll());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tiposEventos.setAdapter(adapter);

        evento.setId(null);

        InputMethodManager imm = (InputMethodManager) MarcarColetaActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(descricao.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    @OnItemSelected(R.id.spinner_tipo_evento)
    public void onItemSelected(AdapterView<?> parent, View view, int position) {
        EnumTipoEvento periodo = EnumTipoEvento.values()[position];
        updateTipoEventoSpinner(periodo);
    }

    private void updateTipoEventoSpinner(EnumTipoEvento periodo) {

        evento.setTipoEvento(periodo);

    }

    @OnClick(R.id.btn_voltar_evento)
    public void btnVoltar(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.btn_avanca_evento)
    public void btnAvancar(){
        if( descricao.getText().toString().isEmpty() ||
            dataEvento.getText().toString().isEmpty() ||
            horaEvento.getText().toString().isEmpty() ){

            new SweetAlertDialog(MarcarColetaActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Campos vazios")
                    .setContentText("Preencha todas as informações para continuar").show();

        } else if( !new VerificaConexao(MarcarColetaActivity.this).verificaConexao()){

            new SweetAlertDialog(MarcarColetaActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Sem conexão")
                    .setContentText("Para continuar, é necessário ter conexão com a internet").show();

        } else{
            evento.setDataEvento( calendarEvento.getTime() );
            evento.setDescricao( descricao.getText().toString() );
            evento.setLongitude( null );
            evento.setLatitude( null );

            MapsController.getInstance().setEvento(evento);
            MapsController.getInstance().isAdicao(true);

            startActivity( new Intent(MarcarColetaActivity.this, MapsActivity.class));
            finish();
        }
    }


    @OnClick(R.id.tv_evento_data)
    public void mostrarDatePickerParaData() {
        InputMethodManager imm = (InputMethodManager) MarcarColetaActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tiposEventos.getWindowToken(), 0);

        TimeZone tz = TimeZone.getDefault();
        TimeZone.setDefault(tz);
        timeInMostrar = GregorianCalendar.getInstance(tz);
        int ano = timeInMostrar.get(Calendar.YEAR);
        int mes = timeInMostrar.get(Calendar.MONTH);
        int dia = timeInMostrar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog mdatePickerDialog = new DatePickerDialog(MarcarColetaActivity.this, MarcarColetaActivity.this, ano, mes, dia);
        //  TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(), ConfirmarAgendamentoFragment.this, hora, min,true);
        mdatePickerDialog.setTitle("Selecione a Data");
        mdatePickerDialog.show();

    }

    @OnClick(R.id.tv_evento_hora)
    public void mostrarTimePicker(){

        InputMethodManager imm = (InputMethodManager) MarcarColetaActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tiposEventos.getWindowToken(), 0);

        TimeZone tz = TimeZone.getDefault();
        TimeZone.setDefault(tz);
        timeInMostrar = GregorianCalendar.getInstance(tz);
        int hora = timeInMostrar.get(Calendar.HOUR_OF_DAY);
        int minuto = timeInMostrar.get(Calendar.MINUTE);
        TimePickerDialog pickerDialog = new TimePickerDialog(MarcarColetaActivity.this, MarcarColetaActivity.this, hora, minuto, true);
        pickerDialog.setTitle("Selecione a hora");
        pickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        TimeZone tz = TimeZone.getDefault();
        TimeZone.setDefault(tz);
        calendarEvento.set(Calendar.YEAR, year);
        calendarEvento.set(Calendar.MONTH, monthOfYear);
        calendarEvento.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        dataEvento.setText( dayOfMonth + "/" + monthOfYear + "/" + year);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int horas, int minutos) {
        calendarEvento.set(Calendar.HOUR_OF_DAY, horas);
        calendarEvento.set(Calendar.MINUTE, minutos);

        horaEvento.setText( horas + ":" + minutos );
    }
}
