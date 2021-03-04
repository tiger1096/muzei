package com.alexqzhang.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.alexqzhang.service.WallpaperService;

public class BootReceiver extends BroadcastReceiver {
    private static boolean isBoot = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(isBoot) {
            return ;
        }
        Log.e("BootReceiver", "BootReceiver received msg");

        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, WallpaperService.class));
        } else {
            context.startService(new Intent(context, WallpaperService.class));
        }

        isBoot = true;
    }
}
