package com.ippie52.centurionphototimer.utils;

import android.R.integer;

/**
 * {@link ColourFader} Class
 * 
 * Class used to obtain a faded colour between a start and end colour. Provide a
 * start and end colour, and the fader will fade the red, green, blue and alpha
 * channels based on a percentage between start and end, and provide the faded
 * colour
 * 
 * TODO: Potentially add a mid-colour to transition to, along with a percentage
 * indicator
 * 
 * TODO: Potentially add an alpha fader variant
 * **/
public class ColourFader {

    /**
     * Constructor
     * **/
    public ColourFader() {
    }

    /**
     * Constructor - Implements the initialise method
     * 
     * @param aStart
     *            The initial colour to transition from
     * @param aEnd
     *            The final colour to transition to
     * **/
    public ColourFader(final int aStart, final int aEnd) {
        initialise(aStart, aEnd);
    }

    /**
     * Method to obtain a colour based on the percentage fade between the start
     * and end colours supplied on initialise()
     * 
     * @param aPercent
     *            The percentage change from start colour
     * 
     * @return The colour value encoded as an {@link integer}
     * **/
    public int getColour(final float aPercent) {
        int value = 0;
        if (aPercent < 0f) {
            value = mStart;
        } else if (aPercent >= 100f) {
            value = mEnd;
        } else {
            final int alphaInc = (int) (mAlphaInc * aPercent);
            final int redInc = (int) (mRedInc * aPercent);
            final int greenInc = (int) (mGreenInc * aPercent);
            final int blueInc = (int) (mBlueInc * aPercent);

            value += mAlphaStart + (alphaInc << ALPHA_SHIFT);
            value += mRedStart + (redInc << RED_SHIFT);
            value += mGreenStart + (greenInc << GREEN_SHIFT);
            value += mBlueStart + (blueInc << BLUE_SHIFT);
        }
        return value;
    }

    /**
     * initialise - Initialise the {@link ColourFader} class with a start and
     * end colour to fade between
     * 
     * @param aStart
     *            The initial colour to transition from
     * @param aEnd
     *            The final colour to transition to
     * **/
    public void initialise(final int aStart, final int aEnd) {
        mStart = aStart;
        mEnd = aEnd;

        mAlphaStart = mStart & ALPHA_MASK;
        mRedStart = mStart & RED_MASK;
        mGreenStart = mStart & GREEN_MASK;
        mBlueStart = mStart & BLUE_MASK;

        mAlphaEnd = mEnd & ALPHA_MASK;
        mRedEnd = mEnd & RED_MASK;
        mGreenEnd = mEnd & GREEN_MASK;
        mBlueEnd = mEnd & BLUE_MASK;

        mAlphaInc = getInc(mAlphaStart, mAlphaEnd, ALPHA_SHIFT);
        mRedInc = getInc(mRedStart, mRedEnd, RED_SHIFT);
        mGreenInc = getInc(mGreenStart, mGreenEnd, GREEN_SHIFT);
        mBlueInc = getInc(mBlueStart, mBlueEnd, BLUE_SHIFT);
    }

    /**
     * Method to obtain the increment per percentage
     * **/
    private float getInc(final int aStart, final int aEnd, final int aShift) {
        return ((aStart >> aShift) - (aEnd >> aShift)) / 100f;
    }

    /**
     * Class Constants
     * **/
    private final static int WHITE = 0xFFFFFFFF;
    private final static int BLACK = 0xFF000000;

    private final static int ALPHA_MASK = 0xFF000000;
    private final static int RED_MASK = 0x00FF0000;
    private final static int GREEN_MASK = 0x0000FF00;
    private final static int BLUE_MASK = 0x000000FF;

    private final static int ALPHA_SHIFT = 24;
    private final static int RED_SHIFT = 16;
    private final static int GREEN_SHIFT = 8;
    private final static int BLUE_SHIFT = 0;

    /**
     * Member Variables
     * **/
    private int mStart;
    private int mEnd;

    private int mAlphaStart = (BLACK | ALPHA_MASK);
    private int mRedStart = (BLACK | RED_MASK);
    private int mGreenStart = (BLACK | GREEN_MASK);
    private int mBlueStart = (BLACK | BLUE_MASK);

    private int mAlphaEnd = (WHITE | ALPHA_MASK);
    private int mRedEnd = (WHITE | RED_MASK);
    private int mGreenEnd = (WHITE | GREEN_MASK);
    private int mBlueEnd = (WHITE | BLUE_MASK);

    private float mAlphaInc;
    private float mRedInc;
    private float mGreenInc;
    private float mBlueInc;
}
