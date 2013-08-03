package com.ippie52.centurionphototimer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ippie52.centurionphototimer.R;
import com.ippie52.centurionphototimer.utils.FragmentFrame;
import com.ippie52.centurionphototimer.utils.ShotGlassView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link ShotGlassFragment.OnFragmentInteractionListener} interface to handle
 * interaction events.
 * 
 */
public class ShotGlassFragment extends Fragment {

    /**
     * Constructor
     * **/
    public ShotGlassFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shot_glass, container,
                false);
        FragmentFrame frame = (FragmentFrame) v
                .findViewById(R.id.shot_glass_frag_frame);
        if (frame != null) {
            frame.setColour(0x33552288);
        }
        mGlass = (ShotGlassView) v.findViewById(R.id.shot_glass_glass_view);
        if (mGlass != null) {
            mGlass.setProgressPixels(0);
        }

        return v;
    }

    /**
     * updateLevel - Method to update the level of the glass to a fill
     * percentage
     * 
     * @param aPercent
     *            The fill percentage
     * **/
    public void updateLevel(final float aPercent) {
        final int progress = (int) ((mGlass.getMaxPixels() * aPercent) / 100);
        mGlass.setProgressPixels(progress);
    }

    private ShotGlassView mGlass;

}
