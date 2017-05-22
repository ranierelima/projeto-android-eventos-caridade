package com.erroronserver.eventosdecaridade.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.erroronserver.eventosdecaridade.application.EventoCaridadeApplication;
import com.erroronserver.eventosdecaridade.database.EventoCaridadeDBContract;
import com.erroronserver.eventosdecaridade.database.EventoCaridadeDBHelper;
import com.erroronserver.eventosdecaridade.model.AbstractIdentificavel;

/**
 * Created by Raniere de Lima - contato@erroronserver.com on 17/05/2017.
 */

public abstract class AbstractDAO<T extends AbstractIdentificavel> {

    protected EventoCaridadeDBHelper dbHelper;
    private final Class<T> clazz;

    public AbstractDAO(Class<T> clazz) {
        dbHelper = EventoCaridadeDBHelper.getInstance(EventoCaridadeApplication.getAppContext());
        this.clazz = clazz;
    }

    protected abstract void fillContent(ContentValues content, T object);
    protected abstract void fillObject(T object, Cursor cursor);
    protected abstract String getTableName();

    public synchronized T insertOrUpdate(T object) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();
        fillContent(content, object);

        long id = object.getId();

        if (id == 0 || db.update(getTableName(), content, String.format("%s = ?", EventoCaridadeDBContract.TableContract.COLUMN_ID),
                new String[] { String.valueOf(id) }) == 0) {
            id = db.insert(getTableName(), null, content);
            if (id > 0) object.setId(id);
        }

        db.close();

        return object;
    }

    public synchronized boolean delete(T object) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        boolean deleted = db.delete(getTableName(), String.format("%s = ?", EventoCaridadeDBContract.TableContract.COLUMN_ID),
                new String[] { String.valueOf(object.getId()) }) == 1;

        db.close();

        return deleted;
    }

    public synchronized int delete() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int deleted = db.delete(getTableName(), null, null);

        db.close();

        return deleted;
    }

    public synchronized T findById(long id) {
        T object = null;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(getTableName(), null,
                String.format("%s = ?", EventoCaridadeDBContract.TableContract.COLUMN_ID),
                new String[] { String.valueOf(id) }, null, null, null);

        if (cursor.moveToFirst()) {
            try {
                object = clazz.newInstance();
                fillObject(object, cursor);
            } catch (InstantiationException e){

            }catch(IllegalAccessException e) {

            }
        }

        db.close();

        return object;
    }

}
