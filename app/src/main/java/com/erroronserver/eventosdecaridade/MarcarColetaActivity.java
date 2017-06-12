package com.erroronserver.eventosdecaridade;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.erroronserver.eventosdecaridade.model.Evento;
import com.erroronserver.eventosdecaridade.model.enumerations.EnumTipoEvento;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

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
        Evento evento = new Evento();
        evento.setDescricao( descricao.getText().toString() );
//        finish();
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
        Calendar instance = GregorianCalendar.getInstance(tz);
        instance.set(Calendar.YEAR, year);
        instance.set(Calendar.MONTH, monthOfYear);
        instance.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        evento.setDataEvento(instance.getTime());

        dataEvento.setText( dayOfMonth + "/" + monthOfYear + "/" + year);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int horas, int minutos) {
        Date dataEvento = evento.getDataEvento();
        Calendar instance = Calendar.getInstance();
        instance.setTime(dataEvento);

        instance.set(Calendar.HOUR_OF_DAY, horas);
        instance.set(Calendar.MINUTE, minutos);

        horaEvento.setText( horas + ":" + minutos );
    }
}
