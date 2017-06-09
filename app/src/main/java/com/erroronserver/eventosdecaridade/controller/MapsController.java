package com.erroronserver.eventosdecaridade.controller;

import com.erroronserver.eventosdecaridade.model.Evento;

import java.util.List;

/**
 * Created by Raniere de Lima (ranieredelima@gmail.com) on 09/06/2017.
 */

public class MapsController {

    private static MapsController instance;
    private Evento evento;
    private MapsController(){}

    public static MapsController getInstance(){

        if(instance == null){
            instance = new MapsController();
        }
        return instance;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
