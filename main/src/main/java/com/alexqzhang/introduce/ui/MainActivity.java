package com.alexqzhang.introduce.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.apps.muzei.MissingResourcesDialogFragment;
import com.google.android.apps.muzei.TutorialFragment;
import com.google.android.apps.muzei.WelcomeFragment;
import com.google.android.apps.muzei.settings.EffectsFragment;

import net.nurik.roman.muzei.R;

/**
 * MainActivity作用：
 * 1、整个APP的首页，但是根据不同情况，展示不同的页面；
 * 2、如果有广告，则展示广告页；
 * 3、如果是首次安装，或者当前手机没有启用我们的自动化壁纸，则展示应用壁纸页；
 * 4、否则，进入历史页面。
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO 广告页面暂时没有

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean seenTutorial = sp.getBoolean(TutorialFragment.PREF_SEEN_TUTORIAL, false);

        if (!seenTutorial) {
            seenTutorial = !isLiveWallpaperRunning(this, getPackageName());
            if (seenTutorial) {
                sp.edit().putBoolean(TutorialFragment.PREF_SEEN_TUTORIAL, true);
            }
        }

        if (seenTutorial) {
            TutorialFragment fragment = new TutorialFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } else {
            WelcomeFragment fragment = new WelcomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    /**
     * 判断一个动态壁纸是否已经在运行
     *
     * @param context          :上下文
     * @param tagetPackageName :要判断的动态壁纸的包名
     * @return
     */
    public static boolean isLiveWallpaperRunning(Context context, String tagetPackageName) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context); // 得到壁纸管理器
        WallpaperInfo wallpaperInfo = wallpaperManager.getWallpaperInfo(); // 如果系统使用的壁纸是动态壁纸话则返回该动态壁纸的信息,否则会返回null
        if (wallpaperInfo != null) { // 如果是动态壁纸,则得到该动态壁纸的包名,并与想知道的动态壁纸包名做比较
            String currentLiveWallpaperPackageName = wallpaperInfo.getPackageName();
            if (currentLiveWallpaperPackageName.equals(tagetPackageName)) {
                return true;
            }
        }
        return false;
    }
}
