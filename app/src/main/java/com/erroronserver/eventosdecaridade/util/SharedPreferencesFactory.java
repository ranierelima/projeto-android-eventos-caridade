package com.erroronserver.eventosdecaridade.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.erroronserver.eventosdecaridade.application.EventoCaridadeApplication;

/**
 * Created by Raniere de Lima - contato@erroronserver.com on 02/05/2017.
 */

public class SharedPreferencesFactory {
    private static SharedPreferences sPreferencias;

    public static SharedPreferences getInstance(Context context) {

        if (sPreferencias == null) {
            sPreferencias = context.getSharedPreferences(Constantes.PREF_FILE_NAME, context.MODE_PRIVATE);
        }

        return sPreferencias;
    }

    public static void set(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    public static String get(Context context, String preferenceName, String defaultValue) {
        return getInstance(context).getString(preferenceName, defaultValue);
    }

    public void remove(Context context, String chave){
        SharedPreferences sharedPreferences = getInstance(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(chave);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
}
