package com.erroronserver.eventosdecaridade.model;

import com.orm.SugarRecord;

/**
 * Created by Raniere de Lima - contato@erroronserver.com on 02/05/2017.
 */

public class Evento extends SugarRecord{

    private Integer cep;
    private String cidade;
    private String estado;
    private String bairro;
    private String rua;
    private String numero;
    private String data;

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
