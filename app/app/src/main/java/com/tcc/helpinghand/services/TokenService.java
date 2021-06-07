package com.tcc.helpinghand.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.tcc.helpinghand.constants.Keys.ACCESS_TOKEN;

public class TokenService {

    public static void registerToken(Context context, String token) {
        SharedPreferences shared = context.
                getSharedPreferences(
                        ACCESS_TOKEN, Context.MODE_PRIVATE
                );

        SharedPreferences.Editor editor = shared.edit();
        editor.putString(ACCESS_TOKEN, token);

        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences shared = context.getSharedPreferences(ACCESS_TOKEN, Context.MODE_PRIVATE);
        return shared.getString(ACCESS_TOKEN, null);
    }

    public static void removeToken(Context context) {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.remove(ACCESS_TOKEN);
        editor.apply();
    }

}
