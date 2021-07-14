package com.nice.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.nice.config.NiceToSeeYouConstant;

public class SharedPreferencesUtils {
    private static Context context = null;

    public static void setContext(Context context) {
        SharedPreferencesUtils.context = context;
    }

    public static String getString(String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NiceToSeeYouConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(name, "");
    }

    public static void setString(String name, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NiceToSeeYouConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static void deleteString(String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NiceToSeeYouConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, "");
        editor.commit();
    }

    public static void setInt(String name, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NiceToSeeYouConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public static int getInt(String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NiceToSeeYouConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(name, 0);
    }

    public static void setBoolean(String name, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NiceToSeeYouConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static boolean getBoolean(String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NiceToSeeYouConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(name, false);
    }


    public static void deleteInt(String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                NiceToSeeYouConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name, -1);
        editor.commit();
    }
}
