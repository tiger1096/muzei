package com.nice.render;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.alexqzhang.base.config.PictureConfig;
import com.alexqzhang.base.config.TextConfig;
import com.alexqzhang.base.data.ZFont;
import com.alexqzhang.print.PrintService;
import com.alexqzhang.print.utils.BitmapUtil;
import com.nice.data.DataCenter;
import com.nice.entity.Knowledge;
import com.nice.storage.DataBuffer;
import com.nice.storage.SQLiteStorageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;


public class AlexWallpaperService extends WallpaperService {

    private final Handler handler = new Handler();
    private Bitmap bitmap;

    private DataBuffer dataBuffer = new DataBuffer();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Engine onCreateEngine() {
        return new AlexEngine();
    }

    public class AlexEngine extends Engine {
        private boolean visible = true;
        private int width = 720;
        private int height = 1280;
        private Paint paint;

        // 测试逻辑
        private List<List<String>> poems;
        private int seed = 0;

        private final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                drawFrame();
            }
        };

        private void drawFrame() {
            final SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            canvas = holder.lockCanvas();
            canvas.save();

            PictureConfig pictureConfig = new PictureConfig();
            pictureConfig.width = 1440;
            pictureConfig.height = 2560;
            pictureConfig.style = 0;
            pictureConfig.blankX = 120;
            pictureConfig.blankY = 240;
            pictureConfig.blankWidth = 1200;
            pictureConfig.blankHeight = 560;
            pictureConfig.screenWidth = this.width;
            pictureConfig.screenHeight = this.height;
            pictureConfig.isVerticle = false;

            TextConfig textConfig = new TextConfig();
            textConfig.style = 0;
            textConfig.textNum = 2;
//            textConfig.texts = new ArrayList<>();
            textConfig.texts = poems.get(seed);
            Knowledge knowledge = (Knowledge) dataBuffer.pop();
            if (knowledge != null && knowledge.text != null) {
                textConfig.texts = Arrays.asList(knowledge.text.split("。"));
            }
            textConfig.fonts = new ArrayList<>();
            textConfig.fonts.add(new ZFont(16, 0));
            textConfig.fonts.add(new ZFont(16, 0));

            Bitmap outputBitmap = PrintService.print(bitmap, pictureConfig, textConfig);
            canvas.drawBitmap(outputBitmap, 0, 0, paint);
            canvas.restore();
            holder.unlockCanvasAndPost(canvas);

            try {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                int ret = wallpaperManager.setBitmap(outputBitmap, null, true, WallpaperManager.FLAG_LOCK);
            } catch (Exception e) {

            }

            handler.removeCallbacks(runnable);
            if (visible) {
                handler.postDelayed(runnable, 10);
            }
        }

        public AlexEngine() {
            paint = new Paint();
            bitmap = BitmapUtil.getBitmapFromAssets(getApplicationContext(), "river.jpg");

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

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            setTouchEventsEnabled(true);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(runnable);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            this.visible = visible;
            if (visible) {
                drawFrame();
            } else {
                handler.removeCallbacks(runnable);
            }
        }

        @Override
        public Bundle onCommand(String action, int x, int y, int z, Bundle extras,
                                boolean resultRequested) {
            if (action.equals(WallpaperManager.COMMAND_TAP)) {
                seed = Math.abs((new Random()).nextInt()) % (poems.size());
            }

            DataCenter.getInstance().fetchKnowledgeFromBackend();
            DataCenter.getInstance().provideKnowledge(dataBuffer);

//            SQLiteStorageUtils.dropTable(Knowledge.class);

//            Knowledge knowledge = new Knowledge();
//            SQLiteStorageUtils.insert(knowledge);
//
//            List<Knowledge> knowledges = SQLiteStorageUtils.getQueryAll(Knowledge.class);
//            if (knowledges != null) {
//                for (Knowledge k : knowledges)
//                Log.e("alex", k.id + " = [" + k.knowledge_id + ", " + k.text + "]");
//            }

            return super.onCommand(action, x, y, z, extras, resultRequested);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    seed = Math.abs((new Random()).nextInt()) % (poems.size());
//                    break;
//            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            this.width = width;
            this.height = height;
            drawFrame();
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
            handler.removeCallbacks(runnable);
        }
    }

}
