package com.tcc.helpinghand.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TokenService {

    public static void registerToken(Context context, String key, String token) {
        SharedPreferences shared = context.
                getSharedPreferences(
                        key, Context.MODE_PRIVATE
                );

        SharedPreferences.Editor editor = shared.edit();
        editor.putString(key, token);

        editor.apply();
    }

    public static String getToken(Context context, String key) {
        SharedPreferences shared = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return shared.getString(key, null);
    }

    public static void removeToken(Context context, String key) {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = shared.edit();
        editor.remove(key);
        editor.apply();
    }

}
