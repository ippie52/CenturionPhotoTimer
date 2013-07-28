package com.ippie52.centurionphototimer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class ShotGlassView extends View {

    public ShotGlassView(Context context) {
        super(context);
        initialise();
    }

    public ShotGlassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    public ShotGlassView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialise();
    }

    private void initialise() {
        // mFrontMask = BitmapFactory.decodeResource(R.drawable., id, opts)
    }

    private Bitmap mFrontMask;
    private Bitmap mFillImage;
    private final Rect mSource = new Rect();
    private final Rect mDest = new Rect();
}
