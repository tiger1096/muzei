package com.alexqzhang.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alexqzhang.base.config.PictureConfig;
import com.alexqzhang.base.config.TextConfig;
import com.alexqzhang.base.data.ZFont;
import com.alexqzhang.print.PrintService;

public class WallpaperService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("alex", "WallpaperService onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("alex", "WallpaperService onStartCommand");

        ZFont zFont = new ZFont();
        PictureConfig pictureConfig = new PictureConfig();
        TextConfig textConfig = new TextConfig();

        Bitmap outputBitmap = PrintService.print(null, null, null);

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        Log.e("alex", "WallpaperService onDestroy");

        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
