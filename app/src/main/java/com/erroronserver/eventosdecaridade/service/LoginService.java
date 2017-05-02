package com.erroronserver.eventosdecaridade.service;

import com.erroronserver.eventosdecaridade.model.Usuario;
import com.erroronserver.eventosdecaridade.util.Constantes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Raniere de Lima - contato@erroronserver.com on 02/05/2017.
 */

public interface LoginService {

    @POST(Constantes.URL_LOGIN)
    Call<Void> realizarLogin (@Body final Usuario usuario);
}
