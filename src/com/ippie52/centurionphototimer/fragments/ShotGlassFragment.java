package com.ippie52.centurionphototimer.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

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

    /**
     * onAttach - Method checks that activity being attached implements the
     * {@link OnShotGlassFragmentListener} interface
     * **/
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnShotGlassFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnShotGlassFragmentListener");
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        Button b = (Button) v.findViewById(R.id.shot_glass_update_value_btn);
        if (b != null) {
            b.setText("Click me!");
            b.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (mGoingUp) {
                        int delta = ((mProgress + mGlass.getMaxPixels()) / 2)
                                - mProgress;
                        mProgress += delta == 0 ? 1 : delta;

                        if (mProgress == mGlass.getMaxPixels()) {
                            mGoingUp = !mGoingUp;
                        }

                    } else {
                        mProgress /= 2;

                        if (mProgress == 0) {
                            mGoingUp = !mGoingUp;
                        }

                    }
                    mGlass.setProgressPixels(mProgress);
                }
            });
        }
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated to
     * the activity and potentially other fragments contained in that activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnShotGlassFragmentListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private ShotGlassView mGlass;
    private OnShotGlassFragmentListener mListener;
    private int mProgress = 0;
    private boolean mGoingUp = true;

}
