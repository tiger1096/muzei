package com.alexqzhang.introduce.ui;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.alexqzhang.mainpage.ui.MainActivity;
import com.alexqzhang.user.ui.LoginActivity;
import com.google.android.apps.muzei.TutorialFragment;
import com.google.android.apps.muzei.WelcomeFragment;

import net.nurik.roman.muzei.R;

public class IntroActivity extends AppCompatActivity {

    private boolean isInited = false;
    private TutorialVideoView tutorialVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initBackgroundVideo();

        isInited = false;
        initView();
    }

    @Override
    protected void onResume() {
        initView(); // 用于在设置背景后返回MainActivity重新绘制
        super.onResume();
    }

    @Override
    protected void onPause() {
        isInited = false;
        tutorialVideoView.stopPlayback();
        super.onPause();
    }

    //返回重启加载
    @Override
    protected void onRestart() {
        initBackgroundVideo();
        super.onRestart();
    }

    private void initBackgroundVideo() {
        //加载视频资源控件
        tutorialVideoView = findViewById(R.id.background);
        //设置播放加载路径
        tutorialVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rimoldi));
        //播放
        tutorialVideoView.start();
        //循环播放
        tutorialVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                tutorialVideoView.start();
            }
        });
        tutorialVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                            tutorialVideoView.setBackgroundColor(Color.TRANSPARENT);
                        return true;
                    }
                });
            }
        });
    }

    private void initView() {
        if (isInited) {
            return;
        }

        // TODO 广告页面暂时没有

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean seenTutorial = sp.getBoolean(TutorialFragment.PREF_SEEN_TUTORIAL, false);

        if (!seenTutorial) {
            seenTutorial = isLiveWallpaperRunning(this, getPackageName());
            if (seenTutorial) {
                sp.edit().putBoolean(TutorialFragment.PREF_SEEN_TUTORIAL, true);
            }
        }

        if (!seenTutorial) {
            WelcomeFragment fragment = new WelcomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } else {
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
        }
        isInited = true;
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