package com.alexqzhang.mainpage.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.apps.muzei.MainFragment;

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
        MainFragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
