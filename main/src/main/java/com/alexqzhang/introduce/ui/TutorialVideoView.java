package com.alexqzhang.introduce.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class TutorialVideoView extends VideoView {

    public TutorialVideoView(Context context) {
        super(context);
    }

    public TutorialVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TutorialVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
