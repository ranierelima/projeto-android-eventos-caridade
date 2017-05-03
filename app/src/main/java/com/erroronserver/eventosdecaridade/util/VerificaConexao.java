package com.erroronserver.eventosdecaridade.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Raniere de Lima - contato@erroronserver.com on 02/05/2017.
 */

public class VerificaConexao {

    private Context context;

    public VerificaConexao(Context context){
        this.context = context;
    }

    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }
}
