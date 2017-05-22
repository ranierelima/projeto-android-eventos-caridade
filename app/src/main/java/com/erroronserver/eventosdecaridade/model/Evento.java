package com.erroronserver.eventosdecaridade.model;

import android.util.Log;

import com.erroronserver.eventosdecaridade.model.enumerations.EnumTipoEvento;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Raniere de Lima - contato@erroronserver.com on 02/05/2017.
 */

public class Evento extends AbstractIdentificavel implements Serializable{


    /*public static String COLUMN_TIPO_EVENTO = "tipoEvento";
    public static String COLUMN_DATA_EVENTO = "dataEvento";
    public static String COLUMN_LATITUDE = "latitude";
    public static String COLUMN_LONGITUDE = "longitude";
    public static String COLUMN_DESCRICAO = "descricao";*/

    private EnumTipoEvento tipoEvento;
    private Date dataEvento;
    private double latitude;
    private double longitude;
    private String descricao;

    public EnumTipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(EnumTipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
