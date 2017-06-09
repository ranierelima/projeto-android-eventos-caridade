package com.erroronserver.eventosdecaridade.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventoCaridadeDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "eventocaridadeapp.db";
    private static final int DATABASE_VERSION = 1;

    private static EventoCaridadeDBHelper instance;

    private EventoCaridadeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized EventoCaridadeDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new EventoCaridadeDBHelper(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableEventos(db);
        createTableContato(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTableEventos(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (" + //TABLE_NAME
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " + //COLUMN_ID
                        "%s TEXT, " + //COLUMN_TIPO_EVENTO
                        "%s DATETIME, " + //COLUMN_DATA_EVENTO
                        "%s TEXT, " + //COLUMN_LATITUDE
                        "%s TEXT, " + //COLUMN_LONGITUDE
                        "%s TEXT) ",  //COLUMN_DESCRICAO
                EventoCaridadeDBContract.Evento.TABLE_NAME,
                EventoCaridadeDBContract.Evento.COLUMN_ID,
                EventoCaridadeDBContract.Evento.COLUMN_TIPO_EVENTO,
                EventoCaridadeDBContract.Evento.COLUMN_DATA_EVENTO,
                EventoCaridadeDBContract.Evento.COLUMN_LATITUDE,
                EventoCaridadeDBContract.Evento.COLUMN_LONGITUDE,
                EventoCaridadeDBContract.Evento.COLUMN_DESCRICAO
        ));
    }

    private void createTableContato(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (" + //TABLE_NAME
                        "%s INTEGER PRIMARY KEY AUTOINCREMENT, " + //COLUMN_ID
                        "%s TEXT, " + //COLUMN_ASSUNTO
                        "%s TEXT, " + //COLUMN_DESCRICAO
                        "%s INTEGER) ", //COLUMN_ENVIADA
                EventoCaridadeDBContract.Contato.TABLE_NAME,
                EventoCaridadeDBContract.Contato.COLUMN_ID,
                EventoCaridadeDBContract.Contato.COLUMN_ASSUNTO,
                EventoCaridadeDBContract.Contato.COLUMN_DESCRICAO,
                EventoCaridadeDBContract.Contato.COLUMN_ENVIADA
        ));
    }
}




