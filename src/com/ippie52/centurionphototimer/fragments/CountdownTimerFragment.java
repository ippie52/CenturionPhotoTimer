package com.ippie52.centurionphototimer.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ippie52.errorhandler.ErrorHandler;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link CountdownTimerFragment.OnCountdownTimerListener} interface to handle
 * interaction events.
 * 
 */
public class CountdownTimerFragment extends Fragment {

    /**
     * Constructor
     * **/
    public CountdownTimerFragment() {
    }

    /**
     * attachListener - Method to attach an {@link OnCountdownTimerListener}
     * **/
    public void attachListener(OnCountdownTimerListener aListener) {
        try {
            mListeners.add(aListener);
        } catch (ClassCastException e) {
            new ErrorHandler(this, new ClassCastException(aListener.toString()
                    + " must implement OnCountdownTimerListener"));
        }
    }

    /**
     * cancel - Method to cancel the timers
     * **/
    public void cancel() {
        if (mInitialised && mRunning) {
            mMajorCdTimer.cancel();
            mMinorCdTimer.cancel();

            for (OnCountdownTimerListener l : mListeners) {
                l.onFinalTickEvent();
            }
            mRunning = false;
        }
    }

    /**
     * initialise - Method used to initialise the count down timer
     * 
     * @param aTotalMinutes
     *            The number of minutes the timer should run for
     * @param aMajorTickMs
     *            The number of milliseconds between each major tick event
     * @param aMinorTickMs
     *            The number of milliseconds between each minor tick event
     * 
     * @throws Exception
     * **/
    public boolean initialise(final int aTotalMinutes, final int aMajorTickMs,
            final int aMinorTickMs) throws Exception {
        boolean result = false;
        if (mRunning) {
            throw new Exception("Cannot initialise when running");
        } else {
            mTotalMinutes = aTotalMinutes;
            mMajorTickMs = aMajorTickMs;
            mMinorTickMs = aMinorTickMs;
            mInitialised = true;
            result = true;
        }
        return result;
    }

    /**
     * onAttach - Overridden method to attach an activity as a listener
     * **/
    @Override
    public void onAttach(Activity aActivity) {
        super.onAttach(aActivity);
        try {
            mListeners.add((OnCountdownTimerListener) aActivity);
        } catch (ClassCastException e) {
            new ErrorHandler(this, new ClassCastException(aActivity.toString()
                    + " must implement OnCountdownTimerListener"));
        }
    }

    /**
     * onCreateView - This fragment is not designed to have a view
     * **/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return null;
    }

    /**
     * onDetach - Overridden method used to detach from an activity
     * **/
    @Override
    public void onDetach() {
        super.onDetach();
        mListeners.clear();
    }

    /**
     * startTimer - Method used to begin the count down timer
     * 
     * @throws Exception
     * **/
    public boolean startTimer() throws Exception {
        boolean result = false;
        if (mInitialised) {
            if (mRunning) {
                throw new Exception("Error: Attempting to start whilst running");
            }
            final long duration = (SECONDS_PER_MINUTE * MILLISECONDS_PER_SECOND * mTotalMinutes);

            if (initialiseMajorTimer(duration, mMajorTickMs)) {
                mMajorCdTimer.start();
            }
            if (initialiseMinorTimer(duration, mMinorTickMs)) {
                mMinorCdTimer.start();
            }

            mRunning = true;
            result = true;
        }
        return result;
    }

    /**
     * initialiseMajorTimer - Initialise the major tick timer
     * 
     * @param aTickMs
     *            The tick increment time in milliseconds
     * **/
    private boolean initialiseMajorTimer(final long aDuration, final int aTickMs) {
        boolean result = (aTickMs != 0);
        if (result) {
            mMajorCdTimer = new CountDownTimer(aDuration, aTickMs) {

                @Override
                public void onFinish() {
                    for (OnCountdownTimerListener l : mListeners) {
                        l.onFinalTickEvent();
                    }
                }

                @Override
                public void onTick(long aMillisecondsRemaining) {
                    for (OnCountdownTimerListener l : mListeners) {
                        l.onMajorTickEvent(aMillisecondsRemaining);
                    }
                }
            };
        }
        return result;
    }

    /**
     * initialiseMinorTimer - Initialise the major tick timer
     * 
     * @param aTickMs
     *            The tick increment time in milliseconds
     * **/
    private boolean initialiseMinorTimer(final long aDuration, final int aTickMs) {
        boolean result = (aTickMs != 0);
        if (result) {
            mMinorCdTimer = new CountDownTimer(aDuration, aTickMs) {

                @Override
                public void onFinish() {
                    for (OnCountdownTimerListener l : mListeners) {
                        l.onFinalTickEvent();
                    }
                }

                @Override
                public void onTick(long aMillisecondsRemaining) {
                    for (OnCountdownTimerListener l : mListeners) {
                        l.onMinorTickEvent(aMillisecondsRemaining);
                    }
                }
            };
        }
        return result;
    }

    /**
     * OnCountdownTimerListener - Interface used to communicate events to
     * listeners
     * **/
    public interface OnCountdownTimerListener {
        /**
         * onFinalTickEvent - Interface method - used to inform the listener
         * that this is the last tick event to be received
         * **/
        public void onFinalTickEvent();

        /**
         * onMajorTickEvent - Interface method - used to inform the listener of
         * a major tick event, as desired in the initialise method
         * 
         * @param aMsRemaining
         *            The number of milliseconds remaining until the count down
         *            is over
         * **/
        public void onMajorTickEvent(final long aMsRemaining);

        /**
         * onMinorTickEvent - Interface method - used to inform the listener of
         * a minor tick event, as desired in the initialise method
         * 
         * @param aMsRemaining
         *            The number of milliseconds remaining until the count down
         *            is over
         * **/
        public void onMinorTickEvent(final long aMsRemaining);
    }

    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final int SECONDS_PER_MINUTE = 60;

    /**
     * Member variables
     * **/
    private final ArrayList<OnCountdownTimerListener> mListeners = new ArrayList<OnCountdownTimerListener>();
    private int mMajorTickMs;
    private int mMinorTickMs;
    private int mTotalMinutes;
    private boolean mInitialised = false;
    private boolean mRunning = false;
    private CountDownTimer mMajorCdTimer;
    private CountDownTimer mMinorCdTimer;
}
