package com.alexqzhang.print.utils;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.util.Log;

import com.tencent.filter.GLSLRender;

public class RenderUtil {
    static {
        System.loadLibrary("image_filter_common");
        System.loadLibrary("image_filter_gpu");
    }
    public static void saveTextureToBitmap(int texture, int width, int height, Bitmap bitmap) {
        saveTextureToBitmapWithShare(texture, width, height, bitmap, -1);
    }

    public static void saveTextureToBitmapWithShare(int texture, int width, int height, Bitmap bitmap, int witch) {
        if (witch < 0) {
//            if (DeviceAttrs.getInstance().gpuWorkaroundForTU880) {
//                // Set the input texture
//                GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//                checkGlError("glActiveTexture");
//
//                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
//                checkGlError("glBindTexture");
//                GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width,
//                        height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
//                checkGlError("glTexImage2D");
//            }
            int[] frame = new int[1];
            GLES20.glGenFramebuffers(1, frame, 0);

            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frame[0]);
            checkGlError("glBindFramebuffer");
            GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER,
                    GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, texture, 0);
            checkGlError("glFramebufferTexture2D");

            checkGlError("glReadPixels");

            GLSLRender.nativeCopyPixelToBitmap(bitmap);

            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
            GLES20.glDeleteFramebuffers(1, frame, 0);
            checkGlError("glBindFramebuffer");
        } else {
            GLSLRender.nativeCopyPixelToBitmapWithShare(bitmap, texture, witch);
        }

    }
    public static Bitmap saveTexture(int texture, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        saveTextureToBitmap(texture, width, height, bitmap);

        return bitmap;
    }

    public static void checkGlError(String op) {
        int error;
        if ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            // throw new RuntimeException(op + ": glError " + error);
            Log.e("RendererUtils", op + ": glError " + error);
            java.util.Map<Thread, StackTraceElement[]> ts = Thread
                    .getAllStackTraces();
            StackTraceElement[] ste = ts.get(Thread.currentThread());
            for (StackTraceElement s : ste) {
                Log.e("SS     ", s.toString());
            }
        }
    }

}
