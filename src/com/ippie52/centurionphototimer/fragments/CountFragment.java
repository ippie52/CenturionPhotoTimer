package com.ippie52.centurionphototimer.fragments;

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
 * {@link CountFragment.OnCountFragmentListener} interface to handle interaction
 * events.
 * 
 */
public class CountFragment extends Fragment {

    /**
     * Blank Constructor
     * **/
    public CountFragment() {
    }

    /**
     * Overridden method to initialise the fragment on creation
     * **/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mMax = args.getInt(MAX_COUNT_KEY);
        }
    }

    /**
     * Overridden method to create the view for this fragment
     * **/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_count, container, false);
        FragmentFrame frame = (FragmentFrame) v
                .findViewById(R.id.count_frag_frame);
        if (frame != null) {
            frame.setColour(0x5522AA77);
        }
        mCountTv = (TextView) v.findViewById(R.id.count_text_view);
        if (mCountTv != null) {
            mCountTv.setText(String.valueOf(mCount));
            mCountTv.setTextSize(120f);
        }
        return v;
    }

    /**
     * Method to set the count value to be shown
     * 
     * @param aCount
     *            The count to be displayed
     * **/
    public void setCount(final int aCount) {
        if (mCount != aCount) {
            mCount = aCount;
            mCountTv.setText(String.valueOf(mCount));
            final float percent = 100f * aCount / mMax;
            final int colour = mFader.getColour(percent);
            mCountTv.setTextColor(colour);
        }
    }

    /**
     * Method to obtain a correctly instantiated instance of the CountFragment
     * 
     * @param aMax
     *            The maximum count to display
     * 
     * @return An instantiated {@link CountFragment}
     * **/
    public static CountFragment newInstance(final int aMax) {
        CountFragment fragment = new CountFragment();
        Bundle args = new Bundle();
        args.putInt(MAX_COUNT_KEY, aMax);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Class Constants
     * **/
    private final static int END_COLOUR = 0xFFFF0000;
    private final static int START_COLOUR = 0XFF00FF00;
    private final static int DEFAULT_MAX_COUNT = 100;
    private final static String MAX_COUNT_KEY = "com.ippie52.centurionphototimer.countfragment.max-count";

    /**
     * Member Variables
     * **/
    private int mMax = DEFAULT_MAX_COUNT;
    private int mCount = 0;
    private TextView mCountTv;
    private final ColourFader mFader = new ColourFader(START_COLOUR, END_COLOUR);

}
