package com.ippie52.centurionphototimer.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.ippie52.centurionphototimer.R;

public class ShotGlassView extends View {

    /**
     * ShotGlassView - Constructor
     * **/
    public ShotGlassView(Context context) {
        super(context);
        initialise();
    }

    /**
     * ShotGlassView - Constructor
     * **/
    public ShotGlassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    /**
     * ShotGlassView - Constructor
     * **/
    public ShotGlassView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialise();
    }

    /**
     * getMaxPixels - Method used to obtain the maximum number of pixels the
     * fill image can fill with
     * 
     * @return The maximum number of pixels the fill image takes
     * **/
    public int getMaxPixels() {
        return mFillImage.getHeight();
    }

    /**
     * onDraw - Overridden function called when the view is drawn
     * 
     * @param aCanvas
     *            The canvas onto which the view is drawn
     * **/
    @Override
    public void onDraw(Canvas aCanvas) {
        super.onDraw(aCanvas);
        mGlassOrigin.x = (aCanvas.getWidth() - mFrontMask.getWidth()) / 2;
        mGlassOrigin.y = (aCanvas.getHeight() - mFrontMask.getHeight()) / 2;

        mYOffset = mFillImage.getHeight() - mCurrentProgressPixels;
        mSource.set(0, mYOffset, mFillImage.getWidth(), mFillImage.getHeight());
        mDest.set(mGlassOrigin.x, mGlassOrigin.y + mYOffset,
                mFillImage.getWidth() + mGlassOrigin.x, mFillImage.getHeight()
                        + mGlassOrigin.y);
        aCanvas.drawBitmap(mFillImage, mSource, mDest, null);
        aCanvas.drawBitmap(mFrontMask, mGlassOrigin.x, mGlassOrigin.y, null);
    }

    /**
     * setProgressPixels - Method used to set the desired progress value for the
     * fill
     * 
     * @param aProgPix
     *            The progress of the fill in pixels
     * **/
    public void setProgressPixels(final int aProgPix) {
        synchronized (this) {
            mTotalProgressPixels = aProgPix;
            beginProcessing();
        }
    }

    /**
     * beginProcessing - Method used to kick start the timer to "animate" the
     * filling of the glass
     * **/
    private void beginProcessing() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mCurrentTickInc = mTotalProgressPixels - mCurrentProgressPixels;
        if (mCurrentTickInc != 0) {
            int interval = DEFAULT_PROGRESS_INCREMENT_MS
                    / Math.abs(mCurrentTickInc);
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    timerTaskEventAction();
                }
            }, DEFAULT_PROGRESS_DELAY_MS, interval);
        }
    }

    /**
     * initialise - Method used to initialise the class
     * **/
    private void initialise() {
        final Resources res = getResources();
        mFrontMask = BitmapFactory.decodeResource(res, R.drawable.glass_front);
        mFillImage = BitmapFactory.decodeResource(res, R.drawable.glass_filled);
    }

    /**
     * timerTaskEventAction - Method used to update the fill amount of the glass
     * **/
    private void timerTaskEventAction() {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                synchronized (this) {
                    if (mCurrentTickInc > 0) {
                        ++mCurrentProgressPixels;

                    } else {
                        --mCurrentProgressPixels;
                    }

                    if ((mCurrentProgressPixels == mTotalProgressPixels)
                            && (mTimer != null)) {
                        mTimer.cancel();
                        mTimer = null;
                    }

                    invalidate();
                }
            }
        });
    }

    /**
     * Class Constants
     * **/
    private final static int DEFAULT_PROGRESS_INCREMENT_MS = 500;
    private final static int DEFAULT_PROGRESS_DELAY_MS = 10;

    /**
     * Member Variables
     * **/
    private Bitmap mFrontMask;
    private Bitmap mFillImage;
    private final Handler mHandler = new Handler();
    private Timer mTimer;
    private int mYOffset;
    private int mCurrentProgressPixels;
    private int mTotalProgressPixels;
    private int mCurrentTickInc;
    private final Point mGlassOrigin = new Point(0, 0);
    private final Rect mSource = new Rect();
    private final Rect mDest = new Rect();
}
