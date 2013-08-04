package com.ippie52.centurionphototimer.fragments;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ippie52.centurionphototimer.R;
import com.ippie52.centurionphototimer.utils.ColourFader;
import com.ippie52.centurionphototimer.utils.FragmentFrame;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link TimeToNextFragment.OnFragmentInteractionListener} interface to handle
 * interaction events.
 * 
 */
public class TimeToNextFragment extends Fragment {

    /**
     * Blank Constructor
     * **/
    public TimeToNextFragment() {
    }

    /**
     * Overridden method
     * 
     * TODO Fill in the arguments here if needed
     * **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Overridden method to create the view for this fragment after setting up
     * components as necessary
     * **/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_time_to_next, container,
                false);
        FragmentFrame frame = (FragmentFrame) v
                .findViewById(R.id.time_to_next_frag_frame);
        if (frame != null) {
            frame.setColour(0x34aa4411);
        }

        Bundle args = getArguments();
        if (args != null) {
            mMaxTimeToNext = args.getInt(MAX_TIME_TO_NEXT_MILLISECONDS);
        } else {
            mMaxTimeToNext = (int) DEFAULT_TIME;
        }

        mTimeToNextText = (TextView) v
                .findViewById(R.id.time_to_next_text_view);
        if (mTimeToNextText != null) {
            mTimeToNextText.setText(mDf.format(new Date(DEFAULT_TIME)));
            mTimeToNextText.setTextSize(120f);
        }
        return v;
    }

    /**
     * Method to set the maximum time to be displayed
     * 
     * @param aMaxTime
     *            The maximum time to be displayed in milliseconds
     * **/
    public void setMaxTime(final int aMaxTimeMs) {
        mMaxTimeToNext = aMaxTimeMs;
    }

    /**
     * Method to set the time remaining in milliseconds
     * 
     * @param aTimeToNextMs
     *            The time remaining in milliseconds
     * **/
    public void setTimeRemaining(final long aTimeToNextMs) {
        mTimeToNextText.setText(mDf.format(aTimeToNextMs));
        float percent = (100f * aTimeToNextMs) / mMaxTimeToNext;
        mTimeToNextText.setTextColor(mFader.getColour(percent));
    }

    /**
     * Class Constants
     * **/
    private final static long DEFAULT_TIME = (60 * 1000);
    public final static String MAX_TIME_TO_NEXT_MILLISECONDS = "com.ippie52.centurionphototimer.timetonextfragment.max-time-to-next-milliseconds";
    private final static int START_COLOUR = Color.GREEN;
    private final static int END_COLOUR = Color.RED;
    private final static ColourFader mFader = new ColourFader(START_COLOUR,
            END_COLOUR);
    @SuppressLint("SimpleDateFormat")
    private final static SimpleDateFormat mDf = new SimpleDateFormat("ss:SSS");

    /**
     * Member Variables
     * **/
    private TextView mTimeToNextText;
    private int mMaxTimeToNext;

}
