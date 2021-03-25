package com.alexqzhang.introduce.ui;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.preference.PreferenceManager;

import com.google.android.apps.muzei.TutorialFragment;
import com.google.android.apps.muzei.WelcomeFragment;

import net.nurik.roman.muzei.R;

public class IntroActivity extends AppCompatActivity {

    private static boolean needShowSplash = true;

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
    protected void onPostResume() {
        super.onPostResume();
        ImageView imageView = findViewById(R.id.advertise);
        if (imageView.getVisibility() == View.VISIBLE) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                }
            },1000);
        }
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
        toggleViews(seenTutorial ? View.INVISIBLE : View.VISIBLE);

        if (!seenTutorial) {
            WelcomeFragment fragment = new WelcomeFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
        isInited = true;
    }

    public void toggleViews(int tutorialVisible) {
        TutorialVideoView tutorialVideoView = findViewById(R.id.background);
        tutorialVideoView.setVisibility(tutorialVisible);
        FragmentContainerView fragmentContainerView = findViewById(R.id.container);
        fragmentContainerView.setVisibility(tutorialVisible);

        ImageView imageView = findViewById(R.id.advertise);
        if (tutorialVisible == View.VISIBLE) {
            imageView.setVisibility(View.INVISIBLE);
        } else {
            imageView.setVisibility(View.VISIBLE);
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