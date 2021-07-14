package com.nice.config;

import android.content.Context;

public class NiceToSeeYouConstant {
    public static final String SHARED_PREFERENCES_NAME = "NiceToSeeYou";

    public static final String SET_COOKIE_NUM = "Set-Cookie-Num";
    public static final String SET_COOKIE = "Set-Cookie";

    public static final String KNOWLEDGE_REPEAT_TIME = "NiceToSeeYou";
    public static final String KNOWLEDGE_REPEAT_INTERVAL = "NiceToSeeYou";

    private static Context context;

    public static void setContext(Context context) {
        NiceToSeeYouConstant.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
