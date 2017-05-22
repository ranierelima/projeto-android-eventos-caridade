package com.erroronserver.eventosdecaridade.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.erroronserver.eventosdecaridade.database.EventoCaridadeDBContract;
import com.erroronserver.eventosdecaridade.model.Evento;

/**
 * Created by Raniere de Lima - contato@erroronserver.com on 21/05/2017.
 */

public class EventoDAO extends AbstractDAO<Evento> {

    public EventoDAO(){
        super(Evento.class);
    }

    @Override
    protected void fillContent(ContentValues content, Evento object) {
        content.put(EventoCaridadeDBContract.Evento.COLUMN_ID, object.getId());
    }

    @Override
    protected void fillObject(Evento object, Cursor cursor) {

    }

    @Override
    protected String getTableName() {
        return EventoCaridadeDBContract.Evento.TABLE_NAME;
    }
}
