package com.ippie52.centurionphototimer.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class FragmentFrame extends View {

    public FragmentFrame(Context context) {
        super(context);
        initialise();
    }

    public FragmentFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    public FragmentFrame(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialise();
    }

    public void setColour(final int aColour) {
        mPaint.setColor(aColour);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas aCanvas) {
        super.onDraw(aCanvas);
        mRect.set(0, 0, getWidth(), getHeight());
        aCanvas.drawRoundRect(mRect, X_RADIUS, Y_RADIUS, mPaint);
    }

    private void initialise() {
        mPaint.setColor(DEFAULT_COLOUR);
    }

    private static final float X_RADIUS = 12f;
    private static final float Y_RADIUS = 12f;
    private static final int DEFAULT_COLOUR = 0x22402070;
    private final RectF mRect = new RectF();
    private final Paint mPaint = new Paint();

}
