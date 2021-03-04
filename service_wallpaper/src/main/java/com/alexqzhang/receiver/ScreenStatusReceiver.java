package com.alexqzhang.receiver;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class ScreenStatusReceiver extends BroadcastReceiver {

    Context mContext;

    public ScreenStatusReceiver() {
        super();
    }

    public ScreenStatusReceiver(Context context) {
        Log.e("ScreenStatusReceiver", "ScreenStatusReceiver construct");
        mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("ScreenStatusReceiver", "ScreenStatusReceiver receive " + intent.getAction());

        if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            setLockWallPaper();
        }
    }

    private void setLockWallPaper() {
        Log.e("setLockWallPaper","setLockWallPaper start");

        try {
            WallpaperManager mWallManager = WallpaperManager.getInstance(mContext);
            Bitmap bitmap = getImageFromAssetsFile("bg1.jpg");
            if (bitmap == null) {
                Log.e("setLockWallPaper","bitmap is null");
            }

            Log.e("setLockWallPaper","bitmap is null2");

//            InputPictureParam inputPictureParam = new InputPictureParam();
//            InputTextParam inputTextParam = new InputTextParam();
//            OutputPictureParam outputPictureParam = new OutputPictureParam();
//            inputPictureParam.setBitmap(bitmap);
//            inputPictureParam.setStartPoint(new PointF(0.05f, 0.9f));
//            inputTextParam.setFontSize(35.f);
//            String[] texts = new String[1];
//            texts[0] = "前进吧，多啦A梦！";
//            inputTextParam.setTexts(texts);
//
//            Bitmap tarBitmap = TextUtil.generateMeaningfulPicture(outputPictureParam, inputPictureParam, inputTextParam);
//
////            int ret = mWallManager.setBitmap(tarBitmap, null, true, WallpaperManager.FLAG_SYSTEM);
////            int ret = mWallManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
//            int ret = mWallManager.setBitmap(tarBitmap, null, true, WallpaperManager.FLAG_SYSTEM);
//            Log.e("setLockWallPaper","setBitmap = " + ret);
        } catch (Exception e) {
            Log.e("ScreenStatusReceiver", "ScreenStatusReceiver receive fail " + e.getMessage() + ", and name = " + e.getClass().getName());
        }
    }

    private Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = mContext.getResources().getAssets();
        try
        {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (IOException e)
        {
            Log.e("setLockWallPaper","getImageFromAssetsFile fail = " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("setLockWallPaper","getImageFromAssetsFile fail = " + e.getMessage());
            e.printStackTrace();
        }

        return image;

    }
}
