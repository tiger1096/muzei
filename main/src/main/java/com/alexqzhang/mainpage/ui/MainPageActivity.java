package com.alexqzhang.mainpage.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.alexqzhang.discover.ui.DiscoverFragment;
import com.alexqzhang.history.ui.HistoryFragment;
import com.alexqzhang.me.ui.SettingFragment;
import com.alexqzhang.util.StatusBarUtil;
import com.nice.seeyou.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainPageActivity extends AppCompatActivity implements OnClickListener {

    private ImageView tabHistoryScan;
    private ImageView tabDiscoverWallpaper;
    private ImageView tabStartJourney;
    private ImageView tabDiscoverKnowledge;
    private ImageView tabMe;

    //声明四个Tab分别对应的Fragment
    private Fragment mFragHistory;
    private Fragment mFragDiscover;
    private Fragment mFragCommunity;
    private Fragment mFragSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setStatusBarMode(this, true, R.color.colorWhite);
        }
        setContentView(R.layout.activity_main_page);
        initViews();//初始化控件
        initEvents();//初始化事件
        selectTab(0);//默认选中第一个Tab
    }

    private void initEvents() {
        //初始化四个Tab的点击事件
        tabHistoryScan.setOnClickListener(this);
        tabDiscoverWallpaper.setOnClickListener(this);
        tabStartJourney.setOnClickListener(this);
        tabDiscoverKnowledge.setOnClickListener(this);
        tabMe.setOnClickListener(this);
    }

    private void initViews() {
        // 初始化五个Tab的Image按钮
        tabHistoryScan = findViewById(R.id.tab_history_scan);
        tabDiscoverWallpaper = findViewById(R.id.tab_discover_wallpaper);
        tabStartJourney = findViewById(R.id.tab_start_journey);
        tabDiscoverKnowledge = findViewById(R.id.tab_discover_knowledge);
        tabMe = findViewById(R.id.tab_me);
    }

    //处理Tab的点击事件
    @Override
    public void onClick(View v) {
        //先将四个ImageButton置为灰色
        resetImgs();
        switch (v.getId()) {
            case R.id.tab_history_scan:
                selectTab(0);//当点击的是微信的Tab就选中微信的Tab
                break;
            case R.id.tab_discover_wallpaper:
                selectTab(1);
                break;
            case R.id.tab_start_journey:
                selectTab(2);
                break;
            case R.id.tab_discover_knowledge:
                selectTab(3);
                break;
            case R.id.tab_me:
                selectTab(4);
                break;
        }

    }

    //进行选中Tab的处理
    private void selectTab(int i) {
        //获取FragmentManager对象
        FragmentManager manager = getSupportFragmentManager();
        //获取FragmentTransaction对象
        FragmentTransaction transaction = manager.beginTransaction();
        //先隐藏所有的Fragment
        hideFragments(transaction);
        switch (i) {
            //当选中点击的是微信的Tab时
            case 0:
                //设置微信的ImageButton为绿色
                tabHistoryScan.setBackgroundResource(R.drawable.history_scan_selected);
                //如果微信对应的Fragment没有实例化，则进行实例化，并显示出来
                if (mFragHistory == null) {
                    mFragHistory = new HistoryFragment();
                    transaction.add(R.id.id_content, mFragHistory);
                } else {
                    //如果微信对应的Fragment已经实例化，则直接显示出来
                    transaction.show(mFragHistory);
                }
                break;
            case 1:
                tabDiscoverWallpaper.setBackgroundResource(R.drawable.discover_wallpaper_selected);
                if (mFragDiscover == null) {
                    mFragDiscover = new DiscoverFragment();
                    transaction.add(R.id.id_content, mFragDiscover);
                } else {
                    transaction.show(mFragDiscover);
                }
                break;
            case 2:
                tabStartJourney.setBackgroundResource(R.drawable.start_journey_selected);
                if (mFragCommunity == null) {
                    mFragCommunity = new AddressFragment();
                    transaction.add(R.id.id_content, mFragCommunity);
                } else {
                    transaction.show(mFragCommunity);
                }
                break;
            case 3:
                tabDiscoverKnowledge.setBackgroundResource(R.drawable.discover_knowledge_selected);
                if (mFragCommunity == null) {
                    mFragCommunity = new AddressFragment();
                    transaction.add(R.id.id_content, mFragCommunity);
                } else {
                    transaction.show(mFragCommunity);
                }
                break;
            case 4:
                tabMe.setBackgroundResource(R.drawable.me_selected);
                if (mFragSetting == null) {
                    mFragSetting = new SettingFragment();
                    transaction.add(R.id.id_content, mFragSetting);
                } else {
                    transaction.show(mFragSetting);
                }
                break;
        }
        //不要忘记提交事务
        transaction.commit();
    }

    //将四个的Fragment隐藏
    private void hideFragments(FragmentTransaction transaction) {
        if (mFragHistory != null) {
            transaction.hide(mFragHistory);
        }
        if (mFragDiscover != null) {
            transaction.hide(mFragDiscover);
        }
        if (mFragCommunity != null) {
            transaction.hide(mFragCommunity);
        }
        if (mFragSetting != null) {
            transaction.hide(mFragSetting);
        }
    }

    //将四个ImageButton置为灰色
    private void resetImgs() {
        tabHistoryScan.setBackgroundResource(R.drawable.history_scan);
        tabDiscoverWallpaper.setBackgroundResource(R.drawable.discover_wallpaper);
        tabStartJourney.setBackgroundResource(R.drawable.start_journey);
        tabDiscoverKnowledge.setBackgroundResource(R.drawable.discover_knowledge);
        tabMe.setBackgroundResource(R.drawable.me);
    }
}
