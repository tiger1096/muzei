package com.alexqzhang.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.alexqzhang.base.config.PictureConfig;
import com.alexqzhang.base.config.TextConfig;
import com.alexqzhang.base.data.ZFont;
import com.alexqzhang.print.utils.BitmapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestPrint {
    public static List<List<String>> poems;

    static {
        init();
    }

    public static void init() {
        poems = new ArrayList<>();
        List<String> poem1 = new ArrayList<>();
        poem1.add("但使龙城飞将在，");
        poem1.add("不教胡马渡阴山。");
        poems.add(poem1);
        List<String> poem2 = new ArrayList<>();
        poem2.add("钟山风雨起苍黄，");
        poem2.add("百万雄师过大江。");
        poems.add(poem2);
        List<String> poem3 = new ArrayList<>();
        poem3.add("洛阳亲友如相问，");
        poem3.add("一片冰心在玉壶。");
        poems.add(poem3);
        List<String> poem4 = new ArrayList<>();
        poem4.add("何当共剪西窗烛，");
        poem4.add("却话巴山夜雨时。");
        poems.add(poem4);
        List<String> poem5 = new ArrayList<>();
        poem5.add("无边落木萧萧下，");
        poem5.add("不尽长江滚滚来。");
        poems.add(poem5);
        List<String> poem6 = new ArrayList<>();
        poem6.add("落红不是无情物，");
        poem6.add("化作春泥更护花。");
        poems.add(poem6);
    }

    public static Bitmap genBitmap(Context context, int screenWidth, int screenHeight) {
        Log.e("alex", "WallpaperService onStartCommand");

        PictureConfig pictureConfig = new PictureConfig();
        pictureConfig.width = 1440;
        pictureConfig.height = 2560;
        pictureConfig.style = 0;
        pictureConfig.blankX = 120;
        pictureConfig.blankY = 240;
        pictureConfig.blankWidth = 1200;
        pictureConfig.blankHeight = 560;
        pictureConfig.screenWidth = screenWidth;
        pictureConfig.screenHeight = screenHeight;
        pictureConfig.isVerticle = false;

        TextConfig textConfig = new TextConfig();
        textConfig.style = 0;
        textConfig.textNum = 2;
        textConfig.texts = new ArrayList<>();
        int index = Math.abs((new Random()).nextInt()) % (poems.size());
        textConfig.texts = poems.get(index);
        textConfig.fonts = new ArrayList<>();
        textConfig.fonts.add(new ZFont(16, 0));
        textConfig.fonts.add(new ZFont(16, 0));

        long startTime = System.currentTimeMillis();
        Bitmap inputBitmap = BitmapUtil.getBitmapFromAssets(context, "river.jpg");
        Log.e("Performance", "getBitmapFromAssets cost : " + (System.currentTimeMillis() - startTime));

        return PrintService.print(inputBitmap, pictureConfig, textConfig);
    }
}
