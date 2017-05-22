package com.erroronserver.eventosdecaridade.model;

import com.erroronserver.eventosdecaridade.model.enumerations.EnumTipoEvento;

/**
 * Created by Raniere de Lima - contato@erroronserver.com on 21/05/2017.
 */
public class EventoTO extends AbstractIdentificavel{

    private EnumTipoEvento tipoEvento;
    private String dataEvento;
    private String latitude;
    private String longitude;
    private String descricao;

    public EnumTipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(EnumTipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
