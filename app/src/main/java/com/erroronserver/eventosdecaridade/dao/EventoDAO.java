package com.erroronserver.eventosdecaridade.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.erroronserver.eventosdecaridade.database.EventoCaridadeDBContract;
import com.erroronserver.eventosdecaridade.model.Evento;
import com.erroronserver.eventosdecaridade.model.enumerations.EnumTipoEvento;

/**
 * Created by Raniere de Lima - contato@erroronserver.com on 21/05/2017.
 */

public class EventoDAO extends AbstractDAO<Evento> {

    public EventoDAO(){
        super(Evento.class);
    }

    @Override
    protected void fillContent(ContentValues content, Evento object) {
        content.put(EventoCaridadeDBContract.Evento.COLUMN_ID, object.getId() );
        content.put(EventoCaridadeDBContract.Evento.COLUMN_DATA_EVENTO, object.getDataEvento().getTime() );
        content.put(EventoCaridadeDBContract.Evento.COLUMN_DESCRICAO, object.getDescricao() );
        content.put(EventoCaridadeDBContract.Evento.COLUMN_LATITUDE, String.valueOf( object.getLatitude() ) );
        content.put(EventoCaridadeDBContract.Evento.COLUMN_LONGITUDE, String.valueOf( object.getLongitude() ) );
        content.put(EventoCaridadeDBContract.Evento.COLUMN_TIPO_EVENTO, String.valueOf( object.getTipoEvento() ) );
    }

    @Override
    protected void fillObject(Evento object, Cursor cursor) {

        object.setId( cursor.getInt(cursor.getColumnIndex( EventoCaridadeDBContract.Evento.COLUMN_ID )) );
        object.setDataEvento( cursor.getString(cursor.getColumnIndex( EventoCaridadeDBContract.Evento.COLUMN_DATA_EVENTO )) );
        object.setDescricao( cursor.getString(cursor.getColumnIndex( EventoCaridadeDBContract.Evento.COLUMN_DESCRICAO )) );
        object.setLatitude( Double.parseDouble( cursor.getString( cursor.getColumnIndex(EventoCaridadeDBContract.Evento.COLUMN_LATITUDE ) ) )  );
        object.setLongitude( Double.parseDouble( cursor.getString(cursor.getColumnIndex(EventoCaridadeDBContract.Evento.COLUMN_LONGITUDE) ) ) );
        object.setTipoEvento( EnumTipoEvento.valueOf( cursor.getString( cursor.getColumnIndex(EventoCaridadeDBContract.Evento.COLUMN_TIPO_EVENTO) )  ));

    }

    @Override
    protected String getTableName() {
        return EventoCaridadeDBContract.Evento.TABLE_NAME;
    }
}
