package com.tencent.filter;

import android.graphics.Bitmap;

public class QImage {
    private int width;
    private int height;

    /**
     * #define RGB 0x01020300 #define RGBA 0x01020304 #define ARGB 0x02030401
     * #define BGR 0x03020100 #define BGRA 0x03020104
     */

    private int pixelFormat;

    private long nativeImage;// filter 算法库内部使用的Image对象的内存地址
    private int pixelBytes;// 每个像素占用的字节数

    public QImage() {
        width = 0;
        height = 0;
        nativeImage = 0;
        pixelBytes = 0;
    }


    public QImage(int w, int h) {
        this.CreateImage(w, h, 4);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPixelFormat() {
        return pixelFormat;
    }

    public int getPixelBytes() {
        return pixelBytes;
    }


    /**
     * 基于当前图片创建一张子图
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    
    public native QImage createSubImage(int x, int y, int width, int height);


    //加水印，和原图ALpha混合,bitmap是RGBA_8888格式
    
    public native boolean alphaMix(Bitmap bitmap, int startX, int startY);


    public native void CopyExif(QImage srcImage);

    // 将srcImage的附加信息转移到 image， srcImage的附加信息将为设为空， 不存在内存拷贝
    
    public native void WrapExif(QImage srcImage);

    /***
     * 解码JUV420格式
     *
     * @param yuv
     * @param width
     * @param height
     */
    
    public native void YUV420sp2RGB(byte[] yuv, int width, int height);

    
    public native void YUV420sp2YUV(byte[] yuv, int width, int height);

    
    public native void YUV422toRGB(byte[] yuv, int width, int height);

    
    public native void YUV420sp2YUV2(byte[] yuv, int width, int height, int scale);

    
    public native boolean FromBitmap(Bitmap bitmap);

    
    public native static QImage Bitmap2QImage(Bitmap bitmap);

    
    public native static QImage BindBitmap(Bitmap bitmap);

    
    public native boolean UnBindBitmap(Bitmap bitmap);//need use follow with BindBitmap

    
    public native boolean ToBitmap(Bitmap bitmap);

    
    public native int[] nativeGetArrayHistogram();

    
    public native int[] nativeGetArrayHistogramChannels();

    
    public native void nativeUpdateROI();//更新底层参数

    public native void RGB565toRGB(byte[] rgb656, int width, int height);

    /***
     * 释放申请的内存
     */
    
    public native void Dispose();

    /**
     * Image的pixelBytes必须等于4
     *
     * @param rgb 返回的像素格式 与android定义的一致 ： ARGB(字节序 BGRA)
     */
    
    public native void CopyPixels(int[] rgb, int flipX);

    /**
     * 上下左右翻转，不改变原图大小
     *
     * @param flipx=1 左右翻转
     * @param flipy=1 上下翻转
     * @return
     */
    
    public native boolean nativeFlip(int flipx, int flipy);

    /**
     * 逆时针旋转 0,90,180,270 旋转90和270会宽度和高度会交换
     *
     * @param degree 旋转角度
     * @return
     */
    
    public native boolean nativeRotate(int degree);

    /**
     * Image的pixelBytes必须等于4
     *
     * @param rgb 返回的像素格式 : ABGR(字节序 RGBA)
     */
    
    public native void CopyPixelsRGB(int[] rgb, int flipX);

    // 计算图像每个通道的均值，返回格式：ARGB(字节序：BGRA)
    
    public native int AverageChannels();

    // 内存不足会抛出RuntimeException异常
    
    public native void CreateImage(int width, int height, int pixelBytes);

    
    public native QImage CreateImageFromQImage();

    
    static public native byte[] CompressJPEGFromRGBA(byte[] data, int width, int height, int ratio);

    
    public native QImage InplaceBlur8bitQImage(int ratio, int blur);

    
    public native long getByteSize();

    
    public native void toGPUTexture(int texture);
}
