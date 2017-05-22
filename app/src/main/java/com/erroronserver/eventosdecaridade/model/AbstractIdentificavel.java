package com.erroronserver.eventosdecaridade.model;

/**
 * Created by Raniere de Lima - contato@erroronserver.com on 17/05/2017.
 */

public class AbstractIdentificavel {
    public Long id;

    public Long getId(){
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
