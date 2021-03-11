package com.tencent.filter;

import android.graphics.Bitmap;


public class GLSLRender {
    public static native void nativePreviewData(byte[] data, int texture,
                                                int width, int height);

    public static native void nativePreviewYuvData(byte[] data, int texture,
                                                   int width, int height);

    public static native void nativeToRGBData(byte[] data, int width, int height);


    // 拷贝texture到bitmap
    public static native int nativeRenderPixelToBitmap(Bitmap bitmap, int startx, int starty);

    public static native int nativeCopyPixelToBitmap(Bitmap bitmap);

    // 设置屏幕宽高比,直接裁剪到合适的框高比
    // public static void nativeScreenAspectRatio(double aspectRatio){}
    // 获取美脸人工定位扩大区域图片
    public static native void nativeUpdateScaleBitmap(Bitmap sourceBitmap,
                                                      Bitmap maskBitmap, Bitmap resultBitmap, int top, int left);


    // large jpeg picture
    public static native void nativePreprocessJepg(QImage qImage, int[] xyCount);

    public static native void nativePickJepgToTexture(QImage qImage, int x,
                                                      int y, int xCount, int yCount, int texture, int[] textureSize);

    public static native void nativePushJepgFromTexture(QImage qImage, int x,
                                                        int y, int xCount, int yCount, int witch);

    public static native void nativePushDataFromTexture(byte[] data, int width, int height, int witch);

    public static native void nativePushBitmapFromTexture(Bitmap bitmap, int witch);

    /**
     * 从native层alloc size大小的内存，并返回到java层
     */
    public static native long nativeAllocBuffer(int size);

    /**
     * 释放从native层alloc的内存
     */
    public static native void nativeFreeBuffer(long handle);

    /**
     * 从GPU中取一帧数据计算直方图曲线 flag==-2 最初初始化,不会改变曲线 flag==-1根据直方图，计算调整曲线
     * flag==0重新计算直方图 flag==分块统计直方图
     */
    public static native void nativeCalHistogramFromGPU(long pixBuffer, long hisbuffer,
                                                        int x, int y, int width, int height,
                                                        float lowPercent, float highPercent, int count, int index, float ratio, float threshold);

	/**
	 * 将GPU渲染的结果，保存到bitmap， bitmap需要预先分配好内存. 此方法需要在GPU的render线程内调用
	 * libimage_filter_common.so
	 * @param bmp
	 * @return
	 */
	public static native int nativeSnap(Bitmap bmp);

    public static native void nativeTextCure(int[] value, int texture);

    public static native void nativeTextImage(QImage qImage, int texture);// push
    // qimage
    // to
    // texture

    public static native QImage nativeCopyTexture(int width, int height);// snap

    // frame
    // buffer
    // to
    // QImage
    public static native void nativeCopyTexture2(QImage qImage);


    public static native int nativeInitMagicEngine(int bufCount, int witch);

    public static native int nativeCheckMagicEngine(int bufCount, int witch);

    public static native void nativeBeginUseEglImage(int width, int height, int witch);

    public static native void nativeEndUseEglImage(int width, int height, int witch);

    public static native int nativeDeinitMagicEngine(int witch);

    public static native void nativeCopyTexturToDataWithShare(int texture, byte[] data, int width, int height, int witch);

    public static native int nativeCopyPixelToBitmapWithShare(Bitmap bitmap, int texture, int witch);

    public static native QImage nativeCopyTextureWithShare(int width, int height, int texture, int witch);


    public static final int FILTER_SHADER_NONE = 0;


    public static final int FILTER_FACE_RGB2YCBCR_SHADER = 1;
    public static final int FILTER_FACE_YCBCR2RGB_SHADER = 2;
    public static final int FILTER_ALPHA_ADJUST = 3;
    public static final int FILTER_ALPHA_ADJUST_REAL = 4;

    public static final int SHARE_SHADER_FILM_1 = FILTER_ALPHA_ADJUST_REAL + 1;
    public static final int SHARE_SHADER_FILM_2 = SHARE_SHADER_FILM_1 + 1;
    public static final int FILTER_2D_CURVE = SHARE_SHADER_FILM_2 + 1;
    public static final int FILTER_DARKCORNER_CURVE = FILTER_2D_CURVE + 1;
    public static final int FILTER_EGL_IMAGE = FILTER_DARKCORNER_CURVE + 1;
    public static final int FILTER_COLORPENCIL = FILTER_EGL_IMAGE + 1;
    public static final int FILTER_SHADER_YUV = FILTER_COLORPENCIL + 1;
    public static final int FILTER_SHADER_BEAUTY = FILTER_SHADER_YUV + 1;
    public static final int FILTER_VIBRANCE = FILTER_SHADER_BEAUTY + 1;

    public static final int FILTER_FLARES = FILTER_VIBRANCE + 1;
    public static final int FILTER_HORIZONTAL_BEAUTY = FILTER_FLARES + 1;
    public static final int FILTER_VERTICAL_BEAUTY = FILTER_HORIZONTAL_BEAUTY + 1;
    public static final int FILTER_HIPASS_BEAUTY = FILTER_VERTICAL_BEAUTY + 1;
    public static final int FILTER_OVERLAP_BEAUTY = FILTER_HIPASS_BEAUTY + 1;
    public static final int FILTER_GLOWCENTER_BEAUTY = FILTER_OVERLAP_BEAUTY + 1;

    public static final int FILTER_QINXINMEIBAI_BEAUTY = FILTER_GLOWCENTER_BEAUTY + 1;
    public static final int FILTER_TIANMEIKEREN_BEAUTY = FILTER_QINXINMEIBAI_BEAUTY + 1;
    public static final int FILTER_SHENDUMEIBAI_BEAUTY = FILTER_TIANMEIKEREN_BEAUTY + 1;
    public static final int FILTER_LIEYANHONGCHUN_BEAUTY = FILTER_SHENDUMEIBAI_BEAUTY + 1;
    public static final int FILTER_TIANSHENLIZHI_BEAUTY = FILTER_LIEYANHONGCHUN_BEAUTY + 1;
    public static final int FILTER_MEIFU_BEAUTY = FILTER_TIANSHENLIZHI_BEAUTY + 1;
    public static final int FILTER_FENNEN_BEAUTY = FILTER_MEIFU_BEAUTY + 1;

    public static final int FILTER_NIGHT_BOKEH = FILTER_FENNEN_BEAUTY + 1;

    public static final int FILTER_RGBTOHSV = FILTER_NIGHT_BOKEH + 1;
    public static final int FILTER_HSVTORGB = FILTER_RGBTOHSV + 1;
    public static final int FILTER_CHANNEL_STRECH = FILTER_HSVTORGB + 1;

    public static final int FILTER_CHANNEL_SATURATION = FILTER_CHANNEL_STRECH + 1;
    public static final int FILTER_HISTOGRAMS_STRCTCH = FILTER_CHANNEL_SATURATION + 1;
    public static final int FILTER_CHANNEL_SHARPEN_FR = FILTER_HISTOGRAMS_STRCTCH + 1;
    public static final int FILTER_TILT_TOUCH_CIRCLE_NEW = FILTER_CHANNEL_SHARPEN_FR + 1;
    public static final int FILTER_TILT_TOUCH_ECLIPSE_NEW = FILTER_TILT_TOUCH_CIRCLE_NEW + 1;
    public static final int FILTER_TILT_TOUCH_LINE_NEW = FILTER_TILT_TOUCH_ECLIPSE_NEW + 1;
    public static final int FILTER_TILT_TOUCH_GAUSS = FILTER_TILT_TOUCH_LINE_NEW + 1;
    public static final int FILTER_TILT_TOUCH_MASK = FILTER_TILT_TOUCH_GAUSS + 1;
    public static final int FILTER_NIGHT_BOKEH_EX = FILTER_TILT_TOUCH_MASK + 1;
    public static final int FILTER_TIANMEIKEREN_BEAUTYP2 = FILTER_NIGHT_BOKEH_EX + 1;
    public static final int FILTER_YANGGUANG_BEAUTYP2 = FILTER_TIANMEIKEREN_BEAUTYP2 + 1;
    public static final int FILTER_HONGRUN_BEAUTYP2 = FILTER_YANGGUANG_BEAUTYP2 + 1;

    public static final int FILTER_SHENDUMEIBAI_BEAUTY_SHUIYIN = FILTER_HONGRUN_BEAUTYP2 + 1;
    //
    public static final int FILTER_RGB2YUV_SHADER = FILTER_SHENDUMEIBAI_BEAUTY_SHUIYIN + 1;
    public static final int FILTER_YUV2RGB_SHADER = FILTER_RGB2YUV_SHADER + 1;
    public static final int FILTER_GAUSS_INNER_H_FRAG = FILTER_YUV2RGB_SHADER + 1;
    public static final int FILTER_GAUSS_INNER_V_FRAG = FILTER_GAUSS_INNER_H_FRAG + 1;
    public static final int FILTER_DAMPING_GAUSS_H_FRAG = FILTER_GAUSS_INNER_V_FRAG + 1;
    public static final int FILTER_ALPHAREVERT = FILTER_DAMPING_GAUSS_H_FRAG + 1;
    public static final int FILTER_FRAME_SHADER = FILTER_ALPHAREVERT + 1;

    public static final int FILTER_TEXTREPET_SHADER = FILTER_FRAME_SHADER + 1;

    public static final int FILTER_DOF_SHADER = FILTER_TEXTREPET_SHADER + 1;
    public static final int FILTER_TILT_TOUCH_MASK2 = FILTER_DOF_SHADER + 1;

    public static final int FILTER_MIC_CURE_NEW_SHADER = FILTER_TILT_TOUCH_MASK2 + 1;

    public static final int FILTER_SHADER_COLOR_TEMP = FILTER_MIC_CURE_NEW_SHADER + 1;

    public static final int FILTER_SHADER_SCREEN_BLEND = FILTER_SHADER_COLOR_TEMP + 1;
    public static final int FILTER_SHADER_COLOR_BALANCE = FILTER_SHADER_SCREEN_BLEND + 1;
    public static final int FILTER_SHADER_CHANNEL_MIXER = FILTER_SHADER_COLOR_BALANCE + 1;
    public static final int FILTER_SHADER_MULTIPLY_BLEND = FILTER_SHADER_CHANNEL_MIXER + 1;
    public static final int FILTER_HSVADJUSTER_SHADER = FILTER_SHADER_MULTIPLY_BLEND + 1;
    public static final int FILTER_SOFTLIGHTBLEND_SHADER = FILTER_HSVADJUSTER_SHADER + 1;
    public static final int FILTER_HARDLIGHTBLEND_SHADER = FILTER_SOFTLIGHTBLEND_SHADER + 1;
    public static final int FILTER_SHADER_DARKEN_BLEND = FILTER_HARDLIGHTBLEND_SHADER + 1;
    public static final int FILTER_SHADER_EXPOSURE = FILTER_SHADER_DARKEN_BLEND + 1;
    public static final int FILTER_SHADER_AUTOLEVEL_HIS = FILTER_SHADER_EXPOSURE + 1;
    // add new shader here
    public static final int FILTER_MARK = FILTER_SHADER_AUTOLEVEL_HIS + 1;
    public static final int FILTER_NEW_SKETCH = FILTER_MARK + 1;

    public static final int FILTER_GAUSSBLUR_V = FILTER_NEW_SKETCH + 1;
    public static final int FILTER_GAUSSBLUR_H = FILTER_GAUSSBLUR_V + 1;

    public static final int FILTER_BILATERALFILTER2_NEW = FILTER_GAUSSBLUR_H + 1;
    public static final int FILTER_BILATERALFILTER_NEW = FILTER_BILATERALFILTER2_NEW + 1;
    public static final int FILTER_DIRECTIONFILTERP = FILTER_BILATERALFILTER_NEW + 1;
    public static final int FILTER_FLOWBASEDBL = FILTER_DIRECTIONFILTERP + 1;
    public static final int FILTER_FLOWBASEDBLUR = FILTER_FLOWBASEDBL + 1;
    public static final int FILTER_FLOWBASEDDOG = FILTER_FLOWBASEDBLUR + 1;
    public static final int FILTER_FLOWBASEDDOGFOREDGE = FILTER_FLOWBASEDDOG + 1;
    public static final int FILTER_GAUSSBLURH = FILTER_FLOWBASEDDOGFOREDGE + 1;
    public static final int FILTER_GAUSSBLURV = FILTER_GAUSSBLURH + 1;
    public static final int FILTER_GRAYFILTERP = FILTER_GAUSSBLURV + 1;
    public static final int FILTER_MIXEDGEFILTEREDIT = FILTER_GRAYFILTERP + 1;
    public static final int FILTER_MIXEDGENOSTAGE = FILTER_MIXEDGEFILTEREDIT + 1;
    public static final int FILTER_STRUCTTENSORFILTER = FILTER_MIXEDGENOSTAGE + 1;

    public static final int FILTER_STRUCTTENSORFORPOSTER = FILTER_STRUCTTENSORFILTER + 1;
    public static final int FILTER_FLOWBASEDBLFORPOSTER = FILTER_STRUCTTENSORFORPOSTER + 1;
    public static final int FILTER_FLOWBASEDDOGFORPOSTER = FILTER_FLOWBASEDBLFORPOSTER + 1;
    public static final int FILTER_FLOWBASEDBLURFORPOSTER = FILTER_FLOWBASEDDOGFORPOSTER + 1;
    public static final int FILTER_FLOWBASEDBLURFORPOSTER_Tegra = FILTER_FLOWBASEDBLURFORPOSTER + 1;
    //
    public static final int FILTER_NIGHT_RGBSTRETCH = FILTER_FLOWBASEDBLURFORPOSTER_Tegra + 1;
    public static final int FILTER_GRAYFORMANGA_SHADER = FILTER_NIGHT_RGBSTRETCH + 1;
    public static final int FILTER_NONMAXSUPRESS_SHADER = FILTER_GRAYFORMANGA_SHADER + 1;
    public static final int FILTER_SOBELBLUR_SHADER = FILTER_NONMAXSUPRESS_SHADER + 1;
    public static final int FILTER_MANGA_SHADER = FILTER_SOBELBLUR_SHADER + 1;
    public static final int FILTER_BACKGROUNDTEXUL_SHADER = FILTER_MANGA_SHADER + 1;
    public static final int FILTER_SOBELFORMANGA_SHADER = FILTER_BACKGROUNDTEXUL_SHADER + 1;
    public static final int FILTER_MANGAFORSAVE_SHADER = FILTER_SOBELFORMANGA_SHADER + 1;
    public static final int FILTER_NONMAXSUPRESS_SHADER_LOWDEVICE = FILTER_MANGAFORSAVE_SHADER + 1;


    //  for ttpic shader

    public static final int FILTER_MIC_CURE_SHADER = FILTER_NONMAXSUPRESS_SHADER_LOWDEVICE + 1;
    public static final int FILTER_SELECTIVECOLOR_SHADER = FILTER_MIC_CURE_SHADER + 1;
    public static final int FILTER_TONEADJUST_SHADER = FILTER_SELECTIVECOLOR_SHADER + 1;
    public static final int FILTER_FACE_LENSFLARE_SHADER = FILTER_TONEADJUST_SHADER + 1;
    public static final int FILTER_FACE_ALPHABLEND_SHADER = FILTER_FACE_LENSFLARE_SHADER + 1;
    public static final int FILTER_FACE_ALPHABLENDCROSS_SHADER = FILTER_FACE_ALPHABLEND_SHADER + 1;
    public static final int FILTER_FACE_ATTACHCROSS_EX_SHADER = FILTER_FACE_ALPHABLENDCROSS_SHADER + 1;


    public static final int FILTER_SPRING_MORPH_SHADER = FILTER_FACE_ATTACHCROSS_EX_SHADER + 1;

    public static final int FILTER_SHADER_YUV420P = FILTER_SPRING_MORPH_SHADER + 1;

    public static final int FILTER_SHADER_SELECTIVECOLOR = FILTER_SHADER_YUV420P + 1;
    public static final int FILTER_SHADER_MIC_CURE_MASK = FILTER_SHADER_SELECTIVECOLOR + 1;
    public static final int FILTER_HSVADJUSTER2_SHADER = FILTER_SHADER_MIC_CURE_MASK + 1;
    public static final int FILTER_GPULEVELS_SHADER = FILTER_HSVADJUSTER2_SHADER + 1;

    public static final int FILTER_LUMINOSITY_BLEND_SHADER = FILTER_GPULEVELS_SHADER + 1;
    public static final int FILTER_HIGHTLIGHT_SHADOW_SHADER = FILTER_LUMINOSITY_BLEND_SHADER + 1;
    public static final int FILTER_HSVNCHANNEL_SHARPEN_SHADER = FILTER_HIGHTLIGHT_SHADOW_SHADER + 1;
    public static final int FILTER_CHANNELSTRECH2_SHADER = FILTER_HSVNCHANNEL_SHARPEN_SHADER + 1;
    public static final int FILTER_CHANNELSTRECH3_SHADER = FILTER_CHANNELSTRECH2_SHADER + 1;
    public static final int FILTER_OVERLAY_BLEND2_SHADER = FILTER_CHANNELSTRECH3_SHADER + 1;
    public static final int FILTER_SOFTLIGHTBLEND2_SHADER = FILTER_OVERLAY_BLEND2_SHADER + 1;
    public static final int FILTER_SHADER_MULTIPLY_BLEND_RSS = FILTER_SOFTLIGHTBLEND2_SHADER + 1;
    public static final int FILTER_SHADER_SCREEN_BLEND_OLD = FILTER_SHADER_MULTIPLY_BLEND_RSS + 1;
    public static final int FILTER_SHADER_BEAUTY_HORIZONTAL_REAL = FILTER_SHADER_SCREEN_BLEND_OLD + 1;
    public static final int FILTER_SHADER_BEAUTY_VERTICAL_REAL = FILTER_SHADER_BEAUTY_HORIZONTAL_REAL + 1;
    public static final int FILTER_SHADER_BEAUTY_HIPASS_REAL = FILTER_SHADER_BEAUTY_VERTICAL_REAL + 1;
    public static final int FILTER_GPULEVELSEX_SHADER = FILTER_SHADER_BEAUTY_HIPASS_REAL + 1;
    public static final int FILTER_RGB2LAB_SHADER = FILTER_GPULEVELSEX_SHADER + 1;
    public static final int FILTER_LAB2RGB_SHADER = FILTER_RGB2LAB_SHADER + 1;
    public static final int FILTER_GPULEVELS1_SHADER = FILTER_LAB2RGB_SHADER + 1;
    public static final int FILTER_INPUT2_SHADER = FILTER_GPULEVELS1_SHADER + 1;
    public static final int FILTER_SHADER_MULTIPLY_BLEND_3 = FILTER_INPUT2_SHADER + 1;
    public static final int FILTER_SHADER_COLORBURN_BLEND = FILTER_SHADER_MULTIPLY_BLEND_3 + 1;
    public static final int FILTER_SHADER_HSVCOLOR_0 = FILTER_SHADER_COLORBURN_BLEND + 1;
    public static final int FILTER_SHADER_HSVCOLOR_1 = FILTER_SHADER_HSVCOLOR_0 + 1;
    public static final int FILTER_SHADER_HSVCOLOR_2 = FILTER_SHADER_HSVCOLOR_1 + 1;
    public static final int FILTER_SHADER_HSVCOLOR_3 = FILTER_SHADER_HSVCOLOR_2 + 1;
    public static final int FILTER_SHADER_HSVCOLOR_ALL = FILTER_SHADER_HSVCOLOR_3 + 1;
    public static final int FILTER_HARDLIGHTBLEND2_SHADER = FILTER_SHADER_HSVCOLOR_ALL + 1;
    public static final int FILTER_SHADER_MULTIPLY_BLEND_4 = FILTER_HARDLIGHTBLEND2_SHADER + 1;
    public static final int FILTER_SHADER_VIVID_LIGHT_BLEND = FILTER_SHADER_MULTIPLY_BLEND_4 + 1;
    public static final int FILTER_BEAUTY_SMOOTH_NEW = FILTER_SHADER_VIVID_LIGHT_BLEND + 1;
    public static final int FILTER_BEAUTY_WHITEN_NEW = FILTER_BEAUTY_SMOOTH_NEW + 1;


    public static final int GL_TEXTURE_EXTERNAL_OES_ENUM = 0x8D65;
    public static final int GL_TEXTURE_2D = 0x0DE1;

    //////////////////////// VERTEXT ////////////////////////
    public static final int VERTEXT_SHADER_JNI = 0;
    public static final int VERTEXT_SHADER_DEFAULT = 1;
    public static final int VERTEXT_SHADER_SHARPEN = 2;

    public static final int VERTEXT_GAUSS_INNER_H = 3;
    public static final int VERTEXT_GAUSS_INNER_V = 4;
    public static final int VERTEXT_DAMPING_GAUSS_H = 5;
    public static final int VERTEXT_FRAME_SHADER = 6;
    public static final int VERTEXT_FRAMEORIGIN_SHADER = 7;
    public static final int VERTEXT_FRAMESTRETCH_SHADER = 8;
    public static final int VERTEXT_GRAYFORMANGA_SHADER = 9;
    public static final int VERTEXT_SOBELFORMANGA_SHADER = 10;
    public static final int VERTEXT_SOBELBLUR_SHADER = 11;


    public static final int VERTEXT_LENSFLARE_SHADER = VERTEXT_SOBELBLUR_SHADER + 1;
    public static final int VERTEXT_HSVCHANNELSHARPEN_SHADER = VERTEXT_LENSFLARE_SHADER + 1;
}
