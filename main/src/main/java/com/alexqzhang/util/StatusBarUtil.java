package com.alexqzhang.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

/**
 * 状态栏工具类
 * 作者： JairusTse
 * 日期： 17/12/19
 */
public class StatusBarUtil {

    /**
     * 修改状态栏颜色，支持4.4以上版本
     * @param activity
     * @param colorId
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(Activity activity, int colorId) {
        //Android6.0（API 23）以上，系统方法
        Window window = activity.getWindow();
        window.setStatusBarColor(activity.getResources().getColor(colorId));
    }

    /**
     * 设置状态栏模式
     * @param activity
     * @param isTextDark 文字、图标是否为黑色 （false为默认的白色）
     * @param colorId 状态栏颜色
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setStatusBarMode(Activity activity, boolean isTextDark, int colorId) {
        if(!isTextDark) {
            //文字、图标颜色不变，只修改状态栏颜色
            setStatusBarColor(activity, colorId);
        } else {
            //修改状态栏颜色和文字图标颜色
            setStatusBarColor(activity, colorId);
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}

