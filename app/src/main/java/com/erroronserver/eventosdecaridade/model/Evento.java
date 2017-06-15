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
    private Double latitude;
    private Double longitude;
    private String descricao;
    private boolean manual;

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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setId(int anInt) {
        this.setId( Long.parseLong( String.valueOf(anInt) ) );
    }

    public void setDataEvento(String dataEvento) {
        this.setDataEvento( new Date( Long.parseLong( dataEvento ) ) );
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }
}
