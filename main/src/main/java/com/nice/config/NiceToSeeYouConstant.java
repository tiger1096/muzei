package com.nice.config;

import android.content.Context;

import com.nice.storage.SharedPreferencesUtils;

public class NiceToSeeYouConstant {
    public static final String SHARED_PREFERENCES_NAME = "NiceToSeeYou";
    public static final String USER_ID = "USER_ID";

    public static final String SET_COOKIE_NUM = "Set-Cookie-Num";
    public static final String SET_COOKIE = "Set-Cookie";

    public static final String KNOWLEDGE_REPEAT_TIME = "NiceToSeeYou";
    public static final String KNOWLEDGE_REPEAT_INTERVAL = "NiceToSeeYou";

    private static Context context;
    private static int userId = -1;

    public static void setContext(Context context) {
        NiceToSeeYouConstant.context = context;
        userId = SharedPreferencesUtils.getInt(USER_ID);
    }

    public static Context getContext() {
        return context;
    }

    public static int getUserId() {
        if (userId < 0) {
            userId = SharedPreferencesUtils.getInt(USER_ID);
        }

        return userId;
    }
}
