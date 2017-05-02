package com.erroronserver.eventosdecaridade.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Raniere de Lima - contato@erroronserver.com on 02/05/2017.
 */

public class EventoCaridadeApplication extends Application {

    private static EventoCaridadeApplication sInstance;

    public static EventoCaridadeApplication getInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }

}
