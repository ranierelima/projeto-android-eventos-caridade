package com.erroronserver.eventosdecaridade.controller;

import com.erroronserver.eventosdecaridade.model.Evento;

import java.util.List;

/**
 * Created by Raniere de Lima - contato@erroronserver.com on 02/05/2017.
 */

public class EventosController {

    private static EventosController eventosController;
    private Evento evento;
    private List<Evento> eventos;
    private EventosController(){}

    public static EventosController getInstance(){

        if(eventosController == null){
            eventosController = new EventosController();
        }
        return  eventosController;
    }

    public void setListaEventos(List<Evento> eventos){
        this.eventos = eventos;
    }

    public void setEventoById(Integer id){
        for(Evento e : eventos){
            if(e.getId().equals(id)){
                this.evento = e;
                break;
            }
        }
    }

    public Evento getById(Integer id){
        Evento retorno = null;
        for(Evento e : eventos){
            if(e.getId().equals(id)){
                retorno = e;
            }
        }
        return retorno;
    }

}
